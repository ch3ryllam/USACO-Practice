//USACO 2023 January Contest, Bronze
//Problem 1. Leaders

import java.util.*;
public class Leaders {
  public static String cows;
  public static char[] arr;
  public static int[] list;
  public static int n;
  public static int findLeader(char breed) {
    int result = -1;
    int min = cows.indexOf(String.valueOf(breed));
    int max = cows.lastIndexOf(String.valueOf(breed));  
    for (int i=0; i<n; i++) {
      if(result == -1 && arr[i] == breed) {
         if (i == min && list[i] > max) {
           result = i;
           break;
         }
      }
    }
    return result;
  }
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    n = in.nextInt();
    cows = in.next();
    arr = cows.toCharArray();
    list = new int[n];
    for (int i=0; i<n; i++) {
      list[i] = in.nextInt();
    }
    int gLeader = findLeader ('G');
    int hLeader = findLeader ('H');
    int gCount = 0;
    int hCount = 0;
    if (gLeader!=-1) {
      gCount++;
    }
    if (hLeader!=-1) {
      hCount++;
    }
    if (hLeader != -1) {
      for (int i=0; i<n; i++) {
        if (arr[i] == 'G' && i <= hLeader && list[i] > hLeader) {
          gCount++;
        }
      }
    }
    if (gLeader != -1) {
      for (int i=0; i<n; i++) {
        if (arr[i] == 'H' && i <= gLeader && list[i] > gLeader) {
          hCount++;
        }
      }
    }
    System.out.println(gCount * hCount);
  }
}