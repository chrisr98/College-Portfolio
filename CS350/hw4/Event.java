import java.util.*;

public class Event implements Comparable<Event> {
	/**
	 * Time that the event happens
	 */
	private double time;
	
	private static boolean s11Free = true;
	private static boolean s12Free = true;

	private EventType eventType;

	/* Requests being served. */
	private static ArrayList<Request> inServer11 = new ArrayList<Request>();
	private static ArrayList<Request> inServer12 = new ArrayList<Request>();;

	public double getTime() {
		return time;
	}

	/**
	 * @param time      when the event starts
	 * @param eventType Monitor or Birth or Death
	 */
	public Event(double time, EventType eventType) {
		this.time = time;
		this.eventType = eventType;

	}

	/**
	 *
	 * @param schedule  contains all the scheduled future events
	 * @param state     of the simulation (request queue, logging info, etc.)
	 * @param timestamp current time in the discrete simulation
	 * @param K         maximum length of the queue expressed in number of requests
	 */
	public void function(PriorityQueue<Event> schedule, State state, double timestamp, int K, double lambda, double Ts0,
			double Ts1, double Ts2, double t1, double p1, double t2, double p2, double t3, double p3, double prob0_1,
			double prob0_2, double prob3_out, double prob3_1, double prob3_2) {

		switch (eventType) {
		case DEATH:
			/**
			 * remove the record of the request from the data structure of requests in the
			 * system Also, collect and compute some statistics.
			 */
			if(state.queue_4.size() == 0) {
				break;
			}
			Request req = state.queue_4.remove();
			req.setFinishServiceTime3(timestamp);
			
			state.totalRequestTime_3 += req.getTq3();
			state.busyTime_4 += req.getTs();
			state.numCompletedRequests_3 += 1;
//			state.numBusyServer--;

			System.out.printf("R%d DONE S3: %f%n", req.getId(), req.getFinishServiceTime3());
			
			double percent = Math.random()*(prob3_out + prob3_2 + prob3_1);
			
			if(percent <= prob3_out) {
				System.out.printf("R%d FROM S3 TO OUT: %f%n", req.getId(), req.getFinishServiceTime3());
			} else if (percent > prob3_out && percent <= prob3_out+prob3_2) {
				System.out.printf("R%d FROM S3 TO S2: %f%n", req.getId(), req.getFinishServiceTime3());
				Event S2Arr = new Event(req.getFinishServiceTime3(), EventType.BIRTH_S2);
				schedule.add(S2Arr);
				state.queue_3.add(req);
			} else {
				System.out.printf("R%d FROM S3 TO S1: %f%n", req.getId(), req.getFinishServiceTime3());
				Event S1Arr = new Event(req.getFinishServiceTime3(), EventType.BIRTH_S1);
				schedule.add(S1Arr);
				state.queue_2.add(req);
			}
			

			/**
			 * look for another blocked event in the queue that wants to execute and
			 * schedule it's death. at this time the waiting request enters processing time.
			 */
			if (state.queue.size() > 0) {
				Request nextReq = state.queue.peek();

				nextReq.setStartServiceTime(timestamp);
				
				System.out.printf("R%d START S3: %f%n", nextReq.getId(), timestamp);
				
//				state.numBusyServer++;
//				state.setBusyServer(serverid);

				/* Schedule the next death event. */
				
				double serviceLoc = Math.random()*(p1 + p2 + p3);
				
				if(serviceLoc <= p1) {
					Event nextDeath = new Event(timestamp + t1, EventType.DEATH);
					schedule.add(nextDeath);
				} else if (serviceLoc > p1 && serviceLoc <= p1+p2) {
					Event nextDeath = new Event(timestamp + t2, EventType.DEATH);
					schedule.add(nextDeath);
				} else {
					Event nextDeath = new Event(timestamp + t3, EventType.DEATH);
					schedule.add(nextDeath);
				}
				
			}

			break;

		case DEATH_S0:
			/**
			 * remove the record of the request from the data structure of requests in the
			 * system Also, collect and compute some statistics.
			 */
			if(state.queue.size() == 0) {
				break;
			}
			Request req_0 = state.queue.remove();
			req_0.setFinishServiceTime(timestamp);

			state.totalRequestTime += req_0.getTq();
			state.busyTime += req_0.getTs();
			state.numCompletedRequests += 1;

			System.out.printf("R%d DONE S0: %f%n", req_0.getId(), req_0.getFinishServiceTime());
			
			if(Math.random()*prob0_1+prob0_2 <= prob0_1) {
				System.out.printf("R%d FROM S0 TO S1: %f%n", req_0.getId(), req_0.getFinishServiceTime());
				Event S1Arr = new Event(req_0.getFinishServiceTime(), EventType.BIRTH_S1);
				schedule.add(S1Arr);
				state.queue_2.add(req_0);
			} else {
				System.out.printf("R%d FROM S0 TO S2: %f%n", req_0.getId(), req_0.getFinishServiceTime());
				Event S2Arr = new Event(req_0.getFinishServiceTime(), EventType.BIRTH_S2);
				schedule.add(S2Arr);
				state.queue_3.add(req_0);
			}

			/**
			 * look for another blocked event in the queue that wants to execute and
			 * schedule it's death. at this time the waiting request enters processing time.
			 */
			if (state.queue.size() > 0) {
				Request nextReq = state.queue.peek();

				nextReq.setStartServiceTime(timestamp);

				System.out.printf("R%d START S0: %f%n", nextReq.getId(), timestamp);

				/* Schedule the next death event. */
				Event nextDeath = new Event(timestamp + getTimeOfNextDeath(Ts0), EventType.DEATH_S0);
				schedule.add(nextDeath);
			}

			break;
			
		case DEATH_S1:
			/**
			 * remove the record of the request from the data structure of requests in the
			 * system Also, collect and compute some statistics.
			 */
			if(inServer11.size() == 1) {
				Request req_1 = inServer11.get(0);
				inServer11.remove(0);
				s11Free = true;
				req_1.setFinishServiceTime1(timestamp);

				state.totalRequestTime_1 += req_1.getTq1();
				state.busyTime_11 += req_1.getTs1();
				state.numCompletedRequests_1 += 1;

				System.out.printf("R%d DONE S1,1: %f%n", req_1.getId(), req_1.getFinishServiceTime1());
				
				System.out.printf("R%d FROM S1,1 TO S3: %f%n", req_1.getId(), req_1.getFinishServiceTime1());
				Event S3Arr = new Event(req_1.getFinishServiceTime1(), EventType.BIRTH_S3);
				schedule.add(S3Arr);
				state.queue_4.add(req_1);

				/**
				 * look for another blocked event in the queue that wants to execute and
				 * schedule it's death. at this time the waiting request enters processing time.
				 */
				if (state.queue_2.size() > 0) {
					Request nextReq = state.queue_2.peek();

					nextReq.setStartServiceTime(timestamp);

					System.out.printf("R%d START S1,1: %f%n", nextReq.getId(), timestamp);

					/* Schedule the next death event. */
					Event nextDeath = new Event(timestamp + getTimeOfNextDeath(Ts1), EventType.DEATH_S1);
					schedule.add(nextDeath);
				}
			} else if(inServer12.size() == 1) {
				Request req_1 = inServer12.get(0);
				inServer12.remove(0);
				s12Free = true;
				req_1.setFinishServiceTime1(timestamp);

				state.totalRequestTime_1 += req_1.getTq1();
				state.busyTime_12 += req_1.getTs1();
				state.numCompletedRequests_1 += 1;

				System.out.printf("R%d DONE S1,2: %f%n", req_1.getId(), req_1.getFinishServiceTime1());
				
				System.out.printf("R%d FROM S1,2 TO S3: %f%n", req_1.getId(), req_1.getFinishServiceTime1());
				Event S3Arr = new Event(req_1.getFinishServiceTime1(), EventType.BIRTH_S3);
				schedule.add(S3Arr);
				state.queue_4.add(req_1);

				/**
				 * look for another blocked event in the queue that wants to execute and
				 * schedule it's death. at this time the waiting request enters processing time.
				 */
				if (state.queue_2.size() > 0) {
					Request nextReq = state.queue_2.peek();

					nextReq.setStartServiceTime(timestamp);

					System.out.printf("R%d START S1,2: %f%n", nextReq.getId(), timestamp);

					/* Schedule the next death event. */
					Event nextDeath = new Event(timestamp + getTimeOfNextDeath(Ts1), EventType.DEATH_S1);
					schedule.add(nextDeath);
				}
			}
			
			
			

			break;
			
		case DEATH_S2:
			/**
			 * remove the record of the request from the data structure of requests in the
			 * system Also, collect and compute some statistics.
			 */
			if(state.queue_3.size() == 0) {
				break;
			}
			Request req_2 = state.queue_3.remove();
			req_2.setFinishServiceTime2(timestamp);

			state.totalRequestTime_2 += req_2.getTq2();
			state.busyTime_3 += req_2.getTs2();
			state.numCompletedRequests_2 += 1;

			System.out.printf("R%d DONE S2: %f%n", req_2.getId(), req_2.getFinishServiceTime2());
			System.out.printf("R%d FROM S2 TO S3: %f%n", req_2.getId(), req_2.getFinishServiceTime2());
			Event S23Arr = new Event(req_2.getFinishServiceTime2(), EventType.BIRTH_S3);
			schedule.add(S23Arr);
			state.queue_4.add(req_2);
			
			/**
			 * look for another blocked event in the queue that wants to execute and
			 * schedule it's death. at this time the waiting request enters processing time.
			 */
			if (state.queue_3.size() > 0) {
				Request nextReq = state.queue_3.peek();

				nextReq.setStartServiceTime2(timestamp);

				System.out.printf("R%d START S2: %f%n", nextReq.getId(), timestamp);

				/* Schedule the next death event. */
				Event nextDeath = new Event(timestamp + getTimeOfNextDeath(Ts2), EventType.DEATH_S2);
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

			System.out.printf("R%d ARR: %f%n", request.getId(), request.getArrivalTime());

			/**
			 * if the queue is execute right away.
			 */
			if(state.queue.size() == 1) {
				request.setStartServiceTime(timestamp);
				Event nextDeath = new Event(timestamp + getTimeOfNextDeath(Ts0), EventType.DEATH_S0);
				schedule.add(nextDeath);
				System.out.printf("R%d START S0: %f%n", request.getId(), timestamp);
			}

			/**
			 * schedule the next arrival
			 */
			Event nextArrival = new Event(timestamp + getTimeOfNextBirth(lambda), EventType.BIRTH);
			schedule.add(nextArrival);

			break;

		case MONITOR:
			/**
			 * inspect the data structures describing the simulated system and log them
			 */
			state.numMonitorEvents += 1;
			state.totalQueueLen += state.queue.size();
			state.totalQueueLen_2 += state.queue_2.size();
			state.totalQueueLen_3 += state.queue_3.size();
			state.totalQueueLen_4 += state.queue_4.size();

			/**
			 * Schedule another monitor event following PASTA principle
			 */
			Event nextMonitor = new Event(timestamp + getTimeOfNextMonitor(lambda), EventType.MONITOR);
			schedule.add(nextMonitor);

			break;
			
			
			
		case BIRTH_S1:
			Request ReqS1 = state.queue_2.peek();
			if(state.queue_2.size() > 0) {
				if(s11Free == true) {
					if(state.queue_2.size() == 1) {
						s11Free = false;
						inServer11.add(ReqS1);
						state.queue_2.remove();
						ReqS1.setStartServiceTime1(timestamp);
						Event event = new Event(timestamp + getTimeOfNextDeath(Ts1), EventType.DEATH_S1);
						schedule.add(event);
						System.out.printf("R%d START S1,1: %f%n", ReqS1.getId(), timestamp);
					}
				} else if(s12Free == true) {
					if(state.queue_2.size() == 1) {
						s12Free = false;
						inServer12.add(ReqS1);
						state.queue_2.remove();
						ReqS1.setStartServiceTime1(timestamp);
						Event event = new Event(timestamp + getTimeOfNextDeath(Ts1), EventType.DEATH_S1);
						schedule.add(event);		
						System.out.printf("R%d START S1,2: %f%n", ReqS1.getId(), timestamp);
					}
				}
			}
			
			break;
			
			
			
			
		case BIRTH_S2:
			Request ReqS2 = state.queue_3.peek();
			if(state.queue_3.size() == K) {
				state.totalDropped += 1;
				System.out.printf("R%d DROP S2: %f%n", ReqS2.getId(), timestamp);
			} else {
				state.queue_3.add(ReqS2);
				if(state.queue_3.size() > 0) {
					ReqS2.setStartServiceTime2(timestamp);
					Event event = new Event(timestamp + getTimeOfNextDeath(Ts2), EventType.DEATH_S2);
					schedule.add(event);		
					System.out.printf("R%d START S2: %f%n", ReqS2.getId(), timestamp);
				}
			}
			break;
		case BIRTH_S3:
			Request ReqS3 = state.queue_4.peek();
			if(state.queue_4.size() > 0) {
				ReqS3.setFinishServiceTime3(timestamp);
				double serviceLoc = Math.random()*(p1 + p2 + p3);
				if(serviceLoc <= p1) {
					Event nextDeath = new Event(timestamp + t1, EventType.DEATH);
					schedule.add(nextDeath);
				} else if (serviceLoc > p1 && serviceLoc <= p1+p2) {
					Event nextDeath = new Event(timestamp + t2, EventType.DEATH);
					schedule.add(nextDeath);
				} else {
					Event nextDeath = new Event(timestamp + t3, EventType.DEATH);
					schedule.add(nextDeath);
				}
				System.out.printf("R%d START S3: %f%n", ReqS3.getId(), timestamp);
			}
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
	 * exponential distribution used by {@link #getTimeOfNextBirth()},
	 * {@link #getTimeOfNextDeath()} and {@link #getTimeOfNextMonitor()}
	 * 
	 * @param rate
	 * @return
	 */
	private static double exp(double rate) {
		return (-Math.log(1.0 - Math.random()) / rate);
	}

	/**
	 *
	 * @return time for the next birth event
	 */
	public static double getTimeOfNextBirth(double l) {

		return exp(l);
	}

	/**
	 *
	 * @return time for the next death event
	 */
	public static double getTimeOfNextDeath(double timeservice) {
		return exp(1.0 / timeservice);
	}

	/**
	 *
	 * @return time for the next monitor event
	 */
	public static double getTimeOfNextMonitor(double l) {
		return exp(l);
	}
}
