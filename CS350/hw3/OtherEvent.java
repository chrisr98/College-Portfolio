import java.util.PriorityQueue;

public class OtherEvent implements Comparable<OtherEvent> {
    /**
     * Time that the event happens
     */
    private double time;

    private EventType eventType;

    private int server;

    private int size;

    public double getTime() {
    	return time;
    }

	/**
	 * @param time when the event starts
	 * @param eventType Monitor or Birth or Death
	 */
	public OtherEvent(double time, EventType eventType, int server) {
		this.time = time;
		this.eventType = eventType;
        this.server = server;

	}

    /**
     *
     * @param schedule contains all the scheduled future events
     * @param state of the simulation (request queue, logging info, etc.)
     * @param timestamp current time in the discrete simulation
     */
    public void functionK(PriorityQueue<OtherEvent> schedule, State state, double timestamp, double maxQueueK) {
    	schedule.remove(this);

		switch (eventType) {
		case DEATH:
	    	/**
		     * remove the record of the request from the data structure of requests in the system
		     * Also, collect and compute some statistics.
	    	 */

            if (server == 0)
            {
                Request req = state.queue.remove();
                req.setFinishServiceTime(timestamp);

                state.totalRequestTime += req.getTq();
                state.busyTime += req.getTs();
                state.numCompletedRequests += 1;

                System.out.printf("R%d DONE: %f\n", req.getId(), req.getFinishServiceTime());
            } else
            {
                Request req = state.queue2.remove();
    		    req.setFinishServiceTime(timestamp);

    	    	state.totalRequestTime += req.getTq();
    		    state.busyTime2 += req.getTs();
    		    state.numCompletedRequests += 1;

    		    System.out.printf("R%d DONE: %f\n", req.getId(), req.getFinishServiceTime());
            }

		    /**
		     * look for another blocked event in the queue that wants to execute and schedule it's death.
		     * at this time the waiting request enters processing time.
	    	 */
		    if (size > 0){
                if(server == 0)
                {
                    Request nextReq = state.queue.peek();
    				nextReq.setStartServiceTime(timestamp);

    				System.out.printf("R%d START %d: %f\n", nextReq.getId(), server, nextReq.getStartServiceTime());

                    /* Schedule the next death event. */
                    OtherEvent nextDeath = new OtherEvent(timestamp + getTimeOfNextDeath(), EventType.DEATH, server);
                    schedule.add(nextDeath);

                } else
                {
                    Request nextReq = state.queue2.peek();
    				nextReq.setStartServiceTime(timestamp);

    				System.out.printf("R%d START %d: %f\n", nextReq.getId(), server, nextReq.getStartServiceTime());

                    /* Schedule the next death event. */
                    OtherEvent nextDeath = new OtherEvent(timestamp + getTimeOfNextDeath(), EventType.DEATH, server);
                    schedule.add(nextDeath);
                }




		    }

	    	break;


		case BIRTH:
	    	/**
		     * add the newly born request to the data structure of requests in the system.
		     */
		    Request request = new Request(state.getNextId());
		    request.setArrivalTime(timestamp);
            state.queue.add(request);

            System.out.printf("R%d ARR: %f\n", request.getId(), request.getArrivalTime());

            /**
		     * Run an if statement to calculate dropped requests.
	    	 */
            if (state.queue.size() >= maxQueueK)
            {
                size = state.queue2.size();
                System.out.printf("R%d REDIR: %f\n", request.getId(), request.getArrivalTime());
                state.totalDrop += 1;
                state.queue.remove(request);
                state.queue2.add(request);
                server = 1;
            } else
            {
                size = state.queue.size();
                server = 0;
            }

            /**
             * if the queue is empty then start executing directly there is no waiting time.
             */
            if(size == 1) {



                if(server == 0)
                {
                    request.setStartServiceTime(timestamp);
                    OtherEvent event = new OtherEvent(timestamp + getTimeOfNextDeath(), EventType.DEATH, 0);
                    schedule.add(event);

                    System.out.printf("R%d START %d: %f\n", request.getId(), server, timestamp);
                } else
                {
                    request.setStartServiceTime(timestamp);
                    OtherEvent event = new OtherEvent(timestamp + getTimeOfNextDeath2(), EventType.DEATH, 1);
                    schedule.add(event);

                    System.out.printf("R%d START %d: %f\n", request.getId(), server, timestamp);
                }
            }

            /**
             * schedule the next arrival
             */
            OtherEvent nextArrival = new OtherEvent(timestamp + getTimeOfNextBirth(), EventType.BIRTH, server);
            schedule.add(nextArrival);

            break;

		case MONITOR:
		    /**
		     * inspect the data structures describing the simulated system and log them
		     */
		    state.numMonitorEvents += 1;
		    state.totalQueueLen += state.queue.size();

            state.totalQueueLen2 += state.queue2.size();

		    /**
		     * Schedule another monitor event following PASTA principle
		     */
		    OtherEvent nextMonitor = new OtherEvent(timestamp + getTimeOfNextMonitor(), EventType.MONITOR, server);
		    schedule.add(nextMonitor);

		    break;
		}
    }

    /* Make sure the events are sorted according to their happening time. */
    public int compareTo(OtherEvent e) {
    	double diff = time - e.getTime();
    	if (diff < 0) {
	    	return -1;
		} else if (diff > 0) {
		    return 1;
		} else {
		    return 0;
		}
    }

    /**
	 * exponential distribution
	 * used by {@link #getTimeOfNextBirth()}, {@link #getTimeOfNextDeath()} and {@link #getTimeOfNextMonitor()}
	 * @param rate
	 * @return
	 */
	private static double exp(double rate) {
		return (- Math.log(1.0 - Math.random()) / rate);
	}

	/**
	 *
	 * @return time for the next birth event
	 */
	public static double getTimeOfNextBirth() {
		return exp(Simulator.lambda);
	}

	/**
	 *
	 * @return time for the next death event
	 */
	public static double getTimeOfNextDeath() {
		return exp(1.0/Simulator.Ts1);
	}

    public static double getTimeOfNextDeath2() {
		return exp(1.0/Simulator.Ts2);
	}

	/**
	 *
	 * @return time for the next monitor event
	 */
	public static double getTimeOfNextMonitor() {
		return exp(Simulator.lambda);
	}
}
