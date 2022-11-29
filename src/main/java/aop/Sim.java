package aop;

import lombok.Data;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
class Attr {
    private String attributeCode1;

    private String attributeCode2;

    private String attributeCode3;

    private String attributeCode4;

    private String attributeValue1;

    private String attributeValue2;

    private String attributeValue3;

    private String attributeValue4;

}

@Data
class Line {
    private String cFree1;

    private String cFree2;

    private String cFree3;

    private String cFree4;

    private String cFree5;

    private String cFree6;

    private String cFree7;

    private String cFree8;

    private String cFree9;

    private String cFree10;

}

public class Sim {
    public void xx() {
        Attr dto = new Attr();
        dto.setAttributeCode1("cFree1");
        dto.setAttributeValue1("ahaha1");
        dto.setAttributeCode2("cFree2");
        dto.setAttributeValue2("ahaha2");
        Line lineDTO = new Line();
        Field[] hfds = Attr.class.getDeclaredFields();
        for (Field f : hfds) {
            String name = f.getName();
            if (name.startsWith("attributeCode")) {
                String idx = name.substring(name.length() - 1);
                f.setAccessible(true);
                try {
                    Object code = f.get(dto);
                    if (code != null) {
                        Field lf;
                        try {
                            lf = Line.class.getDeclaredField(code.toString());
                            lf.setAccessible(true);
                            Field valueF = Attr.class.getDeclaredField("attributeValue" + idx);
                            valueF.setAccessible(true);
                            lf.set(lineDTO, valueF.get(dto));
                        } catch (NoSuchFieldException ignored) {
                        }
                    }
                } catch (IllegalAccessException ignored) {
                }
            }
        }
        System.out.println(lineDTO);
    }

    @Test
    public void gg() {
        Sim sim = new Sim();
        sim.xx();
    }


    public int singleNumber(int[] nums) {
        int x1 = 0;
        int x2 = 0;
        int mask;
        for (int num : nums) {
            x2 ^= x1 & num;
            x1 ^= num;
            mask = ~(x1 & x2);
            x2 = x2 & mask;
            x1 = x1 & mask;
        }
        return x1;
    }

    @Test
    public void ggf() {
        System.out.println(find(new int[]{1, 1, 1, 4, 4, 4, 2, 2, 2, -8}));
    }

    public static int find(int[] arr) {
        int[] temp = new int[32];
        int res = 0;
        for (int i : arr) {
            for (int j = 0; j < 32 && i != 0; j++) {
                temp[j] += i & 1;
                i = i >>> 1;
            }
        }
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] % 3 != 0) {
                res += 1 << i;
            }
        }
        return res;
    }


    @Test
    public void go(){
        List<Integer> li = new ArrayList<Integer>();
        li.add(1);
        li.add(3);
        li.add(2);
        li = li.stream().sorted(Comparator.comparing(e -> Long.parseLong(e.toString()))).collect(Collectors.toList());
        System.out.println(li);

    }
}



