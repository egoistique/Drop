package info.fandroid.drop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import info.fandroid.drop.Drop;
import info.fandroid.drop.sprites.Bucket;


public class GameScreen extends State {
	private Vector3 position;

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

	public GameScreen(GameStateManager gsm) {
		super(gsm);


		batch = new SpriteBatch();

//		bucket = new Bucket(400, 10);

		touchPos = new Vector3();
		dropImage = new Texture("droplet.png");
		brickImage = new Texture("brick.png");
		brokenImage = new Texture("brokenbc.png");

		dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));

		rainMusic.setLooping(true);
		rainMusic.play();

		camera.setToOrtho(false, 800, 480);

//		bucket = new Rectangle();
//		bucket.x = 800 / 2 - 70 / 2;
//		bucket.y = 20;
//		bucket.width = 70;
//		bucket.height = 70;
//
//		brokenbc = new Rectangle();
//		brokenbc.x = 800 / 2 - 64 / 2;
//		brokenbc.y = 20;
//		brokenbc.width = 64;
//		brokenbc.height = 64;

		raindrops = new Array<Rectangle>();
		bricks = new Array<Rectangle>();

		spawnBrick();
		spawnRaindrop();

	}

	@Override
	protected void handleInput() {
		if (Gdx.input.isTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = (int) (touchPos.x - 70 / 2);//ведро центрируется под нажатием
		}
	}

	@Override
	public void update(float dt) {
		handleInput();
		camera.update();
//		bucket.update(dt);

		if (TimeUtils.nanoTime() - lastDropTime > 500000000) spawnRaindrop();
		if (TimeUtils.nanoTime() - lastBrickTime > 1000000000) spawnBrick();


		Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= (200 * Gdx.graphics.getDeltaTime());
			if (raindrop.y + 64 < 0) iter.remove();
			if (raindrop.overlaps(bucket)) {
				dropsGatchered++;
				iter.remove();
			}
		}

		Iterator<Rectangle> iter1 = bricks.iterator();
		while (iter1.hasNext()) {
			Rectangle brick = iter1.next();
			brick.y -= (200 * Gdx.graphics.getDeltaTime());
			if (brick.y + 70 < 0) iter1.remove();
			if (brick.overlaps(bucket)) {
				iter1.remove();
				break;
			}
		}
	}
	public void render (SpriteBatch sb){
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
//		font.draw(batch, "Score: " + dropsGatchered, 10, 470);
		sb.draw(bucketImage, bucket.x, bucket.y);
		for (Rectangle raindrop : raindrops) {
			sb.draw(dropImage, raindrop.x, raindrop.y);
		}
		for (Rectangle brick : bricks) {
			sb.draw(brickImage, brick.x, brick.y);
		}
		batch.end();

		if(Gdx.input.isKeyJustPressed((Input.Keys.LEFT))) position.x -=200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyJustPressed((Input.Keys.RIGHT))) position.x +=200 * Gdx.graphics.getDeltaTime();
		if(position.x <0) position.x = 0;
		if(position.x >800 - 70) position.x = 800-70; //для цеентрирования ведра при нажатии на клавиши
	}

	private void spawnRaindrop () {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800 - 64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	private void spawnBrick () {
		Rectangle brick = new Rectangle();
		brick.x = MathUtils.random(0, 800 - 64);
		brick.y = 480;
		brick.width = 64;
		brick.height = 44;
		bricks.add(brick);
		lastBrickTime = TimeUtils.nanoTime();
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
}
