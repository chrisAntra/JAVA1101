package week1;

import javax.print.attribute.standard.NumberOfInterveningJobs;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Day4 {
    volatile static boolean flag=true;

    public static void main(String[] args) {


        Thread t1 = new Thread(()->{
            try{
                Thread.sleep(1000);
            }catch (Exception ex){
                System.out.println(ex);
            }
            System.out.println("t1 end sleeping");
            flag=false;
        });
        Thread t2 = new Thread(()->{
            while(flag){}
            System.out.println("i am out from the while loop!");
        });
        t1.start();
        t2.start();

    }
}
/**
 * 1.volatile(itself cannot provide threadsafe)
 *      1.visibility
 *      2. m fence
 *      3.prevent your code from reording
 */

/**
 * 2. CAS compare and set (atomic two step happen in one instruction) optimistic lock
 * true
 * false
 * while(!CAS){}
 *   num =0
 *    t1                      t2
 *    1 curr==old read(exp)
 *   Unsafe.compareAndSet(object, (offset)attribute, old+1, old)
 */
class TestAtomicLibrary {
    static AtomicInteger ai = new AtomicInteger();

    public static void main(String[] args) throws Exception{
        Thread t1 = new Thread(()->{
            for(int i=0;i<10000;i++) {
                ai.incrementAndGet();
            }
        });
        Thread t2 = new Thread(()->{
            for(int i=0;i<10000;i++) {
                ai.incrementAndGet();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(ai.get());

    }
}

/**
 * 1.synchronized
 * 2.volatile +cas
 *      Hashtable                vs             ConcurrentHashMap
 *      synchronized whole table                synchronized on the node bucket level, n bucket, at most n thread can work concurrently on this map
 *
 */
/**
 * Deadlock
 *                       t1                        t2
 *   holding          lock1                       lock2
 *   try to acquire   lock2                       lock1
 *   Solution:
 *       1.lock in order
 *       2. set time out, when cannot acquire the lock for certain time, then give up the lock it is holding
 *       3. try lock
 */
class TestDeadLock{
    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();
        Thread t1 = new Thread(()->{
            try{
                synchronized (lock1){
                    Thread.sleep(1000);
                    System.out.println("t1 trying lock2");
                    //time out
                    //lock1.wait();
                    synchronized (lock2){
                        System.out.println("t1 finihsed");
                    }
                }
            }catch (Exception ex){}
        });
        //lock1 waiting list: t1 t3 t4 t5 (queue)
        //wait list1: t1
        //wait list2: t3
        Thread t2 = new Thread(()->{
            try{
                synchronized (lock1){
                    Thread.sleep(1000);
                    System.out.println("t2 trying lock1");
                    synchronized (lock2){
                        System.out.println("t2 finihsed");
                        //lock1.notify();
                    }
                }
            }catch (Exception ex){}
        });
        t1.start();
        t2.start();
    }
}
/**
 * cons of synchronized:
 *      1. no try lock
 *      2. no fair lock
 *      3. no multiple waiting list
 */

/**
 * ReentrantLock
 *      1.lock.lock();
 *          lock.lock();
 *         tryLock()
 *         fair()
 *         Condition multiple waiting list
 */
class TestReentrantLock{
    static ReentrantLock lock = new ReentrantLock();
    static Condition list0 = lock.newCondition();
    static Condition list1 = lock.newCondition();

    public static void main(String[] args) throws Exception{
        Thread t1 = new Thread(()->{
            try {
                lock.lock();
                System.out.println("t1 has the lock, and then release");
                //critical part...
                list0.await();
                System.out.println("t1 is woke up");
                lock.unlock();
            }catch (Exception ex){
                System.out.println(ex);
            }
        });
        Thread t2 = new Thread(()->{
            try {
                Thread.sleep(1000);
                lock.lock();
                //critical part...
                System.out.println("t2 has the lock");
                Thread.sleep(2000);
                System.out.println("t2 end sleeping");
                list0.signal();
                lock.unlock();
            }catch (Exception ex){
                System.out.println(ex);
            }
        });
        t1.start();
        t2.start();

    }
}

/**
 * Blocking queue
 * producer -> queue[][][][][] -> consumer
 *  when queue is full , block producer
 *  when queue is empty, block consumer
 *  when consumer consume sth, notify the producer
 *  producer produce sth, notify the consumer
 */
class MyBlockingQueue{
    int capacity=16;
    ReentrantLock lock = new ReentrantLock();
    Condition producerList = lock.newCondition();
    Condition consumerList = lock.newCondition();
    Queue<Integer> queue = new LinkedList<>();//FIFO queue
    //method called by producer
    boolean offer(int ele) throws Exception{
        lock.lock();
        while(queue.size()==capacity) {
            //put thread into block and wait for notify
            producerList.await();
        }
        queue.offer(ele);
        consumerList.signal();
        lock.unlock();
        return true;
    }
    //called by consumer
    int poll() throws Exception{
        lock.lock();
        while(queue.isEmpty()){
            consumerList.await();
        }
        int v = queue.poll();
        producerList.signal();
        lock.unlock();
        return v;
    }

}
class TestTheQueue {
    public static void main(String[] args) {
        MyBlockingQueue myBlockingQueue = new MyBlockingQueue();
        //producer thread
        Thread t1 = new Thread(()->{
            try{
                for(int i=0;i<20;i++) {
                    myBlockingQueue.offer(i);
                }
            }catch (Exception ex){
                System.out.println(ex);
            }
        });
        //Consumer thread
        Thread t2 = new Thread(()->{
            try{
                for(int i=0;i<20;i++){
                    System.out.println(myBlockingQueue.poll());
                }
            }catch (Exception ex){}
        });
        t1.start();
        t2.start();

    }
}
/**
 * ThreadPool
 *      create a thread is expensive
 *      blocking queue(task0, task1)
 *      [ta][ta][ta][ta][ta][ta1]core0 core1 +2extra
 *      pool(worker0, worker1)
 */

/**
 * Executor, ExecutorService provide us method to work with pool
 * Executors,ThreadPoolExecutor
 */
class TestThreadPool{
    public static void main(String[] args) throws Exception{
        //Executors.newSingleThreadExecutor();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 4, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        System.out.println(pool.getPoolSize());
        pool.execute(()->{
            System.out.println(Thread.currentThread().getName());
            System.out.println("this is task2");
        });
        Thread.sleep(1000);
        System.out.println(pool.getActiveCount());
        System.out.println(pool.getPoolSize());
        Thread.sleep(1000);
        pool.execute(()->{
            System.out.println(Thread.currentThread().getName());
            System.out.println("this is task3");
        });
        System.out.println(pool.getPoolSize());
        pool.shutdown();

    }
}