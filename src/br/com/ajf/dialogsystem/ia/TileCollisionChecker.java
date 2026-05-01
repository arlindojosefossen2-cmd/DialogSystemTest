package br.com.ajf.dialogsystem.ia;

import br.com.ajf.game.gameobject.GameObject;
import br.com.ajf.game.moviment.FourDirections;
import br.com.ajf.game.tile.TileManager;

public final class TileCollisionChecker
{
	public TileCollisionChecker()
	{
		super();
	}

	public void checkTile(final GameObject gameObject, final TileManager tManager,int tileSize)
	{
		int gameObjectLeftWorldX = gameObject.position.getX() + gameObject.collider.getX();
		int gameObjectRightWorldX = gameObject.position.getX() + gameObject.collider.getX()+ gameObject.collider.getWidth();
		
		int gameObjectTopWorldY = gameObject.position.getY() + gameObject.collider.getY();
		int gameObjectBottomWorldY = gameObject.position.getY() + gameObject.collider.getY() + gameObject.collider.getHeight();

		int gameObjectLeftColumn = gameObjectLeftWorldX / tileSize;
		int gameObjectRightColumn = gameObjectRightWorldX / tileSize;
		int gameObjectTopRow = gameObjectTopWorldY / tileSize;
		int gameObjectBottomRow = gameObjectBottomWorldY / tileSize;
		
		int tileId1 = 0 ;
		int tileId2 = 0 ;	
		
		for(int layerId = 0; layerId < tManager.getData().length;layerId++)
		{
			switch(gameObject.direction)
			{
				case FourDirections.UP:
					gameObjectTopRow = (gameObjectTopWorldY - gameObject.velocity.getY()) / tileSize;
					tileId1 = tManager.getData()[layerId][gameObjectTopRow][gameObjectLeftColumn]-1;
		            tileId2 = tManager.getData()[layerId][gameObjectTopRow][gameObjectRightColumn]-1;
    
					break;
				case FourDirections.DOWN:
					gameObjectBottomRow = (gameObjectBottomWorldY + gameObject.velocity.getY()) / tileSize;
					tileId1 = tManager.getData()[layerId][gameObjectBottomRow][gameObjectLeftColumn]-1;
		            tileId2 = tManager.getData()[layerId][gameObjectBottomRow][gameObjectRightColumn]-1;
		    		       
					break;
				case FourDirections.LEFT:
					gameObjectLeftColumn = (gameObjectLeftWorldX - gameObject.velocity.getX()) / tileSize;
					tileId1 = tManager.getData()[layerId][gameObjectTopRow][gameObjectLeftColumn]-1;
		            tileId2 = tManager.getData()[layerId][gameObjectBottomRow][gameObjectRightColumn]-1;
		
					break;
				case FourDirections.RIGHT:
					gameObjectRightColumn = (gameObjectRightWorldX + gameObject.velocity.getX()) / tileSize;
					tileId1 = tManager.getData()[layerId][gameObjectTopRow][gameObjectLeftColumn]-1;
		            tileId2 = tManager.getData()[layerId][gameObjectBottomRow][gameObjectRightColumn]-1;
				
					break;
			}
			
			if(tileId1 ==  -1)
			{
				if(tileId2 != -1)
					if(tManager.getTiles()[tileId2].solid())
				    {
						gameObject.collision = true;
				    } 
			}
			else if(tileId2 == -1)
			{
				if(tManager.getTiles()[tileId1].solid())
				{
					gameObject.collision = true;
				}
			}
			else if(tManager.getTiles()[tileId2].solid() || tManager.getTiles()[tileId1].solid())
			{
				gameObject.collision = true;
			} 
		}
	}
}