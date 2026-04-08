package br.com.ajf.dialogsystem.player;

import java.awt.event.KeyEvent;

import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.character.AbstractCharacterInput;
import br.com.ajf.game.input.GameInput;
import br.com.ajf.game.moviment.FourDirections;
import br.com.ajf.game.player.Player;

/**
 * The Class CharacterInputs.
 */
public final class DartPlayerInputs extends AbstractCharacterInput
{
	/**
	 * Instantiates a new character inputs.
	 *
	 * @param character the character
	 */
	public DartPlayerInputs(AbstractCharacter character)
	{
		super(character);
	}
	
	/**
	 * Update inputs.
	 */
	public void updateInputs()
	{
		if(isAttacking())
		{
			character.moving = false;
			((Player)character).attacking = true;
		}

		if(isUp())
		{
			character.direction = FourDirections.UP;
			character.moving = true;
			((Player)character).attacking = false;
		}
		else if(isDown())
		{
			character.direction = FourDirections.DOWN;
			character.moving = true;
			((Player)character).attacking = false;
		}
		else if(isLeft())
		{
			character.direction = FourDirections.LEFT;
			character.moving = true;
			((Player)character).attacking = false;
		}
		else if(isRight())
		{
			character.direction = FourDirections.RIGHT;
			character.moving = true;
			((Player)character).attacking = false;
		}
		else
		{
			character.moving = false;
		}

	}

	private boolean isAttacking()
	{
		return GameInput.keyDownOnce(KeyEvent.VK_X);
	}

	/**
	 * Checks if is right.
	 *
	 * @return true, if is right
	 */
	private boolean isRight()
	{
		return GameInput.keyDown(KeyEvent.VK_D) || GameInput.keyDown(KeyEvent.VK_RIGHT);
	}

	/**
	 * Checks if is left.
	 *
	 * @return true, if is left
	 */
	private boolean isLeft()
	{
		return GameInput.keyDown(KeyEvent.VK_A) || GameInput.keyDown(KeyEvent.VK_LEFT);
	}

	/**
	 * Checks if is down.
	 *
	 * @return true, if is down
	 */
	private boolean isDown()
	{
		return GameInput.keyDown(KeyEvent.VK_S) || GameInput.keyDown(KeyEvent.VK_DOWN);
	}

	/**
	 * Checks if is up.
	 *
	 * @return true, if is up
	 */
	private boolean isUp()
	{
		return GameInput.keyDown(KeyEvent.VK_W) || GameInput.keyDown(KeyEvent.VK_UP);
	}
}