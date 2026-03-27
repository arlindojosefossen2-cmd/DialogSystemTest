package br.com.ajf.dialogsystem.scenes;

import br.com.ajf.dialogsystem.entity.EntityPlayer;
import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.collision.Collider;
import br.com.ajf.game.framework.Game;
import br.com.ajf.game.scene.Scene;
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
	
		TMXLoader tmxLoader = new TMXLoader("/maps/world2.tmx");
		
		tileManager = tmxLoader.getTileManager();
		
		colliders = tmxLoader.getRectsCollidersByAtributeName(COLLISION);
		transitions = tmxLoader.getRectsCollidersByAtributeName(TRASITION);
		
		layers = tmxLoader.getLayers();
		
		player.position = tmxLoader.getVector2IListFromPropertyTagName("Player").getFirst();
		charactersArray[0] = player;
		
		//testing a entities
		EntityPlayer entityTest = new EntityPlayer();
		entityTest.start();
		
		entityTest.position.set(player.position.getX()-64,player.position.getY()+64);
		entityTest.name = "Paul";
		charactersArray[1] = entityTest;
		
		this.addDialog(entityTest.name,"Testing notting!")
		.addDialog(entityTest.name,"Testing again!")
		.addDialog(entityTest.name,"Testing again!\nTesting\nNothing")
		.addDialog(entityTest.name,"Testing again!\nNothing Again...again!")
		.addDialog(entityTest.name,"Testing again!")
		.addDialog(entityTest.name,"Testing again!");
		
		//testing more than one with dialog system
		entityTest = new EntityPlayer();
		entityTest.start();
		
		entityTest.position.set(player.position.getX()-128,player.position.getY()+96);
		charactersArray[2] = entityTest;
		entityTest.name = "Joan";
		
		 this.addDialog(entityTest.name,"Testing notting!")
		.addDialog(entityTest.name,"Testing again!")
		.addDialog(entityTest.name,"Testing again!\nTesting\nother again.")
		.addDialog(entityTest.name,"Testing again!\nfinished again!")
		.addDialog(entityTest.name,"Testing again!")
		.addDialog(entityTest.name,"Testing again!")
		.addDialog(entityTest.name,"Testing again!")
		.addDialog(entityTest.name,"Default\nTesting\nAgain!");
		//end testing
		
		sceneTransition = new ChangeIslandScene();
		
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
		sceneTransition.changeScene(abstractCharacter, collider,game,player);
	}
}