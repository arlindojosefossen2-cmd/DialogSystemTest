package br.com.ajf.dialogsystem.scenes;

import br.com.ajf.dialogsystem.light.Lighting;
import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.collision.Collider;
import br.com.ajf.game.framework.Game;
import br.com.ajf.game.math.Vector2I;
import br.com.ajf.game.scene.Scene;
import br.com.ajf.game.tmx.TMXLoader;

/**
 * The Class TempleScene.
 */
public final class TempleScene extends AbstractScene
{
	
	/**
	 * Instantiates a new temple scene.
	 *
	 * @param game the game
	 * @param player the player
	 */
	public TempleScene(Game game, AbstractCharacter player)
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
		name = "Temple";
		TMXLoader tmxLoader = new TMXLoader("/maps/temple2.tmx");
		tileManager = tmxLoader.getTileManager();
		colliders = tmxLoader.getRectanglesCollidersByAttributeName(COLLISION);
		transitions = tmxLoader.getRectanglesCollidersByAttributeName(TRASITION);
		
		layers = tmxLoader.getLayers();
		
		Vector2I pos = tmxLoader.getVector2IListFromPropertyTagName("Player").getFirst();
		player.position.set(pos.getX(), pos.getY());
		entities.add(player);
		
		transition.setName(name);

		area = Lighting.inside;
		
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
			if(collider.getId() == 29)
			{
				if(abstractCharacter.collider.intersects(collider))
				{
	//				change position of player when exit of the cave
					player.position.set(2236, 1235);
					this.transition.reset();
					((AbstractScene)(this.game.changeScene("Island"))).transition.reset();	
				}
			}
		}
	}
}