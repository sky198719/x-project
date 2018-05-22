/** 
* Copyright (c) 2014, www.xinxindai.com All Rights Reserved. 
*/
package com.xxdai.util;

/** 
* 一元高次多项式及其求解类
* 
* @since jdk1.6
*/
public class PolyList {
	PolyNode head;
	PolyNode current;

	public PolyList() {
		head = new PolyNode();
		current = head;
		head.next = null;
	}

	// 是否为空
	public boolean isEmpty() {
		return head.next == null;
	}

	// 这里只考虑按顺序插入元素
	public void insert(PolyNode node) {
		current.next = node;
		current = node;
	}

	// 打印多项式
	public String printS() {
		StringBuilder s = new StringBuilder("");
		StringBuilder a = new StringBuilder("");
		StringBuilder i = new StringBuilder("");
		StringBuilder theOne = new StringBuilder("");

		current = head.next;
		while (current != null) {
			a.delete(0, a.length());
			i.delete(0, i.length());
			theOne.delete(0, theOne.length());

			if (current.getA() == 1) {
				a.append("");
			} else {
				a.append(String.valueOf(current.getA()));
			}

			if (current.getI() == 1) {
				i.append("");
				theOne.append(a.toString()).append("x").append(i.toString());
			} else {
				i.append(String.valueOf(current.getI()));
				theOne.append(a.toString()).append("x^").append(i.toString());
			}

			if (current == head.next) {
				s.append(theOne.toString());
			} else {
				s.append("+").append(theOne.toString());
			}
			current = current.next;
		}
		return s.toString();
	}
	
	//待求解方程
	public double func(double x)  {
		double ret = 0;
		double temp = 1;
		current = head.next;
		while (current != null) {
			temp = 1;
			for (int i = 1; i <= current.getI(); i++) {
				temp = temp * x;
			}
			ret += current.getA() * temp;
			current = current.next;
		}
		return ret;
	}
	
	//导数方程
	public double dfunc(double x) {
		double ret = 0;
		double temp = 1;
		current = head.next.next;
		while (current != null) {
			temp = 1;
			for (int i = 1; i <= current.getI() - 1; i++) {
				temp = temp * x;
			}
			ret += current.getA() * current.getI() * temp;
			current = current.next;
		}
		return ret;
	}
	
	/**
	*x代表初始值
	*maxcyc代表迭代次数
	*precision代表精确度
	*得到结果，返回值为1，否则，返回值为0
	*/
	public int NewtonMethod(double[] x, int maxcyc, double precision) {
		double x0, x1;
		int i;

		x0 = x[0]; // 参数传递迭代初始值
		i = 0;
		while (i < maxcyc) // 循环次数不超过设定最大数
		{
			if (dfunc(x0) == 0.0) // 若通过初值，方法返回值为0
			{
				System.out.print("迭代过程中导数为0!\n");
				return 0;
			}
			x1 = x0 - func(x0) / dfunc(x0); // 牛顿迭代计算
			if (Math.abs(x1 - x0) < precision || Math.abs(func(x1)) < precision)
			// 达到预设的结束条件
			{
				x[0] = x1; // 返回结果
				return 1;
			} else // 未达到结束条件
			{
				x0 = x1; // 准备下一次迭代
			}
			i++; // 迭代次数累加
		}
		System.out.print("迭代次数超过预设值!仍没有达到精度！\n");
		return 0;
	}

	// 加法运算
	public static PolyList add(PolyList p1, PolyList p2) {
		PolyList result = new PolyList();
		// 分别指向p1 p2的第一个元素
		p1.current = p1.head.next;
		p2.current = p2.head.next;
		while (p1.current != null && p2.current != null) {
			if (p1.current.getI() == p2.current.getI()) {
				result.insert(new PolyNode(p1.current.getA()
						+ p2.current.getA(), p1.current.getI()));
				p1.current = p1.current.next;
				p2.current = p2.current.next;
			} else if (p1.current.getI() < p2.current.getI()) {
				result.insert(p1.current);
				p1.current = p1.current.next;
			} else {
				result.insert(p2.current);
				p2.current = p2.current.next;
			}
		}
		while (p1.current != null) {
			result.insert(p1.current);
			p1.current = p1.current.next;
		}
		while (p2.current != null) {
			result.insert(p2.current);
			p2.current = p2.current.next;
		}
		return result;
	}

	// 乘法运算
	public static PolyList multiply(PolyList p1, PolyList p2) {
		PolyList result = new PolyList();
		// 分别指向p1 p2的第一个元素
		p1.current = p1.head.next;
		p2.current = p2.head.next;
		while (p1.current != null) {
			while (p2.current != null) {
				double a = p1.current.getA() * p2.current.getA();
				int i = p1.current.getI() + p2.current.getI();
				result.insert(new PolyNode(a, i));
				p2.current = p2.current.next;
			}
			p1.current = p1.current.next;
			p2.current = p2.head.next;
		}
		// 合并同类项
		result.current = result.head.next;
		PolyNode tempPrevious = result.current;
		PolyNode temp = result.current.next;
		while (result.current.next != null) {
			while (temp != null) {
				if (temp.getI() != result.current.getI()) {
					temp = temp.next;
					tempPrevious = tempPrevious.next;
				} else {
					result.current.setA(result.current.getA() + temp.getA());
					tempPrevious.next = temp.next;
					temp = temp.next;
				}
			}
			result.current = result.current.next;
			tempPrevious = result.current;
			temp = result.current.next;
		}
		return result;
	}
	
	/**
	 * 解一元高次方程，求解等额本息年化利率
	 * @param A 本金
	 * @param B 待收总额
	 * @param C 待还月数
	 * @return 
	 */
	public static double calValue(double A, double B, int C) {
		PolyList p1 = new PolyList();
		p1.insert(new PolyNode(1, 0));
		p1.insert(new PolyNode(1, 1));
		PolyList p2 = new PolyList();
		p2.insert(new PolyNode(1, 0));
		p2.insert(new PolyNode(1, 1));

		PolyList p3 = new PolyList();
		p3.insert(new PolyNode(B, 0));
		p3.insert(new PolyNode(-A * C, 1));

		PolyList p4 = new PolyList();
		p4.insert(new PolyNode(-B, 0));

		PolyList resuList = p1;
		for (int i = 0; i < (C - 1); i++) {
			resuList = multiply(resuList, p2);
		}
		resuList = multiply(resuList, p3);
		resuList = add(resuList, p4);

		double precision;
		int maxcyc;

		double[] x = { 2.0 }; // 初始值
		maxcyc = 1000; // 迭代次数
		precision = 0.00001; // 精度
		resuList.NewtonMethod(x, maxcyc, precision);

		double rate = x[0] * 12;
		return rate;
	}
}
