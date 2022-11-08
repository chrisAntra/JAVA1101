package week2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class Day6 {
    public static void main(String[] args) throws Exception{
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Integer> future = es.submit(()->{
            Thread.sleep(3000);
            return 10;
        });
        System.out.println(future.get());
        System.out.println("main resume");
    }
}
/**
 * CompletableFuture compare with future
 *      implements future interface
 *      Fully asynchronized
 *      chain operation
 *      customized the pool used, default is forkJoinPool
 *      help us to gather a list of cf result
 *      allOf, anyOf
 *
 */
class TestCF{
    public static void main(String[] args) throws Exception{
        Executor es = Executors.newSingleThreadExecutor();
        Supplier<Integer> supplier = new Supplier() {
            @Override
            public Integer get() {
                try{
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName());
                }catch (Exception ex){
                    System.out.println(ex);
                }

                return 10;
            }
        };
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(supplier, es);
        //main thread          forkJoinPool -> worker thread
        cf.thenAcceptAsync(x->{
            //2nd step
            System.out.println(Thread.currentThread().getName());
            System.out.println(2*x);
        });
        System.out.println("main is running here");
        cf.join();
    }
}
class TestAllOf{
    static int ind=0;
    static int res=0;
    public static void main(String[] args) throws Exception{
        List<CompletableFuture<Integer>> completableFutures = new LinkedList<>();
//        CompletableFuture[] completableFutures1 = new CompletableFuture[3];
//        CompletableFuture<Integer> cf0 = CompletableFuture.supplyAsync(()->{
//            //making a request to some api
//
//            System.out.println(Thread.currentThread().getName()+" sending request: "+1);
//            return 1;
//        });
//        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(()->{
//            //making a request to some api
//            System.out.println(Thread.currentThread().getName()+" sending request: "+2);
//            return 2;
//        });
//        CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(()->{
//            //making a request to some api
//            try{
//                Thread.sleep(3000);
//            }catch (Exception ex){
//                System.out.println(ex);
//            }
//            System.out.println(Thread.currentThread().getName()+" sending request: "+3);
//            return 3;
//        });
//        completableFutures.add(cf0);
//        completableFutures.add(cf1);
//        completableFutures.add(cf2);
        for(int i=0;i<3;i++) {
            System.out.println(i);
            final int tmpi = i;
            //cf0 cf1 cf2

            CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(()->{
                //making a request to some api
                try{
                    Thread.sleep(3000);
                }catch (Exception ex){
                    System.out.println(ex);
                }
                synchronized (TestAllOf.class){
                    ind++;
                    System.out.println(Thread.currentThread().getName()+" sending request: "+ind);

                }
                return ind;
            });
//            completableFutures1[i]=cf;
            completableFutures.add(cf);
        }
        System.out.println("in main0");
        CompletableFuture signal = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));
        CompletableFuture<Integer> ans = signal.thenApply(Void->{
            System.out.println("step2");
            try{

                for(int i=0;i<3; i++) {
                    int temp = completableFutures.get(i).get();
                    System.out.println(temp);
                    res+=temp;
                }
            }catch (Exception ex){
                //System.out.println("here");
                System.out.println(ex);
            }
            return res;
        });
        System.out.println("in main1");
        //signal.join();
        System.out.println(ans.get());
    }
}