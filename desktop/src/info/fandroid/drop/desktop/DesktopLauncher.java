package info.fandroid.drop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import info.fandroid.drop.Drop;
import info.fandroid.drop.GameScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "GameScreen";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new Drop(), config);
	}
}
