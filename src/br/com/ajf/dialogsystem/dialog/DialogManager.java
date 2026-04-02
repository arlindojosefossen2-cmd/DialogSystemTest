package br.com.ajf.dialogsystem.dialog;

import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.input.GameInput;
import br.com.ajf.game.moviment.FourDirections;
import br.com.ajf.game.player.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public final class DialogManager
{
	public int index;
	public boolean dialog;
	public final CharacterDialog dialogs = new CharacterDialog();

	public DialogManager()
	{
		super();
	}

	public void updateDialog(List<AbstractCharacter> charactersArray, Player player)
	{
		for (int i = 0; i < charactersArray.size(); i++)
		{
			if(validateCharacterDialogCheck(i, charactersArray,player))
			{
				updateDialog(i,player,charactersArray);
			}
		}
	}

	private void updateDialog(int i,Player player,List<AbstractCharacter> charactersArray)
	{
		dialog = true;
		index = i;
		charactersArray.get(i).moving = false;

		if(GameInput.keyDownOnce(KeyEvent.VK_M))
		{
			dialogCheck(i,player, charactersArray);
		}
	}

	private void dialogCheck(int i,Player player ,List<AbstractCharacter> charactersArray)
	{
		dialogs.setDialog(true);
		charactersArray.get(i).dialog = true;

		if(charactersArray.get(i).dialog)
		{
			changeCharacterDirection(i,player,charactersArray);
		}
		else if(!dialogs.isDialog())
		{
			charactersArray.get(i).dialog = false;
		}
	}

	private void changeCharacterDirection(int i, Player player, List<AbstractCharacter> charactersArray)
	{
		switch (player.direction)
		{
			case FourDirections.UP:
				charactersArray.get(i).direction = FourDirections.DOWN;
				charactersArray.get(i).animations.setAnimationByIndex(4);
				break;
			case FourDirections.DOWN:
				charactersArray.get(i).direction = FourDirections.UP;
				charactersArray.get(i).animations.setAnimationByIndex(0);
				break;
			case FourDirections.LEFT:
				charactersArray.get(i).direction = FourDirections.RIGHT;
				charactersArray.get(i).animations.setAnimationByIndex(6);
				break;
			case FourDirections.RIGHT:
				charactersArray.get(i).direction = FourDirections.LEFT;
				charactersArray.get(i).animations.setAnimationByIndex(2);
				break;
		}
	}

	private boolean validateCharacterDialogCheck(int i, List<AbstractCharacter> entities,Player player)
	{
		return entities.get(i) != null && !(entities.get(i) instanceof Player)
				&& player.dialogArea.intersects(entities.get(i).collider);
	}

	public void draw(Graphics2D graphics2d, List<AbstractCharacter> charactersArray)
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
		dialogs.draw(graphics2d,charactersArray.get(index).name);
	}

	public void addDialog(String characterName, String text)
	{
		this.dialogs.add(characterName,text);
	}
	public void addDialog(String characterName, String... texts)
	{
		this.dialogs.add(characterName,texts);
	}
}