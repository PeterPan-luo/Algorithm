package com.javase.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Java: 邻接表表示的"无向图(List Undirected Graph)"
 * @author Administrator
 */
public class ListUDG {

	// 邻接表中表对应的链表的顶点
	private class ENode{
		int ivex; //该边所指向的顶点的位置
		int weight; //权值
		ENode nextEdge; // 指向下一条弧的指针
	}
	// 邻接表中表的顶点
	private class VNode{
		char data;  //顶点信息
		ENode firstEdge;  // 指向第一条依附该顶点的弧
	}
	private static final int INF = Integer.MAX_VALUE;
	private VNode[] mVexs; //顶点数组
	
	/**
	 * 创建图(用已提供的矩阵)
	 * @param vexs -- 顶点数组
	 * @param edgs -- 边数组
	 */
	public ListUDG(char[] vexs, char[][] edgs) {
		int vlen = vexs.length;
		int elen = edgs.length;
		
		//初始化顶点数组
		mVexs = new VNode[vlen];
		for(int i = 0; i < vlen; i++){
			mVexs[i] = new VNode();
			mVexs[i].data = vexs[i];
			mVexs[i].firstEdge = null;
		}
		//初始化边
		for(int i = 0; i < elen; i ++){
			int p1 = getPosition(edgs[i][0]);
			int p2 = getPosition(edgs[i][1]);
			// 初始化node1
			ENode node1 = new ENode();
			node1.ivex = p1;
			if(mVexs[p2].firstEdge == null)
				mVexs[p2].firstEdge = node1;
			else
				linkLast(mVexs[p2].firstEdge, node1);
			//初始化node2
			ENode node2 = new ENode();
			node2.ivex = p2;
			if(mVexs[p1].firstEdge == null)
				mVexs[p1].firstEdge = node2;
			else
				linkLast(mVexs[p1].firstEdge, node2);
		}
		
	}
	/**
	 * 将node节点链接到list的最后
	 * @param list
	 * @param node1
	 */
	private void linkLast(ENode list, ENode node) {
		ENode pNode = list;
		while (pNode.nextEdge != null) {
			pNode = pNode.nextEdge;
		}
		pNode.nextEdge = node;
	}
	//返回ch的位置
	private int getPosition(char ch) {
		for (int i = 0; i < mVexs.length; i++) {
			if(ch == mVexs[i].data)
				return i;
		}
		return -1;
	}
	/*
     * 打印矩阵队列图
     */
    public void print() {
        System.out.printf("List Graph:\n");
        for (int i = 0; i < mVexs.length; i++) {
            System.out.printf("%d(%c): ", i, mVexs[i].data);
            ENode node = mVexs[i].firstEdge;
            while (node != null) {
                System.out.printf("%d(%c) ", node.ivex, mVexs[node.ivex].data);
                node = node.nextEdge;
            }
            System.out.printf("\n");
        }
    }

    public static void main(String[] args) {
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        char[][] edges = new char[][]{
            {'A', 'C'}, 
            {'A', 'D'}, 
            {'A', 'F'}, 
            {'B', 'C'}, 
            {'C', 'D'}, 
            {'E', 'G'}, 
            {'F', 'G'}};
        ListUDG pG;

        // 自定义"图"(输入矩阵队列)
        //pG = new ListUDG();
        // 采用已有的"图"
        pG = new ListUDG(vexs, edges);

        pG.print();   // 打印图
        System.out.println("深度优先遍历================");
        pG.DFS();  //深度优先遍历
        System.out.println();
        System.out.println("广度优先遍历===================");
        pG.BFS();
    }
    
    //==========================深度优先搜索========================
    private void showVertex(char ch) {
		System.out.printf("%c ",ch);
	}
    /**
     * 获取节点v的相邻为未访问过的节点
     * @param v  -- 节点v的数组下表
     * @param visited  -- 顶点访问标志数组
     * @return -1：访问失败
     */
    private int getAdjUnvisitedVertex(int v, boolean[] visited) {
    	if(mVexs[v].firstEdge == null)
    		return -1;
    	ENode pNode = mVexs[v].firstEdge;
    	while (pNode != null) {
			if(visited[pNode.ivex] == false)
				return pNode.ivex;
			pNode = pNode.nextEdge;
		}
		return -1;
	}
    public void DFS() {
		boolean[] visited = new boolean[mVexs.length];//顶点访问标志
		for (int i = 0; i < visited.length; i++) {
			visited[i] = false;
		}
		//节点0入栈，从节点0开始访问
		Stack<Character> gStack = new Stack<>();
		showVertex(mVexs[0].data);
		visited[0] = true;
		gStack.push(mVexs[0].data);
		int ivertex;
		while (!gStack.isEmpty()) {
			ivertex = getAdjUnvisitedVertex(getPosition(gStack.peek()), visited);
			if(ivertex == -1)
				gStack.pop();//最顶上的节点没有了相邻节点，则该节点出栈，从下个节点开始遍历
			else {
				//如果有相邻节点，则访问该节点，并将该节点入栈，下次从该节点开始访问
				showVertex(mVexs[ivertex].data);
				visited[ivertex] = true;
				gStack.push(mVexs[ivertex].data);
			}
		}
	}
    //========================广度优先搜索===================================
    public void BFS() {
		boolean[] visited = new boolean[mVexs.length];//顶点访问标志
		for (int i = 0; i < visited.length; i++) {
			visited[i] = false;
		}
		//节点0入队列，从节点0开始访问
		Queue<Character> gQueue = new LinkedList<Character>();
		showVertex(mVexs[0].data);
		visited[0] = true;
		gQueue.offer(mVexs[0].data);
		int ivert1, ivert2;
		while (!gQueue.isEmpty()) {
			//从队列中取出数据，访问与该点相邻的所有节点
			ivert1 = getPosition(gQueue.poll());
			ivert2 = getAdjUnvisitedVertex(ivert1, visited);
			//访问所有与iVert1相邻的节点
			while (ivert2 != -1) {
				showVertex(mVexs[ivert2].data);
				visited[ivert2] = true;
				gQueue.offer(mVexs[ivert2].data);
				ivert2 = getAdjUnvisitedVertex(ivert1, visited);
			}
		}
	}
    //====================克鲁斯卡尔（Kruskal)最小生成树========================
    private class EData{
    	char start;
    	char end;
    	int weight;
    	public EData(char start, char end, int weight) {
			this.start = start;
			this.end = end;
			this.weight = weight;
		}
    }
    //获取所有的边
    public List<EData> getEdgs() {
		List<EData> rets = new ArrayList<EData>();
		for (int i = 0; i < mVexs.length; i++) {
			ENode pNode = mVexs[i].firstEdge;
			while (pNode != null) {
				EData edge = new EData(mVexs[i].data, mVexs[pNode.ivex].data, pNode.weight);
				rets.add(edge);
				pNode = pNode.nextEdge;
			}
		}
		return rets;
	}
    //====================普里姆(Prim)算法====================================
    /**
     * 获取节点v的未访问过的最小权值相邻节点
     * @param v  -- 节点v的数组下表
     * @param visited  -- 顶点访问标志数组
     * @return -1：访问失败
     */
    private int getAdjUnvisitedMinVertex(int v, boolean[] visited) {
    	if(mVexs[v].firstEdge == null)
    		return -1;
    	int minWeight = INF;
    	int index = -1;
    	ENode pNode = mVexs[v].firstEdge;
    	while (pNode != null) {
			if(visited[pNode.ivex] == false)
			{//为访问 过的相邻的节点
				if (minWeight > pNode.weight) {
					minWeight = pNode.weight;
					index = pNode.ivex;
				}
			}
			pNode = pNode.nextEdge;
		}
		return index;
	}
}
