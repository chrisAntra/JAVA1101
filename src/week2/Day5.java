package week2;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day5 {
    static int num=0;
    static List<Integer> list = Arrays.asList(1,2,3,4,5);
    public static void main(String[] args) throws Exception{
        Executor pool0 = Executors.newSingleThreadExecutor();
        ExecutorService pool1 =  Executors.newCachedThreadPool();
        ExecutorService pool2 = Executors.newFixedThreadPool(5);
        ScheduledExecutorService pool3 = Executors.newScheduledThreadPool(1);
//        pool3.schedule(()->{
//            System.out.println("a task");
//        },3000, TimeUnit.MILLISECONDS);
//        pool3.scheduleAtFixedRate(()->{
//            System.out.println("task run: "+ ++num+" times");
//        },3000,1000,TimeUnit.MILLISECONDS);
//        pool0.execute(()->{
//            System.out.println("here");
//        });
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //Thread.sleep(1000);
            }
        };
        Callable<Integer> callable0 = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(1000);
                System.out.println("in the callable");
                return 10;
            }
        };
//        Future<Integer> future = pool1.submit(callable0);
//        //get() is blocking
//        int i = future.get();
//        System.out.println(i);
//        //
//        i*=2;

        ForkJoinPool pool4 = new ForkJoinPool(3);
        Future f = pool4.submit(()->{
            System.out.println("in fork join pool");
            for(int i=0;i<list.size();i++) {
                System.out.println(Thread.currentThread().getName());
                list.set(i, 2*list.get(i));
            }
            System.out.println(list);
        });
        f.get();
//        pool4.execute(()->{
//            System.out.println("here");
//        });
//        pool4.execute(()->{
//            try{
//                for(int i=0;i<list.size();i++) {
//                    list.set(i, 2*list.get(i));
//                }
//                System.out.println(list);
//            }catch (Exception ex){
//                System.out.println(ex);
//            }
//
//        });


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
 * Executor, ExecutorService(interface) provide us method to work with pool
 * Executors,ThreadPoolExecutor
 *           ScheduleThreadPoolExecutor
 *           ForkJoinPool
 *           [1,2,3,4,5]-> [1,2]   [2,4] -> [2,4,6,8,10]
 *                         [3,4]   [6,8]
 *                         [5]     [10]
 */

/**
 * Java 8 new feature
 *  1. interface: default static private(java 9)
 *  2. Functional interface
 *  3. Lambda Expression
 *  4. Optional
 *  5. Stream/  ParallelStream
 *  6. CompletableFuture
 */
/**
 *  Functional interface
 *      only contains one abstract method
 *      runnable vs callable
 *      void        return type
 *      no throws   throws
 *
 *      Function
 *      Supplier  ()-> return xxx;
 *      Comparable
 *      Comparator
 *      Consumer xxx-> void
 *
 */
//@FunctionalInterface
//interface Inter1{
//    default void method0(){}
//    default void method1(){}
//    void abstrMethod0();
//
//}
class Student implements Comparable<Student>{
    int age;
    @Override
    public int compareTo(Student student){
        return this.age-student.age;
    }
}
class Test7{
    public static void main(String[] args) {
//        Consumer<Integer> consumer = new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) {
//
//            }
//        };
//        Comparable comparable = new Comparable() {
//            @Override
//            public int compareTo(Object o) {
//                return 0;
//            }
//        };
//        Student stu1 = new Student();
//
//        Comparator<Integer> comparator = new Comparator<>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return 0;
//            }
//        };
//        comparator.compare(stu1, stu2);
        Function<Integer, String> function = new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return ""+integer;
            }
        };
        String res = function.apply(10);
        //Inter1 inter1 = ()->{};
    }
}

/**
 * Lambda Expression (can only work with functional interface)
 *  ()->{}
 *  xx-> 10
 */
class TestLambda{
    public static void main(String[] args) {
//        Callable<Integer> callable= new Callable<Integer>() {
//            @Override
//            public Integer call() throws Exception {
//                return null;
//            }
//        };

        Callable<Integer> callable= ()-> 10;
        Consumer<String> consumer0 = new Consumer<String>() {
            @Override
            public void accept(String str) {

            }
        };
        Consumer<List> consumer = (list)->{
            System.out.println(list.add(1));


        };
        Supplier<HashMap> supplier = ()->{
            System.out.println("...other logic");
            return new HashMap();
        };
        consumer.accept(new LinkedList<>());

    }
}

/**
 * Optional(wrapper)
 *  avoid "null" keyword in code
 *  var==null
 *  opt(var)
 *  opt.isPresent()
 *      orElse()
 *      orElseGet()
 *
 */
class TestOptional{
    public static void main(String[] args) {
        Optional<Integer> opt = Optional.ofNullable(10);
        //System.out.println(opt.isPresent());
        //System.out.println(opt.get());

        System.out.println(opt.orElseGet(()->{
            System.out.println("value is null, so returning default value");
            //..more logic, instead just return something
            return 99;
        }));
//        Optional<Integer> opt= Optional.of(null);

    }
}

/**
 * Stream Api (single thread)
 *  for loop
 *  chain operation
 *      consist of intermediate step
 *                      map() x -> y
 *                      flatmap()  list of list -> list
 *                      filter()
 *                      mapToObj()
 *                      ....
 *                  termination step (only when this termination execute, the pipeline start running)
 *                      collect()
 *                      reduce()  reduce to one single object
 *                      forEach()
 */
class TestStream {
    public static void main(String[] args) {
        //List()    *2      >10      -1
//        List<Integer> list = new LinkedList<>();
//
//        for(int i=0;i<10;i++) {
//            list.add(i);
//        };
//        for(int i=0;i<list.size(); i++) {
//            list.set(i, list.get(i)*2);
//        }
//        Iterator<Integer> it = list.iterator();
//        while(it.hasNext()){
//            int curr = it.next();
//            if(curr<=10) it.remove();
//        }
//        System.out.println(list);
        //Stream Object(0, 1,2,3,4....9) -> stream(0, 2,4,6...) -> stream()
        Predicate<Integer> predicate = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer>10;
            }
        };



//        List<Integer> list = IntStream.range(0,10)
//                .parallel()
//                .map((ele)->{
//                    System.out.println("step 1: "+Thread.currentThread().getName()+" take:" +ele);
//                    return 2*ele;
//                })
//                .filter(x->{
//                    System.out.println("step 2: "+Thread.currentThread().getName());
//                    return x>10;
//                })
//                .mapToObj(x->x) //filter(Predicate predicate) when predicate return true, it will pass the ele to the next step
//                .collect(()->new LinkedList<Integer>(),
//                        (res, ele)->{
//                                        res.add(ele);
//                                    },
//                        (l1, l2)->{l1.addAll(l2);});
//        Stream stream = IntStream.range(0,10)
//                .parallel()
//                .map((ele)->{
//                    System.out.println("step 1: "+Thread.currentThread().getName()+" take:" +ele);
//                    return 2*ele;
//                })
//                .filter(x->{
//                    System.out.println("step 2: "+Thread.currentThread().getName());
//                    return x>10;
//                })
//                .mapToObj(x->x); //filter(Predicate predicate) when predicate return true, it will pass the ele to the next step
//
//        stream.collect(Collectors.toList());

        List<List<Integer>> list = new LinkedList<>();
        list.add(Arrays.asList(1,2));
        list.add(Arrays.asList(3,4));
        list.add(Arrays.asList(5,6));
        System.out.println(list);
        //Stream(list(1,2) ,(3,4),(5,6)) ->stream(1,2, 3,4,5,6)
        List<Integer> list1 = list.stream().flatMap((eleList)->{return eleList.stream();}).collect(Collectors.toList());
        System.out.println(list1);
        //.collect(Collectors.toList());
        //System.out.println(list);
    }
}