package my;

class Student implements Cloneable{
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

    @Override
    public Student clone() {
        try {
            Student clone = (Student) super.clone();
            clone.setName(name);
            clone.setClassName(className);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

public class MapExample {
    public static void main2() {
        Student st = new Student();
        st.setName("ssss");
        st.setClassName(("gggg"));
        Student newSt = st.clone();
        ct(newSt);
        System.out.println(st.getClassName()); // 这里就还是打印的  gggg  。因为 newCt是一个被克隆的新的对象

        newSt = st;
        ct(newSt);
        System.out.println(st.getClassName()); // 这里打印 dfdfdfdfdf  。因为 newCt不是克隆过来的，而是直接复制的st的引用，它们本质上指的是同一个对象
    }

    public static void ct(Student st){
        st.setClassName("dfdfdfdfdf");
        System.out.println(st.getClassName());
    }
}
