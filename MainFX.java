package cap_stone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainFX extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
		
		GridPane gridPane = loader.load();
		Scene scene = new Scene(gridPane);
		MainController mainContr = loader.getController();
		primaryStage.setScene(scene);		
		primaryStage.show();
	}

	public static void main(String[] args){
		Application.launch(args);
	}
	
}
