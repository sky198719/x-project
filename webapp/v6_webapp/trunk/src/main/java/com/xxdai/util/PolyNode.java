/** 
* Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
*/
package com.xxdai.util;

/** 
* 一元高次多项式实体类
* 
* @since jdk1.6
*/
public class PolyNode {
	private double a;
	private int i;
	PolyNode next;

	public PolyNode(double a, int i) {
		this.a = a;
		this.i = i;
		this.next = null;
	}

	public PolyNode() {
		this(0.0, 0);
	}

	public double getA() {
		return a;
	}

	public int getI() {
		return i;
	}

	public void setA(double a) {
		this.a = a;
	}

	public void setI(int i) {
		this.i = i;
	}
}
