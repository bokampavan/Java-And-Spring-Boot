package org.sskp.practice.multithreading;

import java.util.concurrent.*;

public class CompletableFutureExample {

    public static void sleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            //                throw new RuntimeException(e);
            System.out.println("Exception during thread sleep" + e.getMessage());
        }
    }

    public static void print(CompletableFuture<String> asyncTask) {
        try {
            System.out.println(asyncTask.get());
        }  catch (Exception e) {
//            throw new RuntimeException(e);
            System.out.println("error during print" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(6, 6, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

//            supplysyncTask1 + thenApply
        CompletableFuture<String> futureObj1 = CompletableFuture.supplyAsync(() -> {
//            CompletableFutureExample.sleep(1000);
            return "supplysyncTask1: " + Thread.currentThread().getName();
        }, poolExecutor).thenApply((String val) -> {
            return val + " and thenApply: " + Thread.currentThread().getName();
        });
        CompletableFutureExample.print(futureObj1);

        //supplysyncTask1 + thenApplyAsync
        CompletableFuture<String> futureObj2 = CompletableFuture.supplyAsync(() -> {
            CompletableFutureExample.sleep(1000);
            return "supplysyncTask2: " + Thread.currentThread().getName();
        }, poolExecutor).thenApplyAsync((String val) -> {
            return val + " and thenApplyAsync: " + Thread.currentThread().getName();
        }, poolExecutor);
        CompletableFutureExample.print(futureObj2);


        //supplysyncTask1 + thenComposeAsync
        CompletableFuture<String> futureObj3 = CompletableFuture.supplyAsync(() -> {
            CompletableFutureExample.sleep(1000);
            return "supplysyncTask3: " + Thread.currentThread().getName();
        }, poolExecutor).thenComposeAsync((String val) ->{
            return CompletableFuture.supplyAsync(() -> {
                CompletableFutureExample.sleep(1000);
                return val + " thenComposeAsync Task3: " + Thread.currentThread().getName();
            }, poolExecutor);
        });
        CompletableFutureExample.print(futureObj3);

        //supplysyncTask1 + thenAcceptAsync
        CompletableFuture<Void> futureObj4 = CompletableFuture.supplyAsync(() -> {
            CompletableFutureExample.sleep(1000);
            return "supplysyncTask4: " + Thread.currentThread().getName();
        }, poolExecutor).thenAcceptAsync((String val) -> {
            CompletableFutureExample.sleep(1000);
            System.out.println( val + " and thenAcceptAsync: " + Thread.currentThread().getName());
        }, poolExecutor);

        //supplysyncTask1 + thenCombineAsync
        CompletableFuture<String> futureObj5= futureObj1.thenCombineAsync(futureObj2, (String val1, String val2) -> {
            CompletableFutureExample.sleep(2000);
            return "combineTask5: {" + val1 + val2 + "} :" + Thread.currentThread().getName();
        }, poolExecutor);
        CompletableFutureExample.print(futureObj5);
    }
}
