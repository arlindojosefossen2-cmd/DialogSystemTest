package br.com.ajf.dialogsystem.light;

import br.com.ajf.dialogsystem.main.GameLauncher;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting
{
	private final BufferedImage imageFilter;
	private float alpha;
	private int counter;

	public static final int day = 0;
	public static final int dusk = 1;
	public static final int night = 2;
	public static final int dawn = 3;

	public int current = day;
	public static final int outside = 0;
	public static final int dungeon = 1;
	public static final int inside = 2;

	private String situation;

	public Lighting()
	{
		imageFilter = new BufferedImage(GameLauncher.WIDTH,GameLauncher.HEIGHT,BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = imageFilter.createGraphics();

		int centerX = GameLauncher.WIDTH/2 + GameLauncher.TILE_SIZE/2;
		int centerY = GameLauncher.HEIGHT/2 + GameLauncher.TILE_SIZE/2;

		//Create a gradation effect within the light circle
		Color[] color = new Color[12];
		float[] fraction = new float[12];

		color[0] = new Color(0,0,0.08f,0.1f);    //Center
		color[1] = new Color(0,0,0.08f,0.42f);
		color[2] = new Color(0,0,0.08f,0.52f);
		color[3] = new Color(0,0,0.08f,0.61f);
		color[4] = new Color(0,0,0.08f,0.69f);
		color[5] = new Color(0,0,0.08f,0.76f);
		color[6] = new Color(0,0,0.08f,0.82f);
		color[7] = new Color(0,0,0.08f,0.87f);
		color[8] = new Color(0,0,0.08f,0.91f);
		color[9] = new Color(0,0,0.08f,0.92f);
		color[10] = new Color(0,0,0.08f,0.93f);
		color[11] = new Color(0,0,0.08f,0.94f);  //Edge

		//Distance between gradation
		fraction[0] = 0f;    //Center
		fraction[1] = 0.4f;
		fraction[2] = 0.5f;
		fraction[3] = 0.6f;
		fraction[4] = 0.65f;
		fraction[5] = 0.7f;
		fraction[6] = 0.75f;
		fraction[7] = 0.8f;
		fraction[8] = 0.85f;
		fraction[9] = 0.9f;
		fraction[10] = 0.95f;
		fraction[11] = 1f;    //Edge

		RadialGradientPaint gPaint = new RadialGradientPaint(
				centerX,
				centerY,
			120,
				fraction,
				color);

		graphics2D.setPaint(gPaint);

		graphics2D.fillRect(0,0,GameLauncher.WIDTH,GameLauncher.HEIGHT);
		graphics2D.dispose();
	}

	public void update()
	{
		if(current == day)
		{
			counter++;

			if(counter > 600)
			{
				current = dusk;
				counter = 0;
			}
		}
		else if(current == dusk)
		{
			alpha += 0.0005f;

			if(alpha > 1f)
			{
				alpha = 1f;
				current = night;
			}
		}
		else if(current == night)
		{
			counter++;

			if(counter > 600)
			{
				current = dawn;
				counter = 0;
			}
		}
		else if(current == dawn)
		{
			alpha -= 0.0005f;
			if(alpha < 0)
			{
				alpha = 0;
				current = day;
			}
		}
	}

	public  void draw(Graphics2D graphics2D,int area)
	{
		if(area == outside)
		{
			graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha)); //only change alpha when outside
		}
		if(area == outside || area == dungeon)
		{
			graphics2D.drawImage(imageFilter,0,0,null);  //draw darkness filter outside or dungeon. alpha = 1f;
		}
		graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f)); //indoor: no darkness filter

		//DEBUG
		situation = switch (current)
		{
			case day -> "Day";
			case dusk -> "Dusk";
			case night -> "Night";
			case dawn -> "Dawn";
			default -> "";
		};

		graphics2D.setColor(Color.white);
		graphics2D.setFont(graphics2D.getFont().deriveFont(32f));
		graphics2D.drawString(situation,800,500);
	}

	public void reset()
	{
		current = day;
		alpha = 0;
	}
}