package br.com.ajf.dialogsystem.ia;

import br.com.ajf.game.tile.ITileManager;

import java.util.ArrayList;
import java.util.List;

public class PathFinder
{
	final ITileManager tileManager;

	private TileNode[][] tileNodes;

	private final List<TileNode> openList = new ArrayList<>();
	public final List<TileNode> pathList = new ArrayList<>();

	TileNode startNode;
	TileNode currentNode;
	TileNode goalNode;

	private boolean goalReached;

	private int step;

	public PathFinder(ITileManager tileManager)
	{
		this.tileManager = tileManager;
		instantiatedTileNodes();
	}

	private void instantiatedTileNodes()
	{
		int rows = this.tileManager.getData()[0].length;
		int columns = this.tileManager.getData()[0][0].length;

		this.tileNodes = new TileNode[rows][columns];

		for (int r = 0; r < rows; r++)
		{
			for (int c = 0; c < columns; c++)
			{
				this.tileNodes[r][c] = new TileNode(r,c);
			}
		}
	}

	private void resetTileNodes()
	{
		int rows = this.tileManager.getData()[0].length;
		int columns = this.tileManager.getData()[0][0].length;

		for (int r = 0; r < rows; r++)
		{
			for (int c = 0; c < columns; c++)
			{
				this.tileNodes[r][c].open = false;
				this.tileNodes[r][c].solid = false;
				this.tileNodes[r][c].checked = false;
			}
		}

		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
	}

	public void setTileNodes(int startRow,int startColumn,int goalRow,int goalColumn)
	{
		resetTileNodes();

		startNode = this.tileNodes[startRow][startColumn];
		currentNode = startNode;
		goalNode = this.tileNodes[goalRow][goalColumn];
		openList.add(currentNode);

		for (int l = 0; l < this.tileManager.getData().length; l++)
		{
			for (int r = 0; r < this.tileManager.getData()[l].length; r++)
			{
				for (int c = 0; c < this.tileManager.getData()[l][r].length; c++)
				{
					int id = this.tileManager.getData()[l][r][c]-1;

					if(id == -1)
					{
						continue;
					}

					if(this.tileManager.getTiles()[id].solid())
					{
						this.tileNodes[r][c].solid = true;
					}

					getCost(this.tileNodes[r][c]);
				}
			}
		}
	}

	public boolean search()
	{
		while (!goalReached && step < 500)
		{
			if(currentNode == null)
				continue;

			int column = currentNode.column;
			int row = currentNode.row;

			currentNode.checked = true;
			openList.remove(currentNode);

			if(row-1 >= 0)
			{
				openTileNode(this.tileNodes[row-1][column]);
			}
			if(column-1 >= 0)
			{
				openTileNode(this.tileNodes[row][column-1]);
			}
			if(row+1 <= this.tileManager.getData()[0].length-1)
			{
				openTileNode(this.tileNodes[row+1][column]);
			}
			if(column+1 <= this.tileManager.getData()[0][0].length-1)
			{
				openTileNode(this.tileNodes[row][column+1]);
			}

			int bestTileNodeIndex = 0;
			int bestTileNodeCost = 99999;

			for (int i = 0; i < openList.size(); i++)
			{
				if(openList.get(i).fCost < bestTileNodeCost)
				{
					bestTileNodeIndex = i;
					bestTileNodeCost = openList.get(i).fCost;
				}
				else if (openList.get(i).fCost == bestTileNodeCost)
				{
					if(openList.get(i).gCost < openList.get(bestTileNodeIndex).gCost)
					{
						bestTileNodeIndex = i;
					}
				}
			}

			if(openList.isEmpty())
			{
				break;
			}

			currentNode = openList.get(bestTileNodeIndex);

			if (currentNode == goalNode)
			{
				goalReached = true;
				trackThePath();
			}

			step++;
		}

		return goalReached;
	}

	private void trackThePath()
	{
		while(goalNode != startNode)
		{
			pathList.addFirst(goalNode);
			goalNode = goalNode.parent;
		}
	}

	private void openTileNode(TileNode tileNode)
	{
		if(!tileNode.open && !tileNode.checked && !tileNode.solid)
		{
			tileNode.open = true;
			tileNode.parent = currentNode;
			openList.add(tileNode);
		}
	}

	private void getCost(TileNode tileNode)
	{
		int xDistance = Math.abs(tileNode.column - startNode.column);
		int yDistance = Math.abs(tileNode.row - startNode.row);

		tileNode.gCost = xDistance + yDistance;

		xDistance = Math.abs(tileNode.column - goalNode.column);
		yDistance = Math.abs(tileNode.row - goalNode.row);

		tileNode.hCost = xDistance + yDistance;

		tileNode.fCost = tileNode.gCost + tileNode.hCost;
	}
}