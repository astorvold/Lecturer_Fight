package com.mygdx.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Model.Entity;
import com.mygdx.game.Model.Player;

public class PlayerController {

    private final Player model;

    public PlayerController(Player model) {
        this.model = model;
    }

    public void move(){
        if(Gdx.input.getX() >= Gdx.graphics.getWidth()/2) {
            if (model.getX() < Gdx.graphics.getWidth() - model.getWidth())
                model.changePos(10);
            else model.changePos(-10);
        }
        else {
            if (model.getX() > 0) model.changePos(-10);
            else model.changePos(10);
        }
    }
    public void setReady(boolean ready){
        model.setReady(ready);
    }
    public void increaseScore(int score){
        model.increaseScore(score);
    }

    public boolean checkColisions(Entity o){
        float x1 = model.getX();
        float y1 = model.getY();

        float x2 = model.getX() + model.getWidth();
        float y2 = model.getY() + model.getHeight();

        float x3 = o.getX();
        float y3 = o.getY();

        float x4 = o.getX() + o.getWidth();
        float y4 = o.getY() + o.getHeight();

        return      ((x1 >= x3 && x1 <= x4) && (y1 >= y3 && y1 <= y4))
                || ((x2 >= x3 && x2 <= x4) && (y2 >= y3 && y2 <= y4))
                || ((x2 >= x3 && x2 <= x4) && (y1 >= y3 && y1 <= y4))
                || ((x1 >= x3 && x1 <= x4) && (y2 >= y3 && y2 <= y4))
                || ((x1 <= x3 && x3 <= x2) && (y1 <= y3 && y3 <= y2));
    }
    public void setX(float x){
        model.setX(x);
    }
    public void setY(float y){model.setY(y);}
    public void setTexture(Texture texture){model.setTexture(texture);}
    public void setBusy(){model.setBusy();}


}
