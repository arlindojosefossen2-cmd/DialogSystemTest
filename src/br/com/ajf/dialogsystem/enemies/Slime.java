package br.com.ajf.dialogsystem.enemies;

import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.character.CharacterLoader;
import br.com.ajf.game.collision.Collider;

public class Slime extends AbstractCharacter
{
	@Override
	public void start()
	{
		name ="Blue-Slime";

		addCharacterInput(new SlimeInput(this));
		addCharacterMovement(new SlimeMoviment(this));

		solidAreaX = 16;
		solidAreaY = 16;

		collider = new Collider(0,0, 16+16,16+16, 32, 32, "Enemy");

		animations = new CharacterLoader().loadAnimationFromXMLFile("/enemies/slime-blue.xml", this);
	}
}
