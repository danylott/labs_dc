public class Garden {
    private boolean lockRead;
    private boolean lockWrite;
    private final String[][] flowers;
    private int readersCount;

    private final String badFlower = "-";
    private final String goodFlower = "+";

    Garden() {
        readersCount = 0;
        flowers = new String[10][10];
        lockWrite = false;
        lockRead = false;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                flowers[i][j] = badFlower;
            }
        }
    }

    boolean getLockRead() {
        return lockRead;
    }

    boolean getLockWrite() {
        return lockWrite;
    }

    synchronized void unlockReader(String name) {
        readersCount -= 1;
        if (readersCount == 0) {
            lockRead = false;
        }
        System.out.println("Action in Thread: " + name + " removes from readers.");
    }

    synchronized void unlockWriter(String name) {
        System.out.println("Action in Thread: " + name + " unlocks writer.");
        lockWrite = false;
    }

    synchronized boolean lockReaderWriter(boolean reader, boolean writer, String name) {
        if (reader) {
            readersCount += 1;
            lockRead = true;
            System.out.println("Action in Thread: " + name + " adds to readers.");
            return true;
        } else if (writer) {
            System.out.println("Action in Thread: " + name + " tries to lock writer.");
            if (lockWrite) {
                System.out.println("Action in Thread: " + name + " didn't lock writer.");
                return false;
            } else {
                System.out.println("Action in Thread: " + name + " locked writer.");
                lockWrite = true;
                return true;
            }
        }
        return false;
    }

    synchronized void toWaterFlowers(int i, String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Action in Thread: " + name + " is watering flowers.");
        for (int j = 0; j < 10; j++) {
            flowers[i][j] = "+";
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Action in Thread: " + name + " watered flowers.");
    }

    String getGardenInfo(String name) {
        System.out.println("Action in Thread: " + name + " asked to get Garden Info.");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                answer.append(flowers[i][j]).append(" ");
            }
            answer.append("\n");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Action in Thread: " + name + " gets Garden Info.");
        return answer.toString();
    }

    synchronized void changeState(int i, int j, String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (flowers[i][j].equals(goodFlower)) {
            flowers[i][j] = badFlower;
            System.out.println("Action in Thread: " + name +
                    " is changing state of plant[" + String.valueOf(i) + "][" + String.valueOf(j) + "] to state -.");
        } else if (flowers[i][j].equals(badFlower)) {
            flowers[i][j] = goodFlower;
            System.out.println("Action in Thread: " + name +
                    " is changing state of plant[" + String.valueOf(i) + "][" + String.valueOf(j) + "] to state +.");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
