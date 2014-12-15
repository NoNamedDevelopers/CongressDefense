package com.nonameddevelopers.congressdefense.gameItems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.CopManager;
import com.nonameddevelopers.congressdefense.screens.GameScreen;
import com.nonameddevelopers.congressdefense.screens.TutorialScreen;
import com.nonameddevelopers.congressdefense.ui.CopIcon;

public class CopSetter {

	private static final float RADIUS = 200;
	
	private static CopManager copManager;
	private CongressDefense game;
	private float x;
	private float y;
	private float stateTime;
	private float scale;
	
	private static Texture backgroundTexture;
	private Sprite backgroundSprite;
	
	private Circle circle;
	
	private float currentRadius;
	
	private Array<CopIcon> icons;
	
	static {
		backgroundTexture = new Texture(Gdx.files.internal("ui/copsetter.png"));
	}
	
	public CopSetter(CongressDefense game, float x, float y) {
		this.game = game;
		this.x = x;	
		this.y = y;
		this.scale = 1f;
		currentRadius = 1f;
		backgroundSprite = new Sprite(backgroundTexture);
		circle = new Circle();
		icons = new Array<CopIcon>();

		icons.add(new CopIcon(game, this, 60, x, y, "ui/meleecopicon.png", CopIcon.MELEE, 1));
		icons.add(new CopIcon(game, this, 180, x, y, "ui/bazookacopicon.png", CopIcon.BAZOOKA, 2));
		icons.add(new CopIcon(game, this, 300, x, y, "ui/swaticon.png", CopIcon.SWAT, 3));
		icons.add(new CopIcon(game, this, 800, x, y, "ui/bombicon.png", CopIcon.BOMB, 4));
	}
	
	public void update(float delta, GameCamera camera) {
		setScale(camera.zoom);
		stateTime += delta *2;
		if (currentRadius< RADIUS) {
			currentRadius += delta*600;
		}
		else {
			currentRadius = RADIUS;
		}
		backgroundSprite.setOrigin(currentRadius*scale, currentRadius*scale);
		
		circle.set(x, y, (currentRadius+CopIcon.RADIUS/2)*scale);
		backgroundSprite.rotate(MathUtils.sin(stateTime));
		backgroundSprite.setAlpha(MathUtils.clamp(Math.abs(MathUtils.sin(stateTime)), 0.4f, 1f));
		backgroundSprite.setSize(currentRadius*2*scale, currentRadius*2*scale);
		backgroundSprite.setCenter(x, y);
		
		for (CopIcon icon : icons) {
			icon.update(delta, currentRadius);
		}
	}
	
	public void draw(SpriteBatch batch) {
		backgroundSprite.draw(batch);
		for (CopIcon icon : icons) {
			icon.draw(batch);
		}
	}
	
	public void click(float x, float y) {
		if (circle.contains(x,y)) {
			for (CopIcon icon : icons) {
				icon.click(x, y);
			}
		}
		else {
			release();
		}
	}
	
	public void release() {
		if (game.getScreen() instanceof GameScreen) {
			((GameScreen) game.getScreen()).copSetter = null;
		}
		if (game.getScreen() instanceof TutorialScreen) {
			((TutorialScreen) game.getScreen()).copSetter = null;
			if (copManager.getCops().size()>0) {
				((TutorialScreen) game.getScreen()).nextTip();
			}
		}
	}
	
	public static void setCopManager(CopManager copManager) {
		CopSetter.copManager = copManager;
	}
	
	public CopManager getCopManager() {
		return copManager;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
		for (CopIcon icon : icons) {
			icon.setScale(scale);
		}
	}
}
