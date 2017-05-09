package Baseball;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by crc07 on 4/29/17.
 */
public class Utils {

    public static int randomNumber() {

    try{
        Random rand = new Random();
        int n = rand.nextInt(100) + 1;
        return n;
    } catch (ArithmeticException ae) {
        System.out.println("RandomNumber message error : " + ae.getMessage() + "Class" + ae.getClass() +"Cause: "+ ae.getCause());
    }
        return 0;
    }
    public void printService(List array) {

        for (Object temp: array ) {
            System.out.println(temp+"\t");
        }

    }
    public void printService(String s) {

        System.out.println(s);

    }

}
