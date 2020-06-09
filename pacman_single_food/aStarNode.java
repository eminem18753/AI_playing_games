public class aStarNode
{
	private aStarNode parent;
	private int row;
	private int col;
	private int travelDistance;
	private int targetDistance;
	private int totalDistance;
	
	public aStarNode()
	{
		totalDistance=0;
	}
	public void setParent(aStarNode parent)
	{
		this.parent=parent;
	}
	public aStarNode getParent()
	{
		return this.parent;
	}
	public void setRow(int row)
	{
		this.row=row;
	}
	public void setCol(int col)
	{
		this.col=col;
	}
	public int getRow()
	{
		return this.row;
	}
	public int getCol()
	{
		return this.col;
	}
	public void setTravelDistance(int travelDistance)
	{
		this.travelDistance=travelDistance;
	}
	public int getTravelDistance()
	{
		return this.travelDistance;
	}
	public void setTargetDistance(int targetDistance)
	{
		this.targetDistance=targetDistance;
	}
	public int getTargetDistance()
	{
		return this.targetDistance;
	}
	public void setTotalDistance()
	{
		this.totalDistance=this.travelDistance+this.targetDistance;
	}
	public void setTotalDistance(int totalDistance)
	{
		this.totalDistance=totalDistance;
	}
	public int getTotalDistance()
	{
		return this.totalDistance;
	}
}