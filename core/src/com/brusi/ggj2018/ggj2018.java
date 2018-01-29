package com.brusi.ggj2018;

import com.badlogic.gdx.Game;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.assets.SoundAssets;
import com.brusi.ggj2018.title.TitleScreen;
import com.brusi.ggj2018.utils.TouchToPoint;

public class ggj2018 extends Game {
	public Assets assets = new Assets();
	public SoundAssets soundAssets = new SoundAssets();
	public TouchToPoint touchToPoint;
	float mouseAspect;

	public ggj2018(float mouseAspect)
	{
		this.mouseAspect = mouseAspect;
	}

	@Override
	public void create () {
		assets.init();
		soundAssets.init();
		touchToPoint = TouchToPoint.create();
		setScreen(new TitleScreen(mouseAspect));
	}

	@Override
	public void dispose () {
		assets.dispose();
	}
}
