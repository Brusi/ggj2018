package com.brusi.ggj2018.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Ori on 29/10/2016.
 */

public class TextUi {

    private final Label label_;
    private final int align_;
    private float alpha_ = 1;
    private Vector2 position_;

    public TextUi(BitmapFont font, float x, float y, int align, String text) {
        position_ = new Vector2(x, y);
        align_ = align;
        label_ = new Label(text, new Label.LabelStyle(font, Color.WHITE));
        label_.setAlignment(align);
    }

    public TextUi(BitmapFont font, float x, float y, int align) {
        this(font, x, y, align, "0");
    }

    // Returns self for text initialization after construction.
    public TextUi setText(String text) {
        label_.setText(text);
        return this;
    }

    public void render(Batch batch) {
        if (label_.getFontScaleX() == 0 || label_.getFontScaleY() == 0) {
            return;
        }
        label_.setAlignment(align_);
        label_.setPosition(position_.x, position_.y);
        label_.draw(batch, alpha_);
    }

    private static float computeAlignDiff(int align, GlyphLayout layout) {
        if ((align & Align.right) != 0) {
            return -layout.width;
        } else if ((align & Align.center) != 0) {
            return -layout.width / 2;
        } else {
            // Aligned left.
            return 0;
        }
    }

    public void setScale(float scale) {
        label_.setFontScale(scale);
    }

    public float getWidth() {
        return label_.getGlyphLayout().width;
    }

    public float getHeight() {
        return label_.getGlyphLayout().height;
    }

    public int getTextLength() {
        return label_.getText().length();
    }
}
