public class Simulator {
    /* Average arrival rate of requests (requests per millisecond). */
    static double lambda;

    /* Average service time at the server (milliseconds). */
    static double Ts0;
    static double Ts1;
    static double Ts2;
    static double t1;
    static double p1;
    static double t2;
    static double p2;
    static double t3;
    static double p3;
    static double prob0_1;
    static double prob0_2;
    static double prob3_out;
    static double prob3_1;
    static double prob3_2;
    
    
    //K2 maximum length of the queue expressed in number of requests at S2
    static int k2;


    public static void simulate(double time) {
        Controller.runSimulation(time, lambda, Ts0, Ts1, Ts2, t1, p1, t2, p2, t3, p3, k2, prob0_1, prob0_2, prob3_out, prob3_1, prob3_2);
    }

    public static void main(String args[]) {
        /* Simulation time (milliseconds). */
        double time = Double.parseDouble(args[0]);

        /* Average arrival rate of requests (requests per millisecond). */
        lambda = Double.parseDouble(args[1]);

        /* Average service time at the server (milliseconds). */
        Ts0 = Double.parseDouble(args[2]);
        Ts1 = Double.parseDouble(args[3]);
        Ts2 = Double.parseDouble(args[4]);
        t1 = Double.parseDouble(args[5]);
        p1 = Double.parseDouble(args[6]);
        t2 = Double.parseDouble(args[7]);
        p2 = Double.parseDouble(args[8]);
        t3 = Double.parseDouble(args[9]);
        p3 = Double.parseDouble(args[10]);
        prob0_1 = Double.parseDouble(args[12]);
        prob0_2 = Double.parseDouble(args[13]);
        prob3_out = Double.parseDouble(args[14]);
        prob3_1 = Double.parseDouble(args[15]);
        prob3_2 = Double.parseDouble(args[16]);

        /* Maximum length of the queue expressed in number of requests. */
        k2 = Integer.parseInt(args[11]);


        simulate(time);
    }
}
