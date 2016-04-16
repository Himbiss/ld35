package de.himbiss.ld35.world;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * Created by Oneidavar on 16/04/2016.
 */
public class MinSpannTree {

    private static int weight;
    private static List<Graph_Edge> mst;
    private static boolean[] marked;
    private static List<Graph_Edge> pq;
    private static List<Graph_Edge> delauney;

    public static List<Graph_Edge> span(List<RoomStrukt> roomlist){
        List<Graph_Edge> graph_edgeList = new ArrayList<Graph_Edge>();

        mst = new ArrayList<Graph_Edge>();
        pq = new ArrayList<Graph_Edge>();
        marked = new boolean[roomlist.size()];
        delauney = Delauney_Graph.delauney(roomlist);

        for(int v = 0; v<roomlist.size(); v++)
            if(!marked[v]) prim(roomlist, v);
        

        return mst;
        //return delauney;
    }

    private static void prim(List <RoomStrukt> roomlist, int v){
        scan(v);
        while(!pq.isEmpty()){
            Graph_Edge e = remMin(roomlist);
            if(marked[e.p1] && marked[e.p2]) continue;
            mst.add(e);
            if(!marked[e.p1]) scan(e.p1);
            if(!marked[e.p2]) scan(e.p2);
        }
    }

    private static void scan(int v){
        if(marked[v]) System.out.println("!!!!!!!MARKED!!!!!!!!!");
        marked[v] = true;
        for(Graph_Edge e:adj(v)){
            int p = e.p1;
            if(p == v) p = e.p2;
            if(!marked[p]) pq.add(e);
        }
    }

    private static Graph_Edge remMin(List<RoomStrukt> list){
        Graph_Edge e = pq.get(0);
        for(Graph_Edge ge:pq) {
            if (dist_square(ge, list) < dist_square(e, list)) e = ge;
        }
        pq.remove(e);
        return e;
    }

    private static int dist_square(Graph_Edge e, List<RoomStrukt> roomStruktList){
        RoomStrukt r1 = roomStruktList.get(e.p1);
        RoomStrukt r2 = roomStruktList.get(e.p2);
        return  (int)Math.sqrt((r1.midX()-r2.midX())*(r1.midX()-r2.midX())+(r1.midY()-r2.midY())*(r1.midY()-r2.midY()));
    }

    private static List<Graph_Edge> adj(int i){
        List<Graph_Edge> list = new ArrayList<>();
        for (Graph_Edge e:delauney) {
            if(e.p1 == i || e.p2 == i) list.add(e);
        }
        return list;
    }
    public static boolean scheiden(Graph_Edge e1, Graph_Edge e2, List<RoomStrukt> list){
        Line2D line1 = new Line2D.Float(list.get(e1.p1).midX(),list.get(e1.p1).midY(),list.get(e1.p2).midX(),list.get(e1.p2).midY());
        Line2D line2 = new Line2D.Float(list.get(e2.p1).midX(),list.get(e2.p1).midY(),list.get(e2.p2).midX(),list.get(e2.p2).midY());
        return line1.intersectsLine(line2);
    }
}
