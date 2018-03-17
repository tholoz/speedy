package speedy;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
	public static void main(String[] args) {
		System.out.println(Checker.check("hello"));
		System.out.println(Checker.check("heo"));
		
		AtomicInteger count = new AtomicInteger(0);
		Checker c1 = new Checker(count,"hello");
		Checker c2 = new Checker(count,"heo");
		try {
		c1.start();
		c2.start();
		c1.join();
		c2.join();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(count);
	}
}