package q149_max_points_on_a_line;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/7
 */

@SuppressWarnings("Duplicates")
public class Q149Solution_new {
    class Point {
        int x;
        int y;

        Point() {
            x = 0;
            y = 0;
        }

        Point(int a, int b) {
            x = a;
            y = b;
        }
    }

    class ExPoint {
        int x;
        int y;

        ExPoint(Point point) {
            this.x = point.x;
            this.y = point.y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ExPoint exPoint = (ExPoint) o;

            if (x != exPoint.x) {
                return false;
            }
            return y == exPoint.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public String toString() {
            return "ExPoint{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    class Line {
        Long k;
        Long b;

        public Line(Long k, Long b) {
            this.k = k;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Line line = (Line) o;

            if (!k.equals(line.k)) return false;
            return b.equals(line.b);
        }

        @Override
        public int hashCode() {
            int result = k.hashCode();
            result = 31 * result + b.hashCode();
            return result;
        }
    }


    public int maxPoints(Point[] points) {
        if (points.length == 1) {
            return 1;
        }
        ExPoint[] exPoints = new ExPoint[points.length];
        Map<ExPoint, List<String>> pointCount = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            ExPoint exPoint = new ExPoint(points[i]);
            exPoints[i] = exPoint;
            List<String> xx = pointCount.computeIfAbsent(exPoint, key -> new ArrayList<>());
            xx.add(exPoint.toString() + i);
        }
        Map<Line, Set<String>> map = new HashMap<>();
        int max = 0;
        for (int i = 0; i < exPoints.length - 1; i++) {
            ExPoint p1 = exPoints[i];
            for (int j = i + 1; j < exPoints.length; j++) {
                //直线方程为y=bx+k;
                //算出每两个点定义的直线的 k 和 b
                //需要先求出来k,由于担心精度丢失,k算出来的值翻100000000倍后用long保存
                //所有直线的斜率都乘上了1000000,所以可以直接将它当成斜率了
                ExPoint p2 = exPoints[j];

                long k;
                long b;
                if (p1.x - p2.x == 0) {
                    k = Long.MAX_VALUE;
                    b = (long) p1.x;
                } else {

                    k = (long) ((p2.y - p1.y + 0.0) / (p2.x - p1.x + 0.0) * 100000);
                    b = p2.y - k * p2.x;
                }
                Line line = new Line(k, b);
                Set<String> set = map.computeIfAbsent(line, key -> new HashSet<>());
                set.addAll(pointCount.get(p1));
                set.addAll(pointCount.get(p2));
                max = Math.max(max, set.size());
            }
        }

        return max;
    }


    @Test
    public void xx() {
        //int[][] x = {{0, -12}, {5, 2}, {2, 5}, {0, -5}, {1, 5}, {2, -2}, {5, -4}, {3, 4}, {-2, 4}, {-1, 4}, {0, -5}, {0, -8}, {-2, -1}, {0, -11}, {0, -9}};
        int[][] x = {{0, 0}, {94911151, 94911150}, {94911152, 94911151}};
        Point[] points = new Point[x.length];
        for (int i = 0; i < x.length; i++) {
            Point p = new Point(x[i][0], x[i][1]);
            points[i] = p;
        }
        int i = maxPoints(points);
        System.out.println(i);
    }


    @Test
    public void l() {
        double d = 1.111111;
        long l = (long) (d * 100000000);
        System.out.println(l);
    }


}
