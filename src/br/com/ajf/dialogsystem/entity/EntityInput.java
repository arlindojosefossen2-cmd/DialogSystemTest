package br.com.ajf.dialogsystem.entity;

import java.util.Random;

import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.character.AbstractCharacterInput;
import br.com.ajf.game.moviment.FourDirections;

public final class EntityInput extends AbstractCharacterInput
{
	private final Random rand = new Random();
	private int direction;
	private int counter;
	
	public EntityInput(AbstractCharacter character)
	{
		super(character);
	}

	@Override
	protected void updateInputs()
	{
			counter++;
		
			if(counter >= 120)
			{
				direction = rand.nextInt(0,6);
				
				if(direction == 0)
				{
					character.direction = FourDirections.UP;
					character.moving = true;
				}
				else if(direction == 1)
				{
					character.direction = FourDirections.DOWN;
					character.moving = true;
				}
				else if(direction == 2)
				{
					character.direction = FourDirections.LEFT;
					character.moving = true;
				}
				else if(direction == 3)
				{
					character.direction = FourDirections.RIGHT;
					character.moving = true;
				}
				else
				{
					character.moving = false;
				}
				
				counter = 0;
			}
	}
}