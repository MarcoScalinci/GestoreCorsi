package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;

public class CorsoDAO {

	public List<Corso> listAll() {
		String sql = "SELECT * FROM corso";
		List<Corso> result = new LinkedList<Corso>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
		
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"),
						rs.getInt("crediti"),
						rs.getString("nome"),
						rs.getInt("pd"));
				
				result.add(c);
			}
			
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
		return result;
	}

	public List<Corso> listCorsiByPD(int periodo) {
		String sql = "SELECT * FROM corso WHERE pd = ?";
		List<Corso> result = new LinkedList<Corso>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"),
						rs.getInt("crediti"),
						rs.getString("nome"),
						rs.getInt("pd"));
				
				result.add(c);
			}
			
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
		return result;
	}
	
   /**
    * selezionato un periodo ("pd" nella tabella dei corsi", vogliamo vedere i corsi di quel periodo con 
    * il relativo numero di iscritti a ciascun corso
    * @param periodo è il periodo( 1 o 2) a cui siamo interessati
    * @return una mappa che contiene il nome corso e ill numero di iscritti
    */
	public Map<String, Integer> getIscrittiCorsi(int periodo) {
		
// 1--	INIZIAMO SEMPRE COPIANDO LA QUERY SQL(conviene provarla prima sul database e poi incollarla
//	     SUCCESSIVAMENTE CREIAMO LA MAPPA che il metodo dovra' ritornare	 
		String sql= "select c.codins, c.nome, COUNT(*) as tot " +  //alla fine di ogni riga levare \n 
                    "from corso as c, iscrizione as i "+           // e mettere uno spazio altrimenti
				    "where c.codins = i.codins and c.pd = ? "+     // vedrebbe la fine di una riga attaccata all'altra
                    "group by c.codins, c.nome" ;
		
        Map<String,Integer> result= new HashMap<String,Integer>();
 
        
//	2-- INSERIAMO IL SOLITO TRY AND CATCH DOVE ANDREMO A FARE
//		LE OPERAZIONI 
	try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,periodo); // definizione del parametro, in questo caso solo uno di tipo int a cui passiamo il periodo passato come parametro
			ResultSet rs = st.executeQuery();
			
			
//	3-- SCANDIAMO IL RISULTATO rs MA IN QUESTO CASO NON DOBBIAMO CREARE NUOVI OGGETTI
//		MA AGGIUNGERE DIRETTAMENTE ALLA MAPPA CREATA
		   while(rs.next()) {
			  //NOTA: il secondo termine e' il risultato del COUNT impostato nella query
			  //   a cui abbiamo precedentemente dato il nome di "tot"
			   result.put(rs.getString("codins"), rs.getInt("tot")); 
		   }
		   
//  4-- ALLA FINE DEL "TRY" CHIUDIAMO LA CONNESSIONE
		   conn.close();
		
	    }catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}

}
