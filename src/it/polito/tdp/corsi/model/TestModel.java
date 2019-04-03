package it.polito.tdp.corsi.model;

import java.util.Map;
import java.util.Map.Entry;

public class TestModel {

	public void run() {
		GestoreCorsi model = new GestoreCorsi();
		
		Map<String,Integer> risultato = model.getIscrittiCorsi(1);
		
//		ENTRY ritorna ad ogni giro del ciclo la coppia chiave valore 
//		piu' comoda rispetto a ciclare sulle chiavi e poi prendere i valori
		for(Entry e : risultato.entrySet()) {
			System.out.println(e.getKey()+"="+e.getValue());
			
		}
		
	}
	public static void main(String[] args) {
		TestModel main= new TestModel();
		main.run();

	}

}
