import java.util.Set;
import java.util.HashSet;
import java.lang.InterruptedException;

public class runMaze
{
	public static void main(String[] args)
	{
		mazeReader maze=new mazeReader("mediumMaze.txt");
		maze.readMaze();
		int[][] ourMazeArray=maze.getMazeArray();
		int rowNumber=maze.getRowNumber();
		int colNumber=maze.getColNumber();
		int[][] arrayToPrint=new int[rowNumber][colNumber];
		int algorithmChoice=4;//1 for depthFirst, 2 for breadthFirst, 3 for bestFirst, 4 for aStar
		
		point currentLocation=maze.getPosition();
		Set<point> foodPoints=maze.getFoodPoints();
		
		plotGraph plotMaze=new plotGraph(ourMazeArray,rowNumber,colNumber);//plot graph
		try
		{
			Thread.sleep(100);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		if(algorithmChoice==1)
		{
			depthFirst depthFirstSearch=new depthFirst(ourMazeArray,rowNumber,colNumber,currentLocation,foodPoints);
			depthFirstSearch.depthFirstAlgorithm();
		}
		else if(algorithmChoice==2)
		{
			breadthFirst breadthFirstSearch=new breadthFirst(ourMazeArray,rowNumber,colNumber,currentLocation,foodPoints);
			breadthFirstSearch.breadthFirstAlgorithm();
			breadthFirstSearch.printPath();
		}
		else if(algorithmChoice==3)
		{
			bestFirst bestFirstSearch=new bestFirst(ourMazeArray,rowNumber,colNumber,currentLocation,foodPoints);
			bestFirstSearch.bestFirstAlgorithm();
		}
		else if(algorithmChoice==4)
		{
			aStarSearch aStarSearchTest=new aStarSearch(ourMazeArray,rowNumber,colNumber,currentLocation,foodPoints);
			aStarSearchTest.aStarSearchAlgorithm();
			aStarSearchTest.printPath();
			arrayToPrint=aStarSearchTest.getArray();
		}
		maze.printResult(arrayToPrint,rowNumber,colNumber);
	}
	
}