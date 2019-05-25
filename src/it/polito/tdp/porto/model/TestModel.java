package it.polito.tdp.porto.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		model.creaGrafo();
		
		System.out.println("\n\n\n");
		
		List<Author> autori = model.getAutori();
		for(Author a : autori)
			System.out.println("Autore: " + a.getId());
		
		Author autore = model.getSingoloAutore(4303);
		
		System.out.println("\n\n\n");
		
		List<Author> autori2 = model.getCoautori(autore);
		for(Author a2 : autori2)
			System.out.println("Autore: " + a2.getId() + "   " + a2.getLastname() + "   " + a2.getFirstname());
	}

}
