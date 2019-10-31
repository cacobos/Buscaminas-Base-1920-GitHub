import java.awt.Button;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class VentanaPrincipal {

	// La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;
	JPanel panelImagen;
	JPanel panelEmpezar;
	JPanel panelPuntuacion;
	JPanel panelJuego;

	// Todos los botones se meten en un panel independiente.
	// Hacemos esto para que podamos cambiar despu√©s los componentes por otros
	JPanel[][] panelesJuego;
	JButton[][] botonesJuego;

	// Correspondencia de colores para las minas:
	Color correspondenciaColores[] = { Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.RED,
			Color.RED, Color.RED, Color.RED, Color.RED };

	JButton botonEmpezar;
	JTextField pantallaPuntuacion;	
	ImageIcon bandera;

	// LA VENTANA GUARDA UN CONTROL DE JUEGO:
	ControlJuego juego;
	
	/*JMenuBar barra;
	JMenu menuUsuario;
	JMenu menuNivel;
	JMenuItem itemElegirUsuario;
	JMenuItem itemNivelFacil;
	JMenuItem itemNivelMedio;
	JMenuItem itemNivelDificil;
	JMenuItem[] itemsNiveles={itemNivelFacil, itemNivelMedio, itemNivelDificil};
	
	
	int nivel;
	final static int NIVEL_FACIL=1;
	final static int NIVEL_MEDIO=2;
	final static int NIVEL_DIFÕCIL=3;*/
	
	

	// Constructor, marca el tama√±o y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 100, 700, 500);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		juego = new ControlJuego();
	}

	// Inicializa todos los componentes del frame
	public void inicializarComponentes() {

		// Definimos el layout:
		ventana.setLayout(new GridBagLayout());

		// Inicializamos componentes
		panelImagen = new JPanel();
		panelEmpezar = new JPanel();
		panelEmpezar.setLayout(new GridLayout(1, 1));
		panelPuntuacion = new JPanel();
		panelPuntuacion.setLayout(new GridLayout(1, 1));
		panelJuego = new JPanel();
		panelJuego.setLayout(new GridLayout(10, 10));

		botonEmpezar = new JButton("Go!");
		pantallaPuntuacion = new JTextField("0");
		pantallaPuntuacion.setEditable(false);
		pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);

		// Bordes y colores:
		panelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		panelPuntuacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));

		// Colocamos los componentes:
		// AZUL
		GridBagConstraints settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelImagen, settings);
		// VERDE
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelEmpezar, settings);
		// AMARILLO
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelPuntuacion, settings);
		// ROJO
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.weightx = 1;
		settings.weighty = 10;
		settings.gridwidth = 3;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelJuego, settings);

		// Paneles
		panelesJuego = new JPanel[10][10];
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1, 1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}

		// Botones
		botonesJuego = new JButton[10][10];
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j] = new JButton("-");
				botonesJuego[i][j].setEnabled(false);
				panelesJuego[i][j].add(botonesJuego[i][j]);
			}
		}

		// Bot√≥nEmpezar:
		panelEmpezar.add(botonEmpezar);
		panelPuntuacion.add(pantallaPuntuacion);
		
		//Icono para bandera
		bandera = new ImageIcon("bandera.png");
		
		
		

	}

	/**
	 * M√©todo que inicializa todos los l√≠steners que necesita inicialmente el
	 * programa
	 */
	public void inicializarListeners() {
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j].addMouseListener(new ActionBoton(this, i, j));
			}
		}
		
		
		
		botonEmpezar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				activarBotones();
				botonEmpezar.setEnabled(false);
			}
		});
		
		
	}

	/**
	 * Pinta en la pantalla el n√∫mero de minas que hay alrededor de la celda Saca
	 * el bot√≥n que haya en la celda determinada y a√±ade un JLabel centrado y no
	 * editable con el n√∫mero de minas alrededor. Se pinta el color del texto
	 * seg√∫n la siguiente correspondecia (consultar la variable
	 * correspondeciaColor): - 0 : negro - 1 : cyan - 2 : verde - 3 : naranja - 4 √≥
	 * m√°s : rojo
	 * 
	 * @param i: posici√≥n vertical de la celda.
	 * @param j: posici√≥n horizontal de la celda.
	 */
	public void mostrarNumMinasAlrededor(int i, int j) {
		if (botonesJuego[i][j].getParent() == panelesJuego[i][j]) {
			panelesJuego[i][j].remove(botonesJuego[i][j]);
			int n = juego.getMinasAlrededor(i, j);
			JLabel label = new JLabel(Integer.toString(n));
			label.setForeground(correspondenciaColores[n]);
			label.setHorizontalAlignment(JLabel.CENTER);
			panelesJuego[i][j].add(label);
			refrescarPantalla();
		}

	}

	/**
	 * Muestra una ventana que indica el fin del juego
	 * 
	 * @param porExplosion : Un booleano que indica si es final del juego porque ha
	 *                     explotado una mina (true) o bien porque hemos desactivado
	 *                     todas (false)
	 * @post : Todos los botones se desactivan excepto el de volver a iniciar el
	 *       juego.
	 */
	public void mostrarFinJuego(boolean porExplosion) {
		reproducirSonido("./sonidos/victoria.mp3");
		String[] mensajes = { "Volver a jugar", "Salir" };
		String victoria;
		if (porExplosion) {
			victoria = "Has perdido";
			reproducirSonido("./sonidos/derrota.mp3");
			
		} else {
			victoria = "Enhorabuena, has ganado";
			reproducirSonido("victoria.mp3");
		}
		JOptionPane.showMessageDialog(ventana, victoria);
		int opcion = JOptionPane.showConfirmDialog(ventana, "øQuieres volver a jugar?", "Fin de la partida",
				JOptionPane.YES_NO_OPTION);
		switch (opcion) {
		case 0:
			resetearJuego();
			actualizarPuntuacion();
			break;
		case 1:
			ventana.dispose();

		}
	}

	public void resetearJuego() {
		panelJuego.removeAll();
		juego = new ControlJuego();
		// Paneles
		panelesJuego = new JPanel[10][10];
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1, 1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}

		// Botones
		botonesJuego = new JButton[10][10];
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j] = new JButton("-");
				botonesJuego[i][j].setEnabled(false);
				panelesJuego[i][j].add(botonesJuego[i][j]);
			}
		}

		inicializarListeners();

		refrescarPantalla();
	}

	public void activarBotones() {
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j].setEnabled(true);
			}
		}
	}

	/**
	 * M√©todo que muestra la puntuaci√≥n por pantalla.
	 */
	public void actualizarPuntuacion() {
		pantallaPuntuacion.setText(Integer.toString(juego.getPuntuacion()));
	}

	/**
	 * M√©todo para refrescar la pantalla
	 */
	public void refrescarPantalla() {
		ventana.revalidate();
		ventana.repaint();
	}

	/**
	 * M√©todo que devuelve el control del juego de una ventana
	 * 
	 * @return un ControlJuego con el control del juego de la ventana
	 */
	public ControlJuego getJuego() {
		return juego;
	}

	/**
	 * M√©todo para inicializar el programa
	 */
	public void inicializar() {
		// IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS
		// COMPONENTES.
		ventana.setVisible(true);
		inicializarComponentes();
		inicializarListeners();
	}

	/**
	 * Llama al mÈtodo pulsarBoton(a,b) pasando por par·metros las casillas adyacentes a la que nos
	 * ha llegado por par·metros 
	 * @param i es la fila de la casilla cuyas adyacentes queremos abrir
	 * @param j es la columna de la casilla cuyas adyacentes queremos abrir
	 */
	public void abrirAdyacentes(int i, int j) {
		//Creamos una matriz de 3x3 "rodeando" a la casilla de origen y abrimos todas menos la central
		//Si quedan fuera del tablero, controlamos la excepciÛn IndexOutOfBounds
		for (int a = i - 1; a <= i + 1; a++) {
			for (int b = j - 1; b <= j + 1; b++) {
				if (a != i || b != j) {
					try {
						if (!juego.casillaAbierta(a, b)) {//Controlamos que no haya sido sido abierta antes
							pulsarBoton(a, b);
						}
					} catch (IndexOutOfBoundsException e) {
						// No hacemos nada
					}
				}
			}
		}
	}

	/**
	 * Si el control de juego nos dice que ha terminado la partida, llamamos a mostrarFinJuego()
	 * Si no, abrimos la casilla llamando a mostrarMinasAlrededor(). Si es un 0, tambiÈn se abren las adyacentes
	 * @param i es la fila del botÛn que se ha pulsado
	 * @param j es la columna del botÛn que se ha pulsado
	 */
	public void pulsarBoton(int i, int j) {
		boolean fin = juego.abrirCasilla(i, j);
		if (fin) {
			if (juego.esFinJuego()) {
				mostrarFinJuego(false);
			} else {
				mostrarNumMinasAlrededor(i, j);
			}
			if (juego.getMinasAlrededor(i, j) == 0) {
				abrirAdyacentes(i, j);
			}
			actualizarPuntuacion();
		} else {
			mostrarFinJuego(true);
		}
	}
	
	
	/**
	 * Si hay un botÛn en el panel, lo elimina y coloca un label con una bandera
	 * Si hay un label con una bandera, coloca el botÛn que le corresponde al panel
	 * @param i es la fila del panel que queremos modificar
	 * @param j es la columna del panel que queremos modificar
	 */
	public void colocarBandera(int i, int j) {
		if (botonesJuego[i][j].getIcon() == bandera) {
			botonesJuego[i][j].setIcon(null);
			botonesJuego[i][j].setText("-");
		} else {
			botonesJuego[i][j].setIcon(bandera);
			botonesJuego[i][j].setText("");
		}

		refrescarPantalla();
	}	
	

	/**
	 * Crea un objeto ReproduccionSonido e inicia una reproducciÛn
	 * @param sonido es la ruta del archivo que queremos reproducir
	 */
	public void reproducirSonido(String sonido) {
		ReproduccionSonido rs=new ReproduccionSonido(sonido);
		rs.start();	      
	}
}
