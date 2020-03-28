package info.fandroid.drop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen extends State {

//    final Drop game;
//    public static final int WIDTH = 800;
//    public static final int HEIGHT = 480;
//    OrthographicCamera camera;
    private Texture background;
    private Texture playBtn;
    private Texture tapBtn;

    public MainMenuScreen(GameStateManager gsm) {
        super(gsm);
//        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        background = new Texture("bg.jpg");
        playBtn = new Texture("playbtn.png");
        tapBtn = new Texture("tap.png");
    }
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new GameScreen(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(playBtn, 327, 195);
        sb.draw(tapBtn, 242, 100);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("MenuState disposed");
    }
}
