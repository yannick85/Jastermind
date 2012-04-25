package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClientLauncher {

	/**
	 * @param args
	 */
	static Socket socket = null;
	static MasterGui masterGui = new MasterGui();
	
	public static void main(String[] args) {
	
	}
	
	/*
	 * remet a zero l'interface graphique , reinitialise le socket
	 */
	public static void nouvellePartie()
	{
		//on vide le gui
		masterGui.clear();
		//fermeture du socket
		close();
		//Recupération de l'ip et du port
		String reponseInput = JOptionPane.showInputDialog("Choisissez le serveur et le port auquel se connecter : ","127.0.0.1:4637");
		Integer port = Integer.parseInt(reponseInput.substring(reponseInput.lastIndexOf(":")+1));
		boolean serverOk=true;
		try {
			//ouverture du socket
			InetAddress serveur = InetAddress.getByName(reponseInput.substring(0, reponseInput.lastIndexOf(":")).toString());
			socket = new Socket(serveur,port);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Impossible d'atteindre l'ip du serveur");
			serverOk=false;
			e.printStackTrace();
		} catch (ConnectException e){
			JOptionPane.showMessageDialog(new JFrame(), "Impossible de se connecter au serveur");
			serverOk=false;
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(serverOk)
		{
			//initialisation du gui
			masterGui.initJeu();
		}
	}
	
	/*
	 * Envoie un message au serveur et attend sa reponse
	 */
	public static String envoyerMessage(String message)
	{
		String reponse = null;
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		try{
			printWriter = new PrintWriter(socket.getOutputStream());
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//On envoie le message
			printWriter.println(message);
			printWriter.flush();
			while(reponse == null | reponse == "")
			{
				//on attend de récupérer le message
				reponse=bufferedReader.readLine();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reponse;
	}
	
	/*
	 * Informe de la victoire et demande si le joueur veut continuer
	 */
	public static void victoire()
	{
		//Affichage d'une boite de dialogue avec oui-non-annuler
		if((JOptionPane.showConfirmDialog(new JFrame(), "VICTOIRE ! Voulez vous continuer?"))==JOptionPane.OK_OPTION)
		{
			nouvellePartie();
		}else
		{
			close();
			System.exit(0);
		}
	}
	
	/*
	 * Informe de la défaite et demande si le joueur veut continuer
	 */
	public static void echec()
	{
		//Affichage d'une boite de dialogue avec oui-non-annuler
		if((JOptionPane.showConfirmDialog(new JFrame(), "ECHEC ! Voulez vous continuer?"))==JOptionPane.OK_OPTION)
		{
			nouvellePartie();
		}else
		{
			close();
			System.exit(0);
		}
	}
	
	/*
	 * ferme le socket si il était ouvert
	 */
	public static void close()
	{
		try {
			if(socket != null)
			{
				socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
