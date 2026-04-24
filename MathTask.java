
public class MathTask implements Runnable {
    
    @Override
    public void run() {
        double sum = 0;
        int j = 2; 

        
        for (int i = 0; i < 10_000_000; i++) {
            
            sum += Math.pow(i, 3) + (i * j);
        }

        
        if (sum == -1) { 
            System.out.println(sum); 
        }
    }
}