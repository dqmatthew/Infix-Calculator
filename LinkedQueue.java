import java.util.NoSuchElementException;

/* Author: Matthew Deng
 * Course: CSC 172
 * Lab Session: MW 1525-1640
 * Lab Assignment: Project 2
 * Date: Feb. 20th, 2016
 */
public class LinkedQueue implements Queue {
	private class Node {
		public Object data;
		public Node next;

		public Node(Object data, Node next) {
			this.data = data;
			this.next = next;
		}
	}

	private Node head = null;
	private Node tail = null;

	public void enqueue(Object item) {
		Node newNode = new Node(item, null);
		if (isEmpty()) {
			head = newNode;
		} else {
			tail.next = newNode;
		}
		tail = newNode;
	}

	public Object dequeue() {
	    if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Object item = head.data;
        if (tail == head) {
            tail = null;
        }
        head = head.next;
        return item;
	}

	public Object peek() {
	    if (head == null) {
            throw new NoSuchElementException();
        }
        return head.data;
	}

	public boolean isEmpty() {
	
		return head == null;
	}

	public void printQueue() {
		Node temp = head;

		while(temp!= null){
			System.out.print(temp.data + " ");
			temp = temp.next;
		}
		
	}
	

	public String concatChar() {
		String str = "";
		Node temp = head;
		while(temp!= null){
			str = str + temp.data;

			temp = temp.next;
		}
		
		return str;
	}

}