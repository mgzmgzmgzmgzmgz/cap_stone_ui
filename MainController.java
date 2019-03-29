package cap_stone;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class MainController {

	@FXML
    private Canvas canvas;

    @FXML
    private Canvas bottom_left_canvas;

    @FXML
    private Label TemperatureLabel;

    @FXML
    private Label InternalLabel;

    @FXML
    private Label ExternalLabel;

    @FXML
    private Label HVACLabel;

    @FXML
    private TextField internalTempTextField;

    @FXML
    private TextField externalTempTextField;

    @FXML
    private TextField HVACTextField;

    @FXML
    private Canvas bottom_right_canvas;

    @FXML
    private Label powerCostEstimationLabel;

    @FXML
    private Label monthlyLabel;

    @FXML
    private Label weeklyLabel;

    @FXML
    private Label yearlyLabel;

    @FXML
    private TextField weeklyCostTextField;

    @FXML
    private TextField monthlyCostTextField;

    @FXML
    private TextField yearlyCostTextField;

    @FXML
    private PieChart pieChart;

    @FXML
    private ToggleButton toggleButton;
    
    
    
///////////////////////////////////////////////////////////////////////////////////
  //initialize block
    
    public void initialize()
	{
    	
    	ArrayList<clickableLight> lst = new ArrayList<clickableLight>();
    	lst.add(new clickableLight("Master Bedroom - Top Right", 43, 50, true));
    	lst.add(new clickableLight("Master Bedroom - Bottom Right", 43,140,true));
    	lst.add(new clickableLight("Master Bedroom - Middle", 100,97,true));
    	lst.add(new clickableLight("Living Room - Center", 310, 235,true));
    	lst.add(new clickableLight("Kids Bedroom - Bottom Left", 43, 300, true));
    	lst.add(new clickableLight("Kids Bedroom - Bottom Right", 113, 300, true));
    	lst.add(new clickableLight("Kids Bedroom - Middle", 80, 250, true));
    	lst.add(new clickableLight("Closet - Living Room - Left", 138, 300, true));
    	lst.add(new clickableLight("Bottom Bathroom", 155, 225, true));
    	lst.add(new clickableLight("Living Room - Top Left", 278, 162, true));
    	lst.add(new clickableLight("Kitchen", 345, 97, true));
    	lst.add(new clickableLight("Top Bathroom", 180, 80 ,true));
    	lst.add(new clickableLight("Closet Office", 173, 135, true));
    	lst.add(new clickableLight("Office Top", 270, 50, true));
    	lst.add(new clickableLight("Office Bottom", 260,135,true));
    	
    	Television tv = new Television("TV - Living Room", 200, 240, true);
    	
    	
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gc_bl = bottom_left_canvas.getGraphicsContext2D();
        GraphicsContext gc_br = bottom_right_canvas.getGraphicsContext2D();
        
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, 
        	       new EventHandler<MouseEvent>() {
        	           @Override
        	           public void handle(MouseEvent e) {
//        	        	   System.out.println("Mouse was clicked");
        	        	   updateLights(lst, (int)e.getX(), (int) e.getY());
        	        	   updateTV(tv, (int)e.getX(), (int) e.getY());
        	        	   drawAllLights(gc, lst);
        	        	   drawTV(gc, tv);
        	           }
        	       });
        
       
        gc_bl.setFill(new LinearGradient(0, 0, 1, 1, true,
                CycleMethod.REFLECT,
                new Stop(0, Color.LIGHTBLUE),
                new Stop(1, Color.BISQUE)));
        
        gc_br.setFill(new LinearGradient(0, 0, 1, 1, true,
                CycleMethod.REFLECT,
                new Stop(0, Color.LIGHTBLUE),
                new Stop(1, Color.BISQUE)));
        
        
        gc.setFill(new LinearGradient(0, 0, 1, 1, true,
                CycleMethod.REFLECT,
                new Stop(0, Color.LIGHTBLUE),
                new Stop(1, Color.BISQUE)));
        
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc_bl.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc_br.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        drawHouse(gc);
        drawOnTV(gc, tv.getxPos(), tv.getyPos());

        drawAllLights(gc, lst);

        
	}
    
    //initialize block
///////////////////////////////////////////////////////////////////////////////////
    
    
    
///////////////////////////////////////////////////////////////////////////////////
    //Drawing functions

    
    
    public void drawHouse(GraphicsContext gc){
    	String imagePath = "file:/Users/micahgiles/Desktop/Documents/workspace/CapStone_House_Project/src/cap_stone/ld_house3.png";
    	Image image = new Image(imagePath);
    	// Draw the Image
    	gc.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void updateLights(ArrayList<clickableLight> lst, int x, int y){
    	for (int i = 0; i < lst.size(); i++) {
    		clickableLight cL = lst.get(i);
    		if((cL.getxPos() <= x) 
    				&& 
    				(cL.getxPos() + 20 >= x)
    				&& 
    				(cL.getyPos() <= y)
    				&&
    				(cL.getyPos() + 20 >= y )){
    			cL.switch_();
    		}
		}
    }
    
    public void updateTV(Television tv, int x, int y){
    		if((tv.getxPos() <= x) 
    				&& 
    				(tv.getxPos() + 20 >= x)
    				&& 
    				(tv.getyPos() <= y)
    				&&
    				(tv.getyPos() + 49 >= y )){
    			tv.switch_();
    		}
    }
    
    public void drawAllLights(GraphicsContext gc, ArrayList<clickableLight> lst){
    	for (int i = 0; i < lst.size(); i++) {
    		clickableLight cL = lst.get(i);
			if(cL.isOn()){
				drawOnLight(gc, cL.getxPos(), cL.getyPos());
			}
			else{
				drawOffLight(gc, cL.getxPos(), cL.getyPos());
			}
		}
    }
    
    public void drawOnLight(GraphicsContext gc, int x_pos, int y_pos){
    	gc.setFill(Color.BLACK);
    	gc.fillOval(x_pos-1, y_pos-1, 19, 19);
    	gc.setFill(Color.YELLOW);
    	gc.fillOval(x_pos, y_pos, 17, 17);
    	gc.setFill(Color.BLACK);
    	gc.fillText("L", x_pos + 5, y_pos + 13); 
    }
    
    public void drawOffLight(GraphicsContext gc, int x_pos, int y_pos){
    	gc.setFill(Color.YELLOW);
    	gc.fillOval(x_pos-1, y_pos-1, 19, 19);
    	gc.setFill(Color.BLACK);
    	gc.fillOval(x_pos, y_pos, 17, 17);
    	gc.setFill(Color.YELLOW);
    	gc.fillText("L", x_pos + 5, y_pos + 13);
    }
    
    public void drawTV(GraphicsContext gc, Television tv){
			if(tv.isOn()){
				drawOnTV(gc, tv.getxPos(), tv.getyPos());
			}
			else{
				drawOffTV(gc, tv.getxPos(), tv.getyPos());
			}
		}
    
    public void drawOnTV(GraphicsContext gc, int x_pos, int y_pos){
    	gc.setFill(Color.BLACK);
    	gc.fillRect(x_pos-1, y_pos-1, 19, 49);
    	gc.setFill(Color.WHITE);
    	gc.fillRect(x_pos, y_pos, 17, 47);
    	gc.setFill(Color.BLACK);
    	gc.fillText("T", x_pos + 5, y_pos + 22);
    	gc.fillText("V", x_pos + 5, y_pos + 34);
    }
    
    public void drawOffTV(GraphicsContext gc, int x_pos, int y_pos){
    	gc.setFill(Color.WHITE);
    	gc.fillRect(x_pos-1, y_pos-1, 19, 49);
    	gc.setFill(Color.BLACK);
    	gc.fillRect(x_pos, y_pos, 17, 47);
    	gc.setFill(Color.WHITE);
    	gc.fillText("T", x_pos + 5, y_pos + 22);
    	gc.fillText("V", x_pos + 5, y_pos + 34);
    }
    


}