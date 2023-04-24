package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.Texture;

public abstract class Entity {
    protected float x,y;
    protected float height, width;
    Texture texture;

    public Entity(Texture texture){
        this.texture = new Texture(texture.getTextureData());
    }
    protected float generateRandomNumber(int from, int to){
        return (float)Math.floor(Math.random() * (to - from + 1) + from);
    }

    public Texture getTexture() {
        return texture;
    }
    public void setTexture(Texture texture) {this.texture = texture;}

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
    public abstract void changePos(int newPos);

    public void disappear(){
        texture = null;
    }

}
