public class Nature implements Runnable {
    private final Garden garden;
    private final Thread thread;

    Nature(Garden _garden) {
        garden = _garden;
        thread = new Thread(this);
        thread.setName("Nature");
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

            garden.changeState((int) (Math.random() * 10), (int) (Math.random() * 10), thread.getName());

            garden.unlockWriter(thread.getName());
        }
    }
}
