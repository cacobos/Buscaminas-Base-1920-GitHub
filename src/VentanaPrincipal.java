import java.awt.Button;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
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

/**
 * Clase que controla la ventana principal del programa. Coloca los paneles y botones del juego y los inicializa con sus listeners
 * @see MÈtosdos inicializarComponentes() e inicializarListeners()
 * {@link inicializar()}
 * @author carloscobos
 *
 */

public class VentanaPrincipal {
	int ladoTablero;
	int minas;

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
	ImageIcon mina;
	

	// LA VENTANA GUARDA UN CONTROL DE JUEGO:
	ControlJuego juego;
	
	JMenuBar menu;
	JMenu opciones;
	JMenuItem salir;

	// Constructor, marca el tama√±o y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 100, 700, 500);
		ventana.setLocationRelativeTo(null);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pedirNivel();
		juego = new ControlJuego(minas, ladoTablero);
				
		
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
		panelJuego.setLayout(new GridLayout(ladoTablero, ladoTablero));

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
		panelesJuego = new JPanel[ladoTablero][ladoTablero];
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1, 1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}

		// Botones
		botonesJuego = new JButton[ladoTablero][ladoTablero];
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

		// Icono para bandera
		bandera = new ImageIcon("bandera.png");
		mina=new ImageIcon("mina.png");
		
		//Men√∫ y sus √≠tems
		menu=new JMenuBar();
		opciones=new JMenu("Opciones");
		salir=new JMenuItem("Salir");
		
		ventana.setJMenuBar(menu);
		menu.add(opciones);
		menu.add(salir);
		opciones.add(salir);
		
	}

	/**
	 * MÈtodo que inicializa todos los listeners que necesita inicialmente el
	 * programa
	 */
	public void inicializarListeners() {
		//Los botones de juego ttienen un ActionBoton como listener
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j].addMouseListener(new ActionBoton(this, i, j));
			}
		}

		//El botÛn empezar activar· el resto de botones
		botonEmpezar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				activarBotones();
				botonEmpezar.setEnabled(false);
			}
		});
		
		//El botÛn salir (del men˙) cerrar· la ventana
		salir.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ventana.dispose();
				
			}
		});
	}

	/**
	 * Pinta en la pantalla el n√∫mero de minas que hay alrededor de la celda Saca
	 * el botÛn que haya en la celda determinada y a√±ade un JLabel centrado y no
	 * editable con el n√∫mero de minas alrededor. Se pinta el color del texto
	 * seg˙n la siguiente correspondecia (consultar la variable
	 * correspondeciaColor): - 0 : negro - 1 : cyan - 2 : verde - 3 : naranja - 4 
	 *  : rojo
	 * 
	 * @param i: posiciÛn vertical de la celda.
	 * @param j: posiciÛn horizontal de la celda.
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
	 * @post : Muestra un mensaje y un sonido diferente en funciÛn de si hemos ganado o perdido.
	 * Da la opciÛn de volver a jugar o cerrar el programa
	 */
	public void mostrarFinJuego(boolean porExplosion) {
		String[] mensajes = { "Volver a jugar", "Salir" };
		String victoria;

		abrirTablero();
		if (porExplosion) {
			victoria = "Has perdido";
			reproducirSonido("./sonidos/derrota.mp3");

		} else {
			victoria = "Enhorabuena, has ganado";
			reproducirSonido("./sonidos/victoria.mp3");
		}
		JOptionPane.showMessageDialog(ventana, victoria);
		int opcion = JOptionPane.showConfirmDialog(ventana, "ÔøQuieres volver a jugar?", "Fin de la partida",
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
	/**
	 * Muestra una ventana emergente que da a elegir al usuario entre nivel f·cil, intermedio o difÌcil
	 */
	private void pedirNivel() {
		String[] opciones= {"F·cil", "Intermedio", "DifÌcil"};
		int opc = JOptionPane.showOptionDialog(null, "Elige el nivel de dificultad",
                "Nivel",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
        switch (opc) {
		case 0:
			ladoTablero=8;
			minas=10;
			ventana.setBounds(100,100,700,400);
			break;
		case 1:
			ladoTablero=16;
			minas=40;
			ventana.setBounds(100,100,900,500);
			break;
		case 2:
			ladoTablero=20;
			minas=99;
			ventana.setBounds(100,100,1050,600);
			break;
		default:
			ladoTablero=8;
			minas=10;
			ventana.setBounds(100,100,700,400);
			break;
        }
	}
	/**
	 * VacÌa el panel de juego y vuelve a colocar nuevos paneles, nuevos botones y habilita el botÛn de iniciar juego
	 */
	public void resetearJuego() {
		panelJuego.removeAll();	
		pedirNivel();
		juego = new ControlJuego(minas, ladoTablero);
		// Paneles
		panelesJuego = new JPanel[ladoTablero][ladoTablero];
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1, 1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}

		// Botones
		botonesJuego = new JButton[ladoTablero][ladoTablero];
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j] = new JButton("-");
				botonesJuego[i][j].setEnabled(false);
				panelesJuego[i][j].add(botonesJuego[i][j]);
			}
		}
		
		botonEmpezar.setEnabled(true);

		inicializarListeners();

		refrescarPantalla();
	}
	
	/**
	 * Muestra la informaciÛn de todos los botones: si son minas o el n˙mero de minas alrededor	
	 */
	public void abrirTablero() {
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {
				if (botonesJuego[i][j].getParent() == panelesJuego[i][j]) {
					panelesJuego[i][j].remove(botonesJuego[i][j]);
					if(juego.abrirCasilla(i, j)) {						
						int n = juego.getMinasAlrededor(i, j);
						JLabel label = new JLabel(Integer.toString(n));
						label.setForeground(correspondenciaColores[n]);	
						label.setHorizontalAlignment(JLabel.CENTER);
						panelesJuego[i][j].add(label);
					}else {
						JLabel label = new JLabel();
						panelesJuego[i][j].add(label);
						Image img=mina.getImage();
						ImageIcon redim=new ImageIcon(img.getScaledInstance(10,  10,Image.SCALE_SMOOTH));
											
						label.setIcon(redim);
						label.setHorizontalAlignment(JLabel.CENTER);
					}	
				}
			}
		}
		refrescarPantalla();
	}

	/**
	 * Activa todos los botones de juego
	 */
	public void activarBotones() {
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j].setEnabled(true);
			}
		}
	}

	/**
	 * MÈtodo que muestra la puntuaciÛn por pantalla.
	 */
	public void actualizarPuntuacion() {
		pantallaPuntuacion.setText(Integer.toString(juego.getPuntuacion()));
	}

	/**
	 * MÈtodo para refrescar la pantalla
	 */
	public void refrescarPantalla() {
		ventana.revalidate();
		ventana.repaint();
	}

	/**
	 * MÈtodo que devuelve el control del juego de una ventana
	 * 
	 * @return un ControlJuego con el control del juego de la ventana
	 */
	public ControlJuego getJuego() {
		return juego;
	}

	/**
	 * MÈtodo para inicializar el programa
	 * {@code public void inicializar() {
		// IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS
		// COMPONENTES.
		ventana.setVisible(true);
		inicializarComponentes();
		inicializarListeners();
	}}
	 */
	public void inicializar() {
		// IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS
		// COMPONENTES.
		ventana.setVisible(true);
		inicializarComponentes();
		inicializarListeners();
	}

	/**
	 * Llama al mÈtodo pulsarBoton(a,b) pasando por par·metros las casillas
	 * adyacentes a la que nos ha llegado por parÔøΩmetros
	 * 
	 * @param i es la fila de la casilla cuyas adyacentes queremos abrir
	 * @param j es la columna de la casilla cuyas adyacentes queremos abrir
	 */
	public void abrirAdyacentes(int i, int j) {
		// Creamos una matriz de 3x3 "rodeando" a la casilla de origen y abrimos todas
		// menos la central
		// Si quedan fuera del tablero, controlamos la excepciÔøΩn IndexOutOfBounds
		for (int a = i - 1; a <= i + 1; a++) {
			for (int b = j - 1; b <= j + 1; b++) {
				if (a != i || b != j) {
					try {
						if (!juego.casillaAbierta(a, b)) {// Controlamos que no haya sido sido abierta antes
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
	 * Si el control de juego nos dice que ha terminado la partida, llamamos a
	 * mostrarFinJuego() Si no, abrimos la casilla llamando a
	 * mostrarMinasAlrededor(). Si es un 0, tambiÔøΩn se abren las adyacentes
	 * 
	 * @param i es la fila del botÔøΩn que se ha pulsado
	 * @param j es la columna del botÔøΩn que se ha pulsado
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
	 * Si hay un botÔøΩn en el panel, lo elimina y coloca un label con una bandera Si
	 * hay un label con una bandera, coloca el botÔøΩn que le corresponde al panel
	 * 
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
	 * 
	 * @param sonido es la ruta del archivo que queremos reproducir
	 */
	public void reproducirSonido(String sonido) {
		ReproduccionSonido rs = new ReproduccionSonido(sonido);
		rs.start();
	}

}
