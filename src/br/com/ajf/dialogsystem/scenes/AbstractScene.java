package br.com.ajf.dialogsystem.scenes;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import br.com.ajf.dialogsystem.dialog.DialogManager;
import br.com.ajf.dialogsystem.player.DartPlayer;
import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.character.CharacterCollisions;
import br.com.ajf.game.character.CharacterOrderLayer;
import br.com.ajf.game.collision.Collider;
import br.com.ajf.game.framework.Game;
import br.com.ajf.game.input.GameInput;
import br.com.ajf.game.scene.Scene;
import br.com.ajf.game.tile.ITileManager;


/**
 * The Class AbstractScene.
 */
public abstract class AbstractScene implements Scene
{
	/** The Constant TRASITION. */
	protected static final String TRASITION = "Transition";
	
	/** The Constant COLLISION. */
	protected static final String COLLISION = "Collision";
	
	/** The rects. */
	protected List<Collider> colliders ;
	
	/** The transitions. */
	protected List<Collider> transitions ;
	
	/** The characters. */
	protected List<AbstractCharacter> characters = new ArrayList<>();
	
	/** The entities array. */
	protected List<AbstractCharacter> entities = new ArrayList<>();

	/** The enemies array. */
	protected List<AbstractCharacter> enemies = new ArrayList<>();

	/** The game. */
	protected Game game;
	
	/** The player. */
	protected DartPlayer player;
	
	/** The tile manager. */
	protected ITileManager tileManager;
	
	/** The layers. */
	protected int[] layers;

	protected String name;
	
	private final CharacterOrderLayer alternator = new CharacterOrderLayer();
	
	protected Transition transition = new Transition(
			"Abstract Scene",
			Game.LOADER.loadScaledBufferedImagesFromSheet("/transition/transition.png", 1, 5,8)
			, 0.16f);

	protected final DialogManager dialogManager = new DialogManager();

	/**
	 * Instantiates a new abstract scene.
	 *
	 * @param game the game
	 * @param player the player
	 */
	public AbstractScene(Game game,AbstractCharacter player)
	{
		this.game = game;
		this.player = (DartPlayer) player;
	}
	
	/**
	 * Transitions.
	 *
	 * @param abstractCharacter the abstract character
	 * @param collider the collider
	 */
	protected abstract void sceneTransition(AbstractCharacter abstractCharacter, Collider collider);
	
	/**
	 * Update.
	 */
	@Override
	public void update()
	{
		//Debug Colliders
		if(GameInput.keyDownOnce(KeyEvent.VK_B))
		{
			DebugScene.debug = !DebugScene.debug;
			//testing a simple life bar
			player.health.setLife(player.health.getLife()-1);
		}
		
		characters.clear();
			
		updateCharactersCollisions();

		updateDialog();

		updateEnemies();

		characters.sort(alternator);
		
		if(transition != null)
		{
			transition.update(game.delta());
		}
	}

	private void updateEnemies()
	{
		for (AbstractCharacter enemy : enemies)
		{
			if(enemy != null)
			{
				enemy.collision = false;
				enemy.update(game.delta());
				characters.add(enemy);

				for (AbstractCharacter abstractCharacter : this.entities)
				{
					if(enemy.collider.intersects(abstractCharacter.collider))
					{
						enemy.preventMovement(game.delta());
						abstractCharacter.preventMovement(game.delta());
					}
				}

				if(CharacterCollisions.isCollisionBYCharacterTypeName(enemy, colliders,COLLISION))
				{
					enemy.preventMovement(game.delta());
				}
			}
		}
	}

	private void updateDialog()
	{
		dialogManager.updateDialog(entities,player);
	}


	public void updateCharactersCollisions()
	{
		for (AbstractCharacter abstractCharacter : entities)
		{
			if(abstractCharacter != null)
			{
				abstractCharacter.collision = false;
				updateCharacter(abstractCharacter);
				collision(abstractCharacter);
			}
		}
	}
	
	protected void collision(AbstractCharacter character)
	{
		for (int i = 0; i < entities.size(); i++)
		{
			if(validateCharacterCollisionChecker(character, i))
			{
				character.collision = true;
				character.preventMovement(game.delta());
			}
		}
	}

	private boolean validateCharacterCollisionChecker(AbstractCharacter character, int i)
	{
		return character != entities.get(i) && entities.get(i) != null
				&& character.collider.intersects(entities.get(i).collider)
				&& character.solid && entities.get(i).solid;
	}

	public void updateCharacter(AbstractCharacter abstractCharacter)
	{
		if(abstractCharacter != null)
		{
			abstractCharacter.update(this.game.delta());
		
			for(Collider collider : transitions)
			{
				if(collider != null)
				{
					sceneTransition(abstractCharacter, collider);
					
					for (int i = 0; i < layers.length; i++)
					{
						layerTransition(abstractCharacter, collider,i);						
					}
				}
			}

			if(CharacterCollisions.isCollisionBYCharacterTypeName(abstractCharacter, colliders,COLLISION))
			{
				abstractCharacter.preventMovement(game.delta());
			}
				
			characters.add(abstractCharacter);
		}
	}

	public void layerTransition(AbstractCharacter abstractCharacter, Collider collider,int i)
	{
		if(abstractCharacter.collider.intersects(collider))
		{
			if(collider.getLayer() == i)
			{
				abstractCharacter.collider.setLayer(i);
			}
		}
	}
	
	/**
	 * Draw.
	 *
	 * @param graphics2d the graphics 2 d
	 */
	@Override
	public void draw(Graphics2D graphics2d)
	{
		DrawScene.draw(graphics2d,game,this);
	}

	@Override
	public String getSceneName()
	{
		return name;
	}

	@Override
	public Scene setSceneName(String name)
	{
		this.name = name;
		return this;
	}

	public AbstractScene addDialog(String characterName,String text)
	{
		this.dialogManager.addDialog(characterName,text);
		return this;
	}
	public AbstractScene addDialog(String characterName,String... texts)
	{
		this.dialogManager.addDialog(characterName,texts);
		return this;
	}
	
	public void setTransition(Transition transition)
	{
		this.transition = transition;
	}
}