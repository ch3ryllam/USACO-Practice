//USACO 2023 January Contest, Bronze
//Problem 3. Moo Operations

import java.util.*;
public class MooOperations {
  public static void main(String[] args){
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    String[] text = new String[n];
    for (int i=0; i<n; i++) {
      text[i] = in.next();
    }
    for (int i=0; i<n; i++) {
      String str = text[i];
      if (str.contains("MOO")) {
        System.out.println(str.length()-3);
      } else if (str.contains("MOM") || str.contains("OOO")) {
        System.out.println(str.length()-2);
      } else if (str.contains("OOM")) {
        System.out.println(str.length()-1);
      } else {
        System.out.println(-1);
      }
    }
  }
}
