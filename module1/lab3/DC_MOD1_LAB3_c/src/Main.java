import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    static final int TOBACCO = 1;
    static final int MATCHES = 2;
    static final int PAPER = 3;
    volatile Semaphore[] semaphore = new Semaphore[4];

    Main() throws InterruptedException {
        for (int i=0; i<4; ++i)
            semaphore[i] = new Semaphore(1);
        semaphore[1].acquire();
        semaphore[2].acquire();
        semaphore[3].acquire();
        new Smoker(1).start();
        new Smoker(2).start();
        new Smoker(3).start();
        new Mediator().start();
    }

    public class Smoker extends Thread {
        int object;

        Smoker(int o) {
            object = o;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    semaphore[object].acquire();
                    System.out.println(object + " smokes.");
                    sleep(1000);
                    System.out.println(object + " finished smoking.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                semaphore[0].release();
            }
        }
    }

    public class Mediator extends Thread {
        @Override
        public void run() {
            Random rnd = new Random();
            while (true) {
                try {
                    semaphore[0].acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int turn = rnd.nextInt(3) + 1;
                System.out.println(turn + " turn.");
                semaphore[turn].release();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Main life = new Main();
    }
}
