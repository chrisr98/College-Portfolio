import java.util.PriorityQueue;

public class Controller {

    /**
     * initialize the schedule with a birth event and a monitor event
     * @return a schedule with two events
     */
    public static PriorityQueue<OtherEvent> initSchedule() {
		PriorityQueue<OtherEvent> schedule = new PriorityQueue<OtherEvent>();

		schedule.add(new OtherEvent(Event.getTimeOfNextBirth(), EventType.BIRTH, 1));
		schedule.add(new OtherEvent(Event.getTimeOfNextMonitor(), EventType.MONITOR, 1));

		return schedule;
    }

    public static void runSimulation(double simulationTime, double lambda, double Ts, double maxQueueK, double Ts2) {
    	/**
		 * declare the data structures that hold the state of the system
		 */
		State state1 = new State();
		PriorityQueue<OtherEvent> schedule = initSchedule();

		double time = 0, maxTime = simulationTime;
		while(time < maxTime) {
			OtherEvent event2 = schedule.remove();
			time = event2.getTime();
            event2.functionK(schedule, state1, time, maxQueueK);

		}

		/**
		 * output the statistics over the simulated system
		 */
		System.out.printf("UTIL 0: %f\n", state1.busyTime / simulationTime); //primary server
        System.out.printf("UTIL 1: %f\n", state1.busyTime2 / simulationTime); //secondary server
		System.out.printf("QLEN 0: %f\n", state1.totalQueueLen / state1.numMonitorEvents);
        System.out.printf("QLEN 1: %f\n", state1.totalQueueLen2 / state1.numMonitorEvents);
		System.out.printf("TRESP: %f\n", state1.totalRequestTime / state1.numCompletedRequests);
        System.out.printf("REDIRECTED: %f\n", state1.totalDrop);
    }
}
