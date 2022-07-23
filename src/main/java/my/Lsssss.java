package my;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Lsssss {
    @Test
    public void xx() {
        List<Student> aa = new ArrayList<>();
        aa.add(new Student(1, "wang"));
        aa.add(new Student(2, "ming"));
        aa.add(new Student(3, "hong"));
        aa.add(new Student(4, "le"));
        aa.add(new Student(5, "qifa"));
        System.out.println(aa.get(0));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Student {
        private Integer age;
        private String name;
    }
}

