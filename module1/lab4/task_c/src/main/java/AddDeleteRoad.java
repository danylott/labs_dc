public class AddDeleteRoad implements Runnable {
    private Graph graph;
    private Thread thread;

    AddDeleteRoad(Graph graph) {
        this.graph = graph;
        thread = new Thread(this);
        thread.setName("AddDeleteRoad");
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (graph.lock.isWriteLocked()) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            graph.lock.readLock().lock();
            City fCity = graph.getCity("Sofia", thread.getName());
            graph.lock.readLock().unlock();

            while (graph.lock.isWriteLocked()) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            graph.lock.readLock().lock();
            City sCity = graph.getCity("Kyiv", thread.getName());
            graph.lock.readLock().unlock();

            while (graph.lock.getReadLockCount() > 0) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (!graph.lock.writeLock().tryLock()) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            graph.connectCities(fCity, sCity, thread.getName());
            graph.lock.writeLock().unlock();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (graph.lock.isWriteLocked()) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            graph.lock.readLock().lock();
            City fCity1 = graph.getCity("London", thread.getName());
            graph.lock.readLock().unlock();

            while (graph.lock.isWriteLocked()) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            graph.lock.readLock().lock();
            City sCity1 = graph.getCity("Madrid", thread.getName());
            graph.lock.readLock().unlock();

            while (graph.lock.getReadLockCount() > 0) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (!graph.lock.writeLock().tryLock()) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            graph.disconnectCities(fCity1, sCity1, thread.getName());
            graph.lock.writeLock().unlock();
        }
    }
}
