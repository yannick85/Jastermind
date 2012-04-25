package client;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
/*
 * Classe representant les 4 petits pegs indiquant les reponses a la combinaison entrée
 */
public class RespLine extends JPanel{
	SmallPeg[] listePeg = new SmallPeg[4];
	
	RespLine()
	{
		setLayout(new GridLayout(2,2));
		setBorder(BorderFactory.createLineBorder(Color.RED));
		setVisible(true);
		for(int i =0;i<4;i++)
		{
			listePeg[i]= new SmallPeg();
			add(listePeg[i]);
		}
	}
	
	/*
	 * En fonction du String reponse envoyer par le serveur , cette classe affiche plus ou moins de ronds blancs et rouges
	 */
	boolean afficherReponse(String reponse)
	{
		Integer nombreBonnePlace = Integer.valueOf(reponse.substring(0,1));
		Integer nombreMauvaisePlace = Integer.valueOf(reponse.substring(1,2));
		
		int nombrePegUtilise=0;
		int i = 0;
		while(i < nombreBonnePlace)
		{
			listePeg[nombrePegUtilise].setColor(0);
			nombrePegUtilise++;
			i++;
		}
		
		i=0;
		while(i < nombreMauvaisePlace)
		{
			listePeg[nombrePegUtilise].setColor(1);
			nombrePegUtilise++;
			i++;
		}
		
		/*
		 * Si nous avons gagné il renvoie true , sinon false
		 */
		if(nombreBonnePlace==4)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}