package com.javase.link;

import java.lang.reflect.Array;

/**
 * java实现队列
 * @author Administrator
 *
 * @param <T>
 */
public class ArrayQueue<T> {

	private static final int DEFAULT_SIZE = 12;
	private T[] mArray;
	private int mCount;
	public ArrayQueue(Class<T> type) {
		this(type, DEFAULT_SIZE);
	}
	public ArrayQueue(Class<T> type, int size) {
		mArray = (T[]) Array.newInstance(type, size);
		mCount = 0;
	}
	// 将val添加到队列的末尾
	public void add(T val) {
		mArray[mCount++] = val;
	}
	// 返回“队列开头元素”
	public T front() {
		return mArray[0];
	}
	// 返回“栈顶元素值”，并删除“栈顶元素”
	public T pop() {
		T ret = mArray[0];
		mCount--;
		for (int i = 1; i <= mCount; i++) 
			mArray[i - 1] = mArray[i];
		return ret;
	}
}
