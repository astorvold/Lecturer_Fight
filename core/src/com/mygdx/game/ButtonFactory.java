package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonFactory {
    public static Image createButton(Texture texture, float x, float y, float width, float height){
        Image aux = new Image(texture);
        aux.setX(x);
        aux.setY(y);
        aux.setWidth(width);
        aux.setHeight(height);
        /*ImageButton b = new ImageButton(new TextureRegionDrawable( new TextureRegion( texture)));
        b.setBounds(x,y,width,height);*/
        return aux;
    }
}
