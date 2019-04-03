package cap_stone;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainFX extends Application {
	MainController mainController;

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
		
		GridPane gridPane = loader.load();
		mainController = loader.getController();
		
		Scene scene = new Scene(gridPane);
		MainController mainContr = loader.getController();
		primaryStage.setScene(scene);		
		
//		primaryStage.setOnCloseRequest(this::onClose);
		
		primaryStage.show();
	}

//	private void onClose(WindowEvent event) {
////		new Alert(AlertType.INFORMATION, event.getEventType().getName()).showAndWait();
//		FileOutputStream out = null;
//		try {
//			out = new FileOutputStream("appliances.xml");
//			XMLEncoder encoder = new XMLEncoder(out);
//			encoder.writeObject(mainController.getLightList());
//			encoder.close();
//			
//		} catch (Exception e) {
//			if(out != null)
//				try{
//					out.close();
//				}catch(IOException ex) {
//					ex.printStackTrace();
//				}
//		}
//		
//	}

	public static void main(String[] args){
		Application.launch(args);
	}
	
}
