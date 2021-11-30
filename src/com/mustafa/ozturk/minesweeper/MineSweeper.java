package com.mustafa.ozturk.minesweeper;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
	int rows;
	int collumn;
	String[][] board;
	String[][] minedboard;
	int[] movesCollum;
	int[] movesRow;
	int numberOfMoves;
	
	public MineSweeper(int rows, int collumn) {
		super();
		this.rows = rows;
		this.collumn = collumn;
		this.board = createBoard(this.board);
		this.minedboard = createBoard(this.minedboard);
		this.movesCollum = new int[2];
		this.movesRow = new int[2];
		this.numberOfMoves = (this.rows * this.collumn) - ((this.rows * this.collumn) / 4);
	}
	
	public String[][] createBoard(String[][] board) {
		board = new String[this.rows][this.collumn];
		for (int i = 0; i < board.length; i++) {
			
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = "-";
				
			}
		}
		
		return board;
	}
	
	public void printBoard(String[][] board) {
		int count = 1;
		int count2 = 1;
		System.out.print(" ");
		for (int i = 0; i < board[0].length; i++) {
			System.out.print(" " + count2 + " ");
			count2++;
		}
		
		System.out.println();
		for (String[] is : board) {
			System.out.print(count + "|");
			count++;
			for (String is2 : is) {
				
				System.out.print(is2 + " |");
				
			}
			System.out.println();
		}
	}
	
	public void placingMines(String[][] board) {
		
		int numberOfMines = this.rows * this.collumn;
		Random random = new Random();
		
		for (int i = 0; i < (numberOfMines) / 4; i++) {
			
			while (true) {
				int row = random.nextInt(this.rows) + 0;
				int collumn = random.nextInt(this.collumn) + 0;
				if (board[row][collumn].equals("-")) {
					
					board[row][collumn] = "*";
					break;
				} else {
					continue;
				}
			}
			
		}
		
	}
	
	public void chooseDestination() {
		int[] location;
		Scanner scanner = new Scanner(System.in);
		while (numberOfMoves > 0) {
			
			location = checkDestination();
			if (location[1] > this.board[0].length || location[1] < 0 || location[0] > this.board.length
					|| location[0] < 0) {
				System.out.println("TARLANIN BOYUTU DIŞINDA BİR SAYI GİRDİNİZ!!\n");
				System.out.println("Lütfen Tekrar Değer Griniz\n");
				location = checkDestination();
			}
			if (this.board[location[0]][location[1]].equals("-")) {
				if (checkMine(location[0], location[1])) {
					break;
				}
				this.board = numberOfContactMines(location[0], location[1]);
				numberOfMoves--;
			} else {
				System.out.println("DAHA ÖNCE OYNANMIŞ BİR HAMLE ");
			}
			if (numberOfMoves == 0) {
				System.out.println("\n\t***Tebrikler Kazandınız***");
				printBoard(minedboard);
			}
			
		}
		
	}
	
	public int[] checkDestination() {
		int row;
		int collumn;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("lütfen Yatay seçmek istedğiniz konumu giriniz");
		row = scanner.nextInt();
		System.out.println("lütfen Dikey seçmek istedğiniz konumu giriniz");
		collumn = scanner.nextInt();
		
		row = row - 1;
		collumn = collumn - 1;
		int[] location = { row, collumn };
		return location;
		
	}
	
	public boolean checkMine(int row, int collumn) {
		if (this.minedboard[row][collumn].equals("*")) {
			System.out.println("Mayına bastınız!!!");
			return true;
			
		} else {
			return false;
		}
		
	}
	
	private String[][] numberOfContactMines(int row, int collumn) {
		ArrayList<int[][]> arrayList = new ArrayList<>();
		int count2 = 0;
		
		if (checkMine(row, collumn)) {
			System.out.println("Mayına bastınız!!!");
			
		} else {
			arrayList = possibleContacts(row, collumn);
			for (int i = 0; i < arrayList.size(); i++) {
				int r = arrayList.get(i)[0][0];
				int c = arrayList.get(i)[0][1];
				if (this.minedboard[r][c] == "*") {
					count2++;
					
				}
				
			}
			
		}
		this.minedboard[row][collumn] = String.valueOf(count2);
		this.board[row][collumn] = String.valueOf(count2);
		printBoard(this.board);
		return this.board;
	}
	
	private ArrayList<int[][]> possibleContacts(int row, int collumn) {
		
		ArrayList<int[][]> arrayList = new ArrayList<>();
		int[][] temp = new int[8][2];
		
		int count = 0;
		int x = this.board.length;
		int y = this.board[0].length;
		
		temp[0][0] = row - 1;
		temp[0][1] = collumn;
		temp[1][0] = row - 1;
		temp[1][1] = collumn - 1;
		temp[2][0] = row - 1;
		temp[2][1] = collumn + 1;
		temp[3][0] = row + 1;
		temp[3][1] = collumn;
		temp[4][0] = row + 1;
		temp[4][1] = collumn - 1;
		temp[5][0] = row + 1;
		temp[5][1] = collumn + 1;
		temp[6][0] = row;
		temp[6][1] = collumn - 1;
		temp[7][0] = row;
		temp[7][1] = collumn + 1;
		int z = temp.length;
		for (int i = 0; i < z; i++) {
			if (temp[i][0] >= 0 && temp[i][1] >= 0 && temp[i][0] < x && temp[i][1] < y) {
				int a = temp[i][0];
				int b = temp[i][1];
				int[][] temp1 = { { a, b } };
				
				arrayList.add(temp1);
			}
		}
		return arrayList;
	}
	
	public void startgame() {
		System.out.println("\t\t===MAYIN TARLASI===\n");
		printBoard(this.board);
		placingMines(this.minedboard);
		printBoard((this.minedboard));
		chooseDestination();
	}
	
	public static void main(String[] args) {
		
		MineSweeper mineField = new MineSweeper(2, 2);
		mineField.startgame();
		
	}
	
}
