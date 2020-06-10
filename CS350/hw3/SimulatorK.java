public class SimulatorK {
    /* Average arrival rate of requests (requests per millisecond). */
    public static double lambda;

    /* Maximum queue length K*/
    public static double maxQueueK;

    /* Average service time at the server (milliseconds). */
    public static double Ts;

    public static void simulate(double time) {
        ControllerK.runSimulation(time, lambda, Ts, maxQueueK);
    }

    public static void main(String args[]) {
        /* Simulation time (milliseconds). */
        double time = Double.parseDouble(args[0]);

        /* Average arrival rate of requests (requests per millisecond). */
        lambda = Double.parseDouble(args[1]);

        /* Average service time at the server (milliseconds). */
        Ts = Double.parseDouble(args[2]);

        /* Maximum queue length K*/
        maxQueueK = Double.parseDouble(args[3]);

    	simulate(time);
    }
}
