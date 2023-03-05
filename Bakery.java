//USACO 2023 February Contest, Silver
//Problem 1. Bakery

import java.util.*;
public class Bakery {
    int T;
    int N;
    long tC; 
    long tM;
    static final boolean debug = false;
    static final int debugLevel = 0;
    Equation[] equations;
    long GlobalMax=0;
    public class Equation implements Comparable<Equation> {
        public long x;
        public long y;
        public long maxWaitTime;
        public long order;
        Equation (long c, long m, long max) { 
            x=c; y = m; maxWaitTime=max;
            order = maxWaitTime/x + maxWaitTime/y;
        }
        boolean check (long l_x, long l_y) {
            return (maxWaitTime >= (x*l_x + y*l_y));
        }
        @Override
        public String toString() {
            return (order + " (" + x + ", " + y + ") " + maxWaitTime);
        }
        public int compareTo(Equation n) { return Long.compare(order, n.order); }
    }
    boolean checkEquations(long x, long y) {
        for (int i=0; i<equations.length; i++) {
            if (!equations[i].check(x, y)) 
                return false;
        }
        return true;
    }
    void printEquations () {
        for (int i=0; i<N; i++) {
            System.out.println (equations[i]);
        }
    }
    long lastWorkedX;
    long lastWorkedY;
    long searchXY (long xMin, long xMax, long yMin, long yMax, long level) {
        if (xMin > xMax || yMin > yMax)
            return 0;
        long l_xMin = xMin;
        long l_yMin = yMin;
        long l_xMax = xMax;
        long l_yMax = yMax;
        long l_max = 0;
        long l_lastX = xMin;
        long l_lastY = yMin;

        while (l_xMin <= l_xMax && l_yMin <= l_yMax) {
            long midX = (l_xMin + l_xMax) / 2;
            long midY = (l_yMin + l_yMax) / 2;
            
            boolean midValue = checkEquations (midX, midY);
            if (midValue) {
              l_lastX = midX;
              l_lastY = midY;
              l_max = l_lastX + l_lastY;
              GlobalMax = Math.max (l_max, GlobalMax);
            }

            if (l_xMin == l_xMax && l_yMin == l_yMax)
                break;

            if (midValue == true) {
              l_xMin = Math.min (midX+1, l_xMax);  
              l_yMin = Math.min (midY+1, l_yMax);
            } else {
              l_xMax = Math.max (midX-1, l_xMin);  
              l_yMax = Math.max (midY-1, l_yMin);
            }
        }

        if (xMin != xMax && yMin != yMax) {
            long leftRect = searchXY (xMin, l_lastX, Math.min(l_lastY+1, yMax), yMax, level+1);
            l_max = Math.max(leftRect, l_max);
            GlobalMax = Math.max (l_max, GlobalMax);
            long rightRect = searchXY (Math.min(l_lastX+1, xMax), xMax, yMin, l_lastY, level+1);
            l_max = Math.max (rightRect, l_max);
            GlobalMax = Math.max (l_max, GlobalMax);
        }
        return l_max;
    }


    public void runTestCase(Scanner in) {
        N = in.nextInt(); 
        tC = in.nextInt(); 
        tM = in.nextInt();
        equations = new Equation[N];

        for (int i=0; i<N; i++) {
            long a = in.nextLong();
            long b = in.nextLong();
            long c = in.nextLong();
            equations[i] = new Equation(a, b, c);
        }
        Arrays.sort (equations);

        long max =  searchXY (1, tC, 1, tM, 1);

        if (debug) System.out.println ("Max = " + max + ",  minMoneySpent = " + (tC + tM - max));
        
        System.out.println ((tC + tM - max));
    }

    public void runTestCases () {
        Scanner in = new Scanner(System.in);
        T = in.nextInt();   
        for (int i=0; i<T; i++)
            runTestCase(in);

        in.close();    
    }

	public static void main(String[] args) {
        Bakery bake = new Bakery();
        bake.runTestCases ();
    }
}
