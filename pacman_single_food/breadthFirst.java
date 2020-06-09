import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class breadthFirst
{
	private int[][] array;
	private int rowNumber;
	private int colNumber;
	private int nodeExpanded;
	private breadthNode root;
	private Queue<breadthNode> traversal;
	private point currentLocation;
	private Set<point> foodLocations;
	private breadthNode goal;
	private Queue<breadthNode> path;
	//for debug
	private plotGraph plotMaze;
	private int countPlot;
	//for debug
	
	public breadthFirst(int[][] array,int rowNumber,int colNumber,point currentLocation,Set<point> foodLocations)
	{
		this.array=array;
		this.rowNumber=rowNumber;
		this.colNumber=colNumber;
		this.currentLocation=currentLocation;
		this.foodLocations=foodLocations;
		this.nodeExpanded=0;
		
		root=new breadthNode();
		traversal=new LinkedList();
		path=new LinkedList();
		
		//for debug
		plotMaze=new plotGraph(array,rowNumber,colNumber);
		countPlot=0;
		//for debug
	}
	public void breadthFirstAlgorithm()
	{
		root.setRow(currentLocation.getRow());
		root.setCol(currentLocation.getCol());
		root.setParent(null);
		traversal.add(root);
		
		point foodLocation=new point(-1,-1);
		for(Iterator<point> it=foodLocations.iterator();it.hasNext();)
		{
			point foodTemp=it.next();
			foodLocation=foodTemp;
		}
		
		while(!traversal.isEmpty())
		{
			breadthNode nodeNow=traversal.remove();
			
			countPlot++;
			int flag=0;
			int existUp=0;
			int existDown=0;
			int existLeft=0;
			int existRight=0;
			
			if(countPlot%500==0||((nodeNow.getRow()==foodLocation.getRow())&&(nodeNow.getCol()==foodLocation.getCol())))
			{
				/*
				//wait 100ms
				try
				{
					Thread.sleep(100);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				//wait 100ms
				plotMaze.deletePanel();
				*/
				plotMaze=new plotGraph(array,rowNumber,colNumber);
			}			
			
			if((nodeNow.getRow()==foodLocation.getRow())&&(nodeNow.getCol()==foodLocation.getCol()))
			{
				goal=nodeNow;
				break;
			}
			
			if((array[nodeNow.getRow()-1][nodeNow.getCol()]!=2)&&(array[nodeNow.getRow()-1][nodeNow.getCol()]!=99)&&(array[nodeNow.getRow()-1][nodeNow.getCol()]!=100))
			{
				breadthNode point=new breadthNode();
				array[nodeNow.getRow()][nodeNow.getCol()]=100;
				point.setRow(nodeNow.getRow()-1);
				point.setCol(nodeNow.getCol());
				
				breadthNode nodeTemp;
				breadthNode nodeFind=null;
				int nodeId=0;
				
				for(Iterator<breadthNode> it=traversal.iterator();it.hasNext();)
				{
					nodeTemp=it.next();
					if((nodeTemp.getRow()==point.getRow())&&(nodeTemp.getCol()==point.getCol()))
					{
						nodeFind=nodeTemp;
						existUp=1;
					}
				}
				if(existUp==0)
				{
					point.setParent(nodeNow);
					traversal.add(point);
				}
				
				flag=1;
			}
			if((array[nodeNow.getRow()+1][nodeNow.getCol()]!=2)&&(array[nodeNow.getRow()+1][nodeNow.getCol()]!=99)&&(array[nodeNow.getRow()+1][nodeNow.getCol()]!=100))
			{
				breadthNode point=new breadthNode();
				array[nodeNow.getRow()][nodeNow.getCol()]=100;
				point.setRow(nodeNow.getRow()+1);
				point.setCol(nodeNow.getCol());

				breadthNode nodeTemp;
				breadthNode nodeFind=null;
				int nodeId=0;
				
				for(Iterator<breadthNode> it=traversal.iterator();it.hasNext();)
				{
					nodeTemp=it.next();
					if((nodeTemp.getRow()==point.getRow())&&(nodeTemp.getCol()==point.getCol()))
					{
						nodeFind=nodeTemp;
						existDown=1;
					}
				}
				if(existDown==0)
				{
					point.setParent(nodeNow);
					traversal.add(point);
				}
				
				flag=1;
			}
			if((array[nodeNow.getRow()][nodeNow.getCol()-1]!=2)&&(array[nodeNow.getRow()][nodeNow.getCol()-1]!=99)&&(array[nodeNow.getRow()][nodeNow.getCol()-1]!=100))
			{
				breadthNode point=new breadthNode();
				array[nodeNow.getRow()][nodeNow.getCol()]=100;
				point.setRow(nodeNow.getRow());
				point.setCol(nodeNow.getCol()-1);
				
				breadthNode nodeTemp;
				breadthNode nodeFind=null;
				int nodeId=0;
				
				for(Iterator<breadthNode> it=traversal.iterator();it.hasNext();)
				{
					nodeTemp=it.next();
					if((nodeTemp.getRow()==point.getRow())&&(nodeTemp.getCol()==point.getCol()))
					{
						nodeFind=nodeTemp;
						existLeft=1;
					}
				}
				if(existLeft==0)
				{
					point.setParent(nodeNow);
					traversal.add(point);
				}				
				flag=1;
			}
			if((array[nodeNow.getRow()][nodeNow.getCol()+1]!=2)&&(array[nodeNow.getRow()][nodeNow.getCol()+1]!=99)&&(array[nodeNow.getRow()][nodeNow.getCol()+1]!=100))
			{
				breadthNode point=new breadthNode();
				array[nodeNow.getRow()][nodeNow.getCol()]=100;
				point.setRow(nodeNow.getRow());
				point.setCol(nodeNow.getCol()+1);

				breadthNode nodeTemp;
				breadthNode nodeFind=null;
				int nodeId=0;
				
				for(Iterator<breadthNode> it=traversal.iterator();it.hasNext();)
				{
					nodeTemp=it.next();
					if((nodeTemp.getRow()==point.getRow())&&(nodeTemp.getCol()==point.getCol()))
					{
						nodeFind=nodeTemp;
						existRight=1;
					}
				}
				if(existRight==0)
				{
					point.setParent(nodeNow);
					traversal.add(point);
				}
				flag=1;
			}
			if(flag==0)
			{
				array[nodeNow.getRow()][nodeNow.getCol()]=100;
			}
		}
	}
	public void printPath()
	{
		breadthNode point=new breadthNode();
		point=goal;
		while((point=point.getParent())!=null)
		{
			System.out.print("Row:");
			System.out.print(point.getRow());
			System.out.print(" Col:");
			System.out.println(point.getCol());
			array[point.getRow()][point.getCol()]=99;
			path.add(point);
		}
		plotMaze=new plotGraph(array,rowNumber,colNumber);
	}
	public int[][] getArray()
	{
		return array;
	}
}