package br.com.ajf.dialogsystem.main;

import br.com.ajf.dialogsystem.player.DartPlayer;
import br.com.ajf.dialogsystem.scenes.IslandScene;
import br.com.ajf.game.framework.Game;
import br.com.ajf.game.thread.IGameThreadManager;

public final class GameLauncher
{
	public static final String TITLE = "Game 2D Test";
	
	public static final int ORIGINAL_TILE_SIZE = 16;
	public static final float SCALE = 3;
	public static final int TILE_SIZE = (int) (ORIGINAL_TILE_SIZE *SCALE);
	public static final int MAX_NUMBER_ROWS = 12;
	public static final int MAX_NUMBER_COLUMNS = 19;
	public static final int WIDTH = TILE_SIZE * MAX_NUMBER_COLUMNS;
	public static final int HEIGHT = TILE_SIZE * MAX_NUMBER_ROWS;
	
	public static void main(String[] args)
	{
		Game game = new Game(TITLE, WIDTH, HEIGHT, IGameThreadManager.GAME_THREAD_TIMER_TASK);
		game.addScene(new IslandScene(game,new DartPlayer()).start().setSceneName("Island"));
		game.init(IGameThreadManager.FPS_30);
	}
}