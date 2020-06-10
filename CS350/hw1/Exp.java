import java.util.*;


public class Exp
{
    public static double getExp(double lambda)
    {
        Random randomNumber = new Random();
        double res = -1 * (Math.log(randomNumber.nextDouble())/lambda);
        return res;
    }

    public static void main(String[] args)
    {
        double lambda = Double.parseDouble(args[0]);
        for (int x = 0; x < Integer.parseInt(args[1], 10); x++)
        {
            System.out.println(getExp(lambda));
        }
    }
}
