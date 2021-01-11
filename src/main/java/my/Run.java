package my;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenwei
 * @date 2019/12/16
 */

public class Run {
    public static void main(String[] args) throws Exception {
        QThread qt = new QThread();
        qt.kk();
    }
}


class QThread {
    AtomicInteger counta = new AtomicInteger(0);
    AtomicInteger countb = new AtomicInteger(0);
    AtomicInteger countc = new AtomicInteger(0);
    Lock la = new ReentrantLock();
    Lock lb = new ReentrantLock();
    Lock lc = new ReentrantLock();
    File fa = new File("D:\\wei\\Desktop\\res\\a.txt");
    File fb = new File("D:\\wei\\Desktop\\res\\b.txt");
    File fc = new File("D:\\wei\\Desktop\\res\\c.txt");

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    @Test
    public void kk() throws Exception {
        FileWriter outa = new FileWriter(fa);
        FileWriter outb = new FileWriter(fb);
        FileWriter outc = new FileWriter(fc);
        Thread ta = new Thread(() -> aha(outa, outb, outc, "1", 0, 2, 1));
        Thread tb = new Thread(() -> aha(outa, outb, outc, "2", 1, 1, 2));
        Thread tc = new Thread(() -> aha(outa, outb, outc, "3", 2, 0, 0));
        ta.start();
        tb.start();
        tc.start();
        ta.join();
        tb.join();
        tc.join();
        outa.close();
        outb.close();
        outc.close();
    }

    private void aha(FileWriter outa, FileWriter outb, FileWriter outc, String s, int i, int i2, int i3) {
        int a = 0;
        int b = 0;
        int c = 0;
        do {
            if (la.tryLock()) {
                try {
                    if (counta.get() % 3 == i && a < 10) {
                        outa.write(s);
                        outa.flush();
                        counta.incrementAndGet();
                        a++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    la.unlock();
                }
            }
            if (lb.tryLock()) {
                try {
                    if (countb.get() % 3 == i2 && b < 10) {
                        outb.write(s);
                        outb.flush();
                        countb.incrementAndGet();
                        b++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    lb.unlock();
                }
            }
            if (lc.tryLock()) {
                try {
                    if (countc.get() % 3 == i3 && c < 10) {
                        outc.write(s);
                        outc.flush();
                        countc.incrementAndGet();
                        c++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    lc.unlock();
                }
            }
        } while (a < 10 || b < 10 || c < 10);
    }
}

