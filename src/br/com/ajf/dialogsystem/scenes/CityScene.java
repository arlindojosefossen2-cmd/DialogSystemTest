package br.com.ajf.dialogsystem.scenes;

import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.collision.Collider;
import br.com.ajf.game.framework.Game;
import br.com.ajf.game.math.Vector2I;
import br.com.ajf.game.moviment.FourDirections;
import br.com.ajf.game.scene.Scene;
import br.com.ajf.game.tmx.TMXLoader;


/**
 * The Class CityScene.
 */
public final class CityScene extends AbstractScene
{
	
	/**
	 * Instantiates a new city scene.
	 *
	 * @param game the game
	 * @param player the player
	 */
	public CityScene(Game game, AbstractCharacter player)
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
		name = "City";
		TMXLoader tmxLoader = new TMXLoader("/maps/city2.tmx");
		tileManager = tmxLoader.getTileManager();
		colliders = tmxLoader.getRectsCollidersByAtributeName(COLLISION);
		transitions = tmxLoader.getRectsCollidersByAtributeName(TRASITION);
		
		layers = tmxLoader.getLayers();
		
		Vector2I pos = tmxLoader.getVector2IListFromPropertyTagName("Player").getFirst();
		player.position.set(pos.getX(), pos.getY());
		charactersArray[0] = player;
		
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
			if(collider.getId() == 25)
			{
				if(abstractCharacter.collider.intersects(collider))
				{
	//				change position of player when exit of the city
					player.position.set(1080, 1680);
					player.direction = FourDirections.DOWN;
					this.transition.reset();
					((AbstractScene)(this.game.changeScene("Island"))).transition.reset();
				}
			}
		}
	}
}