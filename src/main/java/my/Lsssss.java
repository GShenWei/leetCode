package my;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Lsssss {
    @Test
    public void xx() {
        List<Student> aa = new ArrayList<>();
        aa.add(new Student(1,"wang"));
        aa.add(new Student(2,"ming"));
        aa.add(new Student(3,"hong"));
        aa.add(new Student(4,"le"));
        aa.add(new Student(5,"qifa"));
    }

    class Student {
        public Student(Integer age, String name) {
            this.age = age;
            this.name = name;
        }

        Integer age;
        String name;
    }
}

