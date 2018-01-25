package com.brusi.ggj2018;

import com.badlogic.gdx.Game;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.GameScreen;

public class ggj2018 extends Game {
	public Assets assets = new Assets();

	@Override
	public void create () {
		assets.init();
		setScreen(new GameScreen());
	}

	@Override
	public void dispose () {
		assets.dispose();
	}
}
