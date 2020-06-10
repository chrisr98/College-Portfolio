import java.util.PriorityQueue;

public class Event implements Comparable<Event> {
    /**
     * Time that the event happens
     */
    private double time;

    private EventType eventType;

    public double getTime() {
    	return time;
    }

	/**
	 * @param time when the event starts
	 * @param eventType Monitor or Birth or Death
	 */
	public Event(double time, EventType eventType) {
		this.time = time;
		this.eventType = eventType;

	}

    /**
     *
     * @param schedule contains all the scheduled future events
     * @param state of the simulation (request queue, logging info, etc.)
     * @param timestamp current time in the discrete simulation
     */
    public void function(PriorityQueue<Event> schedule, State state, double timestamp, double maxQueueK) {
    	schedule.remove(this);

		switch (eventType) {
		case DEATH:
	    	/**
		     * remove the record of the request from the data structure of requests in the system
		     * Also, collect and compute some statistics.
	    	 */
		    Request req = state.queue.remove();
		    req.setFinishServiceTime(timestamp);

	    	state.totalRequestTime += req.getTq();
		    state.busyTime += req.getTs();
		    state.numCompletedRequests += 1;

		    System.out.printf("R%d DONE: %f\n", req.getId(), req.getFinishServiceTime());

		    /**
		     * look for another blocked event in the queue that wants to execute and schedule it's death.
		     * at this time the waiting request enters processing time.
	    	 */
		    if (state.queue.size() > 0){
				Request nextReq = state.queue.peek();
				nextReq.setStartServiceTime(timestamp);

				System.out.printf("R%d START: %f\n", nextReq.getId(), nextReq.getStartServiceTime());

				/* Schedule the next death event. */
				Event nextDeath = new Event(timestamp + getTimeOfNextDeath(), EventType.DEATH);
				schedule.add(nextDeath);
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
                System.out.printf("R%d DROP: %f\n", request.getId(), request.getArrivalTime());
                state.totalDrop += 1;
                state.queue.remove(request);
            } else
            {

    		    /**
    		     * if the queue is empty then start executing directly there is no waiting time.
    	    	 */
    		    if(state.queue.size() == 1) {
    				request.setStartServiceTime(timestamp);
    				Event event = new Event(timestamp + getTimeOfNextDeath(), EventType.DEATH);
    				schedule.add(event);

    				System.out.printf("R%d START: %f\n", request.getId(), timestamp);
    	  	  	}
            }

            /**
             * schedule the next arrival
             */
            Event nextArrival = new Event(timestamp + getTimeOfNextBirth(), EventType.BIRTH);
            schedule.add(nextArrival);

            break;

		case MONITOR:
		    /**
		     * inspect the data structures describing the simulated system and log them
		     */
		    state.numMonitorEvents += 1;
		    state.totalQueueLen += state.queue.size();

		    /**
		     * Schedule another monitor event following PASTA principle
		     */
		    Event nextMonitor = new Event(timestamp + getTimeOfNextMonitor(), EventType.MONITOR);
		    schedule.add(nextMonitor);

		    break;
		}
    }

    /* Make sure the events are sorted according to their happening time. */
    public int compareTo(Event e) {
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
		return exp(SimulatorK.lambda);
	}

	/**
	 *
	 * @return time for the next death event
	 */
	public static double getTimeOfNextDeath() {
		return exp(1.0/SimulatorK.Ts);
	}

	/**
	 *
	 * @return time for the next monitor event
	 */
	public static double getTimeOfNextMonitor() {
		return exp(SimulatorK.lambda);
	}
}
