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
		
		dialogs.add(entityTest.name,"Testing notting!")
		.add(entityTest.name,"Testing again!")
		.add(entityTest.name,"Testing again!5\nTestiiiiiiiii\nfghabsjdfb")
		.add(entityTest.name,"Testing again!6\ngdgdygsbcndhsdfhlsje")
		.add(entityTest.name,"Testing again!8")
		.add(entityTest.name,"Testing again!9");
		
		//testing more than one with dialog system
		entityTest = new EntityPlayer();
		entityTest.start();
		
		entityTest.position.set(player.position.getX()-128,player.position.getY()+96);
		charactersArray[2] = entityTest;
		entityTest.name = "Joan";
		
		dialogs.add(entityTest.name,"Testing notting!")
		.add(entityTest.name,"Testing again!")
		.add(entityTest.name,"Testing again!3\nTestiiiiiiiii\nfghabsjdfb")
		.add(entityTest.name,"Testing again!2\ngdgdygsbcndhsdfhlsje")
		.add(entityTest.name,"Testing again!4")
		.add(entityTest.name,"Testing again!1")
		.add(entityTest.name,"Testing again!5")
		.add(entityTest.name,"Default\nTesting\nAgaing!");
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