import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class ReproduccionSonido extends Thread{
	String uri;

	public ReproduccionSonido(String uri) {
		this.uri = uri;
	}
	
	/**
	 * Reproduce el sonido cuya ruta le hemos pasado por parámetros
	 */
	public void run() {
		Player apl;
		try {
			apl = new Player(new FileInputStream(uri));			
			apl.play();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
