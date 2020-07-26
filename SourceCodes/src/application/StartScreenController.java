package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class StartScreenController implements Initializable {
	
	  @FXML
	    private Label progress;
	    
	    public static Label label;
	    
	    
	    @FXML
	    private ProgressBar progressBar;
	    
	    public static ProgressBar statProgressBar;
	    
	    @FXML
	    private void handleButtonAction(ActionEvent event) {
	       
	        
	    }
	    
	    @Override
	    public void initialize(URL url, ResourceBundle rb) {
	       label = progress ;
	       statProgressBar = progressBar;
	    }    
	    
	}


