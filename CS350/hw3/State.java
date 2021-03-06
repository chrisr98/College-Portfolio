import java.util.LinkedList;

public class State {
    /**
     * Queuing system state
     */

    /* The request list. */
    public LinkedList<Request> queue;

    /* Used to generate the ID of the next request. */
    private int nextId;

    /**
     * Simulation statistics
     */

    /* Used to calculate the utilization. */
    public double busyTime;

    /* Number of completed requests during the simulation. */
    public double numCompletedRequests;

    /* Total request time. */
    public double totalRequestTime;

    /* Number of monitor events, each monitor event will record the queue length. */
    public double numMonitorEvents;

    /* Total queue length. */
    public double totalQueueLen;

    /* Number of dropped requests*/

    public double totalDrop;


    /* The request list. */
    public LinkedList<Request> queue2;

    /* Used to calculate the utilization. */
    public double busyTime2;

    /* Total queue length. */
    public double totalQueueLen2;


    public State() {
    	queue = new LinkedList<Request>();
    	nextId = 0;
    	busyTime = 0;
    	numCompletedRequests = 0;
    	totalRequestTime = 0;
    	numMonitorEvents = 0;
    	totalQueueLen = 0;
        totalDrop = 0;
        queue2 = new LinkedList<Request>();
        busyTime2 = 0;
        totalQueueLen2 = 0;
    }

    /**
     * Get next request ID.
     */
    public int getNextId() {
    	return nextId++;
    }
}
