public class DynamicScaling {

    public static void main(String[] args) {
        
        int coreCount = Runtime.getRuntime().availableProcessors();
        System.out.println("Logical processors available: " + coreCount);
        System.out.println("--------------------------------------------------");

        
        System.out.println("Running measurement with 1 thread...");
        long time1 = measureTime(1);
        System.out.println("Time taken (1 thread): " + time1 + " ms\n");

        System.out.println("Running measurement with " + coreCount + " threads...");
        long timeMax = measureTime(coreCount);
        System.out.println("Time taken (" + coreCount + " threads): " + timeMax + " ms");
    }

    
    private static long measureTime(int threadCount) {
        
        Thread[] threads = new Thread[threadCount];

        
        long startTime = System.currentTimeMillis();

        
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new MathTask());
            threads[i].start();
        }

        
        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join(); 
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
            }
        }

        
        long endTime = System.currentTimeMillis();
        
        return endTime - startTime;
    }
}


class MathTask implements Runnable {
    @Override
    public void run() {
        double sum = 0;
        for (int i = 0; i < 10_000_000; i++) {
            sum += Math.pow(i, 3) + (i * 2); 
        }
        
        if (sum == -1) { 
            System.out.println(sum); 
        }
    }
}