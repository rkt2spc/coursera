import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Scheduler {
  public List<Job> schedule(List<Job> jobs) {
    List<Job> schedule = new ArrayList<>(jobs);
    schedule.sort((a, b) -> Double.compare(
      b.weight / (double) b.length,
      a.weight / (double) a.length
    ));
    return schedule;
  }

  public static long weightedSumCompletionTimes(List<Job> schedule) {
    long ans = 0;
    long completionTime = 0;
    for (Job job : schedule) {
      completionTime += job.length;
      ans += job.weight * completionTime;
    }
    return ans;
  }

  public static List<Job> parseInputFile(String filepath) throws FileNotFoundException {
    List<Job> result = new ArrayList<>();

    Scanner sc = new Scanner(new File(filepath));

    int numJobs = sc.nextInt();
    for (int i = 0; i < numJobs; ++i) {
      int weight = sc.nextInt();
      int length = sc.nextInt();
      result.add(new Job(weight, length));
    }
    sc.close();

    return result;
  }

  public static void main(String[] args) throws FileNotFoundException {
    List<Job> jobs = parseInputFile(args[0]);

    Scheduler s = new Scheduler();
    List<Job> schedule = s.schedule(jobs);

    System.out.println(String.format("[ORIGINAL]  Weighted Sum of Completion Times: %d", Scheduler.weightedSumCompletionTimes(jobs)));
    System.out.println(String.format("[SCHEDULED] Weighted Sum of Completion Times: %d", Scheduler.weightedSumCompletionTimes(schedule)));
  }
}
