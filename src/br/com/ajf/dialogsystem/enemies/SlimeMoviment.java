package br.com.ajf.dialogsystem.enemies;

import br.com.ajf.game.animation.IAnimationManager;
import br.com.ajf.game.character.AbstractCharacterMovement;
import br.com.ajf.game.moviment.FourDirections;

public class SlimeMoviment extends AbstractCharacterMovement
{
	public SlimeMoviment(Slime slime)
	{
		super(slime);
	}

	@Override
	protected void update(float delta, IAnimationManager animations)
	{
		if(character.moving && !character.collision)
		{
			switch(character.direction)
			{
				case FourDirections.UP:
					character.position.setY(Math.round(character.position.getY() - character.velocity.getY()*delta));
					animations.setAnimationByIndex(3);

					break;
				case FourDirections.DOWN:
					character.position.setY(Math.round(character.position.getY() + character.velocity.getY()*delta));
					animations.setAnimationByIndex(2);
					break;
				case FourDirections.LEFT:
					character.position.setX(Math.round(character.position.getX() - character.velocity.getX()*delta));
					animations.setAnimationByIndex(1);

					break;
				case FourDirections.RIGHT:
					character.position.setX(Math.round(character.position.getX() + character.velocity.getX()*delta));
					animations.setAnimationByIndex(0);

					break;
			}
		}
		else
		{
			switch(character.direction)
			{
				case FourDirections.UP:
					animations.setAnimationByIndex(3);
					break;
				case FourDirections.DOWN:
					animations.setAnimationByIndex(2);
					break;
				case FourDirections.LEFT:
					animations.setAnimationByIndex(1);
					break;
				case FourDirections.RIGHT:
					animations.setAnimationByIndex(0);
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
				character.position.setY(Math.round(character.position.getY() + character.velocity.getY()*delta));
				break;
			case FourDirections.DOWN:
				character.position.setY(Math.round(character.position.getY() - character.velocity.getY()*delta));
				break;
			case FourDirections.LEFT:
				character.position.setX(Math.round(character.position.getX() + character.velocity.getX()*delta));
				break;
			case FourDirections.RIGHT:
				character.position.setX(Math.round(character.position.getX() - character.velocity.getX()*delta));
				break;
		}
	}
}