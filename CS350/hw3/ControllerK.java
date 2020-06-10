import java.util.PriorityQueue;

public class ControllerK {

    /**
     * initialize the schedule with a birth event and a monitor event
     * @return a schedule with two events
     */
    public static PriorityQueue<Event> initSchedule() {
		PriorityQueue<Event> schedule = new PriorityQueue<Event>();

		schedule.add(new Event(Event.getTimeOfNextBirth(), EventType.BIRTH));
		schedule.add(new Event(Event.getTimeOfNextMonitor(), EventType.MONITOR));

		return schedule;
    }

    public static void runSimulation(double simulationTime, double lambda, double Ts, double maxQueueK) {
    	/**
		 * declare the data structures that hold the state of the system
		 */
		State state = new State();
		PriorityQueue<Event> schedule = initSchedule();

		double time = 0, maxTime = simulationTime;
		while(time < maxTime) {
			Event event = schedule.remove();
			time = event.getTime();
			event.function(schedule, state, time, maxQueueK);
		}

		/**
		 * output the statistics over the simulated system
		 */
		System.out.printf("UTIL: %f\n", state.busyTime / simulationTime);
		System.out.printf("QLEN: %f\n", state.totalQueueLen / state.numMonitorEvents);
		System.out.printf("TRESP: %f\n", state.totalRequestTime / state.numCompletedRequests);
        System.out.printf("DROPPED: %f\n", state.totalDrop);
    }
}
