package sort;

import java.util.Arrays;

/**
 * �ϲ���������
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

		System.out.println("�ϲ��������:" + Arrays.toString(resultArr));
	}

	/**
	 * ��ָ�����ȵ�����������кϲ�������� ����: ����a [1,3,5] ����b [2,4,6] ���c [1,2,3,4,5,6]
	 * 
	 * @param sourceArr
	 * @param sourceLen
	 * @param destArr
	 * @param destLen
	 * @param resultArr
	 */
	public static void mergeOrderList(int[] sourceArr, int sourceLen, int[] destArr, int destLen, int[] resultArr) {
		// step 1 �Ƚ���������ĵ�һ������ ˭С��ȡ˭ ���������Ƴ����� �Ž��������
		int i = 0, j = 0, k = 0;
		while (i < sourceLen && j < destLen) {
			if (sourceArr[i] < destArr[j])
				resultArr[k++] = sourceArr[i++];
			else
				resultArr[k++] = destArr[j++];

			System.out.println("resultArr: " + Arrays.toString(resultArr));
		}

		//step 2 ����������鳤�Ȳ�һ�� ����Ҫ�Ѻ��������鸳ֵ���������
		while (i < sourceLen)
			resultArr[k++] = sourceArr[i++];

		while (j < destLen)
			resultArr[k++] = destArr[j++];
	}

}
