package com.javase.graph;

/**
 * 有向邻接表
 * @author Administrator
 *
 */
public class ListDG {
	// 邻接表中表对应的链表的顶点
		private class ENode{
			int ivex; //该边所指向的顶点的位置
			ENode nextEdge; // 指向下一条弧的指针
		}
		// 邻接表中表的顶点
		private class VNode{
			char data;  //顶点信息
			ENode firstEdge;  // 指向第一条依附该顶点的弧
		}
		
		private VNode[] mVexs; //顶点数组
		
		/**
		 * 创建图(用已提供的矩阵)
		 * @param vexs -- 顶点数组
		 * @param edgs -- 边数组
		 */
		public ListDG(char[] vexs, char[][] edgs) {
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
	    }
}
