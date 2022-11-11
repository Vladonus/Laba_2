package org.example;
import java.util.List;
import java.util.Scanner;

public class Main {

public static void main(String[] args) {
    System.out.println();
    Scanner in = new Scanner(System.in);
    System.out.print("Enter the formula: ");
    String expression = in.nextLine();
    List<symbol> tokens = symbol_list.lexAnalyze(expression);
    symbol_list buffer = new symbol_list(tokens);
    System.out.println(symbol_list.expression(buffer));
}
}