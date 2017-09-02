package my.tutorial.counter;
 
public class CounterTester 
{
    public static void main(String[] args) throws InterruptedException {
        
        int max = 50;
        
        Counter counter = new Counter();
        
        CounterThread[] pool = new CounterThread[max];

        for(int i=0; i<max; i++) {
            CounterThread ct = new CounterThread(counter, i);
            pool[i] = ct;
            pool[i].start();
            // ct.join(1000); // коллизий нет, котому что ждём, пока поток завершится (отработает)
        }
        

        //boolean alive = false;
        int aliveCount = 0;
        
        for (CounterThread item: pool) {
			//System.out.println("item in pool");
			if (item.isAlive()) { aliveCount++; }
		}
		
		Thread.sleep(1000);
        
        System.out.println("aliveCount:" + aliveCount);
        System.out.println("Counter:" + counter.getCounter());
    }
}
 
class Counter
{
    private long counter = 0L;
    
    //public void increaseCounter() {
    public synchronized void increaseCounter() {
        counter++;
    }
    
    public long getCounter() {
        return counter;
    }
}
 
class CounterThread extends Thread
{
    private Counter counter;
    private int index = 0;
    
    public CounterThread(Counter counter, int index) {
        // System.out.println("Создаётся CounterThread");
        this.counter = counter;
        this.index = index;
    }
    
    @Override
    public void run() {
        for(int i=0; i<1000; i++) { counter.increaseCounter(); }
        try { 
			Thread.sleep(10); 
		} catch (InterruptedException e) {
			System.out.println("CounterThread-"+index+": поймано исключение InterruptedException");
		} finally {
            System.out.println("CounterThread-"+index+": обязательный блок в CounterThread");
        }
    }
}
