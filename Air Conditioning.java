//USACO 2023 January Contest, Bronze
//Problem 2. Air Cownditioning II

import java.util.*;
public class Air Cownditioning {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    int m = in.nextInt();
    ArrayList<Integer> s = new ArrayList<Integer>();
    ArrayList<Integer> t = new ArrayList<Integer>();
    ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>();
    for (int i=0; i<n; i++) {
      int start = in.nextInt();
      int end = in.nextInt();
      int temp = in.nextInt();
      for (int j=0; j<end-start+1; j++) {
        s.add(j+start);
        t.add(temp);
      }
    }
    for (int i=0; i<m; i++) {
      a.add(new ArrayList<Integer>());
      for (int j=0; j<4; j++) {
      a.get(i).add(in.nextInt());
      }
    }
    int sum = 0;
    for (int i=0; i<s.size(); i++) {
      int stall = s.get(i);
      int impact = 0;
      int index = -1;
      for (int j=0; j<a.size(); j++) {
        if (stall >= a.get(j).get(0) && stall <= a.get(j).get(1)) {
          impact += a.get(j).get(2);            
          index = j;
        }
      }
      if (impact <= t.get(i) && index != -1) {
        sum += a.get(index).get(3);
        for (int j=a.get(index).get(0); j<=a.get(index).get(1); j++) {
          if (s.contains(j)) {
            int temp = t.get(s.indexOf(j))-a.get(index).get(2);
            if (temp<0) {
              t.set(s.indexOf(j), 0);
            } else
              t.set(s.indexOf(j),temp);            
          } 
        }
        a.remove(index);
      }
    }
    boolean done = true;
    for (int i=0; i<t.size(); i++) {
      if (!t.get(i).equals(0)) {
        done = false;
      }
    }
    while (!done) {
      double min = -1;        
      double cost = 0;
      int index = 0;
      for (int i=0; i<a.size(); i++) {
        int impact = 0;          
        for (int j=a.get(i).get(0); j<= a.get(i).get(1); j++) {
          if (s.contains(j)) {
            impact += Math.min(a.get(i).get(2), t.get(s.indexOf(j)));
          }
          cost = ((double) a.get(i).get(3))/impact;
        }
        if (cost < min || min == -1) {
          min = cost;
          index = i;
        }
      }
      sum += a.get(index).get(3);
      for (int j=a.get(index).get(0); j<=a.get(index).get(1); j++) {
        if (s.contains(j)) {
        int temp = t.get(s.indexOf(j))-a.get(index).get(2);
        if (temp<0) {
          t.set(s.indexOf(j), 0);
        } else
          t.set(s.indexOf(j),temp);
        }
      }
      a.remove(index);
      done = true; 
      for (int i=0; i<t.size(); i++) {
        if (!t.get(i).equals(0)) {
          done = false;
        }
      }
    }    
    System.out.println(sum);
  }  
}