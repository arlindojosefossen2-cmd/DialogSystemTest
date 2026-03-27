package br.com.ajf.dialogsystem.scenes;

import java.awt.Graphics2D;

import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.framework.Game;
import br.com.ajf.game.player.Player;

/**
 * The Class DrawScene.
 */
public final class DrawScene
{
	/**
	 * Instantiates a new draw scene.
	 */
	private DrawScene()
	{
		
	}
	
	/**
	 * Draw.
	 *
	 * @param graphics2d the graphics 2 d
	 * @param game the game
	 * @param scene the scene
	 */
	public static void draw(Graphics2D graphics2d,Game game,AbstractScene scene)
	{
		//draw by layers first tile Manager second characters
		for (int layer : scene.layers)
		{
			//draw tiles maps by layers
			 scene.tileManager.draw(
					graphics2d,game,  
					scene.player.position.getX(),
					scene.player.position.getY(), 
					Player.SCREEN_X,
					Player.SCREEN_Y,layer);
			 
			//draw characters by layers
			for (AbstractCharacter abstractCharacter : scene.characters)
			{
				if(abstractCharacter != null && abstractCharacter.collider.getLayer() == layer)
				{
					abstractCharacter.draw(
							graphics2d,
							- scene.player.position.getX()+Player.SCREEN_X,
							- scene.player.position.getY()+Player.SCREEN_Y);
				}
				//draw debug scene for characters
				DebugScene.draw(graphics2d,scene,abstractCharacter);
			}
		}
		//draw debug scene for colliders dialog and player position
		DebugScene.draw(graphics2d,scene);
		
		//draw player UI
		scene.player.drawUI(graphics2d);
		
		//draw dialogManager
		scene.dialogManager.draw(graphics2d,scene.charactersArray);

		//draw transition
		if(scene.transition != null)
		{
			scene.transition.draw(graphics2d);
		}
	}
}