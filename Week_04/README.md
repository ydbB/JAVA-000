### 周四：
2.写出尽可能多的方法实现 Fibonacci(36) 的解法

1. basecase

```java

/**
 * @author debaoyang
 */
public class BaseCase2GetSum {
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        // 在这里创建一个线程/线程池

        //异步执行下面的方法

        int result = sum();

        //确保得到结果并输出
        System.out.println("异步计算结果：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - startTime) + "ms");
    }

    private static int sum() {
        return fib(36);
    }

    private static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n-1) + fib(n-2);
    }
}

```

2. Loop2GetSum

```java

/**
 * @author debaoyang
 * 使用 循环 的方式得到 fib 值
 */
public class Loop2GetSum {
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        // 在这里创建一个线程/线程池

        //异步执行下面的方法

        int result = sum();

        //确保得到结果并输出
        System.out.println("异步计算结果：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - startTime) + "ms");
    }

    private static int sum() {
        return fib(36);
    }

    private static int fib(int n) {
        //if (n == 0) return 0;
        //if (n == 1) return 1;
        //return fib(n-1) + fib(n-2);
        int first = 0;
        int second = 1;

        for (int i = 0; i < n; ++ i) {
            int temp = first + second;
            first = second;
            second = temp;
        }
        return first;
    }
}
```
3. AtomicInteger2GetSum

```java
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author debaoyang
 */
public class AtomicInter2GetSum {
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        AtomicInteger value = new AtomicInteger();
        // 在这里创建一个线程/线程池
        Thread task = new Thread(() -> {
            value.set(sum());
        });

        task.start();
        task.join();//

        //异步执行下面的方法

        int result = value.get();

        //确保得到结果并输出
        System.out.println("异步计算结果：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - startTime) + "ms");
    }

    private static int sum() {
        return fib(36);
    }

    private static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n-1) + fib(n-2);
    }
}

```
5. Lock2GetSum

```java
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author debaoyang
 *
 */
public class Lock2GetSum {
    private volatile Integer value = null;
    private Lock lock = new ReentrantLock();
    private Condition jobDown = lock.newCondition();
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        // 在这里创建一个线程/线程池
        Lock2GetSum worker = new Lock2GetSum();
        Thread task = new Thread(() -> {
            worker.sum(36);
        });
        task.start();

        //异步执行下面的方法

        int result = worker.getValue();

        //确保得到结果并输出
        System.out.println("异步计算结果：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - startTime) + "ms");
    }

    public  void sum(int n) {
        lock.lock();
        try {
            value = fib(n);
            jobDown.signal();
        } finally {
            lock.unlock();
        }
    }

    public int getValue() throws InterruptedException{
        lock.lock();
        try {
            while (value == null) {
                jobDown.await();
            }
        } finally {
            lock.unlock();
        }
        return value;
    }

    private static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n-1) + fib(n-2);
    }
}

```
6. Synchronized2GetSum

```java
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author debaoyang
 *
 */
public class Lock2GetSum {
    private volatile Integer value = null;
    private Lock lock = new ReentrantLock();
    private Condition jobDown = lock.newCondition();
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        // 在这里创建一个线程/线程池
        Lock2GetSum worker = new Lock2GetSum();
        Thread task = new Thread(() -> {
            worker.sum(36);
        });
        task.start();
        //task.join();

        //异步执行下面的方法

        int result = worker.getValue();

        //确保得到结果并输出
        System.out.println("异步计算结果：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - startTime) + "ms");
    }

    public  void sum(int n) {
        lock.lock();
        try {
            value = fib(n);
            jobDown.signal();
        } finally {
            lock.unlock();
        }
    }

    public int getValue() throws InterruptedException{
        lock.lock();
        try {
            while (value == null) {
                jobDown.await();
            }
        } finally {
            lock.unlock();
        }
        return value;
    }

    private static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n-1) + fib(n-2);
    }
}

```
7. Semaphore2GetSum

```java
import java.util.concurrent.Semaphore;

/**
 * @author debaoyang
 *
 */
public class Semaphore2GetSum {
    private volatile Integer value = null;
    private Semaphore semaphore = new Semaphore(1);
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        // 在这里创建一个线程/线程池
        Semaphore2GetSum worker = new Semaphore2GetSum();
        Thread task = new Thread(() -> {
            try {
                worker.sum(36);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        task.start();
        //task.join();

        //异步执行下面的方法

        int result = worker.getValue();

        //确保得到结果并输出
        System.out.println("异步计算结果：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - startTime) + "ms");
    }
    public  void sum(int n) throws InterruptedException {
        semaphore.acquire(); // 获取信号量
        value = fib(n);
        semaphore.release(); //释放信号量
    }

    public int getValue() throws InterruptedException{
        int result = 0;
        while (value == null) {} // main 线程会先拿到信号量
        semaphore.acquire();
        result = value;
        semaphore.release();
        return result;
    }

    private static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n-1) + fib(n-2);
    }
}

```
8. CyclicBarrier2GetSum

```java
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author debaoyang
 *
 */
public class CyclicBarrier2GetSum {
    private volatile Integer value = null;
    private CyclicBarrier barrier = null;
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        // 在这里创建一个线程/线程池
        CyclicBarrier2GetSum worker = new CyclicBarrier2GetSum();
        CyclicBarrier barrier = new CyclicBarrier(1, () -> {
            int result = 0;
            result = worker.getValue();
            //确保得到结果并输出
            System.out.println("异步计算结果：" + result);

            System.out.println("使用时间：" + (System.currentTimeMillis() - startTime) + "ms");

        });
        worker.setBarrier(barrier);
        Thread task = new Thread(() -> {
            try {
                worker.sum(36);
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        task.start();
        //task.join();

        //异步执行下面的方法

//        int result = worker.getValue();

    }

    public  void sum(int n) throws BrokenBarrierException, InterruptedException {
        value = fib(n);
        barrier.await();
    }

    public int getValue() {
        while (value == null) {
            ;
        }
        return value;
    }

    public void setBarrier(CyclicBarrier ba) {
        barrier = ba;
    }

    private static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n-1) + fib(n-2);
    }
}

```
9. CountDownLatch2GetSum

```java
import java.util.concurrent.CountDownLatch;

/**
 * @author debaoyang
 *
 */
public class CountDownLatch2GetSum {
    private volatile Integer value = null;
    private CountDownLatch latch = new CountDownLatch(1);
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        // 在这里创建一个线程/线程池
        CountDownLatch2GetSum worker = new CountDownLatch2GetSum();
        Thread task = new Thread(() -> {
            worker.sum(36);
        });
        task.start();
        //task.join();

        //异步执行下面的方法

        int result = worker.getValue();

        //确保得到结果并输出
        System.out.println("异步计算结果：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - startTime) + "ms");
    }

    public  void sum(int n) {
        value = fib(n);
        latch.countDown();
    }

    public int getValue() throws InterruptedException{
        latch.await();
        return value;
    }

    private static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n-1) + fib(n-2);
    }
}

```
10. CompletableFuture2GetSum

```java
import java.util.concurrent.CompletableFuture;

/**
 * @author debaoyang
 *
 */
public class CompletableFuture2GetSum {
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        // 在这里创建一个线程/线程池
        int result = CompletableFuture.supplyAsync(() -> sum(36)).join();

        //异步执行下面的方法

        //确保得到结果并输出
        System.out.println("异步计算结果：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - startTime) + "ms");
    }

    public static int sum(int n) {
        return fib(n);
    }

    private static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n-1) + fib(n-2);
    }
}

```
### 周六
4. （必做）把多线程和并发相关知识带你梳理一遍，画一个脑图，截图上传到 Github 上。

如下图所示
![](./Java%20并发编程.png)