package br.com.ajf.dialogsystem.player;

import br.com.ajf.game.animation.IAnimationManager;
import br.com.ajf.game.character.AbstractCharacter;

import br.com.ajf.game.character.AbstractCharacterMovement;
import br.com.ajf.game.moviment.FourDirections;
import br.com.ajf.game.player.Player;

/**
 * The Class CharacterMovement.
 */
public final class DartPlayerMoviment extends AbstractCharacterMovement
{
	public DartPlayerMoviment(AbstractCharacter character)
	{
		super(character);
	}

	/**
	 * Standing.
	 */
	private void standing(IAnimationManager animations)
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

	/**
	 * Moving.
	 *
	 */
	private void moving(IAnimationManager animations,float delta)
	{
		((Player)character).dialogArea.setX(character.position.getX());
		((Player)character).dialogArea.setY(character.position.getY());
		
		switch(character.direction)
		{
			case FourDirections.UP:
				character.position.setY(Math.round(character.position.getY() - character.velocity.getY()*delta));
				animations.setAnimationByIndex(1);
				((Player)character).dialogArea.setY(character.position.getY()-character.getHeight()/2);
			
				break;
			case FourDirections.DOWN:
				character.position.setY(Math.round(character.position.getY() + character.velocity.getY()*delta));
				animations.setAnimationByIndex(5);
				((Player)character).dialogArea.setY(character.position.getY()+character.getHeight()/2);
				
				break;
			case FourDirections.LEFT:
				character.position.setX(Math.round(character.position.getX() - character.velocity.getX()*delta));
				animations.setAnimationByIndex(3);
				((Player)character).dialogArea.setX(character.position.getX()-character.getWidth()/2);
				
				break;
			case FourDirections.RIGHT:
				character.position.setX(Math.round(character.position.getX() + character.velocity.getX()*delta));
				animations.setAnimationByIndex(7);
				((Player)character).dialogArea.setX(character.position.getX()+character.getWidth()/2);
				
				break;
		}
	}
	
	/**
	 * Prevent.
	 *
	 * @param delta the delta
	 */
	public void prevent(float delta)
	{
		switch(character.direction)
		{
			case FourDirections.UP:
				character.position.setY(Math.round(character.position.getY() + character.velocity.getY()*delta));
				break;
			case FourDirections.DOWN:
				character.position.setY(Math.round(character.position.getY() - character.velocity.getY()*delta));
				break;
			case FourDirections.LEFT:
				character.position.setX(Math.round(character.position.getX() + character.velocity.getX()*delta));
				break;
			case FourDirections.RIGHT:
				character.position.setX(Math.round(character.position.getX() - (character.velocity.getX()*delta)));
				break;
		}
	}
	
	/**
	 * Update.
	 *
	 * @param delta the delta
	 */
	public void update(float delta,IAnimationManager animations)
	{	
		if(character.moving && !character.collision)
		{
			moving(animations,delta);
		}
		else
		{
			standing(animations);
		}
		
		animations.update(delta);
	}
}