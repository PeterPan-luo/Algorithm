package com.javase.graph;

import java.util.Stack;

/**
 * 邻接矩阵有向图
 * @author Administrator
 *
 */
public class MatrixDG {
	private char[] mVexs;//顶点集合
	private int[][] mMatrix;//邻接矩阵
	
	/**
	 * 创建图（用已提供的矩阵）
	 * @param vexs -- 顶点数组
	 * @param edgs -- 边数组
	 */
	public MatrixDG(char[] vexs, char[][] edgs) {
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
        char[] vexs = {'A', 'B', 'C', 'D'};
        char[][] edges = new char[][]{
            {'A', 'B'}, 
            {'B', 'C'}, 
            {'A', 'D'}};
    	
        MatrixDG pG;

        // 自定义"图"(输入矩阵队列)
        //pG = new MatrixUDG();
        // 采用已有的"图"
        pG = new MatrixDG(vexs, edges);

        pG.print();   // 打印图
        System.out.println("拓扑排序");
        pG.topoSort();
    }
    //========================拓扑排序===================================
    /**
     * 是否有后继节点
     * @param count -- 数组中有节点数目
     * @return -1:有后继节点；否则返回没有后继节点的节点
     */
    public int  noSuccessors(int count) {
		boolean isEdge;
		for (int row = 0; row < count; row++) {
			isEdge = false;
			for (int col = 0; col < count; col++) {
				if(mMatrix[row][col] > 0){
					isEdge = true;
					break;
				}
			}
			if(!isEdge)
				return row;
		}
		return -1;
	}
    /**
     * 向上移一行
     * @param row
     * @param length
     */
    private void moveRow(int row, int length) {
		for (int col = 0; col < length; col++) {
			mMatrix[row][col] = mMatrix[row+1][col];
		}
	}
    /**
     * 向左移一列
     * @param col
     * @param length
     */
    private void moveCol(int col, int length) {
		for (int row = 0; row < length; row++) {
			mMatrix[row][col] = mMatrix[row][col+1];
		}
	}
    /**
     * 删除一个节点并修正邻接矩阵
     * @param vert -- 要删除的节点
     * @param count -- 数组中剩余的节点数量
     */
    public void delVertex(int vert,int count) {
    	//删除顶点集合的顶点
		for(int i = vert; i < count-1; i++)
			mVexs[i] = mVexs[i+1];
		for(int row=vert; row < count -1; row++)
			moveRow(row, count);
		for(int col = vert; col < count -1; col++)
			moveCol(col, count);
	}
    /**
     * 拓扑排序
     */
    public void topoSort() {
		Stack<Character> gStack = new Stack<>();
		int ivert;
		int count = mVexs.length;
		while (count > 0) {
			ivert = noSuccessors(count);
			if(ivert == -1)
				System.out.println("Error: graph has cycles.");
			gStack.push(mVexs[ivert]);
			delVertex(ivert, count);
			count--;
		}
		while (!gStack.isEmpty()) 
			System.out.printf("%c ", gStack.pop());
	}
}
