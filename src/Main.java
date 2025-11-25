import threads.CallableThreads;
import threads.ExecutorServiceThreads;
import threads.ThreadExample;
import threads.ThreadStopping;


public class Main {
    public static void main(String[] args) throws Exception {

        ThreadExample threadExample = new ThreadExample();
        // threadExample.threadExample();  runs a thread when the main thread is running, thus two threads will be running

        ThreadStopping threadStopping = new ThreadStopping();
//        threadStopping.threadStopping();

        // Callable threads
        CallableThreads callableThreads = new CallableThreads();
//        callableThreads.callableThreads();

        ExecutorServiceThreads serviceThreads = new ExecutorServiceThreads();
        serviceThreads.executorThreads();

    }
}