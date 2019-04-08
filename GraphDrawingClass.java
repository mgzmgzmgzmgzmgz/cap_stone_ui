package cap_stone;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GraphDrawingClass {
	
	public GraphDrawingClass(){}
	
	public void drawGraph(GraphicsContext gc, int farthest_x, int farthest_y, 
			ArrayList<Integer> waterList,
			ArrayList<Integer> electricList,
			ArrayList<Integer> costList)
	{
		drawWhiteBackground(gc,farthest_x, farthest_y);
//		drawOutline(gc,farthest_x, farthest_y);
		String imagePath = "file:/Users/micahgiles/Desktop/Documents/workspace/CapStone_House_Project/src/cap_stone/GraphBackdrop.png";
    	Image image = new Image(imagePath);
    	gc.drawImage(image, -14, -18, farthest_x+28, farthest_y+36);
		drawGraphLineUsage(gc, farthest_x, farthest_y, waterList, electricList, costList);
//		String graphLegendImagePath = "file:/Users/micahgiles/Desktop/Documents/workspace/CapStone_House_Project/src/cap_stone/Graph_legend.png";
//		Image graphLegendImage = new Image(graphLegendImagePath);
//		gc.drawImage(graphLegendImage, 504, 0, 125, 100);
//		drawKey(gc, farthest_x, farthest_y);
	}

	public void drawOutline(GraphicsContext gc, int farthest_x, int farthest_y){
		gc.setFill(Color.BLACK);
		gc.setLineWidth(2.0);
		gc.strokeLine(0, 0, 0, farthest_y);
		gc.strokeLine(0, 0, farthest_x, 0);
		gc.strokeLine(0, farthest_y, farthest_x, farthest_y);
		gc.strokeLine(farthest_x, 0, farthest_x, farthest_y);
    }
	
	public void drawWhiteBackground(GraphicsContext gc, int farthest_x, int farthest_y){
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, farthest_x, farthest_y);
    }
	
	
	
	public int getMax(ArrayList<Integer> list){
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i) > max){
				max = list.get(i);
			}
		}
		return max;
	}
	
	public void drawGraphLineUsage(GraphicsContext gc, int farthest_x, int farthest_y, 
			ArrayList<Integer> waterList,
			ArrayList<Integer> electricList,
			ArrayList<Integer> costList){
		
		
		gc.setLineWidth(2.0);
		
		int waterMax = getMax(waterList);
		int elecMax = getMax(electricList);
		int costMax = getMax(costList);
		
		int initial_x = 3;
		
		int x_incrementer = farthest_x / (costList.size() - 1);
		int water_y_normalizer = farthest_y / waterMax;
		int elec_y_normalizer = farthest_y / elecMax;
		int cost_y_normalizer = farthest_y / costMax;
		
		gc.setStroke(Color.BLUE);
		//Water Draw
		for (int i = 0; i < waterList.size() - 1; i++) 
		{	
			if(i == 0){
				gc.strokeLine(initial_x, waterList.get(i) * water_y_normalizer, x_incrementer * (i + 1), waterList.get(i + 1) * water_y_normalizer);
			}
			else{
				gc.strokeLine(x_incrementer * i, waterList.get(i) * water_y_normalizer, x_incrementer * (i + 1), waterList.get(i + 1) * water_y_normalizer);
			}
		}
		
		gc.setStroke(Color.INDIANRED);
		//Electricity Draw
		for (int i = 0; i < electricList.size() - 1; i++) 
		{	
			if(i == 0){
				gc.strokeLine(initial_x, electricList.get(i) * elec_y_normalizer, x_incrementer * (i + 1), electricList.get(i + 1) * elec_y_normalizer);
			}
			else{
				gc.strokeLine(x_incrementer * i, electricList.get(i) * elec_y_normalizer, x_incrementer * (i + 1), electricList.get(i + 1) * elec_y_normalizer);
			}
		}
		
		gc.setStroke(Color.GREEN);
		//Cost Draw
		for (int i = 0; i < costList.size() - 1; i++) 
		{	
			if(i == 0){
				gc.strokeLine(initial_x, costList.get(i) * cost_y_normalizer, x_incrementer * (i + 1), costList.get(i + 1) * cost_y_normalizer);
			}
			else{
				gc.strokeLine(x_incrementer * i, costList.get(i) * cost_y_normalizer, x_incrementer * (i + 1), costList.get(i + 1) * cost_y_normalizer);
			}
		}
    }
	
	public void drawKey(GraphicsContext gc, int farthest_x, int farthest_y){
		gc.setFill(Color.GRAY);
    	gc.fillRect(0, 0, 70, 45);
    	gc.setFill(Color.BLUE);
    	gc.fillText("Water", 5, 15);
    	gc.setFill(Color.YELLOW);
    	gc.fillText("Electricity", 5, 27);
    	gc.setFill(Color.GREEN);
    	gc.fillText("Cost", 5, 39);
	}
	
	
}
