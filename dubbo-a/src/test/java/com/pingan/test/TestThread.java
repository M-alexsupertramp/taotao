package com.pingan.test;

public class TestThread {

	public static void main(String[] args) throws InterruptedException {
		Thread t=new Thread(){
			public void run(){
				pong();
			}
		};
		//Thread.sleep(1000);
		t.start();
		System.out.println("ping");
	}

	 static void pong() {
		System.out.println("pong");
	}
	
}
