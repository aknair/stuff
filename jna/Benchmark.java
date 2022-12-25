import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Benchmark {
  public static void task() throws Exception {
    long startTime = System.nanoTime();
    Random rnd = new Random();
    int delay = rnd.nextInt(450)+50;
    System.out.println("Thread id : "+Thread.currentThread().getName()+" random delay calculated is "+delay);
    Thread.sleep(delay);
    long endTime = System.nanoTime();
    long elapsedTime = endTime - startTime;
    System.out.println("Thread id : "+Thread.currentThread().getName()+" elapsed time is "+elapsedTime/1000 + " microseconds");    
  }
  public static void main(String[] args) throws Exception {
    int numThreads = 4; 
    int numIterations = 10; 
    ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    long startTime = System.nanoTime();
    for (int i = 0; i < numIterations; i++) {
      executor.submit(new Runnable() {
        public void run() {
          try{
          task();
          }
          catch (Exception e){
          System.out.println(e.getMessage());
          }
          finally{
          }
        }
      });
    }
    executor.shutdown();
    executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    long elapsedTime = System.nanoTime() - startTime;
    double elapsedTimeInSeconds = (double) elapsedTime / 1_000_000_000;
    System.out.printf("Total elapsed time: %.3f seconds%n", elapsedTimeInSeconds);
  }
}
