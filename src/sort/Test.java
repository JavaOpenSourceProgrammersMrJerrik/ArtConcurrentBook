package sort;

public class Test {

	private static Test t = new Test();
	public static int count1 = 0;
	public static int count2;

	private Test() {
		System.out.println(1111);
		count1++;
		count2++;

	}

	public static Test getTest() {
		return t;
	}

	public static void main(String[] args) {
		/*
		 * Test t=new Test(); System.out.println(t.count1);
		 * System.out.println(t.count2);
		 */
	}

}
