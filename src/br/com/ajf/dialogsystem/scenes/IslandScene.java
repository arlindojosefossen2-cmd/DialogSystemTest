package br.com.ajf.dialogsystem.scenes;

import br.com.ajf.dialogsystem.enemies.Slime;
import br.com.ajf.dialogsystem.entity.EntityPlayer;
import br.com.ajf.dialogsystem.light.Lighting;
import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.collision.Collider;
import br.com.ajf.game.framework.Game;
import br.com.ajf.game.scene.Scene;
import br.com.ajf.game.tile.ITile;
import br.com.ajf.game.tmx.TMXLoader;


/**
 * The Class IslandScene.
 */
public final class IslandScene extends AbstractScene
{	
	private ChangeIslandScene sceneTransition;
	/**
	 * Instantiates a new island scene.
	 *
	 * @param game the game
	 * @param player the player
	 */
	public IslandScene(Game game,AbstractCharacter player)
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
		name = "Island";
		player.start();
	
		tmxLoader = new TMXLoader("/maps/world2.tmx");
		
		tileManager = tmxLoader.getTileManager();
		
		colliders = tmxLoader.getRectanglesCollidersByAttributeName(COLLISION);
		transitions = tmxLoader.getRectanglesCollidersByAttributeName(TRASITION);
		
		layers = tmxLoader.getLayers();
		
		player.position = tmxLoader.getVector2IListFromPropertyTagName("Player").getFirst();
		entities.add(player);
		
		//testing a entities
		EntityPlayer entityTest = new EntityPlayer();
		entityTest.start();
		
		entityTest.position.set(player.position.getX()-64,player.position.getY()+64);
		entityTest.name = "Paul";
		entities.add(entityTest);
		
		this.addDialog(entityTest.name,"Hi! My name is "+entityTest.name+"\nI am a dumpy of you.",
				"I've Nothing to say\nfor you boy.Make your way.\nDead all the Blue-Slimes.");
		
		//testing more than one with dialog system
		entityTest = new EntityPlayer();
		entityTest.start();
		
		entityTest.position.set(player.position.getX()-128,player.position.getY()+96);
		entities.add(entityTest);
		entityTest.name = "JoanTeodor";
		
		 this.addDialog(entityTest.name,"Hi!I am a dumpy! but\nmy name is "+entityTest.name+"\nNice to meet you.",
				 "Press \"X\" to attack.");
		//end testing
		
		sceneTransition = new ChangeIslandScene();
		
		transition.setName(name);

		Slime slime = new Slime(this);
		slime.start();
		slime.position.set(player.position.getX()-96,player.position.getY()-96);

		enemies.add(slime);

		area = Lighting.outside;

		ITile[] tiles = tileManager.getTiles();

		for (ITile tile : tiles)
		{
			System.out.println(tile.solid());
		}
		
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
		sceneTransition.changeScene(abstractCharacter, collider,game,player);
	}
}