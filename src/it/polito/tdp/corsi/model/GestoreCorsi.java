package it.polito.tdp.corsi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;
import it.polito.tdp.corsi.db.StudenteDAO;
import it.polito.tdp.corsi.model.Corso;

public class GestoreCorsi {

	public List<Corso> getCorsiByPeriodo(int periodo) {
		CorsoDAO dao = new CorsoDAO();
		
		//Soluzione 1
		/*List<Corso> corsi = dao.listAll();
		List<Corso> result = new ArrayList<Corso>();
		
		for(Corso c : corsi) {
			if(c.getPd() == periodo) {
				result.add(c);
			}
		}*/
		
		//Soluzione 2
		return dao.listCorsiByPD(periodo);
	}

	public Map<String, Integer> getIscrittiCorsi(int periodo){
		CorsoDAO dao= new CorsoDAO();
//		APPROCCIO TOP-DOWN:
//		dopo aver creato il dao, supponiamo di avere nella classe dao relativa
//		un metodo (magari dello stesso nome) a cui delegare le operazioni da compiere
		return dao.getIscrittiCorsi(periodo);
	}

	public List<Studente> elencaStudenti(String codins) {
//		DELEGHIAMO AL DAO creandone un'istanza e richiamando un metodo dello stesso nome a questo che andremo a creare
		StudenteDAO dao = new StudenteDAO();
		return dao.elencaStudenti(codins);
	}
}
