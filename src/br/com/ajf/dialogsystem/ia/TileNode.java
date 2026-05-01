package br.com.ajf.dialogsystem.ia;

public class TileNode
{
	public TileNode parent;

	public int row;
	public int column;

	public int gCost;
	public int hCost;
	public int fCost;

	public boolean solid;
	public boolean open;
	public boolean checked;

	public TileNode(int row, int column)
	{
		this.row = row;
		this.column = column;
	}
}