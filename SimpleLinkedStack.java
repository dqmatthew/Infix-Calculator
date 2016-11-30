import java.util.NoSuchElementException;

public class SimpleLinkedStack implements Stack{
	  private class Node {
	        public Object data;
	        public Node next;
	        public Node(Object data, Node next) {
	            this.data = data;
	            this.next = next;
	        }
	    }
	  private Node topOfStack = null;
	public void push(Object item) {
		topOfStack = new Node(item, topOfStack);
	}

	public Object pop() {
		Object item = peek();
		topOfStack = topOfStack.next;
		return item;
	}

	public Object peek() {
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		return topOfStack.data;
	}


	public boolean isEmpty() {

		return topOfStack == null;
	}

	public void printStack() {
		Node temp = null;
		temp = topOfStack;
		while(temp!= null){
			System.out.print(temp.data + " ");
			temp = temp.next;
		}
		
	}


	public Object ObjectPeek() {

		Object ObjectOnTop = topOfStack.data;
		return ObjectOnTop;
	}

	


}
