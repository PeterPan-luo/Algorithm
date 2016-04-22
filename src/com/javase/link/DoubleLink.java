package com.javase.link;

import java.util.Iterator;

/**
 * Java 实现的双向链表。 
 * 注：java自带的集合包中有实现双向链表，路径是:java.util.LinkedList
 * @author Administrator
 *
 */
public class DoubleLink<T> {

	// 双向链表“节点”对应的结构体
	private class DNode<T>{
		public DNode prev;
		public DNode next;
		public T value;
		public DNode(DNode prev, DNode next, T value) {
			this.prev = prev;
			this.next = next;
			this.value = value;
		}
	}
	
	//表头
	private DNode<T> mHead;
	//节点个数
	private int mCount;
	
	public DoubleLink() {
		// 创建“表头”。注意：表头没有存储数据！
		mHead = new DNode<T>(null, null, null);
		mHead.prev = mHead.next = mHead;
		// 初始化“节点个数”为0
		mCount = 0;
	}
	
	//返回节点数量
	public int size() {
		return mCount;
	}
	
	//节点是否为空
	public boolean isEmpty() {
		return mCount == 0;
	}
	
	//获取index位置节点
	private DNode<T> getNode(int index) {
		if(index < 0 || index >= mCount)
			throw new IndexOutOfBoundsException();
		//正向查找
		if (index <= mCount/2) {
			DNode<T> node = mHead.next;
			for (int i = 0; i < index; i++) 
				node = node.next;
			return node;
		}
		//反向查找
		DNode<T> node = mHead.prev;
		int rIndex = mCount - 1 - index;
		for (int i = 0; i < rIndex; i++) 
			node = node.prev;
		return node;
	}
	
	//获取index位置节点的值
	public T get(int index) {
		DNode<T> node = getNode(index);
		return node.value;
	}
	
	//获取第一个节点的值
	public T getFirst() {
		return get(0);
	}
	//获取最后节点的值
	public T getLast() {
		return get(mCount - 1);
	}
	//将节点插入到第index位置之前
	public void insert(int index,T t) {
		if (index == 0) {
			DNode<T> node = new DNode<T>(mHead, mHead.next, t);
			mHead.next.prev = node;
			mHead.next = node;
			mCount++;
			return;
		}
		DNode<T> indexNode = getNode(index);
		DNode<T> insertNode = new DNode<T>(indexNode.prev, indexNode, t);
		indexNode.prev.next = insertNode;
		indexNode.prev = insertNode;
		mCount++;
	}
	// 将节点插入第一个节点处。
	public void insertFirst(T t) {
		insert(0, t);
	}
	// 将节点追加到链表的末尾
	public void appendLast(T t) {
		DNode<T> lastNode = getNode(mCount - 1);
		DNode<T> insertNode = new DNode<T>(lastNode, mHead, t);
		lastNode.next = insertNode;
		mHead.prev = insertNode;
		mCount++;
	}
	// 删除index位置的节点
	public void delete(int index) {
		if(index < 0 ||  index >= mCount)
			throw new IndexOutOfBoundsException();
		
		DNode<T> indexNode = getNode(index);
		indexNode.prev.next = indexNode.next;
		indexNode.next.prev = indexNode.prev;
		mCount--;
	}
	//删除第一个节点
	public void deleteFirst(){
		delete(0);
	}
	//删除最后一个节点
	public void deleteLast() {
		delete(mCount-1);
	}
}
