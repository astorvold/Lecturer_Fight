package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public abstract class Entity {
    protected float x,y;
    protected final float height, width;
    Texture texture;

    public Entity(String texture, float x, float y, float width, float height){
        this.width = width+generateRandomNumber(128,512);
        this.height = height;
        if(x==0) this.x = 0;
        else this.x = x-this.width;
        this.y = y;
        this.texture = new Texture(texture);
    }
    private float generateRandomNumber(int from, int to){
        return (float)Math.floor(Math.random() * (to - from + 1) + from);
    }

    public Texture getTexture() {
        return texture;
    }
    public float getX() { return x; }

    public float getY() { return y; }

    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }

    public float getHeight() { return height; }

    public float getWidth() { return width; }
    public void changePos(int newPos){
        y+=newPos;
    }

    public boolean checkColisions(Entity o){
        float x1 = this.getX();
        float y1 = this.getY();

        float x2 = this.getX() + this.getWidth();
        float y2 = this.getY() + this.getHeight();

        float x3 = o.getX();
        float y3 = o.getY();

        float x4 = o.getX() + o.getWidth();
        float y4 = o.getY() + o.getHeight();

        return ((x1 >= x3 && x1 <= x4) && (y1 >= y3 && y1 <= y4))
                || ((x2 >= x3 && x2 <= x4) && (y2 >= y3 && y2 <= y4))
                || ((x2 >= x3 && x2 <= x4) && (y1 >= y3 && y1 <= y4))
                || ((x1 >= x3 && x1 <= x4) && (y2 >= y3 && y2 <= y4));
    }
}
