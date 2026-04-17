package br.com.ajf.dialogsystem.dialog;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import br.com.ajf.dialogsystem.main.GameLauncher;
import br.com.ajf.dialogsystem.utility.SubScreen;
import br.com.ajf.game.framework.Game;

public final class CharacterDialog
{
	private final IDialog dialog;
	private final SubScreen screen;
	private final Map<String,BufferedImage> images = new HashMap<>();
	private BufferedImage img;
	
	public CharacterDialog()
	{
		dialog = new DialogLetterByLetter();
		screen = new SubScreen((int) (GameLauncher.WIDTH/4.5), GameLauncher.HEIGHT-128-32, 394, 128);
	}
	
	public CharacterDialog add(String name,String text)
	{
		dialog.add(name,text);
		BufferedImage img = images.get(name);
		
		if(img == null)
		{
			img = Game.LOADER.loadBufferedImage("/player/"+name+".png");
			images.put(name, img);
		}
		
		return this;
	}
	public CharacterDialog add(String name,String...texts)
	{
		dialog.add(name,texts);
		BufferedImage img = images.get(name);

		if(img == null)
		{
			img = Game.LOADER.loadBufferedImage("/player/"+name+".png");
			images.put(name, img);
		}
		return this;
	}
	
	public void draw(Graphics2D graphics2d,String name)
	{
		img = images.get(name);
		
		if(dialog.isDialog())
		{
			screen.draw(graphics2d);
			
			if(img != null)
			{
				graphics2d.drawImage(img, screen.xPos()-img.getWidth()-16,
						screen.yPos()+8, null);
				
				graphics2d.setColor(Color.YELLOW);
				graphics2d.drawRoundRect(screen.xPos()-img.getWidth()-13,
						screen.yPos()+5, img.getWidth(), img.getHeight(), 5, 5);
			}
		}
		
		dialog.draw(graphics2d,name, screen.xPos()+32, screen.yPos()+32);
	}
	
	public boolean isDialog()
	{
		return dialog.isDialog();
	}
	
	public void setDialog(boolean dialog)
	{
		this.dialog.setDialog(dialog);
	}
}