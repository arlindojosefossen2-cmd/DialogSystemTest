package br.com.ajf.dialogsystem.entity;

import br.com.ajf.game.animation.IAnimationManager;
import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.character.AbstractCharacterMoviment;
import br.com.ajf.game.moviment.FourDirections;

public final class EntityMoviment extends AbstractCharacterMoviment
{

	public EntityMoviment(AbstractCharacter character)
	{
		super(character);
		
	}

	@Override
	protected void update(float delta, IAnimationManager animations)
	{	
		if(character.moving && !character.collision)
		{
			switch(character.direction)
			{
				case FourDirections.UP:
					character.position.setY((character.position.getY() - character.velocity.getY()));
					animations.setAnimationByIndex(1);
		
					break;
				case FourDirections.DOWN:
					character.position.setY((character.position.getY() + character.velocity.getY()));
					animations.setAnimationByIndex(5);
					break;
				case FourDirections.LEFT:
					character.position.setX((character.position.getX() - character.velocity.getX()));
					animations.setAnimationByIndex(3);
		
					break;
				case FourDirections.RIGHT:
					character.position.setX((character.position.getX() + character.velocity.getX()));
					animations.setAnimationByIndex(7);
					
					break;
			}
		}
		else 
		{
			switch(character.direction)
			{
				case FourDirections.UP:
					animations.setAnimationByIndex(0);
					break;
				case FourDirections.DOWN:
					animations.setAnimationByIndex(4);
					break;
				case FourDirections.LEFT:
					animations.setAnimationByIndex(2);
					break;
				case FourDirections.RIGHT:
					animations.setAnimationByIndex(6);
					break;
			}
		}
		
		animations.update(delta);
	}

	@Override
	protected void prevent(float delta)
	{
		switch(character.direction)
		{
			case FourDirections.UP:
				character.position.setY((character.position.getY() + character.velocity.getY()));
				break;
			case FourDirections.DOWN:
				character.position.setY((character.position.getY() - character.velocity.getY()));
				break;
			case FourDirections.LEFT:
				character.position.setX((character.position.getX() + character.velocity.getX()));
				break;
			case FourDirections.RIGHT:
				character.position.setX(character.position.getX() - (character.velocity.getX()));
				break;
		}
	}
}