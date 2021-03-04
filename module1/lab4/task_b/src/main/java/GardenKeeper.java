public class GardenKeeper implements Runnable {
    private final Garden garden;
    private final Thread thread;

    private int line;

    GardenKeeper(Garden _garden) {
        garden = _garden;
        line = 0;
        thread = new Thread(this);
        thread.setName("GardenKeeper");
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

            while (!garden.lockReaderWriter(false, true, thread.getName())) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (garden.getLockRead()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            garden.toWaterFlowers(line, thread.getName());

            garden.unlockWriter(thread.getName());

            line += 1;
            if (line == 10) {
                line = 0;
            }
        }
    }
}
