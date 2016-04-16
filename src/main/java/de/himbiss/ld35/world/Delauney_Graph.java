package de.himbiss.ld35.world;

import java.awt.*;
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
                        myAdd(graph,i,j);
                        myAdd(graph,i,k);
                        myAdd(graph,j,k);
                    }
                }
            }
        }
        return graph;
    }

    public static void myAdd(List <Graph_Edge> list,int a, int b){
        Graph_Edge e = new Graph_Edge(a,b);
        for (Graph_Edge ed:list) {
            if(same(e,ed)) return;
        }
        list.add(e);
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
    }
}
