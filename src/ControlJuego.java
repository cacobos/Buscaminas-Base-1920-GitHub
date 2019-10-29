import java.util.ArrayList;
import java.util.Random;

/**
 * Clase gestora del tablero de juego. Guarda una matriz de enteros representado
 * el tablero. Si hay una mina en una posición guarda el número -1 Si no hay
 * una mina, se guarda cuántas minas hay alrededor. Almacena la puntuación de
 * la partida
 * 
 * @author jesusredondogarcia
 *
 */
public class ControlJuego {
	private final static int MINA = -1;
	final int MINAS_INICIALES = 20;
	final int LADO_TABLERO = 10;

	private int[][] tablero;
	private boolean[][] abiertas;
	private int puntuacion;
	private int casAbiertas;

	public ControlJuego() {
		//Inicializamos una nueva partida
				inicializarPartida();
				depurarTablero();
	}	
	

	/**
	 * Método para generar un nuevo tablero de partida:
	 * 
	 * @pre: La estructura tablero debe existir.
	 * @post: Al final el tablero se habrá inicializado con tantas minas como
	 *        marque la variable MINAS_INICIALES. El resto de posiciones que no son
	 *        minas guardan en el entero cuántas minas hay alrededor de la celda
	 */
	public void inicializarPartida() {

		tablero = new int[LADO_TABLERO][LADO_TABLERO];
		//TODO: Repartir minas e inicializar puntaci�n. Si hubiese un tablero anterior, lo pongo todo a cero para inicializarlo.
		puntuacion=0;
		casAbiertas=0;
		abiertas=new boolean[LADO_TABLERO][LADO_TABLERO];
		repartirMinas();
		
		
		//Al final del m�todo hay que guardar el n�mero de minas para las casillas que no son mina:
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				abiertas[i][j]=false;
				if (tablero[i][j] != MINA){
					tablero[i][j] = calculoMinasAdjuntas(i,j);
				}
			}
		}
	}

	/**Cálculo de las minas adjuntas: 
	 * Para calcular el número de minas tenemos que tener en cuenta que no nos salimos nunca del tablero.
	 * Por lo tanto, como mucho la i y la j valdrán LADO_TABLERO-1.
	 * Por lo tanto, como poco la i y la j valdrán 0.
	 * @param i: posición vertical de la casilla a rellenar
	 * @param j: posición horizontal de la casilla a rellenar
	 * @return : El número de minas que hay alrededor de la casilla [i][j]
	 **/
	private int calculoMinasAdjuntas(int f, int c){
		int suma = 0;
		for (int i = (f - 1); i <= (f + 1); i++) {
			for (int j = (c - 1); j <= (c + 1); j++) {
				if (i != f || j != c) {// Evitamos sumar la casilla propia que estamos pasando
					try {
						suma += tablero[i][j] == MINA ? 1 : 0;// Si esa casilla es una mina, sumamos 1. Si no, es 0
					} catch (ArrayIndexOutOfBoundsException e) {
						suma += 0;// Si la casilla no existe, sumamos 0
					}
				}
			}
		}
		return suma;
	}
	
	/**
	 * Método que nos permite 
	 * @pre : La casilla nunca debe haber sido abierta antes, no es controlado por el ControlJuego. Por lo tanto siempre sumaremos puntos
	 * @param i: posición verticalmente de la casilla a abrir
	 * @param j: posición horizontalmente de la casilla a abrir
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j){
		boolean mina=tablero[i][j]==MINA;
		casAbiertas++;
		abiertas[i][j]=true;
		if(mina){
			return false;
		}else{
			puntuacion+=tablero[i][j];
			return true;
		}
	}
	
	
	
	/**
	 * Método que checkea si se ha terminado el juego porque se han abierto todas las casillas.
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son minas.
	 **/
	public boolean esFinJuego(){
		int nCasillas=(int)Math.sqrt(LADO_TABLERO)-MINAS_INICIALES;
		return casAbiertas==nCasillas;
	}
	
	
	/**
	 * Método que pinta por pantalla toda la información del tablero, se utiliza para depurar
	 */
	public void depurarTablero(){
		System.out.println("---------TABLERO--------------");
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println("\nPuntuación: "+puntuacion);
	}

	/**
	 * Método que se utiliza para obtener las minas que hay alrededor de una celda
	 * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace falta calcularlo, símplemente consultarlo
	 * @param i : posición vertical de la celda.
	 * @param j : posición horizontal de la cela.
	 * @return Un entero que representa el número de minas alrededor de la celda
	 */
	public int getMinasAlrededor(int i, int j) {
		return calculoMinasAdjuntas(i,j);
	}

	/**
	 * Método que devuelve la puntuación actual
	 * @return Un entero con la puntuación actual
	 */
	public int getPuntuacion() {
		return puntuacion;
	}

	/**
	 * Este m鴯do se encarga de repartir minas aleatoriamente en el tablero
	 */

	private void repartirMinas() {
		int fila, col;
		for (int i = 0; i < MINAS_INICIALES; i++) {
			do {
				fila = (int) (Math.random() * 10);
				col = (int) (Math.random() * 10);
			} while (esMina(fila, col));
			tablero[fila][col] = MINA;
		}
	}

	/**
	 * M鴯do que nos indica si una casilla es mina
	 *
	 * @param i es la fila
	 * @param j es la columna
	 * @return
	 */
	private boolean esMina(int i, int j) {
		return tablero[i][j] == MINA;
	}


	public boolean casillaAbierta(int i,int j) {
		return abiertas[i][j];
	}


	

}
