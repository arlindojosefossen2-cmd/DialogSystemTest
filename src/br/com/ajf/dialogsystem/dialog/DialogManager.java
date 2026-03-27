package br.com.ajf.dialogsystem.dialog;

import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.input.GameInput;
import br.com.ajf.game.moviment.FourDirections;
import br.com.ajf.game.player.Player;

import java.awt.*;
import java.awt.event.KeyEvent;

public final class DialogManager
{
	public int index;
	public boolean dialog;
	public final CharacterDialog dialogs = new CharacterDialog();

	public DialogManager()
	{
		super();
	}

	public void updateDialog(AbstractCharacter[] charactersArray,Player player)
	{
		for (int i = 0; i < charactersArray.length; i++)
		{
			if(validateCharacterDialogCheck(i, charactersArray,player))
			{
				updateDialog(i,player,charactersArray);
			}
		}
	}

	private void updateDialog(int i,Player player,AbstractCharacter[] charactersArray)
	{
		dialog = true;
		index = i;
		charactersArray[i].moving = false;

		if(GameInput.keyDownOnce(KeyEvent.VK_M))
		{
			dialogCheck(i,player, charactersArray);
		}
	}

	private void dialogCheck(int i,Player player ,AbstractCharacter[] charactersArray)
	{
		dialogs.setDialog(true);
		charactersArray[i].dialog = true;

		if(charactersArray[i].dialog)
		{
			changeCharacterDirection(i,player,charactersArray);
		}
		else if(!dialogs.isDialog())
		{
			charactersArray[i].dialog = false;
		}
	}

	private void changeCharacterDirection(int i,Player player,AbstractCharacter[] charactersArray)
	{
		switch (player.direction)
		{
			case FourDirections.UP:
				charactersArray[i].direction = FourDirections.DOWN;
				charactersArray[i].animations.setAnimationByIndex(4);
				break;
			case FourDirections.DOWN:
				charactersArray[i].direction = FourDirections.UP;
				charactersArray[i].animations.setAnimationByIndex(0);
				break;
			case FourDirections.LEFT:
				charactersArray[i].direction = FourDirections.RIGHT;
				charactersArray[i].animations.setAnimationByIndex(6);
				break;
			case FourDirections.RIGHT:
				charactersArray[i].direction = FourDirections.LEFT;
				charactersArray[i].animations.setAnimationByIndex(2);
				break;
		}
	}

	private boolean validateCharacterDialogCheck(int i, AbstractCharacter[] charactersArray,Player player)
	{
		return charactersArray[i] != null && !(charactersArray[i] instanceof Player)
				&& player.dialogArea.intersects(charactersArray[i].collider);
	}

	public void draw(Graphics2D graphics2d,AbstractCharacter[] charactersArray)
	{
		//draw Dialog Text bottom info
		if(dialog)
		{
			graphics2d.setColor(Color.white);
			graphics2d.setFont(graphics2d.getFont().deriveFont(32F));
			graphics2d.drawString("Press M",Player.SCREEN_X-48, Player.SCREEN_Y-48);
			dialog = false;
		}
		//draw dialogs by character name
		dialogs.draw(graphics2d,charactersArray[index].name);
	}

	public void addDialog(String characterName, String text)
	{
		this.dialogs.add(characterName,text);
	}
}