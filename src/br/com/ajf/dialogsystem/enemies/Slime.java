package br.com.ajf.dialogsystem.enemies;

import br.com.ajf.dialogsystem.main.GameLauncher;
import br.com.ajf.dialogsystem.scenes.AbstractScene;
import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.character.CharacterLoader;
import br.com.ajf.game.collision.Collider;

import java.awt.*;

public class Slime extends AbstractCharacter
{
	private final AbstractScene scene;

	public Slime(AbstractScene scene)
	{
		this.scene = scene;
	}

	@Override
	public void start()
	{
		name ="Blue-Slime";


		addCharacterInput(new SlimeInput(this));
		addCharacterMovement(new SlimeMoviment(this,scene));

		solidAreaX = 16;
		solidAreaY = 16;

		collider = new Collider(0,0, 16+16,16+16, 32, 32, "Enemy");

		animations = new CharacterLoader().loadAnimationFromXMLFile("/enemies/slime-blue.xml", this);
	}

	@Override
	public void draw(Graphics2D graphics2d, int playerXOffset, int playerYOffset)
	{
		super.draw(graphics2d, playerXOffset, playerYOffset);

		graphics2d.setColor(Color.DARK_GRAY);
		graphics2d.fillRoundRect(position.getX()+playerXOffset+getWidth()/2-GameLauncher.TILE_SIZE/2,
				position.getY()+playerYOffset-32,
				GameLauncher.TILE_SIZE,
				16,
				5,
				5);

		graphics2d.setColor(Color.RED);
		graphics2d.fillRoundRect(position.getX()+playerXOffset+getWidth()/2-GameLauncher.TILE_SIZE/2,
				position.getY()+playerYOffset-32,
				( GameLauncher.TILE_SIZE * health.getLife() /health.getMaxLife()),
				16,
				5,
				5);

		graphics2d.setColor(Color.BLACK);
		graphics2d.drawRoundRect(position.getX()+playerXOffset+getWidth()/2-GameLauncher.TILE_SIZE/2,
				position.getY()+playerYOffset-32,
				GameLauncher.TILE_SIZE,
				16,
				5
				,5);
	}
}
