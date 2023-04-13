package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonFactory {
    public static ImageButton createButton(Texture texture, float x, float y, float width, float height){
        ImageButton b = new ImageButton(new TextureRegionDrawable( new TextureRegion( texture)));
        b.setBounds(x,y,width,height);
        return b;
    }
}
