import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

class Unit {
    private final String title;
    private final int price;

    Unit (String s, int p) {
        title = s;
        price = p;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "title='" + title + '\'' +
                '}';
    }

    public int getPrice() {
        return price;
    }
}

public class Main {
    AtomicInteger calculated = new AtomicInteger(0);
    AtomicInteger price = new AtomicInteger(0);
    volatile boolean stockIsEmpty = false;
    volatile boolean allStored = false;
    volatile ArrayList<Unit> unitsAtStock = new ArrayList<>();
    volatile ArrayList<Unit> unitsWaiting = new ArrayList<>();
    volatile ArrayList<Unit> unitsStored = new ArrayList<>();

    class Ivanov implements Runnable {

        @Override
        public void run() {
            while (true)
            {
                Unit unit = null;
                synchronized (this) {
                    if (!unitsAtStock.isEmpty())
                        unit = unitsAtStock.remove(0);
                    else break;
                }
                System.out.println("Unit token from stock.");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                unitsWaiting.add(0,unit);
                System.out.println("Unit is waiting.");
            }
            stockIsEmpty = true;
        }
    }

    class Petrov implements Runnable {

        @Override
        public void run() {
            while (true)
            {
                Unit unit = null;
                synchronized (this) {
                    if (!unitsWaiting.isEmpty())
                        unit = unitsWaiting.remove(0);
                    else if (stockIsEmpty) break;
                }
                if (unit != null) {
                    System.out.println("Unit token from floor.");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    unitsStored.add(unit);
                    System.out.println("Unit stored.");
                }
            }
            allStored = true;
        }
    }

    class Necheporuk implements Runnable {

        @Override
        public void run() {
            while (true)
            {
                Unit unit = null;
                synchronized (this) {
                    if (unitsStored.size() > calculated.get())
                        unit = unitsStored.get(calculated.get());
                    else if (allStored) break;
                }
                if (unit != null) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    price.set(price.get() + unit.getPrice());
                    calculated.set(calculated.get() + 1);
                    System.out.println(price);
                }
            }
        }
    }

    public static void main(String[] args) {
        Main game = new Main();
        game.unitsAtStock.add(new Unit("a",1));
        game.unitsAtStock.add(new Unit("b",6));
        game.unitsAtStock.add(new Unit("c",15));
        game.unitsAtStock.add(new Unit("c",15));
        game.unitsAtStock.add(new Unit("c",15));
        game.unitsAtStock.add(new Unit("c",12));
        game.unitsAtStock.add(new Unit("c",15));
        game.unitsAtStock.add(new Unit("c",18));
        game.unitsAtStock.add(new Unit("c",18));
        game.unitsAtStock.add(new Unit("c",18));
        game.unitsAtStock.add(new Unit("c",18));
        game.unitsAtStock.add(new Unit("c",18));
        game.unitsAtStock.add(new Unit("c",18));
        new Thread(game.new Ivanov()).start();
        new Thread(game.new Petrov()).start();
        new Thread(game.new Necheporuk()).start();
    }
}
