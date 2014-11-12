package com.nonameddevelopers.congressdefense.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ImageActor extends Actor {
	
	private Texture image;
	private TextureRegion texture;
	
	public ImageActor(String src) {
		image = new Texture(Gdx.files.internal(src));
		texture = new TextureRegion(image, image.getWidth(), image.getHeight());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(texture, getX(), getY(), 
				   getOriginX(), getOriginY(),
				   getWidth(), getHeight(), 
				   getScaleX(), getScaleY(),
				   getRotation());
	}
	
	
	public void dispose() {
		image.dispose();
	}

}
