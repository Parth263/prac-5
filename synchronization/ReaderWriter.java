import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class ReaderWriter {

    static Semaphore mutex = new Semaphore(1);
    static Semaphore wrt = new Semaphore(1);
    static int readCount = 0;
    static String message = "Hello";

    static class Reader implements Runnable {
        public void run() {
            try {
                mutex.acquire();
                readCount++;
                if (readCount == 1) {
                    wrt.acquire();
                }
                mutex.release();

                System.out.println("Thread " + Thread.currentThread().getName() + " is READING: " + message);
                Thread.sleep(1500);
                System.out.println("Thread " + Thread.currentThread().getName() + " has FINISHED READING");

                mutex.acquire();
                readCount--;
                if (readCount == 0) {
                    wrt.release();
                }
                mutex.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static class Writer implements Runnable {
        public void run() {
            try {
                wrt.acquire();
                message = "Good Morning";
                System.out.println("Thread " + Thread.currentThread().getName() + " is WRITING: " + message);
                Thread.sleep(1500);
                System.out.println("Thread " + Thread.currentThread().getName() + " has finished WRITING");
                wrt.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of Readers: ");
        int numReaders = sc.nextInt();
        System.out.print("Enter the number of Writers: ");
        int numWriters = sc.nextInt();
        
        // Create and start Reader threads
        for (int i = 1; i <= numReaders; i++) {
            Thread readerThread = new Thread(new Reader());
            readerThread.setName("Reader" + i);
            readerThread.start();
        }

        // Create and start Writer threads
        for (int i = 1; i <= numWriters; i++) {
            Thread writerThread = new Thread(new Writer());
            writerThread.setName("Writer" + i);
            writerThread.start();
        }

        sc.close();
    }
}