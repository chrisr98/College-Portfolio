import java.util.*;

public class Simulator
{
    public static ArrayList<Double> arrTimes = new ArrayList<Double>();
    public static ArrayList<Double> startingTimes = new ArrayList<Double>();
    public static ArrayList<Double> endingTimes = new ArrayList<Double>();

    public static int firstArr = 0;

    public static double avgArrivalRate;
    public static double avgServiceTime;

    public static double time;
    public static double timeToServe;



    public static double getExp(double lambda)
    {
        Random randomNumber = new Random();
        double res = -1 * (Math.log(randomNumber.nextDouble())/lambda);
        return res;
    }



    public static void simulate(double maxTime)
    {
        while (time <= maxTime)
        {
            if(firstArr == 0)
            {
                time = getExp(avgArrivalRate);
                arrTimes.add(time);
                startingTimes.add(time);
                timeToServe = avgServiceTime + time;
                endingTimes.add(timeToServe);
                firstArr += 1;
            } else
            {
                time += getExp(avgArrivalRate);
                arrTimes.add(time);
                if(time >= timeToServe)
                {
                    startingTimes.add(time);
                    timeToServe = avgServiceTime + time;
                    endingTimes.add(timeToServe);
                } else
                {
                    startingTimes.add(timeToServe);
                    timeToServe += avgServiceTime;
                    endingTimes.add(timeToServe);
                }
            }
        }

        int arrivalIndex = 0;
        int startingIndex = 0;
        int endingIndex = 0;
        double completedTask = 0;
        double numerOfReq = 0;

        double firstToArrive = arrTimes.get(0);
        double firstToStart = startingTimes.get(0);
        double firstToEnd = endingTimes.get(0);



        while ((firstToArrive < maxTime) || (firstToStart < maxTime) || (firstToEnd < maxTime))
        {
            firstToArrive = arrTimes.get(0);
            firstToStart = startingTimes.get(0);
            firstToEnd = endingTimes.get(0);

            if((firstToArrive <= firstToStart) && (firstToArrive < firstToEnd) && (firstToArrive < maxTime))
            {
                System.out.printf("R%d ARR: %f%n", arrivalIndex, firstToArrive);
                arrTimes.remove(0);
                arrivalIndex += 1;
                numerOfReq += 1;
                if(arrTimes.size() == 0)
                {
                    arrTimes.add(maxTime+1);
                    firstToArrive = arrTimes.get(0);
                }

            } else if ((firstToStart < firstToArrive) && (firstToStart < firstToEnd) && (firstToStart < maxTime))
            {
                System.out.printf("R%d START: %f%n", startingIndex, firstToStart);
                startingTimes.remove(0);
                startingIndex += 1;
                if(startingTimes.size() == 0)
                {
                    startingTimes.add(maxTime+1);
                    firstToStart = startingTimes.get(0);
                }

            } else if (firstToEnd < maxTime)
            {
                System.out.printf("R%d DONE: %f%n", endingIndex, firstToEnd);
                endingTimes.remove(0);
                completedTask += 1;
                endingIndex += 1;
                if(endingTimes.size() == 0)
                {
                    endingTimes.add(maxTime+1);
                    firstToEnd = endingTimes.get(0);
                }

            } else
            {
                break;
            }
        }

        // double utilization = time/timeToServe;
        // double avgQueueLength = numerOfReq/avgArrivalRate;
        // double avgResponseTime = time/completedTask;
        //
        // System.out.printf("UTIL: %f%n", utilization);
        // System.out.printf("QLEN: %f%n", avgArrivalRate);
        // System.out.printf("TRESP: %f%n", avgServiceTime);

    }



    public static void main(String[] args)
    {
        double simulateTime = Double.parseDouble(args[0]);
        avgArrivalRate = Double.parseDouble(args[1]);
        avgServiceTime = Double.parseDouble(args[2]);

        simulate(simulateTime);
    }

}
