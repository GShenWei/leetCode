package my;

import org.junit.Test;

import java.util.Random;

/**
 * @author chenwei
 * @version 1.0
 * @date 2019-10-09
 */
public class RandomTest {
    @Test
    public void xx(){
        Random random = new Random();
        //random.setSeed(10000L);
        System.out.println(random.nextInt());
    }
}
