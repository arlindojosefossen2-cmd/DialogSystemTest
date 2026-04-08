package br.com.ajf.dialogsystem.scenes;

import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.collision.Collider;
import br.com.ajf.game.player.Player;

import java.awt.*;

public final class DebugScene
{
	public static boolean debug;

	public static void draw(Graphics2D graphics2d, AbstractScene scene, AbstractCharacter abstractCharacter)
	{
		//draw debug colliders for the characters
		if(debug)
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

	public static void draw(Graphics2D graphics2d,AbstractScene scene)
	{
		//Debug Tiles Colliders
		if(debug)
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

			if(scene.player.attacking)
			{
				graphics2d.setColor(Color.RED);
				graphics2d.fillRect(scene.player.attackArea.getX(),
						scene.player.attackArea.getY(),
						scene.player.attackArea.getWidth(),
						scene.player.attackArea.getHeight());
			}

		}
	}
}