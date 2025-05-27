package com.example.wanandroid.test;

import java.util.LinkedList;

public class GraphBFS {
    public int V = 0;//顶点数量
    public LinkedList<Integer>[] adj;//邻接表

    public GraphBFS(int v) {
        this.V = v;
        adj = new LinkedList[v];

        for (int i = 0; i < v; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    void addEdge(int s,int t){
        adj[s].add(t);
    }


}
