import java.util.*;

public class Controller {

	/**
	 * initialize the schedule with a birth event and a monitor event
	 * 
	 * @return a schedule with two events
	 */
	public static PriorityQueue<Event> initSchedule(double lambda) {
		PriorityQueue<Event> schedule = new PriorityQueue<Event>();

		double t1 = Event.getTimeOfNextBirth(lambda);
		schedule.add(new Event(t1, EventType.BIRTH));

		double t2 = Event.getTimeOfNextMonitor(lambda);
		schedule.add(new Event(t2, EventType.MONITOR));

		return schedule;
	}

	public static void runSimulation(double simulationTime, double lambda, double Ts0, double Ts1, double Ts2,
			double t1, double p1, double t2, double p2, double t3, double p3, int K, double prob0_1, double prob0_2,
			double prob3_out, double prob3_1, double prob3_2) {
		/**
		 * declare the data structures that hold the state of the system
		 */
		State state = new State(1);
		PriorityQueue<Event> schedule = initSchedule(lambda);

		// System.out.println("Ts = " + Ts + " Ts2 = " + Ts2);

		double time = 0, maxTime = simulationTime;
		while (time < maxTime) {
			Event event = schedule.remove();
			time = event.getTime();
			event.function(schedule, state, time, K, lambda, Ts0, Ts1, Ts2, t1, p1, t2, p2, t3, p3, prob0_1, prob0_2,
					prob3_out, prob3_1, prob3_2);
		}

		/**
		 * output the statistics over the simulated system
		 */

		System.out.println("\nS0 UTIL: " + state.busyTime / simulationTime);
		System.out.println("S0 QLEN: " + state.totalQueueLen / state.numMonitorEvents);
		System.out.println("S0 TRESP: " + state.totalRequestTime / state.numCompletedRequests);
		
		System.out.println("\nS1,1 UTIL: " + state.busyTime_11 / simulationTime);
		System.out.println("S1,2 UTIL: " + state.busyTime_12 / simulationTime);
		System.out.println("S1 QLEN: " + state.totalQueueLen_2/ state.numMonitorEvents);
		System.out.println("S1 TRESP: " + state.totalRequestTime_1 / state.numCompletedRequests_1);
		
		System.out.println("\nS2 UTIL: " + state.busyTime_3 / simulationTime);
		System.out.println("S2 QLEN: " + state.totalQueueLen_3/ state.numMonitorEvents);
		System.out.println("S2 TRESP: " + state.totalRequestTime_2 / state.numCompletedRequests_2);
		System.out.println("S2 DROPPED: " + state.totalDropped);
		
		System.out.println("\nS3 UTIL: " + state.busyTime_4 / simulationTime);
		System.out.println("S3 QLEN: " + state.totalQueueLen_4/ state.numMonitorEvents);
		System.out.println("S3 TRESP: " + state.totalRequestTime_3 / state.numCompletedRequests_3);
		
		double qTOT =  (state.totalQueueLen / state.numMonitorEvents)  + (state.totalQueueLen_2/ state.numMonitorEvents) + (state.totalQueueLen_3/ state.numMonitorEvents) + (state.totalQueueLen_4/ state.numMonitorEvents);
		double trespTOT = (state.totalRequestTime / state.numCompletedRequests)+ (state.totalRequestTime_1 / state.numCompletedRequests_1) + (state.totalRequestTime_2 / state.numCompletedRequests_2) + (state.totalRequestTime_3 / state.numCompletedRequests_3);
		System.out.println("\nQTOT: " + qTOT);
		System.out.println("TRESP: " + trespTOT);
		
	}
}
