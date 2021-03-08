import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;

public class Checker {
    static int countLetter(String[] arr1, String letter) {
        int answer = 0;
        for (int i = 0; i < 10; i++) {
            if (arr1[i].equals(letter)) {
                answer += 1;
            }
        }
        return answer;
    }

    static boolean checkAnswer(String[] arr1, String[] arr2, String[] arr3, String[] arr4) {
        int count = 0;
        int countA1 = countLetter(arr1, "A");
        int countB1 = countLetter(arr1, "B");
        int countA2 = countLetter(arr2, "A");
        int countB2 = countLetter(arr2, "B");
        int countA3 = countLetter(arr3, "A");
        int countB3 = countLetter(arr3, "B");
        int countA4 = countLetter(arr4, "A");
        int countB4 = countLetter(arr4, "B");
        if (countA1 == countB1) {
            count += 1;
        }
        if (countA2 == countB2) {
            count += 1;
        }
        if (countA3 == countB3) {
            count += 1;
        }
        if (countA4 == countB4) {
            count += 1;
        }

        System.out.println("Array1 " + Arrays.toString(arr1) + "[CountA: " + String.valueOf(countA1) + " : countB: " + String.valueOf(countB1) + "]");
        System.out.println("Array2 " + Arrays.toString(arr2) + "[CountA: " + String.valueOf(countA2) + " : countB: " + String.valueOf(countB2) + "]");
        System.out.println("Array3 " + Arrays.toString(arr3) + "[CountA: " + String.valueOf(countA3) + " : countB: " + String.valueOf(countB3) + "]");
        System.out.println("Array4 " + Arrays.toString(arr4) + "[CountA: " + String.valueOf(countA4) + " : countB: " + String.valueOf(countB4) + "]");
        return count >= 3;
    }

    public static void main() {
        String[] arr1 = new String[]{"A", "C", "D", "B", "A", "D", "B", "C", "A", "D"};
        String[] arr2 = new String[]{"D", "B", "A", "C", "D", "A", "C", "C", "A", "B"};
        String[] arr3 = new String[]{"D", "C", "A", "A", "B", "C", "D", "B", "A", "B"};
        String[] arr4 = new String[]{"B", "D", "A", "C", "A", "D", "A", "B", "B", "C"};
        CyclicBarrier waitGroup = new CyclicBarrier(4);

        while (true) {
            StringThread stringThread1 = new StringThread(arr1, "T1", 1, waitGroup);
            StringThread stringThread2 = new StringThread(arr2, "T2", 7, waitGroup);
            StringThread stringThread3 = new StringThread(arr3, "T3", 3, waitGroup);
            StringThread stringThread4 = new StringThread(arr4, "T4", 5, waitGroup);

            Thread thread1 = new Thread(stringThread1);
            Thread thread2 = new Thread(stringThread2);
            Thread thread3 = new Thread(stringThread3);
            Thread thread4 = new Thread(stringThread4);

            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();

            try {
                thread1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                thread3.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                thread4.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            arr1 = stringThread1.line;
            arr2 = stringThread2.line;
            arr3 = stringThread3.line;
            arr4 = stringThread4.line;

            if (checkAnswer(arr1, arr2, arr3, arr4)) {
                break;
            }
        }
    }
}
