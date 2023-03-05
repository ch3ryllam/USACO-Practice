//USACO 2023 February Contest, Silver
//Problem 3. Moo Route II

import java.util.*;
public class Moo Route {
    int N;
    int E;
    Node[] nArr;
    Edge[] eArr;
    static final int debug = 0;
    public class Node implements Comparable<Node> {
        public int name;
        public int start;
        public int end;
        public int layover;
        public int visited;
        public ArrayList<Edge> edges;
        Node (int l_n, int l_s, int l_e, int l_l, int v) 
            { name = l_n; start=l_s; end = l_e; layover=l_l; visited = v; 
            edges = new ArrayList<Edge>(100);}
        @Override
        public String toString() {
            return (name + " (" + start + ", " + end + ", " + layover + ") " + visited + ", Edges: " + edges);
        }
        void addPQEdges() {
          for (int i=0; i<edges.size(); i++) {
            Edge e = edges.get(i);
            if (e.visited == 0) {
              if (end <= e.start) {
                if (debug >= 10) System.out.println ("Add PQ Edges " + e);
                  pQueue.add (e);
                  e.visited = -1;
                }
              } 
           }
        }
        boolean visit (int arrivalTime) {
            if (debug >= 10) System.out.println ("Visiting Node - Begin " + this + ", Arrival Time = " + arrivalTime);
            if (visited == 0) {    // 1st time visit
              visited++;         // setting visit = 1
              start = arrivalTime;
              end = arrivalTime+layover;
              addPQEdges();
            } else if (visited >= 1) {   // visited previously
              visited++;
              if (arrivalTime < start) {
                start = arrivalTime;
                end = arrivalTime+layover;
                addPQEdges();
              }
            }
            if (debug >= 10) System.out.println ("Visiting Node - End " + this);
            if (debug >= 5) printPriorityQueue();
            return true;
        }
        boolean visit (int startTime, int endTime) {
          if (debug >= 10) System.out.println ("Visiting Node - Begin" + this);
          start = startTime;
          end = endTime;
          visited++;
          addPQEdges();
          if (debug >= 10) System.out.println ("Visiting Node - End " + this);
          if (debug >= 5) printPriorityQueue();
          return true;
        }
        public int compareTo(Node n) { return Integer.compare(name, n.name); }
    }
    void printNodes () {
      for (int i=1; i<=N; i++) {
        System.out.println (nArr[i]);
      }
    }
    public class Edge implements Comparable<Edge> {
        public int name;
        public int start; 
        public int end;
        public int nodeStart;
        public int nodeEnd;
        public int visited;

        Edge (int l_n, int l_s, int l_e, int l_ss, int l_ne, int l_v) 
            { name = l_n; start=l_s; end = l_e; nodeStart=l_ss; nodeEnd=l_ne; visited=l_v;}

        @Override
        public String toString() {

            return ("E="+ name + ", (" + nodeStart + ", " + nodeEnd + "), Time: " + " (" + start + ", " + end + ") " + visited);
        }
        public int compareTo(Edge c) { 
          int comp = Integer.compare(end, c.end);
          if (comp == 0) {
            comp = Integer.compare (name, c.name);
          }
          return comp; 
        }
        boolean visit() {
          if (debug >= 10) System.out.println ("Visiting Edge - Begin " + this);
          if (visited == -1) {  // In priority queue
            visited = 2;
            Node n = nArr[nodeEnd];   // destination
            n.visit (end);
          }
          if (debug >= 10) System.out.println ("Visiting Edge - End " + this);
          if (debug >= 5) printPriorityQueue();
          return true;
        }
    }
    void printEdges () {
      for (int i=1; i<=E; i++) {
        System.out.println (eArr[i]);
      }
    }
    TreeSet<Edge> pQueue = new TreeSet<Edge> ();
    void printPriorityQueue() {
      System.out.println("Printing Priority Queue: ");
      System.out.println(pQueue);
    }
    void emptyPriorityQueue() {
      System.out.println ("Emptying Priority Queue");
      Edge e;
      while (!pQueue.isEmpty()) {
        e=pQueue.first();
        pQueue.remove(e);
        System.out.println (e);
      }
    }

    void pqInitialize() {
      for (int i=1; i<=E; i++) {
        Edge e = eArr[i];
        nArr[e.nodeStart].edges.add (e);
      }
      if (debug >= 10) {
        System.out.println ("Printing Nodes after initialization");
        printNodes();
      }
    }

    void pfs() {
        pqInitialize();
        nArr[1].visit(0, 0);
        Edge e;
        while (!pQueue.isEmpty()) {
          e=pQueue.first();
          pQueue.remove(e);
          e.visit();
        }
        Node n;
        for (int i=1; i<=N; i++) {
          n = nArr[i];
          System.out.println (n.start);
        }
    }
  
    public void runTestCase(Scanner in) {
      N = in.nextInt(); 
      E = in.nextInt(); 
  
      nArr = new Node[N+1];
      eArr = new Edge[E+1];

      for (int i=1; i<=E; i++) {
        int nodeStart = in.nextInt();
        int start = in.nextInt();
        int nodeEnd = in.nextInt();
        int end = in.nextInt();

        Edge e = new Edge (i, start, end, nodeStart, nodeEnd, 0);
        eArr[i] = e;
      }

      for (int i=1; i<=N; i++) {
        int layover = in.nextInt();
        Node n = new Node (i, -1, -1, layover, 0);
        nArr[i] = n;
      }

      if (debug >= 10) printNodes();
      if (debug >= 10) printEdges();
      pfs();

    public void runTestCases () {
      Scanner in = new Scanner(System.in);
      runTestCase(in);
      in.close();    
    }

	public static void main(String[] args) {
        Prob3 p = new Prob3();
        p.runTestCases ();
    }
}
