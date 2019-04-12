package cap_stone;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings("unused")
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
