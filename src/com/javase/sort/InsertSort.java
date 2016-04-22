package com.javase.sort;

import com.javase.util.CommonUtil;

/**
 * 插入排序
 * @author Administrator
 *
 */
public class InsertSort {

	public static void insertSort(int[] arr) {
		int len = arr.length;
		for (int i = 1; i < arr.length; i++) {
			int j;
			int temp = arr[i];
			for (j = i; j > 0; j--) {
				//如果前面的数大于后面的数，则把大的赋值到后面
				if (arr[j - 1] > temp) {
					arr[j] = arr[j - 1];
				}else
					//如果当前的数，不小于前面的数，那就说明不小于前面所有的数
					//因为前面已经是排好了序的，所以直接跳出当前一轮的比较
					break;
			}
			arr[j] = temp;
		}
	}
	
	public static void main(String[] args) {
		int[] arr = {3, 5,1,7,3,6,9,11};
		CommonUtil.print(arr);
		insertSort(arr);
		CommonUtil.print(arr);
	}
}
