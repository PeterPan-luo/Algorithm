package com.javase.sort;

import com.javase.util.CommonUtil;

/**
 * 桶排序，适用于要排序的数很多，但最大值比较小的数列
 * @author Administrator
 *
 */
public class BucketSort {

	/**
	 * 桶排序
	 * @param arr 待排序数组
	 * @param max 数组中最大值的范围
	 */
	public static void bucketSort(int[] arr, int max) {
		int[] buckets;
		if(arr == null || max < 1)
			return;
		// 创建一个容量为max的数组buckets，并且将buckets中的所有数据都初始化为0。
		buckets = new int[max];
		// 1. 计数
		for (int i = 0; i < arr.length; i++) {
			buckets[arr[i]]++;
		}
		// 2. 排序
		int k = 0;
		for (int i = 0; i < buckets.length; i++) {
			while (buckets[i]-- > 0) {
				arr[k++] = i;
			}
		}
		buckets = null;
	}
	
	public static void main(String[] args) {
		int[] arr = {3, 5,1,7,3,6,9,11};
		CommonUtil.print(arr);
		bucketSort(arr, 12);
		CommonUtil.print(arr);
	}
}
