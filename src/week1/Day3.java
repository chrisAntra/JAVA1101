package week1;
import java.util.*;
public class Day3 {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
    }
    void method1(int param){

    }
}
/**
 * 1. JVM memory model
 *      heap  stack(stack frame(local rv))()
 *      stack:
 *          local rv
 *          method paramter
 *          each thread will have its own stack
 *      heap:
 *          object
 *          new Object() -> create in eden -> after several gc->promote to sur1/sur2 -> promote to old
 *                            [o1  o3 ]
 *
 *          [eden][survivor1][survivor2] young generation (serial GC/parallel GC/ parallel New GC)
 *          [                          ] old generation (serial GC/ parallel old gc/ CMS concurrent mark sweep)
 *          [                          ] meta space,method area
 *          Deamon thread
 *          System.gc();
 *          STOP THE WORLD
 *          CMS:
 *              1.initial mark(STW)
 *              2. concurrent mark
 *              3. final mark(STW)
 *              4. concurrent sweep
 *          minor gc: (young) parallel New GC
 *          major gc: (old) parallel old gc, CMS
 *          full gc: G1
 *          g1: e->s->o
 *          [][][][s][e][][]
 *          [][unused][o][][][o][]
 *          ...
 *          [][][][][][o][]
 *          gc root
 *          classLoader, class,
 *
 */

/**
 * 2. JAVA Multithreading
 *      Create a new thread:
 *          1. extends thread class
 *          2. implement runnable provide a task to your thread
 *      LifeCycle of thread:
 *          1.new Thread(): new state
 *          2.start(): active running/runable one core do multithread
 *          3.sleep() wait(): block wait
 *          4. terminated
 *      ThreadPool
 *
 */
class TestThread extends Thread {

    @Override
    public void run(){
        try{
            Thread.sleep(2000);
        }catch (Exception ex){
            System.out.println(ex);
        }

        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) throws Exception{
        System.out.println(Thread.currentThread().getName());
        //Thread.sleep(2000);
        TestThread testThread = new TestThread();
//        //testThread.run();
        testThread.start();
        //testThread.start();
        int i=10;
        Thread thread = new Thread(new Task());
        Thread thread1 = new Thread(()->{

            System.out.println(Thread.currentThread().getName());
        });
        //thread.start();
    }

}
class Task implements Runnable{
    @Override
    public void run(){

        System.out.println(Thread.currentThread().getName());
    }
}
/**
 * Race condition
 *    num=0;
 *    thread0(+1) thread1(+1)
 *    num=1
 *                    num=1+1=2
 *                    main  t1  t2
 *                          mon
 */

/**
 * 3. synchronized
 *      monitor
 */
class TestRaceCondition {
    static volatile int num1=0;
    static int num=0;
    static void add(){
        num1++;
    }

    public static void main(String[] args) throws Exception{
        Object lock = new Object();
        Thread t1 = new Thread(()->{
            for(int i=0;i<10000;i++) {
                //this is critical code block
//                synchronized (lock){
//                    num++;
//                }
                add();

            }
        });
        Thread t2 = new Thread(()->{
            for(int i=0;i<10000;i++) {
//                num++;
//                synchronized (lock){
//                    num++;
//                }
                add();

            }
        });
        t1.start();
        t2.start();
        //block the main, to let main to wait for t1 and t2 to finished
        t1.join();
        t2.join();
        System.out.println(num1);
    }

}

/**
 * 4.volatile read most updated data
 *      t1       t2
 *      cache    cache
 *      memory
 */