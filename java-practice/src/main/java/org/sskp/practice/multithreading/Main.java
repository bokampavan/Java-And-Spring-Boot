package org.sskp.practice.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());



        List<String> input = new ArrayList<>();
        List<String> output = new ArrayList<>();
        Future<List<String>> futureObj = poolExecutor.submit(new MyRunnable(input), output);

        try {
            futureObj.get();

            //1st way
            System.out.println("Runnable use cases ------: " );
            System.out.println("input: " + input.get(0));
           // System.out.println("output: " + output.get(0));

            //2nd way
            List<String> result = futureObj.get();
            System.out.println("future obj return:" + result.get(0));

        } catch (Exception e) {
         //   throw new RuntimeException(e);
            System.out.println("Error in Runnable Exception: " + e.getMessage());
        }


        Future<List<Integer>> futureObj2 = poolExecutor.submit(() -> {
            System.out.println("use case:1 ------- 1st runnable obj");
            List<Integer> listObj = new ArrayList<>();
            listObj.add(2);
            return listObj;
        });

        try {
            List<Integer> result1 = futureObj2.get();
            System.out.println("future obj return Value:" + result1.get(0));

        } catch (Exception e) {
//            throw new RuntimeException(e);
            System.out.println("Error in callable Exception: " + e.getMessage());
        }
    }
}
