package org.sskp.practice.multithreading;

import java.util.List;

public class MyRunnable implements Runnable {
    private List<String> list;

    public MyRunnable(List<String> inputList) {
        this.list = inputList;
    }

    @Override
    public void run() {
        list.add("30");
    }
}
