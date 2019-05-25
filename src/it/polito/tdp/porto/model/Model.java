package it.polito.tdp.porto.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	private SimpleGraph<Author, DefaultEdge> grafo;
	private PortoDAO dao;
	private Map<Integer, Author> autori;
	private List<Author> listAuthor;
	
	public Model() {
		grafo = new SimpleGraph<>(DefaultEdge.class);
		autori = new HashMap<Integer, Author>();
	}
	
	public void creaGrafo() {
		dao = new PortoDAO();
		autori = dao.getMapAutori();
		
		Graphs.addAllVertices(grafo, autori.values());
		
		for(CoAuthor ca : dao.getCoAutori(this.autori)) {
			Author a1 = ca.getA1();
			Author a2 = ca.getA2();
			this.grafo.addEdge(a1, a2);
		}
		
		System.out.println("Grafo creato con: " + grafo.vertexSet().size() + " VERTICI.");
		System.out.println("Grafo creato con: " + grafo.edgeSet().size() + " ARCHI");
	}
	
	public List<Author> getAutori() {
		dao = new PortoDAO();
		
		listAuthor = dao.getListAutori();
		if(this.listAuthor.isEmpty()) 
			throw new RuntimeException("Errore con il database") ;
		
		return listAuthor;	
	}
	
	public List<Author> getCoautori(Author a) {
		dao = new PortoDAO();
		
		if(this.grafo == null)
			creaGrafo();
		
		List<Author> coAutori = Graphs.neighborListOf(this.grafo, a);
		
		return coAutori;
	}
	
	public Author getSingoloAutore(int i) {
		dao = new PortoDAO();
		
		Author a1 = dao.getAutore(i);
		
		return a1;
	}
	
}
