package de.himbiss.ld35.world;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oneidavar on 16/04/2016.
 */
public class Delauney_Graph {


    public static List<Graph_Edge> delauney(List<RoomStrukt> roomlist){
        List<Graph_Edge> graph = new ArrayList<Graph_Edge>();
        int N = roomlist.size();
        for(int i = 0; i < N; i++){
            for(int j = i+1; j<N;j++) {
                for (int k = j + 1; k < N; k++) {
                    boolean triangle = true;
                    for (int a = 0; a < N; a++) {
                        if (a == i || a == j || a == k) continue;
                        if(inside(roomlist.get(a),roomlist.get(i),roomlist.get(j),roomlist.get(k))){
                            triangle = false;
                            break;
                        }
                    }
                    if(triangle){
                        myAdd(graph,i,j,roomlist);
                        myAdd(graph,i,k,roomlist);
                        myAdd(graph,j,k,roomlist);
                    }
                }
            }
        }
        return graph;
    }

    public static void myAdd(List <Graph_Edge> list,int a, int b, List<RoomStrukt> roomlist){
        Graph_Edge e = new Graph_Edge(a,b);
        for (Graph_Edge ed:list) {
            if(same(e,ed)) {
                return;
            }
        }
        List<Graph_Edge> deletelist = new ArrayList<>();


        for (Graph_Edge ed:list) {
            if(scheiden(e,ed,roomlist)) {
                //System.out.println(dist_square(e,roomlist) + " - " + dist_square(ed,roomlist));
                if(dist_square(e,roomlist) < dist_square(ed,roomlist)){
                    deletelist.add(ed);
                }
            }
        }

        for(Graph_Edge ed:deletelist){
            if(stillconnected(list,ed,roomlist)) list.remove(ed);
        }

        list.add(e);


    }

    public static boolean stillconnected(List<Graph_Edge> list, Graph_Edge e, List<RoomStrukt> roomlist){
        boolean k1 = false;
        boolean k2 = false;
        for(Graph_Edge ed:list) {
            if (!same(e, ed)) {
                if(e.p1 == ed.p1 || e.p1 == ed.p2){
                    if(dist_square(e,roomlist) >= dist_square(ed,roomlist)) {
                        k1 = true;
                        break;
                    }
                }
            }
        }
        for(Graph_Edge ed:list) {
            if (!same(e, ed)) {
                if(e.p2 == ed.p1 || e.p2 == ed.p2){
                    if(dist_square(e,roomlist) >= dist_square(ed,roomlist)) {
                        k2 = true;
                        break;
                    }
                }
            }
        }
        return k1 && k2;
    }

    public static boolean same(Graph_Edge e1, Graph_Edge e2){
        if(e1.p1 == e2.p1 && e1.p2 == e2.p2) return true;
        if(e1.p2 == e2.p1 && e1.p1 == e2.p2) return true;
        return false;
    }

    public static boolean inside(RoomStrukt r1,RoomStrukt r2,RoomStrukt r3,RoomStrukt r4){

        Point p1 = new Point();
        p1.setLocation(r1.midX(), r1.midY());
        Point p2 = new Point();
        p2.setLocation(r1.midX(), r2.midY());
        Point p3 = new Point();
        p3.setLocation(r1.midX(), r3.midY());
        Point p4 = new Point();
        p4.setLocation(r1.midX(), r4.midY());
        Polygon poly = new Polygon();
        poly.addPoint(p2.x, p2.y);
        poly.addPoint(p3.x, p3.y);
        poly.addPoint(p4.x, p4.y);

        return poly.contains(p1);


/*
        int x1 = r2.midX(), y1 = r2.midY();
        int x2 = r3.midX(), y2 = r3.midY();
        int x3 = r4.midX(), y3 = r3.midY();
        int x = r1.midX(), y = r1.midY();

        double ABC = Math.abs (x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2));
        double ABP = Math.abs (x1 * (y2 - y) + x2 * (y - y1) + x * (y1 - y2));
        double APC = Math.abs (x1 * (y - y3) + x * (y3 - y1) + x3 * (y1 - y));
        double PBC = Math.abs (x * (y2 - y3) + x2 * (y3 - y) + x3 * (y - y2));

        return ABP + APC + PBC == ABC;
        */
    }

    public static boolean scheiden(Graph_Edge e1, Graph_Edge e2, List<RoomStrukt> list){
        Line2D line1 = new Line2D.Float(list.get(e1.p1).midX(),list.get(e1.p1).midY(),list.get(e1.p2).midX(),list.get(e1.p2).midY());
        Line2D line2 = new Line2D.Float(list.get(e2.p1).midX(),list.get(e2.p1).midY(),list.get(e2.p2).midX(),list.get(e2.p2).midY());
        return line1.intersectsLine(line2);
    }
    private static int dist_square(Graph_Edge e, List<RoomStrukt> roomStruktList){
        RoomStrukt r1 = roomStruktList.get(e.p1);
        RoomStrukt r2 = roomStruktList.get(e.p2);
        return  (int)Math.sqrt((r1.midX()-r2.midX())*(r1.midX()-r2.midX())+(r1.midY()-r2.midY())*(r1.midY()-r2.midY()));
    }
}
