package info.fandroid.drop.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import info.fandroid.drop.states.MainMenuScreen;

public class Items {

    private Texture rainrop, brick;
    public Texture brickImage, brokenImage;

    Array<Rectangle> raindrops;
    Array<Rectangle> bricks;
    long lastDropTime;
    long lastBrickTime;
    int dropsGatchered;

    public Items(){

        brickImage = new Texture("brick.png");
        brokenImage = new Texture("brokenbc.png");

        raindrops = new Array<Rectangle>();
        bricks = new Array<Rectangle>();

        if(TimeUtils.nanoTime() - lastDropTime > 500000000) spawnRaindrop();
        if(TimeUtils.nanoTime() - lastBrickTime > 1000000000) spawnBrick();
        spawnBrick();
        spawnRaindrop();

    }

    private void spawnRaindrop(){
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800-64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    private void spawnBrick(){
        Rectangle brick = new Rectangle();
        brick.x = MathUtils.random(0, 800-64);
        brick.y = 480;
        brick.width = 64;
        brick.height = 44;
        bricks.add(brick);
        lastBrickTime = TimeUtils.nanoTime();
    }

}

