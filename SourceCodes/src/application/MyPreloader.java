package application;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MyPreloader extends Preloader {

	private Stage preloaderStage;
	private Scene scene;
	
	public MyPreloader() {
		
	}
	
	@Override
	public void init() throws Exception{
		Parent root1 = FXMLLoader.load(getClass().getResource("startScreen.fxml"));
		scene = new Scene(root1);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		
		this.preloaderStage = primaryStage;
		
		preloaderStage.setScene(scene);
		preloaderStage.initStyle(StageStyle.UNDECORATED);
		Image taskIcon = new Image("taskicon.png");
		preloaderStage.getIcons().add(taskIcon);
		preloaderStage.show();
		
		Methods methods = new Methods();
		methods.firstStart();
		methods.downloadBackupOnStart();
	}
	
	@Override
	public void handleApplicationNotification(Preloader.PreloaderNotification info) {
		if(info instanceof ProgressNotification) {
			StartScreenController.statProgressBar.setProgress(((ProgressNotification) info).getProgress() );
			StartScreenController.label.setText("Loading..." + ((ProgressNotification) info).getProgress() * 100 + "%" );
		}
	}
	@Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info) {
      
        StateChangeNotification.Type type = info.getType();
        switch (type) {
            
            case BEFORE_START:
                // Called after MyApplication#init and before MyApplication#start is called.
                System.out.println("BEFORE_START");
                preloaderStage.hide();
                break;
        }
}
}