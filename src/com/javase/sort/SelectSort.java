package com.javase.sort;

import com.javase.util.CommonUtil;
/**
 * 选择排序
 * @author Administrator
 *
 */
public class SelectSort {

	public static void selectSort(int[] arr) {
		int len = arr.length;
		
		for (int i = 0; i < len -1 ; i++) {
			int min = i;
			for (int j = i + 1; j < len; j++) 
				if(arr[j] < arr[min])
					min = j;
			if(min != i)
				CommonUtil.swap(arr, i, min);
		}
	}
	public static void main(String[] args) {
		int[] arr = {3, 5,1,7,3,6,9,11};
		CommonUtil.print(arr);
		selectSort(arr);
		CommonUtil.print(arr);
	}
}
