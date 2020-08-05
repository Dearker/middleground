package com.hanyi.daily.thread;


import com.hanyi.daily.thread.pojo.Res;

public class ThreadLocalDemo extends Thread {

    private final Res res;

    private ThreadLocalDemo(Res res) {
        this.res = res;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println(Thread.currentThread().getName() + "," + res.getNumber());
        }
    }

    public static void main(String[] args) {
        Res res = new Res();
        ThreadLocalDemo t1 = new ThreadLocalDemo(res);
        ThreadLocalDemo t2 = new ThreadLocalDemo(res);
        t1.start();
        t2.start();
    }
}
