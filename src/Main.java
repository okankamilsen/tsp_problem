import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;

public class Main {
	static int[] node;
	static int[] nodeX;
	static int[] nodeY;
	static int[][] distances;
	static int numberOfNodes;
	static int[] route;
	static int totalDistance=0;
	static int[] firstLine;
	static int[] lastLine;
	static int distLastFirst;

	public static void main(String[] args) throws IOException {
	  
	  FileReader file=new FileReader("/Users/okankamilsen/Desktop/algorithm proje/tsp-problem/src/test5.txt");
	  //implements distance array
	  distances=distanceArray(file);	
	  //find the best route
	  route = tsp(distances);
	  //to printout as text file
      PrintStream out = new PrintStream(new FileOutputStream("output5.txt"));
	  System.setOut(out);
	  int i=0;
	  //to come back first position added first position to last position
	  double dist = findDistance(nodeX[0],nodeY[0],nodeX[nodeX.length-1],nodeY[nodeY.length-1]);
	  String a = new DecimalFormat("##.##").format(dist);
	  distLastFirst = Integer.parseInt(a);
	  System.out.println(totalDistance+distLastFirst);
	  for(i=0;i<route.length;i++){
		  System.out.println(route[i]);
	  }
	  System.out.println(route[0]);
	  System.exit(0);
  }
	//to seperate each lines to 3 pieces as int
	public static int[] seperateNumbers(String s){
	  int result[] = new int[3];
	  char[] charArray = s.toCharArray();
	  char[][] a=new char[3][9];
	  int i;
	  int j = 0;
	  int k =0;
	  for(i=0;i<charArray.length;i++){
	  if(charArray[i] != ' '){
		  a[k][j]=charArray[i];
	  		j++;
	  }else if(j>0){
		  k++;
		  j=0;
	  }
	  
	  }
	  String first = new String(a[0]);
	  String second = new String(a[1]);
	  String third = new String(a[2]);
	  //System.out.println("1: |"+first+"| 2: |"+second+"| 3: |"+third+"|");
		  result[0]=Integer.parseInt(first.trim());
		  result[1]=Integer.parseInt(second.trim());
		  result[2]=Integer.parseInt(third.trim());
	  return result;
  }
    //to find all distances between nodes and return is matrix of distances
	public static int[][] distanceArray(FileReader file) throws IOException{
	  int[][] distancesArray;
	  BufferedReader br = new BufferedReader(file);
	  try {
	      StringBuilder sb = new StringBuilder();
	      String line = br.readLine();

	      while (line != null) {
	          sb.append(line);
	          sb.append(System.lineSeparator());
	          line = br.readLine();
	      }
	      String everything = sb.toString();
	      int count=countLines(everything);
	      node = new int[count];
	      nodeX = new int[count];
	      nodeY = new int[count];
	      distancesArray = new int[count][count];
	      int i=0;
	      String[] edge = everything.split("\n");
	      for(i=0 ; i<count ; i++){
	    	  int k[]=seperateNumbers(edge[i]);
	    	  node[i]=k[0];
	    	  nodeX[i]=k[1];
	    	  nodeY[i]=k[2];
	      }
	      //System.out.println("distances btw all nodes.");
	      //System.out.print("    |     ");
	      for(i=0;i<=count;i++){
	    	  int j = 0;
	    	  for(j=0;j<count;j++){
	    		  if(i==0){
	    			//  System.out.print("   |   "+j);
	    		  }
	    		  else if(j<count && i-1<count){
	    			  double distance = findDistance(nodeX[i-1],nodeY[i-1],nodeX[j],nodeY[j]);
	    			  String a = new DecimalFormat("##.##").format(distance);
	    			  distancesArray[i-1][j]=Integer.parseInt(a);
	    		//	  System.out.print(" | "+distancesArray[i-1][j]);
	    			 // System.out.println("çalışıyor");
	    		  }
	    	  }
	    	//  System.out.println("\n ");
	      }
	      
	      

	      
	  } finally {
	      br.close();
	  }
	  return distancesArray;
  }
	//calculate distance btw 2 node
	public static double findDistance(int x1,int y1,int x2,int y2){
	  return Math.round(Math.sqrt(Math.pow((x1-x2),2)+Math.pow((y1-y2),2)));
	}
	//calculate # of node
	private static int countLines(String str){
	   String[] lines = str.split("\r\n|\r|\n");
	   return  lines.length;
		}

	//tsp algorithm 
  	public static int[] tsp(int distanceMatrix[][])
  	{
      numberOfNodes = distanceMatrix[1].length ;//- 1;
      int[] isVisited = new int[numberOfNodes + 1];
      int element, destination = 0, i;
      isVisited[destination] = 1;
      int minValue = Integer.MAX_VALUE;
      int minControl = 0;
      int countTotal=1;
      int[] routeOfSalesman=new int[numberOfNodes];
      routeOfSalesman[0]=destination;
      while (routeOfSalesman[numberOfNodes-1] == 0)
      {
    	  element= routeOfSalesman[countTotal-1];
          i = 0;
          minValue = Integer.MAX_VALUE;
          while (i < numberOfNodes)
          {
              if (distanceMatrix[element][i] > 1 && isVisited[i] == 0)
              {
                  if (minValue > distanceMatrix[element][i])
                  {
                	  minValue = distanceMatrix[element][i];
                      destination = i;
                      minControl = 1;
                  }
              }
              i++;
          }
          //find the Closest one
          if (minControl == 1)
          {
        	  totalDistance+=minValue;
        	  countTotal++;
        	  isVisited[destination] = 1;
              routeOfSalesman[countTotal-1]=destination;
              minControl = 0;
              continue;
          }
      }
      return routeOfSalesman;
  }
}