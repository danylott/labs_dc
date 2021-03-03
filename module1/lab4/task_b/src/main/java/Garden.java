public class Garden {
    private boolean lockRead;
    private boolean lockWrite;
    private final String[][] plants;
    private int numOfReaders;

    private final String badPlant = "-";
    private final String goodPlant = "+";

    Garden() {
        numOfReaders = 0;
        plants = new String[10][10];
        lockWrite = false;
        lockRead = false;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                plants[i][j] = badPlant;
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
        numOfReaders -= 1;
        if (numOfReaders == 0) {
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
            numOfReaders += 1;
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
            plants[i][j] = "+";
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
                answer.append(plants[i][j]).append(" ");
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
        if (plants[i][j].equals(goodPlant)) {
            plants[i][j] = badPlant;
            System.out.println("Action in Thread: " + name +
                    " is changing state of plant[" + String.valueOf(i) + "][" + String.valueOf(j) + "] to state -.");
        } else if (plants[i][j].equals(badPlant)) {
            plants[i][j] = goodPlant;
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
