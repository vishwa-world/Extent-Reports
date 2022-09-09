package QKART_TestNG.pages;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

class GFG {
	public static void main(String args[])
	{
		int[] arr = { 5, -2, 23, 7, 87, -42, 509 };	
		arr = sortWrapper(arr);
		System.out.println("\nThe sorted array is: ");
		for (int num : arr) {
			System.out.print(num + " ");
		}
	}

    public static  int[] sortWrapper(int a[])
    {
        Instant start = Instant.now();
        System.out.println(String.format("First and Last element of the array are %s and %s respectively", a[0],a[a.length-1]));
        Arrays.sort(a);
        Instant end = Instant.now();
        System.out.println(String.format("First and Last element of the array are %s and %s respectively", a[0],a[a.length-1]));
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time taken to Sort : "+ timeElapsed.toMillis() +" milliseconds");
        return a;
    }
}
