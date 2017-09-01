package my.tutorial.counter;
 
public class CounterTester 
{
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        int max = 10;

        for(int i=0; i<10; i++) {
            CounterThread ct = new CounterThread(counter);
            ct.start();
            // ct.join(1000); // коллизий нет, котому что ждём, пока поток завершится (отработает)
        }
        Thread.sleep(10);
        
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
    
    public CounterThread(Counter counter) {
        // System.out.println("Создаётся CounterThread");
        this.counter = counter;
    }
    
    @Override
    public void run() {
        for(int i=0; i<1000; i++) {
            counter.increaseCounter();
        }
    }
}
