package cap_stone;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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
    private ToggleGroup toggleGroup;
    
    Boolean dragIsEnabled = false;
    
    @FXML
    void onSelection(ActionEvent event){
    	RadioButton r = (RadioButton)event.getTarget();
    	
    	if(r.isSelected()){
    		if(r.getText().equals("On/Off Mode")){
    			dragIsEnabled = false;
    			System.out.println(this.dragIsEnabled);
    		}
    		else{
    			dragIsEnabled = true;
    			System.out.println(this.dragIsEnabled);
    		}
    	}
    }
    
    clickableLight nullLight = new clickableLight("Null", 0, 0, true);
    clickableLight selectedLight;
    boolean lightWasSelected = false;
    
    
///////////////////////////////////////////////////////////////////////////////////
  //initialize block
    
    public void initialize()
	{
    	
    	
    	ArrayList<clickableLight> lightList = new ArrayList<clickableLight>();
    	lightList.add(new clickableLight("Master Bedroom - Top Right", 43, 50, true));
    	lightList.add(new clickableLight("Master Bedroom - Bottom Right", 43,140,true));
    	lightList.add(new clickableLight("Master Bedroom - Middle", 100,97,true));
    	lightList.add(new clickableLight("Living Room - Center", 310, 235,true));
    	lightList.add(new clickableLight("Kids Bedroom - Bottom Left", 43, 300, true));
    	lightList.add(new clickableLight("Kids Bedroom - Bottom Right", 113, 300, true));
    	lightList.add(new clickableLight("Kids Bedroom - Middle", 80, 250, true));
    	lightList.add(new clickableLight("Closet - Living Room - Left", 138, 300, true));
    	lightList.add(new clickableLight("Bottom Bathroom", 155, 225, true));
    	lightList.add(new clickableLight("Living Room - Top Left", 278, 162, true));
    	lightList.add(new clickableLight("Kitchen", 345, 97, true));
    	lightList.add(new clickableLight("Top Bathroom", 180, 80 ,true));
    	lightList.add(new clickableLight("Closet Office", 173, 135, true));
    	lightList.add(new clickableLight("Office Top", 270, 50, true));
    	lightList.add(new clickableLight("Office Bottom", 260,135,true));
    	lightList.add(new clickableLight("Garage", 500,200,true));

    	
    	ArrayList<Television> tvList = new ArrayList<Television>();
    	tvList.add(new Television("TV - Living Room", 200, 240, true));
    	tvList.add(new Television("TV - Living Room", 140, 100, true));
    	
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gc_bl = bottom_left_canvas.getGraphicsContext2D();
        GraphicsContext gc_br = bottom_right_canvas.getGraphicsContext2D();
        
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, 
        	       new EventHandler<MouseEvent>() {
        	           @Override
        	           public void handle(MouseEvent e) {
//        	        	   System.out.println("Mouse was clicked");
        	        	   if (dragIsEnabled){
        	        		   wasLightSelected(lightList, (int)e.getX(), (int) e.getY());
        	        		   selectedLight = lightThatWasSelected(lightList, (int)e.getX(), (int) e.getY());
        	        		   System.out.println(selectedLight.getName());
        	        	   }
        	        	   else{
        	        	   updateLights(lightList, (int)e.getX(), (int) e.getY());
        	        	   updateTVs(tvList, (int)e.getX(), (int) e.getY());
        	        	   drawAllLights(gc, lightList);
        	        	   drawAllTVs(gc, tvList);
        	        	   }
        	           }
        	       });
        
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, 
        		new EventHandler<MouseEvent>(){
					@Override
					public void handle(MouseEvent event) {
						if(dragIsEnabled){
							if(lightWasSelected){
								selectedLight.setxPos((int) event.getX());
								selectedLight.setyPos((int) event.getY());
								gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
								gc.setFill(new LinearGradient(0, 0, 1, 1, true,
						                CycleMethod.REFLECT,
						                new Stop(0, Color.LIGHTBLUE),
						                new Stop(1, Color.BISQUE)));
						        
						        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
						        
						        drawHouse(gc);
						        drawAllTVs(gc, tvList);
						        drawAllLights(gc, lightList);
							}
						}
						else{}
					}
        });
        
//        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
//        		new EventHandler<MouseEvent>(){
//        			@Override
//        			public void handle(MouseEvent event) {
//        				gc.ree
//        				drawAllLights(gc, lightList);
//        			}});
        
       
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
        drawAllTVs(gc, tvList);
        drawAllLights(gc, lightList);

        
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

    public clickableLight lightThatWasSelected(ArrayList<clickableLight> lst, int x, int y){
    	clickableLight light = nullLight;
    	for (int i = 0; i < lst.size(); i++) {
    		clickableLight cL = lst.get(i);
    		if((cL.getxPos() <= x) 
    				&& 
    				(cL.getxPos() + 20 >= x)
    				&& 
    				(cL.getyPos() <= y)
    				&&
    				(cL.getyPos() + 20 >= y )){
    			light =  cL;
    			return cL;
    		}
		}
    	return light;
    }
    
    public void wasLightSelected(ArrayList<clickableLight> lst, int x, int y){
    	for (int i = 0; i < lst.size(); i++) {
    		clickableLight cL = lst.get(i);
    		if((cL.getxPos() <= x) 
    				&& 
    				(cL.getxPos() + 20 >= x)
    				&& 
    				(cL.getyPos() <= y)
    				&&
    				(cL.getyPos() + 20 >= y )){
    			lightWasSelected = true;
    		}
		}
    }
    
    public boolean tvWasSelected(ArrayList<Television> lst, int x, int y){
    	boolean answer = false;
    	for (int i = 0; i < lst.size(); i++) {
    		Television cL = lst.get(i);
    		if((cL.getxPos() <= x) 
    				&& 
    				(cL.getxPos() + 20 >= x)
    				&& 
    				(cL.getyPos() <= y)
    				&&
    				(cL.getyPos() + 20 >= y )){
    			return true;
    		}
    		else{
    			answer = true;;
    		}
		}
    	return answer;
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
    
    public void updateTVs(ArrayList<Television> lst, int x, int y){
    	for (int i = 0; i < lst.size(); i++) {
    		Television tv = lst.get(i);
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
    
    public void drawAllTVs(GraphicsContext gc, ArrayList<Television> lst){
    	for (int i = 0; i < lst.size(); i++) {
    		Television tv = lst.get(i);
			if(tv.isOn()){
				drawOnTV(gc, tv.getxPos(), tv.getyPos());
			}
			else{
				drawOffTV(gc, tv.getxPos(), tv.getyPos());
			}
		}
    }
    
    public void drawOnTV(GraphicsContext gc, int x_pos, int y_pos){
    	gc.setFill(Color.BLACK);
    	gc.fillRect(x_pos-1, y_pos-1, 19, 49);
    	gc.setFill(Color.YELLOW);
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