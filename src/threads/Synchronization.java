package threads;

/*
 * Synchronization helps avoid race conditions which may cause deadlocks, corrupted state, lost updates and inconsistent data
 * Synchronization has limitations and can't be used in real systems, Lock API is best for production and live systems
 * Java provides:
    ✔ ReentrantLock
    ✔ ReadWriteLock
    ✔ ReentrantReadWriteLock
 */

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Synchronization {

    public static class Account {
        private Lock lock;
        private String fullName;
        private BigDecimal balance;
        private String currency;
        private int personalIdentificationNumber;

        // The synchronized key word allows only one thread to interact with the resource
        private void updateBalance() {
            lock = new ReentrantLock();
            lock.lock();
            try {
                // Critical process here
                System.out.println("Only one thread can update the balance.");
            } finally {
                lock.unlock();
            }
        }

        /*
         *⭐ ReadWriteLock
            For operations like:
            Many readers
            Few writers
            Example in a school system:
            Many users view the exam timetable
            Only admins update it occasionally
         */

        private void checkBalance() {
            ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
            readWriteLock.readLock().lock();
            readWriteLock.writeLock().lock();
        }
    }

    public void synchronization() {

    }

}
