package com.javase.sort;

import com.javase.util.CommonUtil;

/**
 * 冒泡排序
 * @author Administrator
 *
 */
public class BubbleSort {

	public static void bubbleSort(int[] arrays) {
		int temp = 0;
		for (int j = 0; j < arrays.length; j++) {
			//对于每一个数组元素，从0到未来排序的最大下标，总把最大的数字放在后面
			for (int k = 0; k < arrays.length - 1 - j; k++) {
				if (arrays[k] > arrays[k + 1]) {
					temp = arrays[k];
					arrays[k] = arrays[k + 1];
					arrays[k + 1] = temp;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int[] arr = {3, 5,1,7,3,6,9,11};
		CommonUtil.print(arr);
		bubbleSort(arr);
		CommonUtil.print(arr);
	}
	
	
}
