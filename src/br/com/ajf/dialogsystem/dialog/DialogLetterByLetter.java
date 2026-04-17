package br.com.ajf.dialogsystem.dialog;

import br.com.ajf.game.audio.wav.IAudio;
import br.com.ajf.game.audio.wav.SoundEFX;
import br.com.ajf.game.input.GameInput;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public final class DialogLetterByLetter implements IDialog
{
	private int index = -1;

	private final Map<String,List<String>> dialogs = new HashMap<>();

	private boolean dialog;
	private List<String> dialogsList;

	private String textCombined;
	private String currentText;
	private int charIndex;

	private final IAudio speakSound = new SoundEFX("/sounds/speak.wav");

	public DialogLetterByLetter()
	{
		speakSound.setVolume(6f);
	}
	
	public IDialog add(String name,String text)
	{
		List<String> dialogList = this.dialogs.get(name);
		
		if(dialogList == null)
		{
			dialogList = new ArrayList<>();
			dialogList.add(text);
			this.dialogs.put(name, dialogList);
		}
		else
		{
			dialogList.add(text);
		}
		return this;
	}

	public IDialog add(String name,String... texts)
	{
		List<String> dialogList = this.dialogs.get(name);

		if(dialogList == null)
		{
			dialogList = new ArrayList<>();

			Collections.addAll(dialogList, texts);

			this.dialogs.put(name, dialogList);
		}
		else
		{
			Collections.addAll(dialogList, texts);
		}
		return this;
	}
	
	public void draw(Graphics2D graphics2d,String name,int xPos,int yPos)
	{		
		if(dialog)
		{
			if(GameInput.keyDownOnce(KeyEvent.VK_M))
			{
				index++;
				charIndex = 0;
				textCombined = "";
			}
		}
		
		dialogsList = dialogs.get(name);
		
		if(dialogsList == null)
		{
			return;
		}
		
		if(index >= dialogsList.size())
		{
			index = -1;
			dialog = false;
		}
		
		if(index > -1 && dialog)
		{
			graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 18f));
			graphics2d.setColor(Color.white);

			char[] array = this.dialogsList.get(index).toCharArray();

			if(charIndex < array.length)
			{
				speakSound.stop();
				speakSound.play();
				textCombined = textCombined + array[charIndex];
				currentText = textCombined;
				charIndex++;
			}

			int x = xPos;

			graphics2d.setColor(Color.ORANGE);
			String str = name + ": - ";
			graphics2d.drawString(str,x,yPos);

			x += (int)graphics2d.getFont().getStringBounds(str,graphics2d.getFontRenderContext()).getWidth();

			graphics2d.setColor(Color.LIGHT_GRAY);

			String[] split = currentText.split("\n");

			for(int i = 0;i < split.length;i++)
			{
				String textFinal = split[i];

				if(i >= 1)
				{
					x = xPos;
				}

				graphics2d.drawString(textFinal, x,yPos);
				yPos += graphics2d.getFont().getSize();
			}
		}
	}

	public boolean isDialog()
	{
		return dialog;
	}

	public void setDialog(boolean dialog)
	{
		this.dialog = dialog;
	}
}