// USACO 2016 Open Contest, Silver
// Problem 1. Field Reduction

import java.util.*;
import java.io.*;

public class Field {

  static class InputReader {
    BufferedReader reader;
    StringTokenizer tokenizer;
    public InputReader(InputStream stream) {
    reader = new BufferedReader(new InputStreamReader(stream), 32768);
    tokenizer = null;
    }
  
    String next() { // reads in the next string
      while (tokenizer == null || !tokenizer.hasMoreTokens()) {
        try {
          tokenizer = new StringTokenizer(reader.readLine());
        } catch (IOException e) {
        throw new RuntimeException(e);
        }
      }
      return tokenizer.nextToken();
    }

    public int nextInt() { // reads in the next int
     return Integer.parseInt(next());
    }
    public long nextLong() { // reads in the next long
      return Long.parseLong(next());
    }

    public double nextDouble() { // reads in the next double
      return Double.parseDouble(next());
    }
  }

  static InputReader r = new InputReader(System.in);
  static PrintWriter pw = new PrintWriter(System.out);


  boolean debug = false;

  public class Position implements Comparable<Position>{
    public int cIndex;
    public int cX;
    public int cY;
  
    public Position (int i, int x, int y) {
      cIndex = i;
      cX = x;
      cY = y;
    }
    @Override
    public String toString() {
        return ("Position " + cIndex + ", (" + cX + ", " + cY + ") ");
    }
    public int compareTo(Position p) {
      return Integer.compare(cX, p.cX);
    }
  }

  class PositionCompareY implements Comparator<Position>{
    public int compare(Position a, Position b){
      return Integer.compare(a.cY, b.cY);
    }
  }


  public void printIntArray (String n, int[] a) {
    pw.println (n);
    for (int i=1; i<a.length; i++) {
      pw.print (a[i] + ", ");
    }
    pw.println();
  }

  public void printPositionArray (String desc, Position[] pArray) {
    pw.println(desc);
    for (int i=0; i<pArray.length; i++) {
      pw.println (pArray[i]);
    }
  }


  public int nPosition;
  public Position[] pArr;  // cow array
  public Position[] pXArr;
  public Position[] pYArr;


  prob1 () {
    nPosition = 0;
    pArr = null;
    pXArr = null;
    pYArr = null;
  }

  public void readData() { 
    nPosition = r.nextInt();
    pArr = new Position[nPosition];
    pXArr = new Position[nPosition];
    pYArr = new Position[nPosition];

    for (int i=0; i<nPosition; i++) {
      int x = r.nextInt();
      int y = r.nextInt();
      Position p = new Position (i, x, y);
      pArr[i] = p;
      pXArr[i] = p;
      pYArr[i] = p;
      
    }
    if (debug) printPositionArray("Original Array", pArr);
    Arrays.sort(pXArr);
    
    Arrays.sort(pYArr, new PositionCompareY());

    if (debug) {
      printPositionArray("Sorted X Array", pXArr);
      printPositionArray("Sorted Y Array", pYArr);
    }
  }

  // return -1 if Rect is not valid...>3 positions located outside.
  // Otherwise, return area.
  public int calRectArea (int xMin, int xMax, int yMin, int yMax) {
    int nPosOutside = 0;
    for (int i=0; i<nPosition && nPosOutside < 4; i++) {
      if (pArr[i].cX < xMin || pArr[i].cX > xMax || pArr[i].cY < yMin || pArr[i].cY > yMax)
        nPosOutside++;  
    }
    if (nPosOutside > 3)
      return -1;
    return (xMax-xMin)*(yMax-yMin);
  }

  public int solve () {
    readData();
    int minX= 0;
    int minY= 0;
    int maxX= Integer.MAX_VALUE;
    int maxY= Integer.MAX_VALUE;
    int minArea = Integer.MAX_VALUE;

    for (int xMin=0; xMin<4; xMin++) {
      for (int xMax=0; xMax<4; xMax++) {
        for (int yMin=0; yMin<4; yMin++) {
          for (int yMax=0; yMax<4; yMax++) {
            minX = pXArr[xMin].cX;
            maxX = pXArr[nPosition - xMax - 1].cX;
            minY = pYArr[yMin].cY;
            maxY = pYArr[nPosition - yMax - 1].cY;
            int area = calRectArea (minX, maxX, minY, maxY);
            if (area > 0)
              minArea = Math.min (minArea, area);
          }
        }
      }
    }

    return minArea;
  }

  public static void main(String[] args){

    Field field = new Field ();
    int area = field.solve();
    pw.println (area);
    pw.close();
  }

}