/* Author: Matthew Deng
 * Course: CSC 172
 * Lab Session: MW 1525-1640
 * Lab Assignment: Project 2
 * Date: Feb. 20th, 2016
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ShuntingYard {
	SimpleLinkedStack stack = new SimpleLinkedStack();
	static LinkedQueue queue = new LinkedQueue();

	private static final char[] operators = { '(', ')', '+', '-', '/', '*',
			'&', '|', '!', '<', '>', '=', ' ' };

	private enum OperatorPrecedence {
		lParens(0), rParens(1), add(2), minus(3), divide(4), multiply(5), mod(6), exponent(
				7), and(8), or(9), not(10), lessThan(11), greaterThan(12), equal(
				13), point(14), operand(15), sin(16), cos(17), tan(18);
		private int index;

		OperatorPrecedence(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	} // in order of {( , ), +, -, /, *, &, |, !, <, >, =, point, operand} in
		// regards to precedence

	private static final int[] inStackPrecedence = { 0, 19, 11, 11, 12, 12, 12,
			13, 9, 8, 18, 11, 11, 10, 31, 0, 17, 17, 17 };
	private static final int[] inCharPrecedence = { 20, 19, 11, 11, 12, 12, 12,
			13, 9, 8, 18, 11, 11, 10, 31, 0, 17, 17, 17 };

	public OperatorPrecedence getToken(char symbol) {
		switch (symbol) {
		case '(':
			return OperatorPrecedence.lParens;
		case ')':
			return OperatorPrecedence.rParens;
		case '+':
			return OperatorPrecedence.add;
		case '-':
			return OperatorPrecedence.minus;
		case '/':
			return OperatorPrecedence.divide;
		case '*':
			return OperatorPrecedence.multiply;
		case '&':
			return OperatorPrecedence.and;
		case '|':
			return OperatorPrecedence.or;
		case '!':
			return OperatorPrecedence.not;
		case '<':
			return OperatorPrecedence.lessThan;
		case '>':
			return OperatorPrecedence.greaterThan;
		case '=':
			return OperatorPrecedence.equal;
		case '.':
			return OperatorPrecedence.point;
		case '%':
			return OperatorPrecedence.mod;
		case '^':
			return OperatorPrecedence.exponent;
		case 's':
			return OperatorPrecedence.sin;
		case 'c':
			return OperatorPrecedence.cos;
		case 't':
			return OperatorPrecedence.tan;
		default:
			return OperatorPrecedence.operand;
		}
	}

	public Queue postfix(String infix) {

		char tokenCharacter;
		char a = ' ';
		int i;
		int j = 0;
		int ignoreI = 0;
		OperatorPrecedence token;
		for (i = 0; i < infix.length(); i++) {

			token = getToken(infix.charAt(i));
			tokenCharacter = infix.charAt(i);

			if (token == OperatorPrecedence.operand) { // if token is an operand
				Double number;
				String temp = "";

				if (infix.charAt(i) == ' ') {

				} else {

					for (j = i; j < infix.length() && infix.charAt(j) != ' '
							&& infix.charAt(j) != ')'; j++) {
						temp = temp + String.valueOf(infix.charAt(j));
					}

					number = Double.parseDouble(temp);

					if (i == ignoreI && ignoreI != 0) {

					} else {
						if (i + 1 != ignoreI) {
							queue.enqueue(number);
						}
					}
					i = j - 1;

				}

			} else if (token == OperatorPrecedence.point) {// TODO similar to
															// operand
				Double number;

				String temp = "";

				temp = temp + String.valueOf(infix.charAt(i - 1));

				if (infix.charAt(i + 1) != '0') {

					for (j = i; j < infix.length() && infix.charAt(j) != ' '
							&& infix.charAt(j) != ')' && infix.charAt(j) != '.'; j++) {
						temp = temp + String.valueOf(infix.charAt(j));
					}

					ignoreI = i + 1;

					number = Double.parseDouble(temp);
				} else {

					ignoreI = i + 1;

					temp = temp + String.valueOf(infix.charAt(i - 1));
					number = Double.parseDouble(temp);
				}
				if (queue.isEmpty() != true) {
					queue.dequeue();
				}
				queue.enqueue(number);

			} else if (token == OperatorPrecedence.rParens) { // if token is )
																// then pop all
																// stack
																// elements and
																// enqueue them
																// until LParens
																// is found
																// WORKS!
				while (getToken((Character) stack.peek()) != OperatorPrecedence.lParens) {
					queue.enqueue(stack.pop());
				}
				stack.pop();
			} else { // if token is an operator other than )
				if (stack.isEmpty()) {
					stack.push(tokenCharacter);
				} else {

					while (!stack.isEmpty()
							&& inStackPrecedence[(getToken((Character) stack
									.peek())).getIndex()] >= inCharPrecedence[token
									.getIndex()]
							&& (Character) stack.peek() != 0) { // sorta works

						queue.enqueue((Character) stack.pop());
					}
					stack.push(tokenCharacter);
				}

			}
		}

		while (stack.isEmpty() != true)

		{

			queue.enqueue(stack.pop());

		}

		return queue;

	}

	// convert postfix to answer
	public double PostFixtoDoubleAnswer(Queue queue) {
		double answer = 0;
		double dA = 0;
		double dB = 0;
		int count = 0;
		while (queue.isEmpty() != true) {
			if (queue.peek().getClass().equals(Double.class)
					|| queue.peek().getClass().equals(Integer.class)) {
				stack.push(queue.peek());

			} else if (queue.peek().getClass().equals(Character.class)) {

				SimpleLinkedStack tempstack = new SimpleLinkedStack();
				tempstack = stack;

				if (tempstack.peek().getClass().equals(Integer.class)) {

					dA = (Integer) stack.pop();

				} else {
					dA = (Double) stack.pop();
				}
				if (stack.isEmpty() != true) {
					if ((tempstack.peek().getClass().equals(Integer.class))) {
						dB = (Integer) stack.pop();

					} else {
						dB = (Double) stack.pop();
					}
				}
				// calculates based on token
				if (getToken((Character) queue.peek()) == OperatorPrecedence.add) {
					answer = dB + dA;

				} else if (getToken((Character) queue.peek()) == OperatorPrecedence.minus) {
					answer = dB - dA;

				} else if (getToken((Character) queue.peek()) == OperatorPrecedence.multiply) {
					answer = dB * dA;
				} else if (getToken((Character) queue.peek()) == OperatorPrecedence.divide) {
					answer = dB / dA;
				} else if (getToken((Character) queue.peek()) == OperatorPrecedence.and) {
					if (dB == 1 && dA == 1) {
						answer = 1;
					} else {
						answer = 0;
					}
				} else if (getToken((Character) queue.peek()) == OperatorPrecedence.or) {
					if (dB == 1 || dA == 1) {
						answer = 1;
					} else {
						answer = 0;
					}
				} else if (getToken((Character) queue.peek()) == OperatorPrecedence.not) {
					if (dA == 1) {
						answer = 0;
					} else {
						answer = 1;
					}
				} else if (getToken((Character) queue.peek()) == OperatorPrecedence.lessThan) {
					if (dB < dA) {
						answer = 1.0;
					} else {
						answer = 0.0;
					}
				} else if (getToken((Character) queue.peek()) == OperatorPrecedence.greaterThan) {
					if (dB > dA) {
						answer = 1.0;
					} else {
						answer = 0.0;
					}
				} else if (getToken((Character) queue.peek()) == OperatorPrecedence.equal) {
					if (dA == dB) {
						answer = 1.0;
					} else {
						answer = 0.0;
					}
				} else if (getToken((Character) queue.peek()) == OperatorPrecedence.mod) {
					answer = dB % dA;
				} else if (getToken((Character) queue.peek()) == OperatorPrecedence.exponent) {
					answer = Math.pow(dB, dA);

				} else if (getToken((Character) queue.peek()) == OperatorPrecedence.sin) {
					double r;
					r = Math.toRadians(dA);
					answer = Math.sin(r);

				} else if (getToken((Character) queue.peek()) == OperatorPrecedence.cos) {
					double r;
					r = Math.toRadians(dA);
					answer = Math.cos(r);
				} else if (getToken((Character) queue.peek()) == OperatorPrecedence.tan) {
					double r;
					r = Math.toRadians(dA);
					answer = Math.tan(r);
				}
				stack.push(answer);

			} else {

			}
			queue.dequeue();
		}

		return (Double) stack.pop();
	}

	public static void main(String[] args) {
		Scanner scanner;
		try {
			scanner = new Scanner(new File("infix.txt"));

			PrintStream out = new PrintStream(new FileOutputStream(
					"postfix_eval.txt"));
			while (scanner.hasNextLine()) {
				String postfix = " ";
				ShuntingYard shunting = new ShuntingYard();

				String infix = scanner.nextLine();

				shunting.postfix(infix);

				LinkedQueue tempqueue = new LinkedQueue();
				tempqueue = queue;

				out.println(shunting.PostFixtoDoubleAnswer(tempqueue));
			}
			scanner.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}
}