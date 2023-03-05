// USACO 2016 Open Contest, Gold
// Problem 1. Splitting the Field

import java.util.*;
import java.io.*;

public class Split {

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
      if (cX == p.cX) {
        return Integer.compare(cIndex, p.cIndex);
      } else {
        return Integer.compare(cX, p.cX); 
      }
    }
  }

  class PositionCompareY implements Comparator<Position>{
    public int compare(Position a, Position b){
      if (a.cY == b.cY)
        return Integer.compare (a.cIndex, b.cIndex);
      else 
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
  public Position[] pArr;
  public Position[] pXArr;
  public Position[] pYArr;
  public TreeSet<Position> xLeft = new TreeSet<Position>();
  public TreeSet<Position> xRight = new TreeSet<Position>(); 
  public TreeSet<Position> yLower = new TreeSet<Position>(new PositionCompareY());
  public TreeSet<Position> yUpper = new TreeSet<Position>(new PositionCompareY());

  Split () {
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
    if (debug && false) printPositionArray("Original Array", pArr);
    Arrays.sort(pXArr);
    
    Arrays.sort(pYArr, new PositionCompareY());

    if (debug && false) {
      printPositionArray("Sorted X Array", pXArr);
      printPositionArray("Sorted Y Array", pYArr);
    }
  }
  public long calculateAreas (TreeSet<Position> fromX, TreeSet<Position> fromY, TreeSet<Position> toX, TreeSet<Position> toY, Position p) {
    
    if (p!=null) {
      fromX.remove(p);
      fromY.remove(p);
      toX.add(p);
      toY.add(p);
    }
    long fromArea = 0;
    long toArea = 0;
    if (!fromX.isEmpty() && !fromY.isEmpty())
      fromArea = (long) (fromX.last().cX-fromX.first().cX)* (long) (fromY.last().cY - fromY.first().cY);
    if (!toX.isEmpty() && !toY.isEmpty())
      toArea = (long) (toX.last().cX-toX.first().cX)* (long) (toY.last().cY - toY.first().cY);

    long totalArea = fromArea + toArea;
    if (debug && false) {
        pw.println ("Moving Position: " + p);
        pw.println ("X From: " + fromX + ", X To: " + toX);
        pw.println ("Y From: " + fromY + ", Y To: " + toY);
    }
    if (debug && false) pw.println ("From Area: " + fromArea + ", To Area: " + toArea);
    return totalArea;
  }

  public long solve () {
    readData();

    long minArea = Long.MAX_VALUE;

    for (int i=0; i<nPosition; i++) {
      Position p = pXArr[i];
      xRight.add(p);
      yUpper.add(p);
    }
    long initialArea = calculateAreas(xRight, yUpper, xLeft, yLower, null);

    long xMinArea = Long.MAX_VALUE;
    for (int i=0; i<nPosition; i++) {
      Position p = pXArr[i];
      long a = calculateAreas(xRight, yUpper, xLeft, yLower, p);
      xMinArea = Math.min (xMinArea, a);
    }

    long yMinArea = Long.MAX_VALUE;
    for (int i=0; i<nPosition; i++) {
      Position p = pYArr[nPosition-i-1];
      long a = calculateAreas(xLeft, yLower, xRight, yUpper, p);
      yMinArea = Math.min (yMinArea, a);
    }
    minArea = Long.min(xMinArea, yMinArea);
    if (debug) pw.println ("Min Area = " + minArea + ", X Min Area = " + xMinArea + ", Y Min Area = " + yMinArea);
    return (initialArea - minArea);


  }

  public static void main(String[] args){

    long startTime = System.currentTimeMillis();
    Split split = new Split();
   
    long area = split.solve();
    pw.println (area);

    long endTime = System.currentTimeMillis();
    if (split.debug) pw.println ("Total Time: " + (endTime - startTime));

    pw.close();
  }

}