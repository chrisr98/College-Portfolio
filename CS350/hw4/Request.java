
public class Request {
    /**
     * implement the Request class, which should include the necessary information to do statistics
     * over all the requests. e.g., to calculate Tq, one needs to record the arrival time, and finish time
     * of the request
     */
    private int requestId;
    private double arrivalTime;
    private double startServiceTime;
    private double finishServiceTime;
    private double startServiceTime1;
    private double finishServiceTime1;
    private double startServiceTime2;
    private double finishServiceTime2;
    private double startServiceTime3;
    private double finishServiceTime3;
    
    private int serverId;
    
    public Request(int id) {
        requestId = id;
        serverId = -1;
    }

    public int getId() {
        return requestId;
    }

    public void setServerId(int id) {
    	serverId = id;
    }

    public int getServer() {
    	return serverId;
    }

    public void setArrivalTime(double time) {
        arrivalTime = time;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setStartServiceTime(double time) {
        startServiceTime = time;
    }

    public double getStartServiceTime() {
        return startServiceTime;
    }

    public void setFinishServiceTime(double time) {
        finishServiceTime = time;
    }

    public double getFinishServiceTime() {
        return finishServiceTime;
    }
    
    
    //-----------------------------
    public void setStartServiceTime1(double time) {
        startServiceTime1 = time;
    }

    public double getStartServiceTime1() {
        return startServiceTime1;
    }

    public void setFinishServiceTime1(double time) {
        finishServiceTime1 = time;
    }

    public double getFinishServiceTime1() {
        return finishServiceTime1;
    }
    
    
    //-----------------------------
    public void setStartServiceTime2(double time) {
        startServiceTime2 = time;
    }

    public double getStartServiceTime2() {
        return startServiceTime2;
    }

    public void setFinishServiceTime2(double time) {
        finishServiceTime2 = time;
    }

    public double getFinishServiceTime2() {
        return finishServiceTime2;
    }
    
    
    //-----------------------------
    public void setStartServiceTime3(double time) {
        startServiceTime3 = time;
    }

    public double getStartServiceTime3() {
        return startServiceTime3;
    }

    public void setFinishServiceTime3(double time) {
        finishServiceTime3 = time;
    }

    public double getFinishServiceTime3() {
        return finishServiceTime3;
    }
    
    
    

    /**
     * Get total response time for this request
     */
    public double getTq() {
        return finishServiceTime - arrivalTime;
    }
    
    public double getTq1() {
        return finishServiceTime1 - arrivalTime;
    }
    
    public double getTq2() {
        return finishServiceTime2 - arrivalTime;
    }
    
    public double getTq3() {
        return finishServiceTime3 - arrivalTime;
    }

    /**
     * Get service time for this request
     */
    public double getTs() {
        return finishServiceTime - startServiceTime;
    }
    
    public double getTs1() {
        return finishServiceTime1 - startServiceTime1;
    }
    
    public double getTs2() {
        return finishServiceTime2 - startServiceTime2;
    }
    
    public double getTs3() {
        return finishServiceTime3 - startServiceTime3;
    }
}