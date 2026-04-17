package br.com.ajf.dialogsystem.light;

import java.awt.*;

public class LightingManager
{
	public final Lighting lighting;

	public LightingManager()
	{
		lighting = new Lighting();
	}

	public void update()
	{
		lighting.update();
	}

	public void draw(Graphics2D graphics2D,int area)
	{
		lighting.draw(graphics2D,area);
	}

	public void reset()
	{
		lighting.reset();
	}
}
