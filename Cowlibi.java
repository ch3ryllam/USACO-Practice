//USACO 2023 February Contest, Silver
//Problem 2. Cow-libi
  
import java.util.*;
public class Cowlibi {
    int G;
    int N;
    Garden[] gArr;
    Cow[] cArr;
    static final  int debug = 0;
    public class Garden implements Comparable<Garden> {
        public int name;
        public int x;
        public int y;
        public int t;
        Garden (int l_n, int l_x, int l_y, int l_t) { name = l_n; x=l_x; y = l_y; t=l_t;}
        void print() {
            System.out.println (name + " (" + x + ", " + y + "),  " + t);
        }
        @Override
        public String toString() {
            return (name + " (" + x + ", " + y + "),  " + t);
        }
        public int compareTo(Garden g) { return Integer.compare(t, g.t); }
    }
    void printGardens () {
        for (int i=0; i<G; i++) {
            gArr[i].print();
        }
    }
    public class Cow implements Comparable<Cow> {
        public int name;
        public int x;
        public int y;
        public int t;
        Cow (int l_n, int l_x, int l_y, int l_t) { name = l_n; x=l_x; y = l_y; t=l_t;}
        void print() {
            System.out.println (name + " (" + x + ", " + y + "),  " + t);
        }
        public int compareTo(Cow c) { return Integer.compare(t, c.t); }
        boolean checkWithinRange (Garden g) {
            long timeDistance = (long) (t-g.t) * (long) (t-g.t);
            long distance = (long) (x-g.x)* (long) (x-g.x) + (long) (y-g.y)* (long) (y-g.y);
            if (debug>=5) System.out.println ("CheckWithinRange ---- time , distance " + timeDistance + ", " + distance);
            if (timeDistance >= distance) {
                if (debug>=10) System.out.println ("CheckWithinRange ---- Cow is within range, cow:" + this);
            
                return true; 
            }
            if (debug>=10) System.out.println ("CheckWithinRange ---- Cow is outside range, cow: " + this);
            return false;
        }

        @Override
        public String toString() {
            return (name + " (" + x + ", " + y + "),  " + t);
        }
        
        boolean checkInnocence() {
            Garden p;
            Garden n;
            Garden cGarden = new Garden (this.name, this.x, this.y, this.t);
            p = gTree.lower(cGarden);
            n = gTree.ceiling(cGarden);
            if (debug>=5) {
                System.out.println ("Checking innonocence for Cow");
                this.print();
            }
            if (p!=null && n!=null) {
                if (debug>=5) System.out.println ("Checking both previous and next Gardens");
                if (checkWithinRange(p) && checkWithinRange(n)) 
                    return false;
            } else if (p!=null) {
                if (debug>=5) {
                    System.out.println ("Checking only Previous Garden ");
                    p.print();
                }
                if (checkWithinRange(p))
                    return false;
            } else if (n!= null) {
                if (debug>=5) {
                    System.out.println ("Checking only Next Garden ");
                    n.print();
                }
                if (checkWithinRange(n))
                    return false;
            }

            return true;
        }
    }

    void printCows () {
        for (int i=0; i<N; i++) {
            cArr[i].print();
        }
    }


    TreeSet<Garden> gTree = new TreeSet<Garden>();

    void printTreeGarden() {
        System.out.println(gTree);

        Iterator<Garden> iterator = gTree.iterator();
        System.out.println("Tree set data: ");
     
        while (iterator.hasNext())
            System.out.println(iterator.next());
    }

    public void runTestCase(Scanner in) {
        G = in.nextInt(); 
        N = in.nextInt(); 
  
        gArr = new Garden[G];
        cArr = new Cow[N];

        for (int i=0; i<G; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            int t = in.nextInt();
            Garden g = new Garden(i, x, y, t);
            gArr[i] = g;
            gTree.add (g);
        }

        for (int i=0; i<N; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            int t = in.nextInt();
            cArr[i] = new Cow(i, x, y, t);
        }

        if (debug >=10) System.out.println ("# Gardens =" + G + ", # of Cows = " + N);
        Arrays.sort (gArr);
        Arrays.sort (cArr);

        if (debug >= 10) {
            printGardens();
            printCows();
            printTreeGarden();
        }

        int nInn = 0;     
        for (int i=0; i<N; i++) {
            if (cArr[i].checkInnocence()) {
                nInn++;
                if (debug >= 1) System.out.println ("Cow is innocent:  " + cArr[i]);
            } else { 
                if (debug >= 1) System.out.println ("Cow is guilty:  " + cArr[i]);
            }
        }
        System.out.println (nInn);

    }

    public void runTestCases () {
        Scanner in = new Scanner(System.in);

        runTestCase(in);

        in.close();    
    }

	public static void main(String[] args) {
        Cowlibi cow = new Cowlibi();
        cow.runTestCases ();

    }
}
