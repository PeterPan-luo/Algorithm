package com.javase.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import javax.swing.tree.DefaultTreeCellEditor.EditorContainer;

/**
 * 邻接矩阵表示的"无向图(List Undirected Graph)"
 * @author Administrator
 *
 */
public class MatrixUDG {

	private char[] mVexs;//顶点集合
	private int[][] mMatrix;//邻接矩阵
	
	/**
	 * 创建图（用已提供的矩阵）
	 * @param vexs -- 顶点数组
	 * @param edgs -- 边数组
	 */
	public MatrixUDG(char[] vexs, char[][] edgs) {
		// 初始化"顶点数"和"边数"
		int vlen = vexs.length;
		int elen = edgs.length;
		// 初始化"顶点"
		mVexs = new char[vlen];
		for(int i = 0; i < vlen; i++)
			mVexs[i] = vexs[i];
		// 初始化"边"
		mMatrix = new int[vlen][vlen];
		for(int i = 0; i < elen; i++){
			// 读取边的起始顶点和结束顶点
			int p1 = getPosition(edgs[i][0]);
			int p2 = getPosition(edgs[i][1]);
			
			mMatrix[p1][p2] = 1;
			mMatrix[p2][p1] = 1;
		}
		
	}
	/**
	 * 创建图（用已提供的矩阵）
	 * @param vexs -- 顶点数组
	 * @param matrix -- 矩阵（数据）
	 */
	public MatrixUDG(char[] vexs, int[][] matrix) {
		int vlen = vexs.length;
		mVexs = new char[vlen];
		for(int i = 0; i < vlen; i++)
			mVexs[i] = vexs[i];
		//初始化边同时统计边数
		mEdgeNum = 0;
		mMatrix = new int[vlen][vlen];
		for(int i = 0; i < vlen; i++)
			for(int j = 0; j < vlen; j++){
				mMatrix[i][j] = matrix[i][j];
				if(mMatrix[i][j] != INF)
					mEdgeNum++;
			}
		
	}
	//返回ch的位置
	private int getPosition(char ch) {
		for (int i = 0; i < mVexs.length; i++) {
			if(ch == mVexs[i])
				return i;
		}
		return -1;
	}
	 /*
     * 打印矩阵队列图
     */
    public void print() {
        System.out.printf("Martix Graph:\n");
        for (int i = 0; i < mVexs.length; i++) {
            for (int j = 0; j < mVexs.length; j++)
                System.out.printf("%d ", mMatrix[i][j]);
            System.out.printf("\n");
        }
    }
    
    public static void main(String[] args) {
//        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
//        char[][] edges = new char[][]{
//            {'A', 'C'}, 
//            {'A', 'D'}, 
//            {'A', 'F'}, 
//            {'B', 'C'}, 
//            {'C', 'D'}, 
//            {'E', 'G'}, 
//            {'F', 'G'}};
//        MatrixUDG pG;
//        pG = new MatrixUDG(vexs, edges);
//
//        pG.print();   // 打印图
//        System.out.println("深度优先遍历================");
//        pG.DFS();  //深度优先遍历
//        System.out.println();
//        System.out.println("广度优先遍历===================");
//        pG.BFS();
        
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int matrix[][] = {
                 /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
          /*A*/ {   0,  12, INF, INF, INF,  16,  14},
          /*B*/ {  12,   0,  10, INF, INF,   7, INF},
          /*C*/ { INF,  10,   0,   3,   5,   6, INF},
          /*D*/ { INF, INF,   3,   0,   4, INF, INF},
          /*E*/ { INF, INF,   5,   4,   0,   2,   8},
          /*F*/ {  16,   7,   6, INF,   2,   0,   9},
          /*G*/ {  14, INF, INF, INF,   8,   9,   0}};
        MatrixUDG pG;

        // 自定义"图"(输入矩阵队列)
        //pG = new MatrixUDG();
        // 采用已有的"图"
        pG = new MatrixUDG(vexs, matrix);

        //pG.print();   // 打印图
        //pG.DFS();     // 深度优先遍历
        //pG.BFS();     // 广度优先遍历
        //pG.prim(0);   // prim算法生成最小生成树
        EData[] rets = pG.kruskal();   // Kruskal算法生成最小生成树
        pG.printKruskal(rets);
        
        List<EData> retsList = pG.prim(0); //// prim算法生成最小生成树
        System.out.println();
        pG.printPrim(retsList);
    }
    //=================深度优先搜索=======================================
    /**
     * 获取节点v的未访问过的相邻节点
     * @param v -- 节点
     * @param visited --访问数组
     * @return -1：表示没有
     */
    private int  getAdjUnvisitedVertex(int v, boolean[] visited) {
    	for (int i = 0; i < mVexs.length; i++) {
			if(mMatrix[v][i] == 1 && visited[i] == false)
				return i;
		}
		return -1;
	}
    
    private void showVertex(char ch) {
		System.out.printf("%c ", ch);
	}
    /**
     * 深度优先搜索：利用栈实现
     */
    public void DFS() {
    	boolean[] visited = new boolean[mVexs.length]; //顶点访问标志
    	for (int i = 0; i < visited.length; i++) {
			visited[i] = false;
		}
    	//节点0入栈，从节点0开始访问
		Stack<Character> gStack = new Stack<>();
		showVertex(mVexs[0]);
		visited[0] = true;
		gStack.push(mVexs[0]);
		int v;
		while (!gStack.empty()) {
			v = getAdjUnvisitedVertex(getPosition(gStack.peek()), visited);
			if(v == -1)
				gStack.pop();  //最顶上的节点没有了相邻节点，则该节点出栈，从下个节点开始遍历
			else {
				//如果有相邻节点，则访问该节点，并将该节点入栈，下次从该节点开始访问
				showVertex(mVexs[v]);
				visited[v] = true;
				gStack.push(mVexs[v]);
			}
			
		}
	}
    //=====================广度优先搜索===================================
    /**
     * 广度优先搜索，利用队列实现
     */
    public void BFS() {
		boolean[] visited = new boolean[mVexs.length]; //顶点访问标志
		for (int i = 0; i < visited.length; i++) {
			visited[i] = false;
		}
		//节点0入队列，从节点0开始访问
		Queue<Character> gQueue = new LinkedList<>();
		showVertex(mVexs[0]);
		visited[0] = true;
		gQueue.offer(mVexs[0]);
		int iVert1, iVert2;
		while (!gQueue.isEmpty()) {
			//从队列中取出数据，访问与该点相邻的所有节点
			iVert1 = getPosition(gQueue.poll());
			iVert2 = getAdjUnvisitedVertex(iVert1, visited);
			//访问所有与iVert1相邻的节点
			while (iVert2 != -1) {
				showVertex(mVexs[iVert2]);
				visited[iVert2] = true;
				gQueue.offer(mVexs[iVert2]);
				iVert2 = getAdjUnvisitedVertex(iVert1, visited);
			}
		}
	}
    //==========================克鲁斯卡尔（Kruskal)最小生成树===============================
    /**
     * 边的结构体
     */
    private class EData{
    	int weight; //权值
    	char start;  //边的起点
    	char end;  //边的终点
    	public EData(char start, char end, int weight) {
    		this.start = start;
    		this.end = end;
    		this.weight = weight;
    	}
    }
    private int mEdgeNum;  //边的数量
    private static final int INF = Integer.MAX_VALUE; //最大值
    /**
     * 克鲁斯卡尔（Kruskal)最小生成树
     */
    public EData[] kruskal() {
		int index = 0;   //
		int[] vends = new int[mEdgeNum];  //用于保存"已有最小生成树"中每个顶点在该最小树中的终点。
		EData[] rets = new EData[mEdgeNum]; //结果数组，保存kruskal最小生成树的边
		EData[] edges;  //图对应的所有边
		
		//获取"图中的所有边"
		edges = getEdges();
		//将边按照"权"的大小进行排序(从小到大)
		sortEdges(edges, mEdgeNum);
		for(int i = 0; i < mEdgeNum; i++){
			int p1 = getPosition(edges[i].start); //获取第i条边的"起点"的序号
			int p2 = getPosition(edges[i].end); // 获取第i条边的"终点"的序号
			
			int m = getEnd(vends, p1); //获取p1在"已有的最小生成树"中的终点
			int n = getEnd(vends, p2); //获取p2在"已有的最小生成树"中的终点
			// 如果m!=n，意味着"边i"与"已经添加到最小生成树中的顶点"没有形成环路
			if (m != n) {
				rets[index++] = edges[i];
				vends[m] = n;  //设置m在"已有的最小生成树"中的终点为n
			}
		}
		return rets;
	}
    /**
     * 获取i的终点
     * @param vends -- 保存"已有最小生成树"中每个顶点在该最小树中的终点。
     * @param i
     * @return
     */
    private int getEnd(int[] vends, int i) {
		while (vends[i] != 0) {
			i = vends[i];
		}
		return i;
	}
	/**利用冒泡法对所有的边按照权值
     * 从小到大排序
     * @param edges  -- 图中的所有的边
     * @param mEdgeNum  -- 图中边的数量
     */
	private void sortEdges(EData[] edges, int mEdgeNum) {
		EData temp;
		for (int i = 0; i < mEdgeNum; i++) {
			for(int k = 0; k < mEdgeNum - 1 - i; k++){
				if (edges[k].weight > edges[k+1].weight) {
					temp = edges[k];
					edges[k] = edges[k+1];
					edges[k+1] = temp;
				}
			}
		}
	}
	/**
	 * 获取图中的 所有边
	 * @return
	 */
	private EData[] getEdges() {
		int index = 0;
		EData[] edges = new EData[mEdgeNum];
		for (int row = 0; row < mVexs.length; row++) {
			for (int col = 0; col < mVexs.length; col++) {
				if (mMatrix[row][col] != INF) {
					//是一条边
					EData edge = new EData(mVexs[row], mVexs[col], mMatrix[row][col]);
					edges[index++] = edge;
				}
			}
		}
		return edges;
	}
	
    public void printKruskal(EData[] rets) {
    	 // 统计并打印"kruskal最小生成树"的信息
        int length = 0;
        for (int i = 0; i < rets.length; i++) {
			if(rets[i] != null)
				length += rets[i].weight;
		}
        System.out.printf("Kruskal=%d: ", length);
        for (int i = 0; i < rets.length; i++) {
			if(rets[i] != null)
				System.out.printf("(%c,%c) ", rets[i].start, rets[i].end);
		}
        System.out.printf("\n");
	}
    //=========================普里姆(Prim)算法===========================
    /**
     * 普里姆(Prim)算法，是用来求加权连通图的最小生成树的算法。
     */
    public List<EData> prim(int start) {
		if(start < 0 || start >= mVexs.length)
			throw new IndexOutOfBoundsException();
		boolean[] visited = new boolean[mVexs.length];  //访问顶点标志
		List<EData> rets = new ArrayList<>(); //结果数组
		
		for(int i = 0; i < visited.length; i++)
			visited[i] = false;
		Stack<Character> stack = new Stack<>();
		//开始节点入栈
		stack.push(mVexs[start]);
		//showVertex(mVexs[start]);
		visited[start] = true;
		//深度搜索进行遍历
		int vert;
		while (!stack.isEmpty()) {
			vert = getAdjUnvisitedMinVertex(getPosition(stack.peek()), visited);
			if(vert == -1) //没有相邻的节点，则出栈
				stack.pop();
			else {
				//有相邻的节点，访问该节点，并入栈
				showVertex(stack.peek());
				System.out.print("-->");
				showVertex(mVexs[vert]);
				rets.add(new EData(stack.peek(), mVexs[vert], 
						mMatrix[getPosition(stack.peek())][getPosition(mVexs[vert])]));
				visited[vert] = true;
				stack.push(mVexs[vert]);
			}
		}
		return rets;
	}
    /**
     * 获取节点v的未访问过的最小权值相邻节点
     * @param v -- 节点
     * @param visited --访问数组
     * @return -1：表示没有
     */
    private int  getAdjUnvisitedMinVertex(int v, boolean[] visited) {
    	int minWeight = INF;
    	int index = -1;
    	for (int i = 0; i < mVexs.length; i++) {
			if(mMatrix[v][i] != INF  && visited[i] == false)
			{
				if (minWeight > mMatrix[v][i]) {
					minWeight = mMatrix[v][i];
					index = i;
				}
			}
		}
		return index;
	}
    public void printPrim(List<EData> rets) {
   	 // 统计并打印"kruskal最小生成树"的信息
       int length = 0;
       for (int i = 0; i < rets.size(); i++) {
			if(rets.get(i) != null)
				length += rets.get(i).weight;
		}
       System.out.printf("Prim=%d: ", length);
       for (int i = 0; i < rets.size(); i++) {
			if(rets.get(i) != null)
				System.out.printf("(%c,%c) ", rets.get(i).start, rets.get(i).end);
		}
       System.out.printf("\n");
	}
}
