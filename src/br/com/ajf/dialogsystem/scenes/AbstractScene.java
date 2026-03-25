package br.com.ajf.dialogsystem.scenes;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import br.com.ajf.dialogsystem.dialog.CharacterDialog;
import br.com.ajf.dialogsystem.player.DartPlayer;
import br.com.ajf.game.character.AbstractCharacter;
import br.com.ajf.game.character.CharacterCollisions;
import br.com.ajf.game.character.CharacterOrderLayer;
import br.com.ajf.game.collision.Collider;
import br.com.ajf.game.framework.Game;
import br.com.ajf.game.input.GameInput;
import br.com.ajf.game.moviment.FourDirections;
import br.com.ajf.game.player.Player;
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
	
	/** The characters array. */
	protected AbstractCharacter[] charactersArray = new AbstractCharacter[10];
	
	/** The game. */
	protected Game game;
	
	/** The player. */
	protected DartPlayer player;
	
	/** The tile manager. */
	protected ITileManager tileManager;
	
	/** The layers. */
	protected int[] layers;

	/** The debug. */
	protected boolean debug;
	
	protected String name;
	
	private final CharacterOrderLayer ordernator = new CharacterOrderLayer();
	
	protected Transition transition = new Transition(
			"Abstract Scene",
			Game.LOADER.loadScaledBufferedImagesFromSheet("/transition/transition.png", 1, 5,8)
			, 0.16f);

	protected boolean dialog;
	
	public CharacterDialog dialogs = new CharacterDialog();

	public int index;
	
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
			debug = !debug;
			//testing a simple life bar
			player.health.setLife(player.health.getLife()-1);
		}
		
		characters.clear();
			
		updateCharactersCollisions();

		updateDialog();
		
		characters.sort(ordernator);
		
		if(transition != null)
		{
			transition.update(game.delta());
		}
	}

	private void updateDialog()
	{
		for (int i = 0; i < charactersArray.length; i++)
		{
			if(validateCharacterDialogChecher(i))
			{
					updateDialog(i);
			}
		}
	}

	private void updateDialog(int i)
	{
		dialog = true;
		index = i;
		charactersArray[i].moving = false;
		
		if(GameInput.keyDownOnce(KeyEvent.VK_M))
		{
			dialogchecker(i);
		}
	}

	private void dialogchecker(int i)
	{
		dialogs.setDialog(true);
		charactersArray[i].dialog = true;
		
		if(charactersArray[i].dialog)
		{
			changeCharacterdirection(i);
		}
		else if(!dialogs.isDialog())
		{
			charactersArray[i].dialog = false;
		}
	}

	private void changeCharacterdirection(int i)
	{
		switch (player.direction)
		{
			case FourDirections.UP:
				charactersArray[i].direction = FourDirections.DOWN;
				charactersArray[i].animations.setAnimationByIndex(4);
				break;
			case FourDirections.DOWN:
				charactersArray[i].direction = FourDirections.UP;
				charactersArray[i].animations.setAnimationByIndex(0);
				break;
			case FourDirections.LEFT:
				charactersArray[i].direction = FourDirections.RIGHT;
				charactersArray[i].animations.setAnimationByIndex(6);
				break;	
			case FourDirections.RIGHT:
				charactersArray[i].direction = FourDirections.LEFT;
				charactersArray[i].animations.setAnimationByIndex(2);
				break;
		}
	}

	private boolean validateCharacterDialogChecher(int i)
	{
		return charactersArray[i] != null && !(charactersArray[i] instanceof Player) 
				&& player.dialogArea.intersects(charactersArray[i].collider);
	}

	public void updateCharactersCollisions()
	{
		for (AbstractCharacter abstractCharacter : charactersArray)
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
		for (int i = 0; i < charactersArray.length; i++)
		{
			if(validateCharacterCollisionChecker(character, i))
			{
				character.collision = true;
				character.preventMoviment(game.delta());
			}
		}
	}

	private boolean validateCharacterCollisionChecker(AbstractCharacter character, int i)
	{
		return character != charactersArray[i] && charactersArray[i] != null 
				&& character.collider.intersects(charactersArray[i].collider) 
				&& character.solid && charactersArray[i].solid;
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
				abstractCharacter.preventMoviment(game.delta());
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
	
	public void setTransition(Transition transition)
	{
		this.transition = transition;
	}
}