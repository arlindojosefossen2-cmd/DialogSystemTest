package br.com.ajf.dialogsystem.utility;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * The Class SubScreen.
 *
 * @param xPos   The x pos.
 * @param yPos   The y pos.
 * @param width  The width.
 * @param height The height.
 */
public record SubScreen(int xPos, int yPos, int width, int height)
{
	/**
	 * Instantiates a new sub screen.
	 *
	 * @param xPos   the x pos
	 * @param yPos   the y pos
	 * @param width  the width
	 * @param height the height
	 */
	public SubScreen
	{
	}

	/**
	 * Draw.
	 *
	 * @param graphics2d the graphics 2 d
	 */
	public void draw(final Graphics2D graphics2d)
	{
		Color color = new Color(0, 0, 0, 210);
		graphics2d.setColor(color);
		graphics2d.fillRoundRect(xPos, yPos, width, height, 35, 35);

		color = new Color(255, 255, 255);
		graphics2d.setColor(color);
		graphics2d.setStroke(new BasicStroke(5));
		graphics2d.drawRoundRect(xPos + 5, yPos + 5, width - 10, height - 10, 25, 25);
	}

	/**
	 * Gets the x pos.
	 *
	 * @return the x pos
	 */
	@Override
	public int xPos()
	{
		return xPos;
	}

	/**
	 * Gets the y pos.
	 *
	 * @return the y pos
	 */
	@Override
	public int yPos()
	{
		return yPos;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	@Override
	public int width()
	{
		return width;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	@Override
	public int height()
	{
		return height;
	}
}