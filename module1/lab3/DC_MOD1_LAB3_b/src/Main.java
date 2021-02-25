public class Main {
    volatile Boolean haircuttingProcess = false;
    volatile Boolean currentHaircut = false;

    public class Client extends Thread {
        @Override
        public void run() {
            synchronized (Main.this) {
                while (haircuttingProcess)
                    try {
                        Main.this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                Main.this.notifyAll();
                haircuttingProcess = true;
                currentHaircut = true;
                System.out.println("Haircutting started."+this.getId());
                while (currentHaircut)
                    try {
                        Main.this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                haircuttingProcess = false;
                System.out.println("Haircutting finished."+this.getId());
                Main.this.notifyAll();
            }
        }
    }

    public class Hairdresser extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (Main.this) {
                    while (!currentHaircut)
                        try {
                            Main.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    System.out.println("Please, sit down.");
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Done!");
                    currentHaircut = false;
                    Main.this.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Main life = new Main();
        life.new Hairdresser().start();
        life.new Client().start();
        life.new Client().start();
        life.new Client().start();
        life.new Client().start();
        Thread.sleep(10000);
        life.new Client().start();
        life.new Client().start();
    }
}
