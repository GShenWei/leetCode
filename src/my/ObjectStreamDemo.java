package my;

import org.junit.Test;

import java.io.*;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/12/14
 */
public class ObjectStreamDemo {
    @Test
    public void xx() throws IOException {
        A a = new A();
        B b = new B();
        b.a = a;
        a.b = b;
        //创建文件字节输出流对象
        FileOutputStream outputStream = new FileOutputStream("D:\\gggggggg.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(a);
        //最后记得关闭资源，objectOutputStream.close()内部已经将outputStream对象资源释放了，所以只需要关闭objectOutputStream即可
        objectOutputStream.close();
    }


    @Test
    public void yy() throws IOException, ClassNotFoundException {
        FileInputStream inputStream = new FileInputStream("D:\\gggggggg.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        Object o = objectInputStream.readObject();
        System.out.println(o);
    }

    private static volatile ObjectStreamDemo objectStreamDemo;
}

class A implements Serializable {
    B b;
    String aStr = "这是A";
}

class B implements Serializable {
    A a;
    String bStr = "这是B";
}
