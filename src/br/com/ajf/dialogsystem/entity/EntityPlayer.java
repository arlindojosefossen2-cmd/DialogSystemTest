package br.com.ajf.dialogsystem.entity;

import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.character.CharacterLoader;
import br.com.ajf.game.collision.Collider;

public final class EntityPlayer extends AbstractCharacter
{	
	@Override
	public void start()
	{	
		name = "Dumpy";
		addCharacterInput(new EntityInput(this));
		addCharacterMovement(new EntityMoviment(this));

		solidAreaX = 22;
		solidAreaY = 32;
		
		collider = new Collider(1,0, solidAreaX,solidAreaY, 20, 20, "Entity");
		
		animations = new CharacterLoader().loadAnimationFromXMLFile("/entity/entity.xml", this);
	}
}