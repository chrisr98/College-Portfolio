import java.util.*;

public class State {
	/**
	 * Queuing system state
	 */

	/* The request list. */
	public LinkedList<Request> queue;
	public LinkedList<Request> queue_2;
	public LinkedList<Request> queue_3;
	public LinkedList<Request> queue_4;

	/* Used to generate the ID of the next request. */
	private int nextId;

	/**
	 * Simulation statistics
	 */

	/* Used to calculate the utilization. */
	public double busyTime;
	public double busyTime_11;
	public double busyTime_12;
	public double busyTime_3;
	public double busyTime_4;

	/* Number of completed requests during the simulation. */
	public double numCompletedRequests;
	public double numCompletedRequests_1;
	public double numCompletedRequests_2;
	public double numCompletedRequests_3;

	/* Total request time. */
	public double totalRequestTime;
	public double totalRequestTime_1;
	public double totalRequestTime_2;
	public double totalRequestTime_3;

	/* Number of monitor events, each monitor event will record the queue length. */
	public double numMonitorEvents;

	/* Total queue length. */
	public double totalQueueLen;
	public double totalQueueLen_2;
	public double totalQueueLen_3;
	public double totalQueueLen_4;

	/* Total number of dropped requests. */
	public int totalDropped;

	/* Number of busy servers. */
	public int numBusyServer;

	/* Total number of servers. */
	public int N;

	/* Used to get the next available server. */
	public boolean busyServer[];
	public int outcome[];
	public double probability[];

	public State(int n) {
		queue = new LinkedList<Request>();
		queue_2 = new LinkedList<Request>();
		queue_3 = new LinkedList<Request>();
		queue_4 = new LinkedList<Request>();

		nextId = 0;
		busyTime = 0;
		busyTime_11 = 0;
		busyTime_12 = 0;
		busyTime_3 = 0;
		busyTime_4 = 0;
		numCompletedRequests = 0;
		numCompletedRequests_1 = 0;
		numCompletedRequests_2 = 0;
		numCompletedRequests_3 = 0;
		totalRequestTime = 0;
		totalRequestTime_1 = 0;
		totalRequestTime_2 = 0;
		totalRequestTime_3 = 0;

		numMonitorEvents = 0;
		totalQueueLen = 0;
		totalQueueLen_2 = 0;
		totalQueueLen_3 = 0;
		totalQueueLen_4 = 0;

		totalDropped = 0;
		numBusyServer = 0;
		N = n;
		busyServer = new boolean[n];
		outcome = new int[n];
		probability = new double[n];
	}

	/**
	 * Get next request ID.
	 */
	public int getNextId() {
		return nextId++;
	}

//	public int custom(int[] outcomes, double[] probabilities) {
//		Random RandomGenerator = new Random();
//		double Y = RandomGenerator.nextDouble();
//		double sum = 0.0;
//		for (int i = 0; i < probabilities.length; i++) {
//			sum += probabilities[i];
//			if (Y < sum)
//				return outcomes[i];
//		}
//		return -1;
//	}
//
//	public int getServerId() {
//		int index = 0;
//		double num = 0.0;
//
//		for (int j = 0; j < N; j++)
//			outcome[j] = j;
//
//		for (boolean b : busyServer) {
//			if (!b)
//				num++;
//		}
//
//		double prob = (num == 0.0) ? (1.0 / N) : (1.0 / num);
//
//		for (int i = 0; i < N; i++) {
//			if (busyServer[i] == true) {
//				probability[i] = 0;
//			} else {
//				probability[i] = prob;
//			}
//		}
//
//		index = custom(outcome, probability);
//		return index;
//	}

//	public void setBusyServer(int k) {
//		busyServer[k] = true;
//	}
//
//	public void freeBusyServer(int k) {
//		busyServer[k] = false;
//	}
//
//	public int getNumBusyServer() {
//		return numBusyServer;
//	}
}
