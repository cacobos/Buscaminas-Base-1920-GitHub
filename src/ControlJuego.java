import java.util.ArrayList;
import java.util.Random;

/**
 * Clase gestora del tablero de juego. Guarda una matriz de enteros representado
 * el tablero. Si hay una mina en una posición guarda el número -1 Si no hay
 * una mina, se guarda cuántas minas hay alrededor. Almacena la puntuación de
 * la partida
 * 
 * @author carloscobos
 * @version 1.0
 * @since nov-2019
 * @see VentanaPrincipal
 *
 */
public class ControlJuego {
	private final static int MINA = -1;
	int minas;
	int ladoTablero;

	private int[][] tablero;
	private boolean[][] abiertas;
	private int puntuacion;
	private int casAbiertas;

	public ControlJuego(int minas, int lado) {
		//Inicializamos una nueva partida
		this.ladoTablero=lado;
		this.minas=minas;
				inicializarPartida();
				depurarTablero();
	}	
	

	/**
	 * Método para generar un nuevo tablero de partida:
	 * 
	 * @pre: La estructura tablero debe existir.
	 * @post: Al final el tablero se habr� inicializado con tantas minas como
	 *        marque la variable MINAS_INICIALES. El resto de posiciones que no son
	 *        minas guardan en el entero cu�ntas minas hay alrededor de la celda
	 */
	public void inicializarPartida() {
		

		tablero = new int[ladoTablero][ladoTablero];
		//TODO: Repartir minas e inicializar puntaci�n. Si hubiese un tablero anterior, lo pongo todo a cero para inicializarlo.
		puntuacion=0;
		casAbiertas=0;
		abiertas=new boolean[ladoTablero][ladoTablero];
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

	/**C�lculo de las minas adjuntas: 
	 * Para calcular el n�mero de minas tenemos que tener en cuenta que no nos salimos nunca del tablero.
	 * Por lo tanto, como mucho la i y la j valdrán LADO_TABLERO-1.
	 * Por lo tanto, como poco la i y la j valdrán 0.
	 * @param i: posición vertical de la casilla a rellenar
	 * @param j: posición horizontal de la casilla a rellenar
	 * @return : El n�mero de minas que hay alrededor de la casilla [i][j]
	 **/
	private int calculoMinasAdjuntas(int f, int c){
		int suma = 0;
		for (int i = (f - 1); i <= (f + 1); i++) {
			for (int j = (c - 1); j <= (c + 1); j++) {
				if (i != f || j != c) {// Evitamos sumar la casilla propia que estamos pasando
					try {
						suma += tablero[i][j] == MINA ? 1 : 0;// Si esa casilla es una mina, sumamos 1. Si no, es 0
					} catch (ArrayIndexOutOfBoundsException e) {
						// Si la casilla no existe porque est� fuera del tablero, no hacemos nada
					}
				}
			}
		}
		return suma;
	}
	
	/**
	 * M�todo que nos permite 
	 * @pre : La casilla nunca debe haber sido abierta antes, no es controlado por el ControlJuego. Por lo tanto siempre sumaremos puntos
	 * @param i: posici�n verticalmente de la casilla a abrir
	 * @param j: posici�n horizontalmente de la casilla a abrir
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
	 * M�todo que checkea si se ha terminado el juego porque se han abierto todas las casillas.
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son minas.
	 **/
	public boolean esFinJuego(){
		int nCasillas=(ladoTablero*ladoTablero)-minas;
		return casAbiertas==nCasillas;
		
	}
	
	
	/**
	 * M�todo que pinta por pantalla toda la informaci�n del tablero, se utiliza para depurar
	 */
	public void depurarTablero(){
		System.out.println("---------TABLERO--------------");
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j]+"\t");
			}
			System.out.println();
		}
	}

	/**
	 * M�todo que se utiliza para obtener las minas que hay alrededor de una celda
	 * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace falta calcularlo, simplemente consultarlo
	 * @param i : posici�n vertical de la celda.
	 * @param j : posici�n horizontal de la cela.
	 * @return Un entero que representa el n�mero de minas alrededor de la celda
	 */
	public int getMinasAlrededor(int i, int j) {
		return calculoMinasAdjuntas(i,j);
	}

	/**
	 * M�todo que devuelve la puntuaci�n actual
	 * @return Un entero con la puntuaci�n actual
	 */
	public int getPuntuacion() {
		return puntuacion;
	}

	/**
	 * Este m�todo se encarga de repartir minas aleatoriamente en el tablero
	 */

	private void repartirMinas() {
		int fila, col;
		for (int i = 0; i < minas; i++) {
			do {
				fila = (int) (Math.random() * ladoTablero);
				col = (int) (Math.random() * ladoTablero);
			} while (esMina(fila, col));
			tablero[fila][col] = MINA;
		}
	}

	/**
	 * M�todo que nos indica si una casilla es mina
	 *
	 * @param i es la fila
	 * @param j es la columna
	 * @return Un boolean que indica si es mina o no
	 */
	private boolean esMina(int i, int j) {
		return tablero[i][j] == MINA;
	}

	/**
	 * M�todo que nos indica si una casilla ha sido abierta previamente
	 * @param i es la fila
	 * @param j es la columna
	 * @return un boolean que indica si ha sido abierta
	 */
	public boolean casillaAbierta(int i,int j) {
		return abiertas[i][j];
	}
}
