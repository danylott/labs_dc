import java.util.concurrent.CyclicBarrier;

public class Checker implements Runnable {
    NumberThread[] threads = new NumberThread[3];

    public Checker(int n) {
        CyclicBarrier barrier = new CyclicBarrier(3, this);
        for (int i = 0; i < 3; ++i)
            threads[i] = new NumberThread(n, barrier);
        for (int i = 0; i < 3; ++i)
            threads[i].start();
    }

    @Override
    public void run() {
        int sums[] = new int[3];
        int numOfEqual = 1;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < threads[i].getSize(); ++j)
                sums[i] += threads[i].getNumbers(j);
        }
        for (int i = 1; i < 3; ++i)
            if (sums[i - 1] == sums[i])
                numOfEqual++;
        System.out.println("Equal sums in " + numOfEqual + " threads.");
        if (numOfEqual >= 3) {
            for (int i = 0; i < 3; ++i)
                threads[i].interrupt();
        }
    }
}
