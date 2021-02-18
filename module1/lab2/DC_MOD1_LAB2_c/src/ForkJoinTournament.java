import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTournament extends RecursiveTask<Integer> {

    private final ArrayList<Integer> monks;
    private final int start;
    private final int end;

    ForkJoinTournament(ArrayList<Integer> monks, int start, int end) {
        this.monks = monks;
        this.start = start;
        this.end = end;
    }

    ForkJoinTournament(ArrayList<Integer> monks) {
        this.monks = monks;
        this.start = 0;
        this.end = monks.size();
    }

    @Override
    protected Integer compute() {
        if (end - start == 1) {
            System.out.println(start+" won (Energy "+monks.get(start)+"). ");
            return start;
        }
        else
        if (end - start == 2)
        {
            if (monks.get(start)>monks.get(start+1)) {
                System.out.println(start+" won (Energy "+monks.get(start)+"). ");
                return start;
            }
            System.out.println((start + 1) + " won (Energy "+monks.get(start + 1)+"). ");
            return start + 1;
        }

        ForkJoinTournament firstTask = new ForkJoinTournament(monks, start, (start+end)/2);
        firstTask.fork(); //start asynchronously

        ForkJoinTournament secondTask = new ForkJoinTournament(monks, (start+end)/2, end);

        Integer secondTaskResult = secondTask.compute();
        Integer firstTaskResult = firstTask.join();

        if (monks.get(firstTaskResult)>monks.get(secondTaskResult)) {
            System.out.println((firstTaskResult) + " won (Energy "+monks.get(firstTaskResult)+"). ");
            return firstTaskResult;
        } else {
            System.out.println((secondTaskResult) + " won (Energy "+monks.get(secondTaskResult)+"). ");
            return secondTaskResult;
        }
    }

    public static long startForkJoinTournament() {
        ArrayList<Integer> monks = new ArrayList<>();
        monks.add(5);
        monks.add(9);
        monks.add(12);
        monks.add(4);
        monks.add(9);
        monks.add(5);
        monks.add(1632);
        monks.add(4);
        monks.add(5);
        monks.add(9);
        monks.add(12);
        monks.add(4);
        monks.add(9);
        monks.add(5);
        monks.add(1632);
        monks.add(4);
        monks.add(5);
        monks.add(9);
        monks.add(12);
        monks.add(4);
        monks.add(9);
        monks.add(5);
        monks.add(1632);
        monks.add(4);
        monks.add(5);
        monks.add(9);
        monks.add(12);
        monks.add(4);
        monks.add(9);
        monks.add(5);
        monks.add(1632);
        monks.add(4);
        monks.add(5);
        monks.add(9);
        monks.add(12);
        monks.add(4);
        monks.add(9);
        monks.add(5);
        monks.add(1632);
        monks.add(4);
        monks.add(5);
        monks.add(9);
        monks.add(12);
        monks.add(4);
        monks.add(9);
        monks.add(5);
        monks.add(1632);
        monks.add(4);
        monks.add(5);
        monks.add(9);
        monks.add(12);
        monks.add(4);
        monks.add(9);
        monks.add(5);
        monks.add(1632);
        monks.add(4);
        monks.add(5);
        monks.add(9);
        monks.add(12);
        monks.add(4);
        monks.add(9);
        monks.add(5);
        monks.add(1632);
        monks.add(4);
        monks.add(5);
        monks.add(9);
        monks.add(12);
        monks.add(4);
        monks.add(9);
        monks.add(5);
        monks.add(1632);
        monks.add(4);
        monks.add(5);
        monks.add(9);
        monks.add(12);
        monks.add(4);
        monks.add(9);
        monks.add(5);
        monks.add(1632);
        monks.add(4);
        monks.add(5);
        monks.add(9);
        monks.add(12);
        monks.add(4);
        monks.add(9);
        monks.add(5);
        monks.add(1632);
        monks.add(4);
        monks.add(5);
        monks.add(9);
        monks.add(12);
        monks.add(4);
        monks.add(9);
        monks.add(5);
        monks.add(1632);
        monks.add(4);
        monks.add(5);
        monks.add(9);
        monks.add(12);
        monks.add(4);
        monks.add(9);
        monks.add(5);
        monks.add(1632);
        monks.add(4);
        monks.add(5);
        monks.add(9);
        monks.add(12);
        monks.add(4);
        monks.add(9);
        monks.add(5);
        monks.add(1632);
        monks.add(4);
        monks.add(5);
        monks.add(9);
        monks.add(12);
        monks.add(4);
        monks.add(9);
        monks.add(5);
        monks.add(1632);
        monks.add(4);
        monks.add(5);
        monks.add(9);
        monks.add(12);
        monks.add(4);
        monks.add(9);
        monks.add(5);
        monks.add(1632);
        monks.add(4);
        ForkJoinTask<Integer> task = new ForkJoinTournament(monks);
        return new ForkJoinPool().invoke(task);
    }
}