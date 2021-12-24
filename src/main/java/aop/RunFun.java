package aop;

import java.lang.reflect.Proxy;

/**
 * @author chenwei
 * @date 2020/5/28
 */
public class RunFun {
    public static void main(String[] args) {
        var realSubject = new RealSubject();
        var handler = new SubjectInvocationHandler(realSubject);
        var p = (Subject) Proxy.newProxyInstance(realSubject.getClass().getClassLoader(),
                realSubject.getClass().getInterfaces(), handler);
        p.SayGoodBye();
    }
}
