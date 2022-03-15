package com.me;


import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simple {
    @Test
    public void sim() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> mm1 = new HashMap<>();
        Map<String, Object> mm2 = new HashMap<>();
        Map<String, Object> mm3 = new HashMap<>();
        mm1.put("1", 1);
        mm1.put("2", 2);
        mm2.put("3", 3);
        mm2.put("4", 4);
        mm3.put("5", 5);
        mm3.put("6", 6);
        mapList.add(mm1);
        mapList.add(mm2);
        mapList.add(mm3);
        Map<String, Object> map = mapList.stream().reduce(new HashMap<>(), (m1, m2) -> {
            m1.putAll(m2);
            return m1;
        });
        Map<String, Object> map2 = mapList.stream().collect(HashMap::new,HashMap::putAll,HashMap::putAll);

        for (Map.Entry<String, Object> e : map.entrySet()) {
            System.out.println(e.getKey() + "," + e.getValue());
        }
        for (Map.Entry<String, Object> e : map2.entrySet()) {
            System.out.println(e.getKey() + "," + e.getValue());
        }

    }
}
