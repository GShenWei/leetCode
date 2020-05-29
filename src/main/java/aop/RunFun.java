package aop;

import java.lang.reflect.Proxy;

/**
 * @author chenwei
 * @date 2020/5/28
 */
public class RunFun {
    public static void main(String[] args) {
        Subject realSubject = new RealSubject();
        SubjectInvocationHandler handler = new SubjectInvocationHandler(realSubject);
        Subject p = (Subject) Proxy.newProxyInstance(realSubject.getClass().getClassLoader(),
                realSubject.getClass().getInterfaces(), handler);
        p.SayGoodBye();
    }
}
