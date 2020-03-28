package info.fandroid.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Array; // добавила сама чтобы устранить ошибку
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;


public class GameScreen implements Screen {
	final Drop game;
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture dropImage;
	Texture brickImage;
	Texture bucketImage;
	Texture brokenImage;
	Texture overImage;
	Sound dropSound;
	Music rainMusic;
	Rectangle bucket;
	Rectangle brokenbc;
	Rectangle brick;
	Vector3 touchPos;
	Array<Rectangle> raindrops;
	Array<Rectangle> bricks;
	long lastDropTime;
	long lastBrickTime;
	int dropsGatchered;

	public GameScreen (final Drop gam) {
		this.game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		batch = new SpriteBatch();

		touchPos = new Vector3();

		dropImage = new Texture("droplet.png");
		bucketImage = new Texture("bucket.png");
		brickImage = new Texture("brick.png");
		brokenImage = new Texture("brokenbc.png");
		overImage = new Texture("gameover.png");

		dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));

		rainMusic.setLooping(true);
		rainMusic.play();

		bucket = new Rectangle();
		bucket.x = 800 / 2 - 70 /2;
		bucket.y = 20;
		bucket.width = 70;
		bucket.height = 70;

		brokenbc = new Rectangle();
		brokenbc.x = 800 / 2 - 64 /2;
		brokenbc.y = 20;
		brokenbc.width = 64;
		brokenbc.height = 64;

		raindrops = new Array<Rectangle>();
		bricks = new Array<Rectangle>();
		spawnRaindrop();
		spawnBrick();
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

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 0.8f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.font.draw(game.batch, "Score: " + dropsGatchered, 10, 470);
		game.batch.draw(bucketImage, bucket.x, bucket.y);

		for(Rectangle raindrop: raindrops){
			game.batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		for(Rectangle brick: bricks){
			game.batch.draw(brickImage, brick.x, brick.y);
		}

		game.batch.end();

		if (Gdx.input.isTouched()){
			touchPos.set(Gdx.input.getX(),Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = (int) (touchPos.x-70 / 2);
		}

		if(Gdx.input.isKeyJustPressed((Input.Keys.LEFT))) bucket.x -=200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyJustPressed((Input.Keys.RIGHT))) bucket.x +=200 * Gdx.graphics.getDeltaTime();

		if(bucket.x <0) bucket.x = 0;
		if(bucket.x >800 - 70) bucket.x = 800-70;

		if(TimeUtils.nanoTime() - lastDropTime > 500000000) spawnRaindrop();
		if(TimeUtils.nanoTime() - lastBrickTime > 1000000000) spawnBrick();

		Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()){
			Rectangle raindrop = iter.next();
			raindrop.y -=  (200 * Gdx.graphics.getDeltaTime());
			if(raindrop.y + 64< 0) iter.remove();
			if (raindrop.overlaps(bucket)){
				dropsGatchered++;
				dropSound.play();
				iter.remove();
			}
		}

		Iterator<Rectangle> iter1 = bricks.iterator();
		while (iter1.hasNext()){
			Rectangle brick = iter1.next();
			brick.y -=  (200 * Gdx.graphics.getDeltaTime());
			if(brick.y + 70< 0) iter1.remove();
			if (brick.overlaps(bucket)){
				//вызывать гейм овер и картинку разбитого ведра
				game.batch.begin();
				game.batch.draw(brokenImage, bucket.x, bucket.y);
				game.batch.draw(overImage,50, 200);
				game.batch.end();
				dropSound.play();
				
				iter1.remove();
				break;
			}
		}

	}

//	public collides(Rectangle brick){
//		game.batch.begin();
//		game.batch.draw(brokenImage, bucket.x, bucket.y);
//		game.batch.draw(overImage,50, 200);
//		game.batch.end();
////		return brick.overlaps(bucket);
//	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose () {
		bucketImage.dispose();
		dropImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		brickImage.dispose();
		overImage.dispose();
		brokenImage.dispose();
	}

	@Override
	public void show() {
		rainMusic.play();
	}
}
