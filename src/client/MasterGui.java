package client;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MasterGui extends JFrame{
	
	int maxLigne = 10; //nombre de tentative possible
	PegLine[] lignes = new PegLine[maxLigne];//tableau stockant les lignes de tentatives pour résoudre la combinaison
	int numLigne=0;//numéro de la ligne actuelle
	int numPeg=0;//numero du peg courant dans la ligne actuelle
	JPanel panelChoice = null;//Panel stockant les 8 choix de couleurs possibles
	
	//Constructeur
	MasterGui(){
		setTitle("JasterMind");
		setSize(400, 800);
		setVisible(true);
		//Construction du menu
		JMenuBar menuP=construireJMenuBar();
		setJMenuBar(menuP);
	}
	
	/*
	 * Vide la fenetre
	 */
	void clear()
	{
		for(int i = 0 ; i<maxLigne;i++)
		{
			if(isAncestorOf(lignes[i]))//si la ligne est contenu dans la fenetre
			{
				remove(lignes[i]);// on la retire
			}
		};
		if(isAncestorOf(panelChoice))
		{
			remove(panelChoice);
		}
		//On remet a zéro tous les parametres
		lignes = new PegLine[maxLigne];
		numLigne=0;
		numPeg=0;
		panelChoice = null;
		//Appel au garbage collector pour nettoyer tous les éléments qui n'ont plus de références apres cette méthode
		System.gc();
	}
	
	/*
	 * Initialise tous les éléments pour commencer le jeu
	 */
	void initJeu()
	{
		//on choisit le grid layout pour afficher les éléments sous forme de ligne
		setLayout(new GridLayout(maxLigne+1,1));
		//Initialisation des lignes de tentative
		for(int i = 0 ; i<maxLigne;i++)
		{
			lignes[i] = new PegLine();
			add(lignes[i]);
		}
		//Initialisation de la ligne de choix de la couleur
		panelChoice = new JPanel();
		//A chaque couleur équivaut un int
		for(int i = 0 ; i<8;i++)
		{
			final int j = i;
			Peg peg = new Peg();
			peg.setColor(i);
			peg.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					//Appelée lorsqu'on clique sur une couleur dans le frame 
					setColor(j);
					
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
			});
			panelChoice.add(peg);
		}
		panelChoice.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		add(panelChoice);
		paintAll(getGraphics());
	}
	
	/*
	 * Cet méthode est appelée lorsque l'on choisit une couleur
	 */
	void setColor(int color)
	{
		lignes[maxLigne-1-numLigne].setColor(numPeg,color);
		lignes[maxLigne-1-numLigne].repaint();
		numPeg++;
		if(numPeg==4)
		{
			//Ligne suivante et test de la ligne actuelle
			numPeg=0;
			String message = lignes[maxLigne-1-numLigne].getMessage();//Recuperation en string des couleurs
			String reponse = ClientLauncher.envoyerMessage(message);//on envoie au serveur la combinaison et recuperons son jugement
			if(lignes[maxLigne-1-numLigne].afficherReponse(reponse))//VICTOIRE , YOUPI!
			{
				ClientLauncher.victoire();
			}
			else
			{
				numLigne++;
				if(numLigne==maxLigne)
				{
					ClientLauncher.echec();
					//Fin du jeu : Echec
				}
			}
		}
	}
	
	/*
	 * Construction du MenuBar
	 */
	JMenuBar construireJMenuBar()
	{
		JMenuBar menuP = new JMenuBar();
		JMenu menu = new JMenu("Fichier");
		JMenuItem menuNewGame = new JMenuItem("Nouvelle Partie");
		menuNewGame.addActionListener(new ActionListener()//Action si on clique sur le bouton
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if((JOptionPane.showConfirmDialog(new JFrame(), "Etes vous sur de vouloir recommencer?"))==JOptionPane.OK_OPTION)
				{
					ClientLauncher.nouvellePartie();
				}
			}
		});
		JMenuItem menuExit = new JMenuItem("Quitter");
		menuExit.addActionListener(new ActionListener()//Action si on clique sur le bouton
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if((JOptionPane.showConfirmDialog(new JFrame(), "Etes vous sur de vouloir quitter?"))==JOptionPane.OK_OPTION)
				{
					//Quitter
					ClientLauncher.close();
					System.exit(0);
				}
			}
		});
		menuNewGame.setMnemonic(KeyEvent.VK_F1);//Racourci Clavier
		menuExit.setMnemonic(KeyEvent.VK_ESCAPE);
		menu.setVisible(true);
		menu.add(menuNewGame);
		menu.add(menuExit);
		menuP.add(menu);
		return menuP;
	}
}
