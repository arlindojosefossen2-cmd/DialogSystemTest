package br.com.ajf.dialogsystem.scenes;

import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.collision.Collider;
import br.com.ajf.game.framework.Game;
import br.com.ajf.game.moviment.FourDirections;

public final class ChangeIslandScene
{
	public void changeScene(AbstractCharacter abstractCharacter, Collider collider, Game game, AbstractCharacter player)
	{
		if(abstractCharacter.collider.getType().equalsIgnoreCase("player"))
		{
			if(collider.getId() == 1)
			{
				if(abstractCharacter.collider.intersects(collider))
				{
                    game.addScene(new CaveScene(game,player).start());
                    player.position.set(1530,2400);
                }
			}
			else if(collider.getId() == 2)
			{
				if(abstractCharacter.collider.intersects(collider))
				{
                    game.addScene(new CityScene(game,player).start());
                    player.direction = FourDirections.UP;
                    player.position.set(1613,2402);

				}
			}
			else if(collider.getId() == 4)
			{
				if(abstractCharacter.collider.intersects(collider))
				{
                    game.addScene(new TempleScene(game,player).start());
                    player.direction = FourDirections.UP;
                    player.position.set(1279,2349);
                }
			}
			else if(collider.getId() == 3)
			{
				if(abstractCharacter.collider.intersects(collider))
				{
                    game.addScene(new HomeScene(game,player).start());
                    player.direction = FourDirections.UP;
                    player.position.set(1036,2364);
                }
			}
		}
	}
}