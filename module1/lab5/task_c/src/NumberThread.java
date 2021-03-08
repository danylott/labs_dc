import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class NumberThread extends Thread {
    protected ArrayList<Integer> numbers;
    protected CyclicBarrier barrier;

    public NumberThread(int n, CyclicBarrier barrier) {
        numbers = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < n; ++i)
            numbers.add(rnd.nextInt(2));
        this.barrier = barrier;
    }

    public int getNumbers(int i) {
        return numbers.get(i);
    }

    public int getSize() {
        return numbers.size();
    }

    @Override
    public void run() {
        Random rnd = new Random();
        while (!isInterrupted()) {
            int numberIndex = rnd.nextInt(numbers.size());
            int addition = 1;
            if (rnd.nextBoolean())
                addition = -1;
            numbers.set(numberIndex, (numbers.get(numberIndex) + addition) % 5);
            System.out.println(this.getId() + ": " + numbers);
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
