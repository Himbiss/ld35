package de.himbiss.ld35.world;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oneidavar on 16/04/2016.
 */
public class MinSpannTree {

    private static int weight;
    private static List<Graph_Edge> mst;
    private static boolean[] marked;
    private static List<Graph_Edge> pq;

    public static List<Graph_Edge> span(List<RoomStrukt> roomlist){
        List<Graph_Edge> graph_edgeList = new ArrayList<Graph_Edge>();

        mst = new ArrayList<Graph_Edge>();
        pq = new ArrayList<Graph_Edge>();
        marked = new boolean[roomlist.size()];
        for(int v = 0; v<roomlist.size(); v++)
            if(!marked[v]) prim(roomlist, v);

        return graph_edgeList;
    }

    private static void prim(List <RoomStrukt> roomlist, int v){
        scan(roomlist,v);
        while(!pq.isEmpty()){
            Graph_Edge e = remMin(roomlist);
            if(marked[e.p1] && marked[e.p2]) continue;
            mst.add(e);
            if(!marked[e.p1]) scan(roomlist,e.p1);
            if(!marked[e.p2]) scan(roomlist,e.p2);
        }
    }

    private static void scan(List <RoomStrukt> roomlist, int v){
        marked[v] = true;
        //for(Graph_Edge e:)
    }

    private static Graph_Edge remMin(List<RoomStrukt> list){
        Graph_Edge e = pq.get(0);
        for(Graph_Edge ge:pq) {
            if (dist_square(ge, list) < dist_square(e, list)) e = ge;
        }
        pq.remove(e);
        return e;
    }

    public static int dist_square(Graph_Edge e, List<RoomStrukt> roomStruktList){
        RoomStrukt r1 = roomStruktList.get(e.p1);
        RoomStrukt r2 = roomStruktList.get(e.p2);
        return (r1.midX()-r2.midX())^2+(r1.midY()-r2.midY())^2;
    }

}
