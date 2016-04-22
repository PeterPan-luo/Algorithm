package com.javase.sort;

import com.javase.util.CommonUtil;

/**
 * 快速排序
 * @author Administrator
 *
 */
public class QuickSort {

	public static void quickSort(int[] arr, int low, int high) {
		
		if(low >= high)
			return;
		if ((high - low) == 1) {
			if(arr[low] > arr[low + 1])
				CommonUtil.swap(arr, low, low + 1);
			return;
		}
		
		int pivot = arr[low];
		int left = low + 1;
		int right = high;
		while (left < right) {
			//从左边开始找
			while (left < right && left <= high) {
				//如果左小于右，则一直循环，找到一个大的数字
				if(arr[left] > pivot)
					break;
				left++;
			}
			//从右边开始查找
			while (left <= right && right > low) {
				if(arr[right] <= pivot)
					break;
				right--;
			}
			if(left < right)
				CommonUtil.swap(arr, left, right);
		}
		//交换中间数字
		CommonUtil.swap(arr, low, right);
		quickSort(arr, low, right);//排序前面数组
		quickSort(arr, right + 1, high);//排序后面数组
	}
	
	public static void main(String[] args) {
		int[] arr = {3, 5,1,7,3,6,9,11};
		CommonUtil.print(arr);
		quickSort(arr, 0, arr.length - 1);
		CommonUtil.print(arr);
	}
	
}
