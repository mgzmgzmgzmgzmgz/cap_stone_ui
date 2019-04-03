package cap_stone;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GraphDrawingClass {
	
	public GraphDrawingClass(){}
	
	public GraphDrawingClass(int canvasLength, int canvasWidth){
		this.setCanvasLength(canvasLength);
		this.setCanvasWidth(canvasWidth);
	}
	
	private int canvasLength;
	private int canvasWidth;

	public void drawOutline(GraphicsContext gc, int farthest_x, int farthest_y){
		gc.setFill(Color.BLACK);
		gc.setLineWidth(2.0);
		gc.strokeLine(0, 0, 0, farthest_y);
		gc.strokeLine(0, 0, farthest_x, 0);
		gc.strokeLine(0, farthest_y, farthest_x, farthest_y);
		gc.strokeLine(farthest_x, 0, farthest_x, farthest_y);
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
		
		int x_incrementer = farthest_x / (costList.size() - 1);
		int water_y_normalizer = farthest_y / waterMax;
		int elec_y_normalizer = farthest_y / elecMax;
		int cost_y_normalizer = farthest_y / costMax;
		
		gc.setStroke(Color.BLUE);
		//Water Draw
		for (int i = 0; i < waterList.size() - 1; i++) 
		{	
			if(i == 0){
				gc.strokeLine(0, waterList.get(i) * water_y_normalizer, x_incrementer * (i + 1), waterList.get(i + 1) * water_y_normalizer);
			}
			else{
				gc.strokeLine(x_incrementer * i, waterList.get(i) * water_y_normalizer, x_incrementer * (i + 1), waterList.get(i + 1) * water_y_normalizer);
			}
		}
		
		gc.setStroke(Color.YELLOW);
		//Electricity Draw
		for (int i = 0; i < electricList.size() - 1; i++) 
		{	
			if(i == 0){
				gc.strokeLine(0, electricList.get(i) * elec_y_normalizer, x_incrementer * (i + 1), electricList.get(i + 1) * elec_y_normalizer);
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
				gc.strokeLine(0, costList.get(i) * cost_y_normalizer, x_incrementer * (i + 1), costList.get(i + 1) * cost_y_normalizer);
			}
			else{
				gc.strokeLine(x_incrementer * i, costList.get(i) * cost_y_normalizer, x_incrementer * (i + 1), costList.get(i + 1) * cost_y_normalizer);
			}
		}
    }


	public int getCanvasLength() {return canvasLength;}
	public void setCanvasLength(int canvasLength) {this.canvasLength = canvasLength;}

	public int getCanvasWidth() {return canvasWidth;}
	public void setCanvasWidth(int canvasWidth) {this.canvasWidth = canvasWidth;}
	
	
}
