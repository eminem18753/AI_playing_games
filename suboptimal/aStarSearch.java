import java.util.Set;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class aStarSearch
{
	private int[][] array;
	private int rowNumber;
	private int colNumber;
	private point currentLocation;
	private point startLocation;
	private aStarNode goal;
	private Set<point> foodLocations;
	private point foodLocation;
	private Queue<aStarNode> traversal;
	private List<aStarNode> openList;
	private List<aStarNode> closeList;
	private Queue<aStarNode> path;
	private aStarNode root;
	//for debug
	private plotGraph plotMaze;
	private int countPlot;
	
	public aStarSearch(int[][] array,int rowNumber,int colNumber,point currentLocation,Set<point> foodLocations)
	{
		this.array=array;
		this.rowNumber=rowNumber;
		this.colNumber=colNumber;
		this.currentLocation=currentLocation;
		this.startLocation=currentLocation;
		this.foodLocations=foodLocations;
		this.foodLocation=new point(-1,-1);
		this.traversal=new LinkedList();
		this.root=new aStarNode();
		
		openList=new ArrayList<aStarNode>();
		closeList=new ArrayList<aStarNode>();
		path=new LinkedList();
	
		//for debug
		//plotMaze=new plotGraph(array,rowNumber,colNumber);
		countPlot=0;
		//for debug
		
		for(Iterator<point> it=foodLocations.iterator();it.hasNext();)
		{
			point foodTemp=it.next();
			foodLocation=foodTemp;
		}
	}
	
	public void aStarSearchAlgorithm()
	{
		root.setRow(currentLocation.getRow());
		root.setCol(currentLocation.getCol());
		root.setTravelDistance(0);
		root.setTargetDistance(0);
		root.setTotalDistance();
		root.setParent(null);
		
		openList.add(root);
		
		while(!openList.isEmpty())
		{
			int flag=0;
			int existUp=0;
			int existDown=0;
			int existLeft=0;
			int existRight=0;
			
			aStarNode nodeNow=new aStarNode();
			nodeNow.setTotalDistance(2147483600);
			
			countPlot++;
			
//			if(countPlot%500==0||((nodeNow.getRow()==foodLocation.getRow())&&(nodeNow.getCol()==foodLocation.getCol())))
//			{
//				/*
//				//wait 100ms
//				try
//				{
//					Thread.sleep(100);
//				}
//				catch(InterruptedException e)
//				{
//					e.printStackTrace();
//				}
//				//wait 100ms
//				plotMaze.deletePanel();
//				*/
//				plotMaze=new plotGraph(array,rowNumber,colNumber);
//			}
			
			for(Iterator<aStarNode> it=openList.iterator();it.hasNext();)
			{
				aStarNode nodeTemp=it.next();
				if(nodeTemp.getTotalDistance() < nodeNow.getTotalDistance())
				{
					nodeNow=nodeTemp;
				}
			}
			
			if((nodeNow.getRow()==foodLocation.getRow())&&(nodeNow.getCol()==foodLocation.getCol()))
			{
				goal=nodeNow;
				break;
			}
			
//			System.out.print("Row:" + Integer.toString(nodeNow.getRow()) + " Col:" + Integer.toString(nodeNow.getCol()) + "\n");
//			System.out.println(nodeNow.getTravelDistance());
			openList.remove(nodeNow);
			closeList.add(nodeNow);
			
			if((array[nodeNow.getRow()-1][nodeNow.getCol()]!=2)&&(array[nodeNow.getRow()-1][nodeNow.getCol()]!=99)&&(array[nodeNow.getRow()-1][nodeNow.getCol()]!=100))
			{
				aStarNode point=new aStarNode();
				array[nodeNow.getRow()][nodeNow.getCol()]=100;
				point.setRow(nodeNow.getRow()-1);
				point.setCol(nodeNow.getCol());
				point.setTravelDistance(nodeNow.getTravelDistance() + 1);
				point.setTargetDistance(getHeuristicH(point.getRow(),point.getCol()));
				point.setTotalDistance();
				
				aStarNode nodeTemp;
				aStarNode nodeFind=null;
				int nodeId=0;
				
				for(Iterator<aStarNode> it=openList.iterator();it.hasNext();)
				{
					nodeTemp=it.next();
					if((nodeTemp.getRow()==point.getRow())&&(nodeTemp.getCol()==point.getCol()))
					{
						nodeFind=nodeTemp;
						nodeId=openList.indexOf(nodeTemp);
						existUp=1;
					}
				}
				if(existUp==0)
				{
					point.setParent(nodeNow);
					openList.add(point);
				}
				else if(existUp==1)
				{
					if(point.getTotalDistance() < nodeFind.getTotalDistance())
					{
						openList.get(nodeId).setTravelDistance(nodeNow.getTravelDistance() + 1);
						openList.get(nodeId).setTargetDistance(getHeuristicH(point.getRow(),point.getCol()));
						openList.get(nodeId).setTotalDistance();
						openList.get(nodeId).setParent(nodeNow);
					}
				}
				flag=1;
			}
			if((array[nodeNow.getRow()+1][nodeNow.getCol()]!=2)&&(array[nodeNow.getRow()+1][nodeNow.getCol()]!=99)&&(array[nodeNow.getRow()+1][nodeNow.getCol()]!=100))
			{
				aStarNode point=new aStarNode();
				array[nodeNow.getRow()][nodeNow.getCol()]=100;
				point.setRow(nodeNow.getRow()+1);
				point.setCol(nodeNow.getCol());				
				point.setTravelDistance(nodeNow.getTravelDistance() + 1);
				point.setTargetDistance(getHeuristicH(point.getRow(),point.getCol()));
				point.setTotalDistance();
				
				aStarNode nodeTemp;
				aStarNode nodeFind=null;
				int nodeId=0;
				
				for(Iterator<aStarNode> it=openList.iterator();it.hasNext();)
				{
					nodeTemp=it.next();
					if((nodeTemp.getRow()==point.getRow())&&(nodeTemp.getCol()==point.getCol()))
					{
						nodeFind=nodeTemp;
						nodeId=openList.indexOf(nodeTemp);
						existDown=1;
					}
				}
				if(existDown==0)
				{
					point.setParent(nodeNow);
					openList.add(point);
				}
				else if(existDown==1)
				{
					if(point.getTotalDistance()<nodeFind.getTotalDistance())
					{
						openList.get(nodeId).setTravelDistance(nodeNow.getTravelDistance() + 1);
						openList.get(nodeId).setTargetDistance(getHeuristicH(point.getRow(),point.getCol()));
						openList.get(nodeId).setTotalDistance();
						openList.get(nodeId).setParent(nodeNow);
					}
				}

				flag=1;
			}
			if((array[nodeNow.getRow()][nodeNow.getCol()-1]!=2)&&(array[nodeNow.getRow()][nodeNow.getCol()-1]!=99)&&(array[nodeNow.getRow()][nodeNow.getCol()-1]!=100))
			{
				aStarNode point=new aStarNode();
				array[nodeNow.getRow()][nodeNow.getCol()]=100;
				point.setRow(nodeNow.getRow());
				point.setCol(nodeNow.getCol()-1);
				point.setTravelDistance(nodeNow.getTravelDistance() + 1);
				point.setTargetDistance(getHeuristicH(point.getRow(),point.getCol()));
				point.setTotalDistance();
				
				aStarNode nodeTemp;
				aStarNode nodeFind=null;
				int nodeId=0;
				
				for(Iterator<aStarNode> it=openList.iterator();it.hasNext();)
				{
					nodeTemp=it.next();
					if((nodeTemp.getRow()==point.getRow())&&(nodeTemp.getCol()==point.getCol()))
					{
						nodeFind=nodeTemp;
						nodeId=openList.indexOf(nodeTemp);
						existLeft=1;
					}
				}
				
				if(existLeft==0)
				{
					point.setParent(nodeNow);
					openList.add(point);
				}
				else if(existLeft==1)
				{
					if(point.getTotalDistance()<nodeFind.getTotalDistance())
					{
						openList.get(nodeId).setTravelDistance(nodeNow.getTravelDistance() + 1);
						openList.get(nodeId).setTargetDistance(getHeuristicH(point.getRow(),point.getCol()));
						openList.get(nodeId).setTotalDistance();
						openList.get(nodeId).setParent(nodeNow);
					}
				}
				flag=1;
			}
			if((array[nodeNow.getRow()][nodeNow.getCol()+1]!=2)&&(array[nodeNow.getRow()][nodeNow.getCol()+1]!=99)&&(array[nodeNow.getRow()][nodeNow.getCol()+1]!=100))
			{
				aStarNode point=new aStarNode();
				array[nodeNow.getRow()][nodeNow.getCol()]=100;
				point.setRow(nodeNow.getRow());
				point.setCol(nodeNow.getCol()+1);				
				point.setTravelDistance(nodeNow.getTravelDistance() + 1);
				point.setTargetDistance(getHeuristicH(point.getRow(),point.getCol()));
				point.setTotalDistance();
				
				aStarNode nodeTemp;
				aStarNode nodeFind=null;
				int nodeId=0;
				
				for(Iterator<aStarNode> it=openList.iterator();it.hasNext();)
				{
					nodeTemp=it.next();
					if((nodeTemp.getRow()==point.getRow())&&(nodeTemp.getCol()==point.getCol()))
					{
						nodeFind=nodeTemp;
						nodeId=openList.indexOf(nodeTemp);
						existRight=1;
					}
				}
				
				if(existRight==0)
				{
					point.setParent(nodeNow);
					openList.add(point);
				}
				else if(existRight==1)
				{
					if(point.getTotalDistance()<nodeFind.getTotalDistance())
					{
						openList.get(nodeId).setTravelDistance(nodeNow.getTravelDistance() + 1);
						openList.get(nodeId).setTargetDistance(getHeuristicH(point.getRow(),point.getCol()));
						openList.get(nodeId).setTotalDistance();
						openList.get(nodeId).setParent(nodeNow);
					}
				}
				flag=1;
			}
			if(flag==0)
			{
				array[nodeNow.getRow()][nodeNow.getCol()]=100;
			}
		}
		//System.out.println("Algorithm end");
		printPath();
	}
//	public int getHeuristicG(int row,int col)
//	{
//		return Math.abs(startLocation.getRow()-row)+Math.abs(startLocation.getCol()-col);
//	}
	public int getHeuristicH(int row,int col)
	{
		return Math.abs(foodLocation.getRow()-row)+Math.abs(foodLocation.getCol()-col);
	}
	public void printPath()
	{
		aStarNode point=new aStarNode();
		point=goal;
		while((point=point.getParent())!=null)
		{
//			System.out.print("Row:");
//			System.out.print(point.getRow());
//			System.out.print(" Col:");
//			System.out.println(point.getCol());
			array[point.getRow()][point.getCol()]=99;
			path.add(point);
		}
//		System.out.print("Total cost : " + Integer.toString(goal.getTotalDistance()) + "\n");
		
//		plotMaze=new plotGraph(array,rowNumber,colNumber);
	}
	public int[][] getArray()
	{
		return array;
	}
	public int getCost()
	{
		return goal.getTravelDistance();
	}
	public Queue<aStarNode> getPath()
	{
		return this.path;
	}
}