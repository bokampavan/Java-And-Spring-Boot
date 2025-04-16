package org.sskp.practice.multithreading;

import java.util.concurrent.*;

public class FutureExample {

    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        System.out.println("Main Thread before submitting task: " + Thread.currentThread().getName());

        // to get the status of runnable
        Future<?> futureObj1 = poolExecutor.submit(() -> {
            System.out.println("Task1 with Runnable: " + Thread.currentThread().getName());
            try {
                Thread.sleep(7000);
            } catch (Exception e) {
                //
            }
            System.out.println("after 7 seconds waiting ......");
        });


        System.out.println("is task done: " + futureObj1.isDone()); // false
        try {
            System.out.println("waiting for 2 seconds: " + futureObj1.get(2, TimeUnit.SECONDS));
        } catch (Exception e) {
            System.out.println("throwing time out exception as i did not recieve the response in time(2s): " + e.getMessage());
        }

        try {
            Object obj = futureObj1.get();
            System.out.println("futureObj1 response indefinite waiting using get: " + obj);
        } catch (Exception e) {
            //
        }

        futureObj1.cancel(false);
        System.out.println("Future Obj isCancelled: " + futureObj1.isCancelled()); //false
        System.out.println("Task with Runnable:");

    }
}
