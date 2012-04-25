package client;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
/*
 * Classe representant un petit peg servant a afficher la reponse du serveur , gris ou rouge
 */
public class SmallPeg extends JPanel{
	
	private Color couleur = Color.BLACK;
	int intCouleur=0;
	
	SmallPeg()
	{
		setVisible(true);
		//setSize(50, 50);
		
		
	}
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(couleur);
        g.fillOval(0, 0, getWidth(), getHeight());
        g.drawOval(0, 0, getWidth(), getHeight());

	}
	
	public void setColor(int num)
	{
		intCouleur=num;
		switch(num)
		{
			case 0 :
				couleur = Color.RED;
				break;
			case 1 :
				couleur = Color.GRAY;
				break;
			default :
				couleur = Color.DARK_GRAY;
				break;
		}
	}
	
	public int getColor()
	{		
		return intCouleur;
	}
}
