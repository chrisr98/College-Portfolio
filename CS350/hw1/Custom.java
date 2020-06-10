import java.util.*;


public class Custom
{
    public static double getCustom(double[] outcomes, double[] probabilities)
    {
        Random randomNumber = new Random();
        int len = 0;

        for (double elem: probabilities)
        {
            len += elem*100;
        }

        double[] newList = new double[len];

        for (int x = 0, index = 0; x<probabilities.length; x++)
        {
            int y;
            for (y = index; y< index+probabilities[x]*100; y++) {
                newList[y] = outcomes[x];
            }

            index = y;

        }
        double res = newList[randomNumber.nextInt(len)];
        return res;
    }

    public static void main(String[] args)
    {
        double[] outcomes = new double[5];
        outcomes[0] = Double.parseDouble(args[0]);
        outcomes[1] = Double.parseDouble(args[2]);
        outcomes[2] = Double.parseDouble(args[4]);
        outcomes[3] = Double.parseDouble(args[6]);
        outcomes[4] = Double.parseDouble(args[8]);

        double[] probabilities = new double[5];
        probabilities[0] = Double.parseDouble(args[1]);
        probabilities[1] = Double.parseDouble(args[3]);
        probabilities[2] = Double.parseDouble(args[5]);
        probabilities[3] = Double.parseDouble(args[7]);
        probabilities[4] = Double.parseDouble(args[9]);

        for (int x = 0; x < Integer.parseInt(args[10], 10); x++)
        {
            System.out.println(getCustom(outcomes, probabilities));
        }
    }


}
