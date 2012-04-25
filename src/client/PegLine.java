package client;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
/*
 * Classe representant une des 10 lignes contenant 4 ronds et un carre de 4 petit rond(résultat) 
 */
public class PegLine extends JPanel{
	
	RespLine respLine = new RespLine();
	Peg[] listePeg = new Peg[4];
	
	PegLine()
	{
		setLayout(new FlowLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
		setVisible(true);
		for(int i =0;i<4;i++)
		{
			listePeg[i]= new Peg();
			add(listePeg[i]);
		}
		add(respLine);
	}
	
	/*
	 * Etage intermédiaire de la méthode appelée lorsque l'on clique sur une couleur
	 */
	void setColor(int numPeg,int color)
	{
		listePeg[numPeg].setColor(color);
	}
	
	/*
	 * Envoie sous la forme d'un string les 4couleurs
	 */
	String getMessage()
	{
		String message = "";
		for(int i =0;i<4;i++)
		{
			message = message + String.valueOf(listePeg[i].getColor());
		}
		return message;
	}
	
	/*
	 * Etape permettant d'apeller afficherReponse de la respLine de la pegline courante
	 */
	boolean afficherReponse(String reponse)
	{
		return respLine.afficherReponse(reponse);
	}
}
