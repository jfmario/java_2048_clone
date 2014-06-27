package com.adampjr.java2048;

public class Java2048 {
	
	public static void main(String[] args) {
		Game g = new Game();
		g.printArray();
		for (int x = 0; x < 3; x++) {
			g.addNewNumber();
		}
		g.printArray();
		for (int x = 0; x < 3; x++) {
			g.addNewNumber();
			g.printArray();
			g.pushUp();
			g.printArray();
			g.addNewNumber();
			g.printArray();
			g.pushDown();
			g.printArray();
			g.addNewNumber();
			g.printArray();
			g.pushLeft();
			g.printArray();
			g.addNewNumber();
			g.printArray();
			g.pushRight();
			g.printArray();
		}
	}	
	
}