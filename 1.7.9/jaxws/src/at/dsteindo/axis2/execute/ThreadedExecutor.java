package at.dsteindo.axis2.execute;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadedExecutor {
    private final AtomicInteger atomicCount = new AtomicInteger(0);
    private final Runnable callback;

    public ThreadedExecutor(Runnable callback) {
        this.callback = callback;
    }

    public void execute() {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Thread t = new Thread(this::threadWorker);
            t.start();
            threads.add(t);
        }
        boolean allFinished = false;
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(6000);
                allFinished = threads.stream().noneMatch(Thread::isAlive);
                if (allFinished) {
                    System.out.println("Success!!");
                    break;
                }
            }
            if (!allFinished) {
                System.out.print("Failure, maybe the connection pool not handing out connections anymore ...");
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        threads.forEach(Thread::interrupt);
    }

    public void threadWorker() {
        while (atomicCount.incrementAndGet() < 201) {
            callback.run();
        }
    }
}
