package threads;

public class ThreadExample {

    public static class MyThread extends Thread {
        public void run() {
            System.out.println("My thread is running");
            System.out.println("My thread finished running");
        }
    }

    public static class MyRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("My runnable is running");
            System.out.println("My runnable finished executing");
        }
    }

    public void threadExample() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable started");
                System.out.println("Runnable finished");
            }
        };

//        Thread thread = new Thread(new MyRunnable());
        Thread thread = new Thread(runnable);
        thread.start();

        MyThread myThread = new MyThread();
        myThread.start();

        // We can also use lambda functions
        Runnable lambdaRunnable = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + ": Lambda runnable started");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Lambda runnable finished");
        };

        Thread lambdaThread = new Thread(lambdaRunnable, "The Thread Name");
        lambdaThread.start();

    }

}
