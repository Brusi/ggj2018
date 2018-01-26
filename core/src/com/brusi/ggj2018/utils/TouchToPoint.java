package com.brusi.ggj2018.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.brusi.ggj2018.ggj2018;
import com.brusi.ggj2018.game.WorldRenderer;

public class TouchToPoint {
	final float camWidth;
	final float camHeight;
    final float ratio;
	
	int screenWidth;
	int screenHeight;

	// A reused instance of touch point.
	private final Vector2 touchPoint = new Vector2();
	
	public TouchToPoint(int screenWidth, int screenHeight, float camWidth, float camHeight) {
		this.camWidth = camWidth;
		this.camHeight = camHeight;
        ratio = camWidth / camHeight;
		reset(screenWidth, screenHeight);
	}

	public void reset(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	public Vector2 toPoint(int x, int y) {
        if (screenHeight * ratio > screenWidth) {
            float vp_height = screenWidth / ratio;
            // Screen is stretched vertically.
            float rel_x = (float) x / screenWidth;
            float y_margin = (screenHeight - vp_height) / 2;
            float rel_y = ((float) y - y_margin) / vp_height;

            float res_x = (rel_x - 0.5f) * camWidth;
            float res_y = -(rel_y - 0.5f) * camHeight;
            touchPoint.set(res_x, res_y);
            return touchPoint;
        } else {
            // Screen is stretched horizontally.
            float vp_width = screenHeight * ratio;
            float x_margin = (screenWidth - vp_width) / 2;
            float rel_x = ((float) x - x_margin) / vp_width;
            float rel_y = (float) y / screenHeight;

            float res_x = (rel_x - 0.5f) * camWidth;
            float res_y = -(rel_y - 0.5f) * camHeight;
            touchPoint.set(res_x, res_y);
            return touchPoint;
        }
    }
	
	public static TouchToPoint create() {
		return new TouchToPoint(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight(),
				WorldRenderer.FRUSTUM_WIDTH,
				WorldRenderer.FRUSTUM_HEIGHT);
	}

	public static TouchToPoint get() {
		return ((ggj2018)Gdx.app.getApplicationListener()).touchToPoint;
	}
}
