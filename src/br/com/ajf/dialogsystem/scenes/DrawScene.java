package br.com.ajf.dialogsystem.scenes;

import java.awt.Color;
import java.awt.Graphics2D;

import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.collision.Collider;
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
		//draw by layers first tilemenager second characters
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
				
				//draw debug colliders for the characters
				if(scene.debug)
				{
					graphics2d.setColor(Color.yellow);
					assert abstractCharacter != null;
					graphics2d.drawRect(
							Player.SCREEN_X - scene.player.position.getX() + 
							abstractCharacter.position.getX() + abstractCharacter.solidAreaX, 
							Player.SCREEN_Y - scene.player.position.getY() + 
							abstractCharacter.position.getY() + abstractCharacter.solidAreaY,
							abstractCharacter.collider.getWidth(),
							abstractCharacter.collider.getHeight());
				}
			}
		}
		
		//Debug Tiles Colliders
		if(scene.debug)
		{
			//print player position on console for debug 
			System.out.println(scene.player.position);
			
			for (Collider rect : scene.colliders)
			{
				if(rect.getLayer() ==  scene.player.collider.getLayer())
				{
					graphics2d.setColor(Color.RED);
					graphics2d.drawRect(
							rect.getX() - scene.player.position.getX() + Player.SCREEN_X,
							rect.getY() - scene.player.position.getY() + Player.SCREEN_Y,
							rect.getWidth(),
							rect.getHeight());
				}
			}
			
			//draw dialog area for debug
			graphics2d.setColor(Color.GREEN);
			graphics2d.drawRect(scene.player.dialogArea.getX()+Player.SCREEN_X-scene.player.position.getX(),
					scene.player.dialogArea.getY()-scene.player.position.getY()+Player.SCREEN_Y,
					scene.player.dialogArea.getWidth(), scene.player.dialogArea.getHeight());
		}
		
		//draw player UI
		scene.player.drawUI(graphics2d);
		
		//draw Dialog Text buttom info
		if(scene.dialog)
		{
			graphics2d.setColor(Color.white);
			graphics2d.setFont(graphics2d.getFont().deriveFont(32F));
			graphics2d.drawString("Press M",Player.SCREEN_X-48, Player.SCREEN_Y-48);
			scene.dialog = false;
		}
		
		//draw dialogs by character name
		scene.dialogs.draw(graphics2d,scene.charactersArray[scene.index].name);
		
		//draw transition
		if(scene.transition != null)
		{
			scene.transition.draw(graphics2d);
		}
	}
}