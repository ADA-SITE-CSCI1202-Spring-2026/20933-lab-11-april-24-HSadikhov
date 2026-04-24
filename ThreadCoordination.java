public class ThreadCoordination {

    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                resource.set(i); 
                try { 
                    Thread.sleep(500); 
                } catch (InterruptedException e) { } 
            }
        });

        
        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                int value = resource.get(); 
                
                try {
                    System.out.println("   -> Consumer is processing data " + value + "... (waiting 1.5s)\n");
                    Thread.sleep(1500); 
                } catch (InterruptedException e) { }
            }
        });

        producer.start();
        consumer.start();
    }
}


class SharedResource {
    private int data;
    private boolean bChanged = false; 

    public synchronized void set(int value) {
        while (bChanged) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        
        this.data = value;
        this.bChanged = true; 
        
        
        System.out.println("Producer successfully set: " + value);
        
        notify(); 
    }

    public synchronized int get() {
        while (!bChanged) {
            try {
                wait(); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        
        int retrievedData = this.data;
        this.bChanged = false;
        
       
        System.out.println("Consumer successfully retrieved: " + retrievedData);
        
        notify(); 
        
        return retrievedData;
    }
}