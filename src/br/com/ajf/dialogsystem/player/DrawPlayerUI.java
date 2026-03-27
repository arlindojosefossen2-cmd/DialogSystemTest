package br.com.ajf.dialogsystem.player;

import java.awt.*;

import br.com.ajf.dialogsystem.main.GameLauncher;
import br.com.ajf.game.player.IDrawPlayerUI;
import br.com.ajf.game.player.Player;

public final class DrawPlayerUI implements IDrawPlayerUI
{

	public static final int STYLE_FONT_32 = 32;

	@Override
	public void drawUI(Graphics2D graphics2d,Player player)
	{
		graphics2d.setColor(Color.yellow);
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD,STYLE_FONT_32));
		graphics2d.drawString("Life: ", 8, 32);
		
		graphics2d.setColor(Color.blue);

		graphics2d.fillRect(8, 48, 
				(int) (GameLauncher.TILE_SIZE *GameLauncher.SCALE*player.health.getLife()/player.health.getMaxLife()), 32);
	}
}