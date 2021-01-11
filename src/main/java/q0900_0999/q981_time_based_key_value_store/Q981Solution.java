package q0900_0999.q981_time_based_key_value_store;

import org.junit.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author chenwei
 * @version 1.0
 * @date 2019-09-19
 */
public class Q981Solution {
    @Test
    public void gg() {
        float f = 1234234f;
        float k;
        //System.out.println(k = f);
        //System.out.println(k);
        //System.out.println(Float.toHexString(f));
        System.out.println(Float.floatToRawIntBits(f));
    }
}

class TimeMap {

    /**
     * Initialize your data structure here.
     */
    private Map<String, PriorityQueue<Item>> kvMap;

    public TimeMap() {
        kvMap = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        PriorityQueue<Item> ints = kvMap.computeIfAbsent(key, it -> new PriorityQueue<>(Comparator.comparingInt(a -> -a.timestamp)));
        ints.add(new Item(value, timestamp));
    }

    public String get(String key, int timestamp) {
        PriorityQueue<Item> items = kvMap.get(key);
        if (items == null || items.isEmpty()) {
            return "";
        }
        for (Item item : items) {
            if (item.timestamp <= timestamp) {
                return item.value;
            }
        }
        return "";
    }

    class Item {
        String value;
        int timestamp;

        Item(String value, int timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }
    }

}
