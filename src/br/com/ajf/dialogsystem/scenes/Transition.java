package br.com.ajf.dialogsystem.scenes;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import br.com.ajf.game.animation.Animation;
import br.com.ajf.game.animation.IAnimation;
import br.com.ajf.game.audio.wav.IAudio;
import br.com.ajf.game.audio.wav.SoundEFX;

public final class Transition 
{
	private static final float FONT_SIZE = 32;

	private final IAnimation animation;
	
	private String name;
	
	private int width ;
	private int height ;
	
	private FontMetrics metrics;
	
	private final IAudio transitionSound = new SoundEFX("/sounds/transition.wav");
	
	public Transition(String name,BufferedImage[] images, float interval)
	{
		this.name = name;
		this.animation = new Animation(images, interval, false);
		transitionSound.setVolume(5);
	}
	
	public void update(float delta)
	{
		if(!this.animation.isFinished())
		{
			transitionSound.play();	
			this.animation.update(delta);
		}
	}
	
	public void draw(Graphics2D graphics2d)
	{
		if(!this.animation.isFinished())
		{
			if(metrics == null)
			{
				metrics = graphics2d.getFontMetrics(graphics2d.getFont().deriveFont(FONT_SIZE));
				graphics2d.setFont(graphics2d.getFont().deriveFont(FONT_SIZE));
			}
			
			this.animation.draw(graphics2d, 0, 0);
			
			if(width == 0 && height == 0)
			{
				width = (int)metrics.getStringBounds(name, graphics2d).getWidth();
				height = (int)metrics.getStringBounds(name, graphics2d).getHeight();
			}
			
			graphics2d.setColor(Color.WHITE);	
			graphics2d.drawString(name,this.animation.getWidth()/2-width/2, this.animation.getHeight()/2+height/2);
		}
	}
	
	public boolean isFinished()
	{
		transitionSound.stop();
		return this.animation.isFinished();
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void reset()
	{
		this.animation.reset();
	}
}