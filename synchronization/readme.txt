I'll explain the Reader-Writer Problem and the key concepts in detail.

The Reader-Writer Problem is a classic synchronization problem in concurrent programming that deals with a scenario where:
1. Multiple processes/threads need to access shared data
2. Some processes want to read the data (readers)
3. Some processes want to modify the data (writers)

Key Concepts:

1. Concurrent Access Rules:
- Multiple readers can read simultaneously (parallel reading is allowed)
- Only one writer can write at a time (exclusive access)
- When a writer is writing, no reader should be allowed to read
- When at least one reader is reading, no writer should be allowed to write

2. Synchronization Mechanisms Used:
   
a) Semaphores:
- Binary semaphores (mutex) - acts like a mutex lock for critical sections
- Counting semaphores (wrt) - controls writer access
- Semaphores ensure mutual exclusion and synchronization
- acquire() decrements the semaphore count
- release() increments the semaphore count

b) ReadCount:
- Shared variable tracking number of active readers
- Protected by mutex to prevent race conditions
- Used to manage first reader and last reader scenarios

3. First Reader-Last Reader Concept:
- First reader acquires the write lock (wrt)
- Subsequent readers can enter without acquiring write lock
- Last reader releases the write lock
- This ensures writers wait while any reader is active

4. Priority Considerations:
This implementation is neutral (neither reader nor writer priority):
- Readers get preference when they arrive first
- Writers get preference when they arrive first
- This can lead to potential starvation in high-load scenarios

5. Critical Sections:
The code implements three critical sections:
- Entering reader section (updating readCount)
- Reading section (accessing shared data)
- Exiting reader section (updating readCount)
- Writing section (modifying shared data)

6. Thread Safety:
Achieved through:
- Proper semaphore usage
- Protected shared variables
- Controlled access to critical sections
- Exception handling for interrupted operations

7. Resource Management:
- Proper acquisition and release of semaphores
- Handling of shared resources (message string)
- Clean thread termination
- Scanner resource management

This implementation provides a balanced approach to the reader-writer problem, ensuring:
- Data consistency
- Prevention of race conditions
- Deadlock avoidance
- Fair access to resources (though not strictly fair)
- Proper synchronization between readers and writers

The solution uses a combination of two semaphores to achieve this:
1. mutex: Ensures mutual exclusion for readCount updates
2. wrt: Controls access to the actual shared resource

This creates a hierarchical locking system where:
- Readers must acquire mutex to update readCount
- First reader must acquire wrt
- Writers must acquire wrt for exclusive access
- Last reader must release wrt

Would you like me to proceed with the detailed code explanation block by block in the next prompt?


I'll break down the code block by block:

1. Initial Setup and Imports:
```java
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class ReaderWriter {
    static Semaphore mutex = new Semaphore(1);  // For protecting readCount
    static Semaphore wrt = new Semaphore(1);    // For protecting actual writing
    static int readCount = 0;                    // Tracks number of active readers
    static String message = "Hello";             // Shared resource
```
- Two semaphores are initialized: mutex (1 permit) and wrt (1 permit)
- readCount tracks active readers
- message is the shared resource being read/written

2. Reader Class:
```java
static class Reader implements Runnable {
    public void run() {
        try {
            // Entry section
            mutex.acquire();
            readCount++;
            if (readCount == 1) {  // First reader
                wrt.acquire();
            }
            mutex.release();

            // Reading section
            System.out.println("Thread " + Thread.currentThread().getName() + " is READING: " + message);
            Thread.sleep(1500);
            System.out.println("Thread " + Thread.currentThread().getName() + " has FINISHED READING");

            // Exit section
            mutex.acquire();
            readCount--;
            if (readCount == 0) {  // Last reader
                wrt.release();
            }
            mutex.release();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
```
The Reader class has three critical sections:
- Entry Section:
  - Acquires mutex to safely increment readCount
  - First reader acquires wrt to block writers
  - Releases mutex to allow other readers

- Reading Section:
  - Performs the actual reading operation
  - Simulates work with Thread.sleep()

- Exit Section:
  - Acquires mutex to safely decrement readCount
  - Last reader releases wrt to allow writers
  - Releases mutex

3. Writer Class:
```java
static class Writer implements Runnable {
    public void run() {
        try {
            wrt.acquire();  // Get exclusive access
            message = "Good Morning";  // Modify shared resource
            System.out.println("Thread " + Thread.currentThread().getName() + " is WRITING: " + message);
            Thread.sleep(1500);
            System.out.println("Thread " + Thread.currentThread().getName() + " has finished WRITING");
            wrt.release();  // Release exclusive access
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
```
Writer class is simpler:
- Acquires wrt for exclusive access
- Modifies the shared message
- Simulates work with Thread.sleep()
- Releases wrt

4. Main Method:
```java
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
```
Main method handles:
- User input for number of readers and writers
- Creation and starting of reader threads
- Creation and starting of writer threads
- Proper resource cleanup (Scanner)

Key Implementation Features:
1. Thread Safety:
   - Protected access to readCount using mutex
   - Protected access to shared message using wrt
   - Proper semaphore acquisition/release ordering

2. Deadlock Prevention:
   - Readers never hold both semaphores simultaneously
   - Writers only need one semaphore
   - Consistent ordering of lock acquisition

3. Starvation Handling:
   - Neither readers nor writers get absolute priority
   - Access depends on thread scheduling and arrival order

4. Error Handling:
   - InterruptedException catching
   - Proper resource cleanup
   - Thread-safe operations

This implementation provides a classic solution to the reader-writer problem while maintaining thread safety and preventing common concurrent programming issues like race conditions and deadlocks.