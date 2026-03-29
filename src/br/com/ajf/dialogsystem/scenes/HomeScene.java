package br.com.ajf.dialogsystem.scenes;

import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.collision.Collider;
import br.com.ajf.game.framework.Game;
import br.com.ajf.game.math.Vector2I;
import br.com.ajf.game.scene.Scene;
import br.com.ajf.game.tmx.TMXLoader;

/**
 * The Class HomeScene.
 */
public final class HomeScene extends AbstractScene
{
	
	/**
	 * Instantiates a new home scene.
	 *
	 * @param game the game
	 * @param player the player
	 */
	public HomeScene(Game game, AbstractCharacter player)
	{
		super(game, player);
	}

	/**
	 * Start.
	 *
	 * @return the scene
	 */
	@Override
	public Scene start()
	{	
		name = "Home";
		TMXLoader tmxLoader = new TMXLoader("/maps/home2.tmx");
		tileManager = tmxLoader.getTileManager();
		colliders = tmxLoader.getRectsCollidersByAtributeName(COLLISION);
		transitions = tmxLoader.getRectsCollidersByAtributeName(TRASITION);
		
		layers = tmxLoader.getLayers();
		
		Vector2I pos = tmxLoader.getVector2IListFromPropertyTagName("Player").getFirst();
		player.position.set(pos.getX(), pos.getY());
		entities.add(player);
		
		transition.setName(name);
	
		return this;
	}

	/**
	 * Transitions.
	 *
	 * @param abstractCharacter the abstract character
	 * @param collider the collider
	 */
	@Override
	protected void sceneTransition(AbstractCharacter abstractCharacter, Collider collider)
	{		
		if(abstractCharacter.collider.getType().equalsIgnoreCase("player"))
		{	
			if(collider.getId() == 12)
			{
				if(abstractCharacter.collider.intersects(collider))
				{
					player.position.set(2296, 2345);
					this.transition.reset();
					((AbstractScene)(this.game.changeScene("Island"))).transition.reset();	
				}
			}
		}
	}
}