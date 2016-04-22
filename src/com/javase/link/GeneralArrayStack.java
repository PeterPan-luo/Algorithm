package com.javase.link;

import java.lang.reflect.Array;
/**
 * java实现栈
 * @author Administrator
 *
 * @param <T>
 */
public class GeneralArrayStack<T> {

	private static final int DEFAULT_SIZE = 12;
	private T[] mArray;
	private int mCount;
	public GeneralArrayStack(Class<T> type) {
		this(type, DEFAULT_SIZE);
	}
	public GeneralArrayStack(Class<T> type, int size) {
		// 不能直接使用mArray = new T[DEFAULT_SIZE];
		mArray = (T[]) Array.newInstance(type, size);
		mCount = 0;
	}
	// 将val添加到栈中
	public void push(T val) {
		mArray[mCount++] = val;
	}
	// 返回“栈顶元素值”
	public T peek() {
		return mArray[mCount-1];
	}
	// 返回“栈顶元素值”，并删除“栈顶元素”
	public T pop() {
		T ret = mArray[mCount-1];
		mCount--;
		return ret;
	}
	// 返回“栈”的大小
	public int size() {
		return mCount;
	}
	//是否为空
	public boolean isEmpty() {
		return size() == 0;
	}
	
}
