package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
/*
 * Cet classe représente un rond , on lui envoie ou on récupere sa couleur sous forme de Int 
 * les vrais couleurs ne sont gérés que dans cette classe
 */
public class Peg extends JPanel{
	private Color couleur = Color.BLACK;
	int intCouleur=0;
	
	Peg()
	{
		setVisible(true);
		setPreferredSize(new Dimension(25,25));
	}
	//Methode affichant le panel
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(couleur);
        g.fillOval(0, 0, getWidth(), getHeight());
        g.drawOval(0, 0, getWidth(), getHeight());

	}
	
	/*
	 * Dernier etage de la methode declenchée lorsque l'on clique sur une couleur
	 */
	public void setColor(int num)
	{
		intCouleur=num;
		switch(num)
		{
			case 0 :
				couleur = Color.RED;
				break;
			case 1 :
				couleur = Color.YELLOW;
				break;
			case 2 :
				couleur = Color.GREEN;
				break;
			case 3 :
				couleur = Color.BLUE;
				break;
			case 4 :
				couleur = Color.ORANGE;
				break;
			case 5 :
				couleur = Color.GRAY;
				break;
			case 6 :
				couleur = Color.PINK;
				break;
			case 7 :
				couleur = Color.CYAN;
				break;
			default :
				couleur = Color.WHITE;
				break;
		}
	}
	/*
	 * Retourne la couleur
	 */
	public int getColor()
	{		
		return intCouleur;
	}
	
}
