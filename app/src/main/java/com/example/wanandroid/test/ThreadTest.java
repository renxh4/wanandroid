package com.example.wanandroid.test;

public class ThreadTest {
    public void test(){
        Wait_Notify_100 waitNotify100 = new Wait_Notify_100();
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                waitNotify100.printABC(0);
            }
        },"Thread1");
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                waitNotify100.printABC(1);
            }
        },"Thread2");
        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
                waitNotify100.printABC(2);
            }
        },"Thread3");

        threadA.start();
        threadB.start();
        threadC.start();
    }

    static class Wait_Notify_100 {

        private int num;
        private static final Object LOCK = new Object();
        private int maxnum = 10;

        private void printABC(int targetNum) {
            while (true) {
                synchronized (LOCK) {
                    while (num % 3 != targetNum) { //想想这里为什么不能用if代替，想不起来可以看公众号上一篇文章
                        if (num >= maxnum) {
                            break;
                        }
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (num >= maxnum) {
                        break;
                    }
                    num++;
                    System.out.println(Thread.currentThread().getName() + ": " + num);
                    LOCK.notifyAll();
                }
            }

        }
    }


}
