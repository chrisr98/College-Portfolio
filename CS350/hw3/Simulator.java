public class Simulator {
    /* Average arrival rate of requests (requests per millisecond). */
    public static double lambda;

    /* Maximum queue length K*/
    public static double maxQueueK;

    /* Average service time at the primary server (milliseconds). */
    public static double Ts1;

    /* Average service time at the secondary server (milliseconds). */
    public static double Ts2;

    public static void simulate(double time) {
        Controller.runSimulation(time, lambda, Ts1, maxQueueK, Ts2);
    }

    public static void main(String args[]) {
        /* Simulation time (milliseconds). */
        double time = Double.parseDouble(args[0]);

        /* Average arrival rate of requests (requests per millisecond). */
        lambda = Double.parseDouble(args[1]);

        /* Average service time at the primary server (milliseconds). */
        Ts1 = Double.parseDouble(args[2]);

        /* Maximum queue length K*/
        maxQueueK = Double.parseDouble(args[3]);

        /* Average service time at the secondary server (milliseconds). */
        Ts2 = Double.parseDouble(args[4]);

    	simulate(time);
    }
}
