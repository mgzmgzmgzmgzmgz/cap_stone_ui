package cap_stone;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ToggleButton;

public class MainController {

    @FXML private Canvas canvas;
    @FXML private PieChart pieChart;
    @FXML private ToggleButton toggleButton;
    
    
    public void initialize()
	{
    	toggleButton.setOnAction(event->{
    		System.out.println("Wow you dumb bitch way to go.....");
    	});
	}

}