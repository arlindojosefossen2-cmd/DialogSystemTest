package br.com.ajf.dialogsystem.dialog;

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

	public DialogLetterByLetter()
	{

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
				String auxText = String.valueOf(array[charIndex]);
				textCombined = textCombined + auxText;
				currentText = textCombined;
				charIndex++;
			}

			for (String textFinal : currentText.split("\n"))
			{
				graphics2d.drawString(textFinal, xPos,yPos);
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