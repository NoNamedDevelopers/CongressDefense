package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
    SpriteBatch batch;
    Texture img;
    private Sprite sprite;
    OrthographicCamera camera;
    private IsometricTiledMapRenderer mapRenderer;
    private TiledMap map;
    float width, height;

    @Override
    public void resize(int width, int height){
        camera.viewportWidth = width;
        camera.viewportHeight = height;

    }


    @Override
    public void create() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        Gdx.input.setInputProcessor(this);

        batch = new SpriteBatch();
        img = new Texture(Gdx.files.internal("policia_sprite.png"));
        sprite = new Sprite(img);

        map = new TmxMapLoader().load("congress.tmx");
        mapRenderer = new IsometricTiledMapRenderer(map);
        camera = new OrthographicCamera(width, height);
        camera.setToOrtho(false,width,height);

        camera.position.set(width/2, height /2 , 0);
        camera.zoom = 3f;
    }

    @Override
    public void render() {

        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.begin();
        sprite.draw(batch);
        batch.end();
    }


    @Override
    public boolean keyDown(int keycode) {
        return true;
    }


    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.LEFT)
            camera.translate(-32,0);
        if(keycode == Input.Keys.RIGHT)
            System.out.println("Derecha");
            camera.translate(32,0);
        if(keycode == Input.Keys.UP)
            camera.translate(0,-32);
        if(keycode == Input.Keys.DOWN)
            camera.translate(0,32);

        camera.update();
        System.out.println("X:"+camera.position.x +"Y:"+camera.position.y);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
