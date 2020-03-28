package info.fandroid.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;

import info.fandroid.drop.states.GameStateManager;
import info.fandroid.drop.states.MainMenuScreen;

public class Drop extends ApplicationAdapter {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;

    public static final String TITLE = "Drop";

    public BitmapFont font;

    private GameStateManager gsm;
    private SpriteBatch batch;
    private Music music;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        gsm = new GameStateManager();
        music = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();
        gsm.push(new MainMenuScreen(gsm));
//        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
        music.dispose();
        font.dispose();
    }
}
