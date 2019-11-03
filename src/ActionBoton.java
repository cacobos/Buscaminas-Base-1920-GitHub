import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Clase que implementa el listener de los botones del Buscaminas.
 * De alguna manera tendr√° que poder acceder a la ventana principal.
 * Se puede lograr pasando en el constructor la referencia a la ventana.
 * Recuerda que desde la ventana, se puede acceder a la variable de tipo ControlJuego
 * @author jesusredondogarcia
 **
 */
public class ActionBoton implements MouseListener{
	private VentanaPrincipal ventanaPrincipal;
	private int i, j;
	

	public ActionBoton(VentanaPrincipal ventanaPrincipal, int i, int j) {
		this.ventanaPrincipal=ventanaPrincipal;
		this.i=i;
		this.j=j;
	}
	
	/**
	 * Acci√≥n que ocurrir·° cuando pulsamos uno de los botones.
	 * Si el click es izquierdo, se abrir· el botÛn. Si no es izquierdo, se colocar· una bandera
	 */
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
			if(arg0.getButton()==MouseEvent.BUTTON1) {			
				ventanaPrincipal.pulsarBoton(i, j);
			}else {
				ventanaPrincipal.colocarBandera(i, j);
			}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
