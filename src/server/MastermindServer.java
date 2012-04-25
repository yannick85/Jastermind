package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class MastermindServer implements Runnable{
	private Socket socket;
	private int[] tabReponse;//La combinaison secrète
	private int nombreEssai=0;
	
	
	public MastermindServer(Socket socket) {
		this.socket = socket;
		tabReponse = generateRandArray();
	}

	@Override
	public void run() {
		System.out.println("thread running");
		try {
			PrintWriter printWriter = null;
			InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String message = null;
			String reponse = null;
			Integer[] tabTest = new Integer[4];
			//Le client a le droit a 10 tentatives
			while(nombreEssai<10){
				message=null;
				while(message == null | message == "")
				{
					//On recoit le message
					message = bufferedReader.readLine();
				} 
				System.out.println("message entrant:"+message);
				nombreEssai++;
				for(int i =0;i<4;i++)
				{
					//on transforme le string en tableau
					tabTest[i] = Integer.valueOf(message.substring(i, i+1));
				}
				reponse = verifArray(tabTest);
				System.out.println("message sortant:"+reponse);
				
				try {
					printWriter = new PrintWriter(socket.getOutputStream());
					//on envoie le message
					printWriter.println(reponse);
					printWriter.flush();
				} catch (IOException e) {
					throw new IOException("Impossible to send response to the client.", e);
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	/*
	 * Cet méthode set a generer la combinaison aléatoirement
	 */
	private int[] generateRandArray()
	{
		int[] tab = new int[4];
		int intTemp;
		java.util.Random ran=new java.util.Random( ) ; 
		for(int i =0;i<4;i++)
		{
			intTemp = (ran.nextInt() % 8);
			if(intTemp<0)
			{
				intTemp = - intTemp;
			}
			tab[i]= intTemp;
		}
		return tab;
	}
	
	/*
	 * Cet méthode vérifie la combinaison proposée en la comparant au modèle , renvoie le resultat sous forme de string
	 */
	private String verifArray(Integer[] tabI)//retourne un string de 2 char , le nombre de place + couleur correcte et le nombre de couleur correcte mais place incorrecte
	{
		int[] tabModel = tabReponse.clone();
		Integer bonnePlace=0;
		Integer mauvaisePlace=0;
		String str;
		//test bonne couleur bonne place
		for(int i=0;i<4;i++)
		{
			if((tabI[i] == tabModel[i]) & tabI[i] != -1)
			{
				bonnePlace++;
				tabI[i]=-1;
				tabModel[i]=-1;
			}
		}
		
		//test bonne couleur mauvaise place
		for(int i=0;i<4;i++)
		{	
			for(int j = 0;j<4;j++)
			{
				if((tabI[i] == tabModel[j]) & tabI[i] != -1)
				{
					mauvaisePlace++;
					tabI[i]=-1;
					tabModel[j]=-1;
				}
			}
		}
		str = bonnePlace.toString() + mauvaisePlace.toString();
		return str;
	}
	
}
