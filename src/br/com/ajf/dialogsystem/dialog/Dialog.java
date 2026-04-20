package br.com.ajf.dialogsystem.dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.*;

import br.com.ajf.game.input.GameInput;

public final class Dialog implements IDialog
{
	private int index = -1;
	private final Map<String,List<String>> dialogs = new HashMap<>();
	private boolean dialog;
	private List<String> dialogsList;
	
	public Dialog()
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
			}
		}
		
		int x = xPos;
		int y = yPos;
		
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
			String text = dialogsList.get(index);
			String[] aux = text.split("\n");
			
			graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 18f));
			
			graphics2d.setColor(Color.RED);
			String str = name + ": - ";
			graphics2d.drawString(str, x, y);
			
			x += (int) graphics2d.getFont().getStringBounds(str,graphics2d.getFontRenderContext()).getWidth();
			
			graphics2d.setColor(Color.white);

			for (String string : aux)
			{
				graphics2d.drawString(string, x, y);
			
				x += 32;
				y += (graphics2d.getFont().getSize()+4);
				
				if(x > 256)
				{
					x = xPos;
				}
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