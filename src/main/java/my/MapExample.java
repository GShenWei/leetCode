package my;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Student {
    private String className;
    private String name;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

public class MapExample {
    public static void main(String[] args) {
        Stream<Student> s = Stream.of();
        Map<String, List<Student>> mp = s.filter(it -> "yiban".equals(it.getClassName()))
                .collect(Collectors.groupingBy(Student::getClassName));
    }
}
