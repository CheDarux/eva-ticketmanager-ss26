package IDGenerator.IDService;

import IDGenerator.PrimeNumberGenerator.PrimeNumberGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class IDServiceParallel extends Thread implements IDServiceInterface {

    private final PrimeNumberGenerator primeNumberGenerator;
    private final BlockingQueue<Long> availableIds;
    private final Set<Long> usedIds;
    private final List<IDServiceParallel> workers;

    public IDServiceParallel(long lowerLimit, long upperLimit) {
        if (upperLimit < lowerLimit) {
            throw IDServiceException.lowerLimitHigherThanUpperLimit();
        }

        this.primeNumberGenerator = new PrimeNumberGenerator(lowerLimit, upperLimit);
        this.availableIds = new LinkedBlockingQueue<>(1000);
        this.usedIds = ConcurrentHashMap.newKeySet();
        this.workers = new ArrayList<>();

        int amountThreads = Runtime.getRuntime().availableProcessors();

        for (int i = 0; i < amountThreads; i++) {
            IDServiceParallel worker = new IDServiceParallel(
                primeNumberGenerator,
                availableIds,
                usedIds
            );
            workers.add(worker);
            worker.start();
        }
    }

    private IDServiceParallel(
        PrimeNumberGenerator primeNumberGenerator,
        BlockingQueue<Long> availableIds,
        Set<Long> usedIds
    ) {
        this.primeNumberGenerator = primeNumberGenerator;
        this.availableIds = availableIds;
        this.usedIds = usedIds;
        this.workers = null;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            long possibleId = primeNumberGenerator.getRandomPrimeNumberInRange();

            if (possibleId != -1 && usedIds.add(possibleId)) {
                try {
                    availableIds.put(possibleId);
                } catch (InterruptedException exception) {
                    interrupt();
                }
            }
        }
    }

    @Override
    public long getUnusedId() {
        try {
            return availableIds.take();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread wurde beim Warten auf eine ID unterbrochen", exception);
        }
    }

    public void shutdown() {
        for (IDServiceParallel worker : workers) {
            worker.interrupt();
        }
    }
}
