package application;
	


import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	
	private static final int COUNT_LIMIT = 10;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Ui.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			Image taskIcon = new Image("taskicon.png");
			primaryStage.getIcons().add(taskIcon);
			primaryStage.setTitle("Java CodeSnippets Collection");
			
			
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
    @Override
  public void init() throws Exception {       
      
      // Perform some heavy lifting (i.e. database start, check for application updates, etc. )
      for (int i = 1; i <= COUNT_LIMIT; i++) {
          double progress = (double) i/10;
          System.out.println("progress: " +  progress);            
          LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
          Thread.sleep(500);
      }
      
  }
  
  
  public static void main(String[] args) {        
      LauncherImpl.launchApplication(Main.class, MyPreloader.class, args);
  }
  
}