import java.util.Set;
import java.util.Stack;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public class aStarSearchM
{
	private int[][] array;
	private int rowNumber;
	private int colNumber;
	private point startLocation;
	private point currentLocation;
//	private List<point> foodLocations;
	private Set<point> foodLocations;
	private point[] foodLocationsArray;
	private Queue<aStarNode> path;
	private aStarNode root;
	//for debug
	private plotGraph plotMaze;
	private int countPlot;
	
	//for multiple points
	private int[][] costMatrix;
	private ArrayList<ArrayList<Queue<aStarNode>>> pathMatrix;
	private List<aStarNode> oldFood;
	private List<aStarNode> newFood;
	
	public aStarSearchM(int[][] array,int rowNumber,int colNumber,point startLocation,Set<point> foodLocations)
	{
		this.array=array;
		this.rowNumber=rowNumber;
		this.colNumber=colNumber;
		this.startLocation=startLocation;
		this.currentLocation = startLocation;
		this.foodLocations = foodLocations;
		this.foodLocationsArray = new point[foodLocations.size()];
		this.foodLocationsArray = foodLocations.toArray(this.foodLocationsArray);
		this.root=new aStarNode();
		this.costMatrix = new int[foodLocations.size() + 1][foodLocations.size()];	//one more startLocation of src
		this.pathMatrix = new ArrayList();
		this.oldFood = new ArrayList();
		this.newFood = new ArrayList();
		path=new LinkedList();
	
		//for debug
		//plotMaze=new plotGraph(array,rowNumber,colNumber);
		countPlot=0;
		//for debug
		
		for(Iterator<point> it = foodLocations.iterator();it.hasNext();)
		{
			point foodTemp = it.next();
			this.foodLocations.add(foodTemp);
		}
		
//		System.out.println("Before sorting : ");
//		for(Iterator<point> it = this.foodLocations.iterator();it.hasNext();)
//		{
//			point foodTemp = it.next();
//			System.out.println(getManDistance(foodTemp,currentLocation));
//		}
		
//		sortList();
//		foodLocation = this.foodLocations.remove(0);
		
//		System.out.println("After sorting : ");
//		for(Iterator<point> it = this.foodLocations.iterator();it.hasNext();)
//		{
//			point foodTemp = it.next();
//			System.out.println(getManDistance(foodTemp,currentLocation));
//		}
//		
//		System.out.println("interrupt");
	}
	
	public void aStarSearchMAlgorithm()
	{
		
		//build the costMatrix and pathMatrix
		for(int i = 0; i < this.foodLocations.size() + 1; i++) {
			ArrayList<Queue<aStarNode>> path1DArray = new ArrayList(); 
					
			for(int j = 0; j < this.foodLocations.size(); j++) {
				int tempArray[][] = new int [rowNumber][colNumber];
				for(int k = 0; k < rowNumber; k++)
					for(int m = 0; m < colNumber ; m++) {
						if( (k!=foodLocationsArray[j].getRow() || m!=foodLocationsArray[j].getCol()) && this.array[k][m] == 4 ) 
							array[k][m] = 1;
						else
							tempArray[k][m] = array[k][m];
					}
				Set<point> tempSet = new HashSet<point>();
				tempSet.add(foodLocationsArray[j]);
				if(i == this.foodLocations.size()) {
					aStarSearch aStarTest = new aStarSearch(tempArray, this.rowNumber, this.colNumber, this.startLocation, tempSet);
					aStarTest.aStarSearchAlgorithm();
					costMatrix[i][j] = aStarTest.getCost();
					path1DArray.add(aStarTest.getPath());
				}
				else {
					aStarSearch aStarTest = new aStarSearch(tempArray, this.rowNumber, this.colNumber, foodLocationsArray[i], tempSet);
					aStarTest.aStarSearchAlgorithm();
					costMatrix[i][j] = aStarTest.getCost();
					path1DArray.add(aStarTest.getPath());
				}
			}
			pathMatrix.add(path1DArray);
		}
		
//		for(int i = 0; i < this.foodLocations.size() + 1; i++) {
//			for(int j = 0; j < this.foodLocations.size(); j++)
//				System.out.print(Integer.toString(costMatrix[i][j]) + " ");
//			System.out.println();
//		}
		
		//start searching
		int row, col;
		
		root.setRow(this.startLocation.getRow());
		root.setCol(this.startLocation.getCol());
		root.setTravelDistance(0);
		root.setTargetDistance(0);
		root.setTotalDistance();
		root.setParent(null);
		newFood.add(root);
		
		while(!newFood.isEmpty()) {
			countPlot++;
			aStarNode nodeNow = new aStarNode();
			Queue<aStarNode> pathTemp = new LinkedList();
			Stack<aStarNode> nodeStack = new Stack();
			nodeNow = newFood.get(0);
			
//			if(countPlot == 14)
//				System.out.println("countplot : 14");
			
			//check termination
			if(isEnd()) {
//				System.out.println("countplot :" + Integer.toString(countPlot));
//				System.out.println(oldFood.get(oldFood.size()-1).getTotalDistance());
				break;
			}
				
			newFood.remove(nodeNow);
			oldFood.add(nodeNow);
			
			//store the path
			if(nodeNow.getRow() == root.getRow() && nodeNow.getCol() == root.getCol())
				path.add(root);
			else {
				col = indexInArray(nodeNow);							//dest (nodeNow)
				row = indexInArray(oldFood.get(oldFood.size() - 2));	//src of nodeNow
//				System.out.print("Row :" + Integer.toString(row) + " Col :" + Integer.toString(col) + "\n");
				if(oldFood.get(oldFood.size() - 2).getRow() == startLocation.getRow() && oldFood.get(oldFood.size() - 2).getCol() == startLocation.getCol())
					pathTemp = this.pathMatrix.get(this.foodLocations.size()).get(col);
				else
					pathTemp = this.pathMatrix.get(row).get(col);
				
//				System.out.print("countPlot : " + Integer.toString(countPlot) + "\n");
//				System.out.print("Food Row : " + Integer.toString(nodeNow.getRow()) + " | Food Col:" + Integer.toString(nodeNow.getCol()) + "\n");
//				for(Iterator<aStarNode> it = pathTemp.iterator(); it.hasNext();) {
//					aStarNode pathNode = it.next();
//					System.out.print("Row:"+Integer.toString(pathNode.getRow())+" Col:"+Integer.toString(pathNode.getCol()) + " | ");
//				}
//				System.out.println();
				
				nodeStack.push(nodeNow);
				while(!pathTemp.isEmpty()) 
					nodeStack.push(pathTemp.remove());
//				for(Iterator<aStarNode> it = nodeStack.iterator(); it.hasNext();) {
//					aStarNode stackNode = it.next();
//					System.out.print("Row:"+Integer.toString(stackNode.getRow())+" Col:"+Integer.toString(stackNode.getCol()) + " | ");
//				}
//				System.out.println();
				nodeStack.pop();		//do not push src to path
				while(!nodeStack.isEmpty())
					path.add(nodeStack.pop());
			}
			
			//expanding, dest (nodeTemp), src (nodeNow)
			for(int i = 0; i < this.foodLocations.size(); i++) {
				aStarNode nodeTemp = new aStarNode();
				nodeTemp.setRow(foodLocationsArray[i].getRow());
				nodeTemp.setCol(foodLocationsArray[i].getCol());
				if(nodeNow == root) {
					int cost = costMatrix[this.foodLocations.size()][indexInArray(nodeTemp)];
					nodeTemp.setTravelDistance(cost + nodeNow.getTravelDistance());
				}
				else {
					int cost = costMatrix[indexInArray(nodeNow)][indexInArray(nodeTemp)];
					nodeTemp.setTravelDistance(cost + nodeNow.getTravelDistance());
				}
				//System.out.print("Row :" + Integer.toString(nodeTemp.getRow()) + " Col :" + Integer.toString(nodeTemp.getCol()) + "\n");
				nodeTemp.setTargetDistance(getHeuristic(nodeTemp));
				nodeTemp.setTotalDistance();
				
				//there may be problems 1.heuristic of other road of existing food is smaller
				if(isInOldFood(nodeTemp))
					continue;
				else {
					//System.out.print("TotalDistance :" + Integer.toString(nodeTemp.getTotalDistance()) + "\n");
					newFood.add(nodeTemp);
				}
			}
			sortNewFood();
		}
		
	}
	
	public boolean isInOldFood(aStarNode nodeTemp)
	{
		boolean existed = false;
		for(Iterator<aStarNode> it = oldFood.iterator();it.hasNext();)
		{
			aStarNode nodeInOld = it.next();
			if(nodeTemp.getRow() == nodeInOld.getRow() && nodeTemp.getCol() == nodeInOld.getCol())
				existed = true;
		}
		return existed;
	}
	public boolean isInOldFood(point foodTemp)
	{
		boolean existed = false;
		for(Iterator<aStarNode> it = oldFood.iterator();it.hasNext();)
		{
			aStarNode nodeInOld = it.next();
			if(foodTemp.getRow() == nodeInOld.getRow() && foodTemp.getCol() == nodeInOld.getCol())
				existed = true;
		}
		return existed;
	}

	public boolean isEnd()
	{
		boolean end = true;
		boolean inPath;
		for(Iterator<point> foodIt = this.foodLocations.iterator();foodIt.hasNext();)
		{
			point foodTemp = foodIt.next();
			inPath = false;
			for(Iterator<aStarNode> nodeIt = path.iterator();nodeIt.hasNext();) {
				aStarNode nodeTemp = nodeIt.next();
				inPath = inPath || ( (nodeTemp.getRow() == foodTemp.getRow()) && (nodeTemp.getCol() == foodTemp.getCol()) );
			}
			end = end && inPath;
//			if(!end)
//				 System.out.println("End is false");
		}
		
		return end;
	}
	
	public int getHeuristic(aStarNode newNode)
	{
		int sum = 0;
		int colIndex;
		int rowIndex;
		
		//add all
		for (int i = 0; i < this.foodLocations.size(); i++)
			for(int j = 0; j < this.foodLocations.size(); j++)
				sum += costMatrix[i][j];
		
		//minus old and now
		for(Iterator<aStarNode> it = oldFood.iterator(); it.hasNext();) {
			aStarNode oldNowNode = it.next();
			if(oldNowNode.getRow() == this.startLocation.getRow() && oldNowNode.getCol() == this.startLocation.getCol())
				continue;
			colIndex = indexInArray(oldNowNode);
			rowIndex = colIndex;
			//System.out.print("Row :" + Integer.toString(oldNowNode.getRow()) + " Col :" + Integer.toString(oldNowNode.getCol()) + "\n");
			for(int i = 0; i < this.foodLocations.size(); i++) {
				sum = sum - costMatrix[i][colIndex];
				if(!isInOldFood(foodLocationsArray[i]))
					sum = sum - costMatrix[rowIndex][i];
			}
		}
		
		//minus new
		colIndex = indexInArray(newNode);
		for(int i = 0; i < this.foodLocations.size(); i++) 
			if(!isInOldFood(foodLocationsArray[i]))
				sum = sum - costMatrix[i][colIndex];
		
		return sum;
	}
//	public void printPath()
//	{
//		aStarNode point=new aStarNode();
//		point=goal;
//		while((point=point.getParent())!=null)
//		{
//			System.out.print("------------" + Integer.toString(countPlot) + "-------------\n");
//			System.out.print("Row:");
//			System.out.print(point.getRow());
//			System.out.print(" Col:");
//			System.out.println(point.getCol());
//			array[point.getRow()][point.getCol()]=99;
//			path.add(point);
//		}
//		countPlot++;
//		plotMaze=new plotGraph(array,rowNumber,colNumber);
//	}
	public int[][] getArray()
	{
		return array;
	}
	
	public int indexInArray(aStarNode p)
	{
		for(int i = 0; i < this.foodLocations.size(); i++) {
			if(foodLocationsArray[i].getRow() == p.getRow() && foodLocationsArray[i].getCol() == p.getCol())
				return i;
		}
		return -1;
	}
	
	public void sortNewFood()
	{
		newFood.sort(new Comparator<aStarNode>() {
			@Override
			public int compare(aStarNode n1, aStarNode n2) {
				int d1 = n1.getTotalDistance();
				int d2 = n2.getTotalDistance();
				if(d1 == d2)
					return 0;
				return (d1 > d2) ? 1:-1;
			}
		});
	}
	
	public void printPath()
	{
		for(Iterator<point> foodIt = foodLocations.iterator(); foodIt.hasNext();) {
			point foodTemp = foodIt.next();
			array[foodTemp.getRow()][foodTemp.getCol()] = 4;
		}
		for(Iterator<aStarNode> pathIt = path.iterator(); pathIt.hasNext();) {
			aStarNode pathNode = pathIt.next();
			System.out.print("Row:" + Integer.toString(pathNode.getRow()) + " Col:" + Integer.toString(pathNode.getCol()) + "\n");
			if(!isInOldFood(pathNode) || ((pathNode.getRow() == startLocation.getRow()) && (pathNode.getCol() == startLocation.getCol())) )
				array[pathNode.getRow()][pathNode.getCol()] = 99;
			if(isInOldFood(pathNode) && (array[pathNode.getRow()][pathNode.getCol()] != 5)) 
			{
				System.out.print("Food -> Row:" + Integer.toString(pathNode.getRow()) + " Col:" + Integer.toString(pathNode.getCol()) + "\n");
				array[pathNode.getRow()][pathNode.getCol()] = 5;
				plotMaze = new plotGraph(array,rowNumber,colNumber);
			}
		}
//		System.out.print("Total cost : " + Integer.toString(goal.getTotalDistance()) + "\n");
		
//		plotMaze=new plotGraph(array,rowNumber,colNumber);
	}
}