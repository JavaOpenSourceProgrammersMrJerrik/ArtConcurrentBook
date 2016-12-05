package sort;

import java.util.Arrays;

/**
 * 合并有序数列
 * 
 * @author Jerrik
 *
 */
public class MergeOrderList {

	public static void main(String[] args) {
		int[] sourceArr = new int[] { 1, 5 };
		int[] destArr = new int[] { 2, 4, 6, 8 };

		int[] resultArr = new int[sourceArr.length + destArr.length];

		mergeOrderList(sourceArr, sourceArr.length, destArr, destArr.length, resultArr);

		System.out.println("合并后的数组:" + Arrays.toString(resultArr));
	}

	/**
	 * 将指定长度的有序数组进行合并排序输出 例如: 数组a [1,3,5] 数组b [2,4,6] 结果c [1,2,3,4,5,6]
	 * 
	 * @param sourceArr
	 * @param sourceLen
	 * @param destArr
	 * @param destLen
	 * @param resultArr
	 */
	public static void mergeOrderList(int[] sourceArr, int sourceLen, int[] destArr, int destLen, int[] resultArr) {
		// step 1 比较有序数组的第一个数字 谁小就取谁 并将该数移除数组 放进结果数组
		int i = 0, j = 0, k = 0;
		while (i < sourceLen && j < destLen) {
			if (sourceArr[i] < destArr[j])
				resultArr[k++] = sourceArr[i++];
			else
				resultArr[k++] = destArr[j++];

			System.out.println("resultArr: " + Arrays.toString(resultArr));
		}

		//step 2 如果两个数组长度不一致 则需要把后续的数组赋值到结果数组
		while (i < sourceLen)
			resultArr[k++] = sourceArr[i++];

		while (j < destLen)
			resultArr[k++] = destArr[j++];
	}

}
