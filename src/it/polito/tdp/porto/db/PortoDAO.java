package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.CoAuthor;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<CoAuthor> getCoAutori (Map<Integer, Author> idMap) {
		final String sql = "SELECT c1.authorid AS authorid, c2.authorid AS author2id " + 
				"FROM creator c1, creator c2 " + 
				"WHERE c1.eprintid = c2.eprintid AND c1.authorid != c2.authorid " + 
				"GROUP BY c1.authorid, c2.authorid";
		
		List<CoAuthor> coAutori = new ArrayList<CoAuthor>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Author a1 = idMap.get(rs.getInt("authorid"));
				Author a2 = idMap.get(rs.getInt("author2id"));
				
				coAutori.add(new CoAuthor(a1, a2));
			}
			
			conn.close();
			
			return coAutori;
		}  catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public Map<Integer, Author> getMapAutori() {
		String sql = "SELECT * " +
				"FROM author";
		
		Map<Integer, Author> result = new HashMap<>() ;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {		
				Author a = new Author(res.getInt("id"), res.getString("lastname"), res.getString("firstname"));
				result.put(res.getInt("id"), a);
			}
			
			conn.close();
			
			return result ;
		} catch (SQLException e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	public List<Author> getListAutori() {
		String sql = "SELECT id, lastname, firstname " + 
				"FROM author " + 
				"ORDER BY lastname ASC, firstname ASC";
		
		List<Author> autori = new ArrayList<Author>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet res = st.executeQuery();

			while (res.next()) {
				autori.add(new Author(res.getInt("id"), res.getString("lastname"), res.getString("firstname")));
			}
			conn.close();
			
			return autori;
		}  catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db.");
		}

		
	}
	
	
}




