package my;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListStudy {

    @Test
    public void gg() {
        List<Integer> arr = new ArrayList<>(8);
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(4);
        arr.add(5);
        arr.add(6);
        Integer[] ins = new Integer[10];
        Arrays.fill(ins, 99);
        Integer[] integers = arr.toArray(ins);
        System.out.println(Arrays.toString(ins));
        System.out.println(Arrays.toString(integers));
    }

    @Test
    public void xx(){
        List<Integer> arr = new ArrayList<>(8);
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(4);
        arr.add(5);
        arr.add(6);
        arr.remove(new Integer(2));
        for (Integer integer : arr) {
            System.out.println(integer);
        }
    }
}
