
### 笔记
## 1.并发编程线程基础

### 1.1 进程与线程

线程状态：运行 阻塞 就绪  

进程是资源分配的基本单位，线程是独立调度的基本单位  
cpu资源分配给线程  
java中 main函数所在的线程是主线程  
一个进程可以有多个线程，多个线程之间共享 堆 和 方法区  
每个线程有自己独立的 程序计数器 和 栈  

### 1.2线程创建与运行
多线程的三种方式:  
  1. 继承Thread类
     * 获取当前线程直接用this(Thread.currentThread())
      * java不支持多继承，任务和代码没有分离
      * 没有返回值
  2. 实现Runnable方法
     * 没有返回值
  3. FutureTask
     * ft.get()获取返回值

### 1.3 线程通知与等待 Object
1. wait()函数  
    * 阻塞当前线程
      * 其他线程调用notify(), notifyAll()
      * 其他线程调用interrupt() 该线程抛出InterruptedException异常
    * 释放已获取的锁  
    如果调用wait()的线程没有获取该对象的监视器锁，会抛出IllegalMonitorStateException异常
    ```
    避免虚假唤醒
    synchronized(obj) {
        while (!satisfied) {
            obj.wait();
        }
    }
    ```
2. wait(long timeout)函数  
如果没有在给定时间timeout内调用notify()或者notify()唤醒，会因超时而返回  
wait(0) == wait() 传入负数 -> 抛出IllegalArgumentException  
3. wait(long timeout, int nanos)函数  
当nanos>0时, timeout递增(每次while循环都会导致等待时间增加)
    ```
    synchronized (obj) {
        while (<condition does not hold> and <timeout not exceeded>) {
            long timeoutMillis = ... ; // recompute timeout values
            int nanos = ... ;
            obj.wait(timeoutMillis, nanos);
        }
        ... // Perform action appropriate to condition or timeout
    }
   ```
4. notify()函数
   当一个线程调用共享对象的notify()方法后，会唤醒一个在该共享变量上调用wait()方法后被挂起的线程  
   一个共享变量上可能会有多个线程在等待，具体唤醒哪一个是随机的  
   被唤醒的线程要去和其他线程一起竞争该锁  
   o
5. notifyAll()函数
   会唤醒在该共享变量上由于调用wait()函数而被挂起的线程  

### 1.4 等待线程执行终止的join()方法 Thread
   void Thread.join()

### 1.5 线程睡眠sleep() Thread
   让出执行权，不释放该线程拥有的监视器资源  

### 1.6 CPU让出执行权 yield() Thread
   时间片还有，不想用了，主动进入就绪状态 **其他都是阻塞**

### 1.7 线程中断
   1. void interrupt() 设置线程的中断标志位true并立刻返回  
     如果线程正在因为wait, join, sleep而被阻塞，则抛出InterruptedException异常  
   2. boolean isInterrupted() 检测当前线程是否被中断  
   3. boolean interrupted() 检测当前线程是否被中断，并清除中断标志

### 1.8 线程上下文切换
   时机：当前线程的CPU时间片用完处于就绪状态，当前线程被其他线程中断

### 1.9 线程死锁
  1. 同时符合四个条件
     1. 互斥(某项资源同一时刻只能被一个线程使用)
     2. 不可抢占(线程主动释放资源前，其他线程不能抢占)
     3. 循环等待(多个线程之间循环等待其他资源)
     4. 请求和保持(已获得资源的线程继续请求其他资源是不释放已获得的)
  2. 避免死锁
     1. 破环请求和保持 (一次性获取所有资源)
     2. 破环循环等待 (规定获取资源顺序)

### 1.10 守护线程 用户线程
   thread.setDaemon(true)  
   所有的用户线程结束后，结束JVM进程

### 1.11 ThreadLocal
   创建一个ThreadLocal变量，访问该变量的每个线程都会有一个该变量的本地副本；操作该变量时，操作的是
自己本地内存里的变量，避免线程安全问题。













