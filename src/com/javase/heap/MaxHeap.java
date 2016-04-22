package com.javase.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 最大堆：父节点的键值总是大于或等于任意一个子节点的键值
 * @author Administrator
 * 参考：http://www.cnblogs.com/skywang12345/p/3610390.html
 */
public class MaxHeap<T extends Comparable<T>> {

	private List<T> mHeap; 
	public MaxHeap() {
		mHeap = new ArrayList<>();
	}
	//利用数组进行初始化堆
	public MaxHeap(T[] arr){
		mHeap = Arrays.asList(arr);
		for (int i = arr.length-1; i > 0; i--) 
			filterDown(i, arr.length-1);
	}
	/**
	 * 最大堆的向下调整算法
	 * 注：数组实现的堆中，第N个节点的左孩子的索引值是(2N+1)，右孩子的索引是(2N+2)。
	 * @param start -- 被下调节点的起始位置(一般为0，表示从第1个开始)
	 * @param end   -- 截至范围(一般为数组中最后一个元素的索引)
	 */
	protected void filterDown(int start, int end) {
		int cur = start; // 当前(current)节点的位置
		int left = 2*cur + 1; //左(left)孩子的位置
		T temp = mHeap.get(cur); //当前(current)节点的大小
		while (left <= end) {
			 // "left"是左孩子，"left+1"是右孩子
			int cmp = -1;
			if ((left+1) < end) {
				cmp = mHeap.get(left).compareTo(mHeap.get(left+1));
				if(cmp < 0)
					left++; //选取最大的一个孩子节点
			}
			cmp = temp.compareTo(mHeap.get(left));
			if(cmp > 0)
				break;  //调整结束
			else {
				mHeap.set(cur, mHeap.get(left));
				cur = left;
				left = cur * 2 + 1;
			}
		}
		mHeap.set(cur, temp);
	}
	
	/**
	 * 删除最大堆中的data
	 * @param data
	 * @return 0:成功；-1：失败
	 */
	public int remove(T data) {
		// 如果"堆"已空，则返回-1
		if(mHeap.isEmpty())
			return -1;
		
		int index = mHeap.indexOf(data);
		if(index == -1)
			return -1;
		
		int size = mHeap.size();
		mHeap.set(index, mHeap.get(size-1)); // 用最后元素填补
		mHeap.remove(size-1);  //移除最后元素
		if(mHeap.size() > 1)
			filterDown(index, mHeap.size()-1);// 从index号位置开始自上向下调整为最小堆
		return 0;
	}
	/**
	 * 最大堆的向上调整算法(从start开始向上直到0，调整堆)
	 *  注：数组实现的堆中，第N个节点的左孩子的索引值是(2N+1)，右孩子的索引是(2N+2)。
	 * @param start -- 被上调节点的起始位置(一般为数组中最后一个元素的索引)
	 */
	protected void filterUp(int start) {
		int cur = start;
		int parent = (cur-1)/2;
		T temp = mHeap.get(cur);
		while (cur > 0) {
			int cmp = temp.compareTo(mHeap.get(parent));
			if(cmp < 0)
				break;  //调整结束
			else {
				mHeap.set(cur, mHeap.get(parent));
				cur = parent;
				parent = (cur-1)/2;
				
			}
		}
		mHeap.set(cur, temp);
	}
	/**
	 * 将data插入到二叉堆中
	 * @param date
	 */
	public void insert(T date) {
		mHeap.add(date);
		filterUp(mHeap.size()-1); //向上调整堆
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mHeap.size(); i++) {
			sb.append(mHeap.get(i) + " ");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
        int i;
        int a[] = {10, 40, 30, 60, 90, 70, 20, 50, 80};
        MaxHeap<Integer> tree=new MaxHeap<Integer>();

        System.out.printf("== 依次添加: ");
        for(i=0; i<a.length; i++) {
            System.out.printf("%d ", a[i]);
            tree.insert(a[i]);
        }

        System.out.printf("\n== 最 大 堆: %s", tree);

        i=85;
        tree.insert(i);
        System.out.printf("\n== 添加元素: %d", i);
        System.out.printf("\n== 最 大 堆: %s", tree);

        i=90;
        tree.remove(i);
        System.out.printf("\n== 删除元素: %d", i);
        System.out.printf("\n== 最 大 堆: %s", tree);
        System.out.printf("\n");
        
    }
}
