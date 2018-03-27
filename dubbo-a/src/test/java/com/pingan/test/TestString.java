package com.pingan.test;

public class TestString {
	public static void main(String[] args) {
		String a="hello";
		String b="he"+new String("llo");
		String d=new String("hello");
		String e=d;
		String c="hello";
		System.out.println(a==c);
	}
}
