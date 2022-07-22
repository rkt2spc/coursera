import java.util.List;
import java.util.ArrayList;
import java.util.Random;

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

  public static void main(String[] args) {
    final int NUM_JOBS =  (int) 10e1;
    final int RAND_MAX = (int) 10e3;

    Random rnd = new Random();


    List<Job> jobs = new ArrayList<>();
    for (int i = 0; i < NUM_JOBS; ++i)
      jobs.add(new Job(rnd.nextInt(RAND_MAX), rnd.nextInt(RAND_MAX)));

    Scheduler s = new Scheduler();
    List<Job> schedule = s.schedule(jobs);

    System.out.println(String.format("[ORIGINAL]  Weighted Sum of Completion Times: %d", Scheduler.weightedSumCompletionTimes(jobs)));
    System.out.println(String.format("[SCHEDULED] Weighted Sum of Completion Times: %d", Scheduler.weightedSumCompletionTimes(schedule)));
  }
}
