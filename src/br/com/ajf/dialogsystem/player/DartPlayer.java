package br.com.ajf.dialogsystem.player;

import java.awt.Graphics2D;

import br.com.ajf.dialogsystem.main.GameLauncher;
import br.com.ajf.game.character.CharacterLoader;
import br.com.ajf.game.collision.Collider;
import br.com.ajf.game.player.Player;


public final class DartPlayer extends Player
{
	@Override
	public void start()
	{
		name = "Dart";
	
		drawUI = new DrawPlayerUI();
		
		addCharacterInput(new DartPlayerInputs(this));
		addCharacterMovement(new DartPlayerMoviment(this));
		
		SCREEN_X = GameLauncher.WIDTH/2-64/2;
		SCREEN_Y = GameLauncher.HEIGHT/2-64/2;
	
		solidAreaX = 22;
		solidAreaY = 32;
		
		collider = new Collider(1,0, solidAreaX,solidAreaY, 20, 20, "Player");
		
		animations = new CharacterLoader().loadAnimationFromXMLFile("/player/player.xml", this);
	}

	public void drawUI(Graphics2D graphics2d)
	{	
		if(drawUI != null)
		{
			drawUI.drawUI(graphics2d, this);
		}
	}
}