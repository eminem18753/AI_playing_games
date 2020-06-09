import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.Writer;
import java.io.OutputStreamWriter;

public class mazeReader
{
		private String filePath;
		private int colNumber;
		private int rowNumber;
		private int nodeNumber;
		private int[][] mazeArray;
		
		private point currentPosition;
		private Set<point> foodPoints;
		public mazeReader(String filePath)
		{
			this.filePath=filePath;
			nodeNumber=0;
			colNumber=0;
			rowNumber=0;
			currentPosition=new point(-1,-1);
			foodPoints=new HashSet<point>();
		}
		public void readMaze()//read first and then initialize
		{
			try
			{
				BufferedReader mazesGetSize=new BufferedReader(new FileReader(filePath));
				String line;
				int count=0;
				while((line=mazesGetSize.readLine())!=null)
				{
					rowNumber++;
					colNumber=line.length();
					count++;
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			//for test
			//System.out.println(rowNumber);
			//System.out.println(colNumber);
			//
			mazeArray=new int[rowNumber][colNumber];
			try
			{
				BufferedReader mazes=new BufferedReader(new FileReader(filePath));
				String line;
				int colCount=0;
				int rowCount=0;
				int i=0;
				while((line=mazes.readLine())!=null)
				{
					colCount=0;
					for(i=0;i<colNumber;i++)
					{
						if(line.charAt(i)==' ')
						{
							mazeArray[rowCount][colCount]=1;
						}
						else if(line.charAt(i)=='%')
						{
							mazeArray[rowCount][colCount]=2;
						}
						else if(line.charAt(i)=='P')
						{
							mazeArray[rowCount][colCount]=3;
							currentPosition.setPoint(rowCount,colCount);
						}
						else if(line.charAt(i)=='.')
						{
							mazeArray[rowCount][colCount]=4;
							point foodTemp=new point(rowCount,colCount);
							foodPoints.add(foodTemp);
						}
						
						colCount++;
						//print maze
						//System.out.print(line.charAt(i));//clean it
						//
					}
					rowCount++;
					//print maze
					//System.out.println();//clean it
					//
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			//printMaze test
			//printMaze();//clean it
			//
			//for test
			//System.out.println(currentPosition.getRow());//clean it
			//System.out.println(currentPosition.getCol());//clean it
			//for test

			//printPosition();//clean it
			//printFoodPositions();//clean it
		}
		public int getRowNumber()
		{
			return this.rowNumber;
		}
		public int getColNumber()
		{
			return this.colNumber;
		}
		public void printMaze()
		{
			int col=0;
			int row=0;
			for(row=0;row<rowNumber;row++)
			{
					for(col=0;col<colNumber;col++)
					{
						System.out.print(mazeArray[row][col]);
					}
					System.out.println();
			}
		}
		public point getPosition()
		{
			return currentPosition;
		}
		public void printPosition()
		{
			System.out.print("Row:");
			System.out.print(currentPosition.getRow());
			System.out.print(" Col:");
			System.out.println(currentPosition.getCol());
		}
		public Set<point> getFoodPoints()
		{
			return foodPoints;
		}
		public void printFoodPositions()
		{
			int count=1;
			for(Iterator<point> it=foodPoints.iterator();it.hasNext();)
			{
				point foodTemp=it.next();
				System.out.print("Food point:");
				System.out.print(count++);
				System.out.print(" Row:");
				System.out.print(foodTemp.getRow());
				System.out.print(" Col:");
				System.out.println(foodTemp.getCol());
			}
		}
		public int[][] getMazeArray()
		{
			return mazeArray;
		}
		public void printResult(int[][] array,int rowNumber,int colNumber)
		{
			int count=0;
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream("depthFirst.txt"), "utf-8"))) 
			  {
				for(int i=0;i<rowNumber;i++)
				{
					for(int j=0;j<colNumber;j++)
					{
						if(array[i][j]==1||array[i][j]==100)
						{
							writer.write(" ");
						}
						else if(array[i][j]==2)
						{
							writer.write("@");
						}
						else if(array[i][j]==3||array[i][j]==4||array[i][j]==99)
						{
							writer.write(".");
							count++;
						}
						if(array[i][j]==99||array[i][j]==100)
						{
							nodeNumber++;
						}
					}
					writer.write("\n");
				}
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}			
			System.out.println(count);
			System.out.println(nodeNumber);
		}	  
}	