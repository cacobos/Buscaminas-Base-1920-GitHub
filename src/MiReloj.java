

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MiReloj extends JPanel implements Runnable {
	private JLabel contador;
	double tiempoTranscurrido;
	private double tiempoOriginal;
	private Thread hilo = null;
	private int segundos;
	private int minutos;
	private boolean pausa;
	private Font font;

	public MiReloj() {
		super();
		inicializarComponentes();
	}

	public void inicializarComponentes() {
		font=new Font("Verdana", Font.BOLD, 48);
		minutos = 0;
		segundos = 0;
		contador = new JLabel(String.format("%02d", minutos) + ":" + String.format("%02d", segundos));
		contador.setBackground(Color.BLUE);
		setLayout(new GridLayout(1,1));
		contador.setHorizontalAlignment(JLabel.CENTER);
		contador.setFont(font);
		add(contador);
	}

	public void comenzar() {
		if(pausa) {
			pausa = false;
			hilo.interrupt();
			hilo=null;
		}
		if (hilo == null) {
			hilo = new Thread(this);
			hilo.start();
		}

	}

	public void parar() {
		pausa = true;
	}

	public void resetear() {
		segundos=0;
		minutos=0;
		tiempoOriginal = System.currentTimeMillis();
		actualizarPantalla();
	}

	@Override
	public void run() {
		tiempoOriginal = System.currentTimeMillis();
		actualizarPantalla();
		while (true) {
			try {
				//System.out.println("duermo");
				hilo.sleep(1000);
				synchronized (this) {
					if (pausa) {
						//System.out.println("Paused");
						wait();
						//System.out.println("Resumed");
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("calcular tiempo");
			calcularTiempoTranscurrido();
			//System.out.println("actualizar pantalla");
			actualizarPantalla();
		}
	}

	private void calcularTiempoTranscurrido() {
		tiempoTranscurrido = (System.currentTimeMillis() - tiempoOriginal) / 1000;
		segundos = (int) tiempoTranscurrido;
		minutos = segundos / 60;
		segundos = segundos % 60;
	}

	private void actualizarPantalla() {
		contador.setText(String.format("%02d", minutos) + ":" + String.format("%02d", segundos));
	}
	
	public void setFontSize(int size) {
		font=new Font("Verdana", Font.BOLD, size);
		contador.setFont(font);
	}
}
