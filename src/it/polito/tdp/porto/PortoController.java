package it.polito.tdp.porto;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCoautori(ActionEvent event) {
    	txtResult.clear();
    	
    	Author a = boxPrimo.getValue();
    	
    	if(a == null) {
    		txtResult.appendText("Errore: selezionare un autore\n");
    		return ;
    	}
    	List<Author> coAutori = model.getCoautori(a);
    	List<Author> nonCoAutori = model.getAutori();
    	
    	nonCoAutori.removeAll(coAutori);
    	nonCoAutori.remove(a);
    	
    	txtResult.appendText("Coautori di: " + a.toString() + ":\n");
    	for(Author afor : coAutori) {
    		txtResult.appendText(afor.toString() + "\n");
    	}
    	
    	boxSecondo.getItems().clear();
    	boxSecondo.getItems().addAll(nonCoAutori) ;
    	boxSecondo.setDisable(false);
    }

    @FXML
    void handleSequenza(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";
        
        boxSecondo.setDisable(true);
    }
    
    public void setModel(Model model) {
		this.model = model;
		List<Author> autori = this.model.getAutori();
		
		boxPrimo.getItems().addAll(autori);
		boxSecondo.getItems().clear() ;
    }
}
