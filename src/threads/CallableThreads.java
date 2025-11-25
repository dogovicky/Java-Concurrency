package threads;

// Callable: Always returns a result and can throw an exception

import java.util.concurrent.Callable;

public class CallableThreads {

    public void callableThreads() throws Exception {

        Callable<String> task = () -> {
            System.out.println("Callable thread running");
            return "This is a callable thread";
        };

        task.call();

    }

}
