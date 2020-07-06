package application;

import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Kümmert sich um ein- und auslesen aller Fragen
 * @author jacmo
 * 
 */
public class EinAuslesenFragen {	

	public List<Frage> einlesenFragen(String fileCSV) {

		List<Frage> fragen = new ArrayList<Frage>();

		String line = "";
		String splitBy = ";";


		try {
			BufferedReader br = new BufferedReader(new FileReader(fileCSV));
			while ((line = br.readLine()) != null) {
				String[] bestandTeilLinie = line.split(splitBy);
				ArrayList<Antwort> antworten = new ArrayList<Antwort>();
				for (int i = 1; i < 4; i++) {

					Antwort a = new Antwort(bestandTeilLinie[i], false);					 
					antworten.add(a);					
				}
				int correctIndex = Integer.parseInt(bestandTeilLinie[4])-1;
				antworten.get(correctIndex).setCorrect(true);

				Frage f = new Frage(bestandTeilLinie[0], antworten);
				fragen.add(f);

			}
			System.out.println("Fragen einlesen länge: " + fragen.size());
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fragen;
	}


}


