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

		solidAreaX = 22;
		solidAreaY = 32;

		collider = new Collider(0,0, solidAreaX,solidAreaY, 20, 20, "Enemy");

		animations = new CharacterLoader().loadAnimationFromXMLFile("/enemies/slime-blue.xml", this);
	}
}
