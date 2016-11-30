
/* Author: Matthew Deng
 * Course: CSC 172
 * Lab Session: MW 1525-1640
 * Lab Assignment: Project 2
 * Date: Feb. 20th, 2016
 */

public interface Queue {

	void enqueue(Object item);

	Object dequeue();

	Object peek();

	void printQueue();
	
	String concatChar();
	boolean isEmpty();
}