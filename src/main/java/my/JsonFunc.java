package my;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonFunc {
    @Test
    public void dealJson() {
        //String data = "{\"date_cd\":\"20200701\",\"xtjsje\":397554.61,\"yhrzje\":397604.61,\"repeat_filter_content\":\"{\\\"date_cd\\\":\\\"20200701\\\"}\"}";
        String data2 = "[{\"type\": 1, \"field\": \"date_cd\", \"width\": 120, \"showName\": \"账期\", \"xmSpecialId\": 1}, {\"type\": 1, \"field\": \"xtjsje\", \"width\": 120, \"showName\": \"系统结算金额\", \"xmSpecialId\": 2}, {\"type\": 1, \"field\": \"yhrzje\", \"width\": 120, \"showName\": \"实际入账金额\", \"xmSpecialId\": 3}]";
        //System.out.println(data);
        //System.out.println(data2);

        Gson gson = new Gson();
        //Map<String, Object> dt = gson.fromJson(data, Map.class);
        // System.out.println(dt);

        List<Map<String, Object>> dt2 = gson.fromJson(data2, List.class);

        System.out.println(dt2);

        for (Map<String, Object> d : dt2) {
            Object field = d.get("field");
            if (field != null && "date_cd".equals(field.toString())) {
                Object showName = d.get("showName");
                System.out.println(showName);
            }
        }
    }

    @Test
    public void dealMap(){
        List<Map<String,String>> li = new ArrayList<>();
        Map<String,List<String>> res = new HashMap<>();
        for (Map<String, String> m : li) {
            for (Map.Entry<String, String> en : m.entrySet()) {
                List<String> ll = res.computeIfAbsent(en.getKey(), k -> new ArrayList<>());
                ll.add(en.getValue());
            }
        }
        System.out.println(res);
    }
}



