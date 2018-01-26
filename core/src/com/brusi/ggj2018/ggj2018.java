package com.brusi.ggj2018;

import com.badlogic.gdx.Game;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.GameScreen;
import com.brusi.ggj2018.title.TitleScreen;
import com.brusi.ggj2018.utils.TouchToPoint;

public class ggj2018 extends Game {
	public Assets assets = new Assets();
	public TouchToPoint touchToPoint;

	@Override
	public void create () {
		assets.init();
		touchToPoint = TouchToPoint.create();
		setScreen(new TitleScreen());
	}

	@Override
	public void dispose () {
		assets.dispose();
	}
}
