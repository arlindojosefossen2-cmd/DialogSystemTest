package br.com.ajf.dialogsystem.dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ajf.game.input.GameInput;

public final class Dialog
{
	private int index = -1;
	private final Map<String,List<String>> dialogs = new HashMap<>();
	private boolean dialog;
	private List<String> dialogsList;
	
	public Dialog()
	{
		
	}
	
	public Dialog add(String name,String text)
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
			graphics2d.drawString(name+": - ", x, y);
			
			x += 80;
			
			graphics2d.setColor(Color.white);
			
			
			for (String string : aux)
			{
				graphics2d.drawString(string, x, y);
			
				x += 32;
				y += 23;	
				
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