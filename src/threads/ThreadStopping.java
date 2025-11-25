package threads;

public class ThreadStopping {

    public static class StoppableRunnable implements Runnable {

        private boolean stopRequested = false;

        public synchronized void requestStop() {
            this.stopRequested = true;
        }

        public synchronized boolean isStopRequested() {
            return this.stopRequested;
        }

        private void sleep(long millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            System.out.println("Stoppable Thread running");

            while (!isStopRequested()) {
                sleep(2000);
                System.out.println("...");
            }

            System.out.println("Stoppable thread stopped");
        }
    }

    public void threadStopping() {

        StoppableRunnable stoppableRunnable = new StoppableRunnable();
        Thread thread = new Thread(stoppableRunnable, "The thread");
        thread.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Requesting stop");
        stoppableRunnable.requestStop();
        System.out.println("Stop request granted");

    }

}
