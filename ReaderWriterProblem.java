import java.util.concurrent.Semaphore;


class ReaderWritersProblem {

    static Semaphore readLock = new Semaphore(1);
    static Semaphore writeLock = new Semaphore(1);
    static int counter = 0;

    static class Reading implements Runnable {
        @Override
        public synchronized void run() {
            try {
                readLock.acquire();
                counter++;
                if (counter == 1) {
                    writeLock.acquire();
                }
                System.out.println("Thread "+Thread.currentThread().getName() + " is READING");
                Thread.sleep(1500);
                System.out.println("Thread "+Thread.currentThread().getName() + " has FINISHED READING");
                readLock.release();
                counter--;
                if(counter == 0) {
                    writeLock.release();
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static class Writing implements Runnable {
        @Override
        public synchronized void run() {
            try {
                writeLock.acquire();
                System.out.println("Thread "+Thread.currentThread().getName() + " is currently writing");
                Thread.sleep(2000);
                System.out.println("Thread "+Thread.currentThread().getName() + " has finished writing");
                writeLock.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Reading read = new Reading();
        Writing write = new Writing();
        Thread t1 = new Thread(read);
        t1.setName("1st");
        Thread t2 = new Thread(read);
        t2.setName("2nd");
        Thread t3 = new Thread(write);
        t3.setName("3nd");
        Thread t4 = new Thread(read);
        t4.setName("4th");
        t1.run();
        t2.run();
        t3.run();
        t4.run();
    }
}
