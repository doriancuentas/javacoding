package org.coyote.test.java;

import java.io.IOException;
import java.net.InetAddress;

public class VolatileFail {
	
	public static class Counter {
		volatile long counter;
		boolean failFlag;

		public void inc(int factor) {
			for (int i = 0; i < 1000; i++) {
				//reads a volatile variable
				long aux = counter;
				if (i % 50 == 0 && failFlag) {
					try {
						InetAddress.getByName("8.8.8.8").isReachable(500);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//writes a volatile variable
				counter = aux + factor;
				System.out.printf("T : %1$S, - %2$16d%n", Thread.currentThread().getName(), counter);
			}
		}
	}

	public static void main(String[] args) {
		Counter counter = new Counter();
		
		//will force a time gap between read and write the volatile variable
		counter.failFlag = true;
		
		Thread t1 = new Thread(() -> {
			counter.inc(1);
		}, "uno");
		Thread t2 = new Thread(() -> {
			counter.inc(10000);
		}, "dos");
		Thread t3 = new Thread(() -> {
			counter.inc(100000000);
		}, "tre");
		
		t1.start();
		t2.start();
		t3.start();
		
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.printf("Counter : %d", counter.counter);
	}
}	
