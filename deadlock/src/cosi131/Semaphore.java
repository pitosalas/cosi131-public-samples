package cosi131;


public class Semaphore {
	private int permits;

	Semaphore(int count) {
		this.permits = count;
	}
	
	public Semaphore() {
		this(1);
	}

	
	synchronized void  acquire() throws InterruptedException {
		while(permits == 0) {
			wait();
		}
		permits--;
		
	}
	
	synchronized void release() {
		permits++;
		notify();
	}
}

