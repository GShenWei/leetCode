package aop;

/**
 * @author chenwei
 * @date 2020/5/28
 */
public class RealSubject implements Subject {
    @Override
    public String SayHello(String name) {
        return "hello " + name;
    }

    @Override
    public String SayGoodBye() {
        return " good bye ";
    }

}
