import java.io.*;

public class Monitor1 implements Runnable {
    private final Garden garden;
    private final Thread thread;

    Monitor1(Garden _garden) {
        garden = _garden;
        thread = new Thread(this);
        thread.setName("Monitor1");
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (garden.getLockWrite()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            garden.lockReaderWriter(true, false, thread.getName());

            System.out.print("");
            String results = garden.getGardenInfo(thread.getName());

            try (FileWriter writer = new FileWriter("monitor1_results.txt", true)) {
                writer.write(results);
                writer.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            garden.unlockReader(thread.getName());
        }
    }
}
