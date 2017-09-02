package my.tutorial.counter;
 
public class CounterTester
{
    private static int counter = 0;
    private static int aliveCount = 0;
    
    public static int getCounter() { return counter; }
    public synchronized void increaseCounter() { counter++; }


    public static void main(String[] args) throws InterruptedException {
        
        CounterTester tester = new CounterTester();
        
        int max = 2;
        aliveCount = max;

        CounterThread[] threadPool = new CounterThread[max];

        // создаю группу потоков
        for(int i=0; i<max; i++) {
            CounterThread ct = new CounterThread(tester, i);
            threadPool[i] = ct;
            threadPool[i].start();
        }
        
        // жду завершения и считаю
        while (0 != aliveCount) {
            try { 
                tester.wait();
            } catch (InterruptedException e) {
                aliveCount--;
            }
        }
        
        // вывожу результат
        System.out.println("Счётчик=" + getCounter());
    }
}
 
class CounterThread extends Thread
{
    private CounterTester tester;
    private int index = 0;
    
    public CounterThread(CounterTester tester, int index) {
        this.tester = tester;
        this.index = index;
        // System.out.println("Создаётся CounterThread-"+this.index);
    }
    
    @Override
    public void run() {
        for(int i=0; i<1000; i++) { 
            tester.increaseCounter();
        }

        try { 
			Thread.sleep(10); 
		} catch (InterruptedException e) {
			System.out.println("CounterThread-"+index+": поймано исключение InterruptedException");
		} finally {
            // System.out.println("CounterThread-"+index+": обязательный блок в CounterThread");
            tester.notify();
        }
    }
}
