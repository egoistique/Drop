package info.fandroid.drop.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bucket {

    private Texture texture;
    private Texture bucketImage;
    private Vector3 position;
    private Rectangle bounds;

    public Bucket(int x, int y){
        texture = new Texture("bucket.png");
        position = new Vector3(x, y, 0);
        bounds = new Rectangle(x, y, texture.getWidth(),texture.getHeight());

    }

    public void update(float dt){
        if(Gdx.input.isKeyJustPressed((Input.Keys.LEFT))) position.x -=200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyJustPressed((Input.Keys.RIGHT))) position.x +=200 * Gdx.graphics.getDeltaTime();
        if(position.x <0) position.x = 0;
        if(position.x >800 - 70) position.x = 800-70; //для цеентрирования ведра при нажатии на клавиши
    }

    public Rectangle getBounds(){
        return bounds;
    }

}
