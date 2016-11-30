
public interface Queue {

	void enqueue(Object item);

	Object dequeue();

	Object peek();

	void printQueue();
	
	String concatChar();
	boolean isEmpty();
}
