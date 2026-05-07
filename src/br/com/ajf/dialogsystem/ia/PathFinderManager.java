package br.com.ajf.dialogsystem.ia;

import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.moviment.FourDirections;
import br.com.ajf.game.tile.ITileManager;
import br.com.ajf.game.tile.TileManager;

public class PathFinderManager
{
	private final PathFinder finder;
	private boolean onPath;
	private final TileCollisionChecker collisionChecker = new TileCollisionChecker();

	public PathFinderManager(ITileManager tileManager,boolean onPath)
	{
		this.finder = new PathFinder(tileManager);
		this.onPath = onPath;
	}

	public void searchPath(AbstractCharacter start,AbstractCharacter goal,boolean reachDestination,
						   int tileSize,float delta)
	{
		int startColumn = (start.position.getX() + start.collider.getX())/tileSize;
		int startRow = (start.position.getY() + start.collider.getY())/tileSize;

		int goalColumn = (goal.position.getX() + goal.collider.getX())/tileSize;
		int goalRow = (goal.position.getY() + goal.collider.getY())/tileSize;

		this.finder.setTileNodes(startRow,startColumn,goalRow,goalColumn);

		if(this.finder.search())
		{
			int nextX = this.finder.pathList.getFirst().column * tileSize;
			int nextY = this.finder.pathList.getFirst().row * tileSize;

			int enLeftX = start.position.getX() + start.collider.getX();
			int enRightX =  start.position.getX() + start.collider.getX() + start.collider.getWidth();

			int enTopY = start.position.getY() + start.collider.getY();
			int enBottomY = start.position.getY() + start.collider.getY() + start.collider.getHeight();

			if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + tileSize)
			{
				goal.direction = FourDirections.DOWN;
				goal.animations.setAnimationByIndex(2);
				goal.position.setY(Math.round(goal.position.getY() + goal.velocity.getY()*delta));
			}
			else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + tileSize)
			{
				goal.direction = FourDirections.UP;
				goal.animations.setAnimationByIndex(3);
				goal.position.setY(Math.round(goal.position.getY() - goal.velocity.getY()*delta));
			}
			else if (enTopY >= nextY && enBottomY < nextY + tileSize)
			{
				if (enLeftX > nextX)
				{
					goal.direction = FourDirections.LEFT;
					goal.animations.setAnimationByIndex(0);
					goal.position.setX(Math.round(goal.position.getX() + goal.velocity.getX()*delta));
				}

				if (enLeftX < nextX)
				{
					goal.direction = FourDirections.RIGHT;
					goal.animations.setAnimationByIndex(1);
					goal.position.setX(Math.round(goal.position.getX() - goal.velocity.getX()*delta));
				}
			}
			else if (enTopY > nextY && enLeftX > nextX)
			{
				goal.direction = FourDirections.UP;
				goal.animations.setAnimationByIndex(3);
				goal.position.setY(Math.round(goal.position.getY() + goal.velocity.getY()*delta));

				if(checkCollision(goal,finder.tileManager,tileSize))
				{
					goal.direction = FourDirections.LEFT;
					goal.animations.setAnimationByIndex(0);
					goal.position.setX(Math.round(goal.position.getX() + goal.velocity.getX()*delta));
				}
			}
			else if (enTopY > nextY && enLeftX < nextX)
			{
				goal.direction = FourDirections.UP;
				goal.animations.setAnimationByIndex(3);
				goal.position.setY(Math.round(goal.position.getY() + goal.velocity.getY()*delta));

				if(checkCollision(goal,finder.tileManager,tileSize))
				{
					goal.direction = FourDirections.RIGHT;
					goal.animations.setAnimationByIndex(1);
					goal.position.setX(Math.round(goal.position.getX() - goal.velocity.getX()*delta));
				}
			}
			else if (enTopY < nextY && enLeftX > nextX)
			{
				goal.direction = FourDirections.DOWN;
				goal.animations.setAnimationByIndex(2);
				goal.position.setY(Math.round(goal.position.getY() - goal.velocity.getY()*delta));

				if(checkCollision(goal,finder.tileManager,tileSize))
				{
					goal.direction = FourDirections.LEFT;
					goal.animations.setAnimationByIndex(0);
					goal.position.setX(Math.round(goal.position.getX() + goal.velocity.getX()*delta));
				}
			}
			else if (enTopY < nextY && enLeftX < nextX)
			{
				goal.direction = FourDirections.DOWN;
				goal.animations.setAnimationByIndex(2);
				goal.position.setY(Math.round(goal.position.getY() - goal.velocity.getY()*delta));

				if(checkCollision(goal,finder.tileManager,tileSize))
				{
					goal.direction = FourDirections.RIGHT;
					goal.animations.setAnimationByIndex(1);
					goal.position.setX(Math.round(goal.position.getX() - goal.velocity.getX()*delta));
				}
			}
		}

		if (reachDestination)
		{
			if(finder.pathList.isEmpty())
				return;

			int nextCol = finder.pathList.getFirst().column;
			int nextRow = finder.pathList.getFirst().row;

			if (nextCol == goalColumn && nextRow == goalRow)
			{
				onPath = false;
			}
		}
	}

	private boolean checkCollision(AbstractCharacter goal, ITileManager tileManager,int tileSize)
	{
		collisionChecker.checkTile(goal, (TileManager) tileManager,tileSize);
		return goal.collision;
	}
}