package cap_stone;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GraphDrawingClass {
	
	public GraphDrawingClass(){}
	
	public void drawGraph(GraphicsContext gc, int farthest_x, int farthest_y, 
			ArrayList<Integer> waterList,
			ArrayList<Integer> electricList,
			ArrayList<Integer> costList,
			Boolean japSel)
	{
		drawWhiteBackground(gc,farthest_x, farthest_y);

		URL urlA = this.getClass().getClassLoader().getResource("GraphBackdrop.png");
    	try {
			System.out.println(urlA.toURI());
			File fileA = new File(urlA.toURI());
			String imagePath = "File:" + fileA.getAbsolutePath();
			Image image = new Image(imagePath);
			gc.drawImage(image, -14, -18, farthest_x+28, farthest_y+36);
			drawGraphLineUsage(gc, farthest_x, farthest_y, waterList, electricList, costList);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
//		File fileA = new File("GraphBackdrop.png");
//		String imagePath = "file:" + fileA.getAbsolutePath();
//    	Image image = new Image(imagePath);
//    	gc.drawImage(image, -14, -18, farthest_x+28, farthest_y+36);
//		drawGraphLineUsage(gc, farthest_x, farthest_y, waterList, electricList, costList);
		if(!japSel){
			try {
				URL urlB = this.getClass().getClassLoader().getResource("Graph_legend.png");
				File fileB;
				fileB = new File(urlB.toURI());
				String graphLegendImagePath = "file:" + fileB.getAbsolutePath();
				Image graphLegendImage = new Image(graphLegendImagePath);
				gc.drawImage(graphLegendImage, 363, 273, 125, 100);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		else{
//			File fileC = new File("Graph_legend_Japanese.png");
			try {
				URL urlB = this.getClass().getClassLoader().getResource("Graph_legend_Japanese.png");
				File fileB;
				fileB = new File(urlB.toURI());
				String graphLegendImagePath = "file:" + fileB.getAbsolutePath();
				Image graphLegendImage = new Image(graphLegendImagePath);
				gc.drawImage(graphLegendImage, 363, 0, 125, 100);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		}
//		drawKey(gc, farthest_x, farthest_y);

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
	
	public int getMin(ArrayList<Integer> list){
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i) < min){
				min = list.get(i);
			}
		}
		return min;
	}
	
	public void drawGraphLineUsage(GraphicsContext gc, int farthest_x, int farthest_y, 
			ArrayList<Integer> waterList,
			ArrayList<Integer> electricList,
			ArrayList<Integer> costList){
		
		
		gc.setLineWidth(2.0);
		
		int waterMax = getMax(waterList);
		int elecMax = getMax(electricList);
		int costMax = getMax(costList);
		
		int waterMin = getMin(waterList);
		int elecMin = getMin(electricList);
		int costMin = getMin(costList);
		
		int initial_x = 3;
		
		int x_incrementer = farthest_x / (costList.size() - 1);

		
		gc.setStroke(Color.BLUE);
		//Water Draw
		for (int i = 0; i < waterList.size() - 1; i++) 
		{	
			if(i == 0){
//				System.out.println("equation: " +(int)(farthest_y - (int)(((float)waterList.get(i) / (float)waterMax) * (float)farthest_y)));
				gc.strokeLine(initial_x, 
						y_formula(waterList.get(i), waterMax, waterMin, farthest_y),
						x_incrementer * (i + 1),
						y_formula(waterList.get(i + 1), waterMax, waterMin, farthest_y));
			}
			else{
//				System.out.println("equation: " + (int)(farthest_y - (int)(((float)waterList.get(i) / (float)waterMax) * (float)farthest_y)));
				gc.strokeLine(x_incrementer * i, 
						y_formula(waterList.get(i), waterMax, waterMin, farthest_y),
						x_incrementer * (i + 1),
						y_formula(waterList.get(i + 1), waterMax, waterMin, farthest_y));
			}
		}
		
		gc.setStroke(Color.INDIANRED);
		//Electricity Draw
		for (int i = 0; i < electricList.size() - 1; i++) 
		{	
			if(i == 0){
//				System.out.println("equation: " +(int)(farthest_y - (int)(((float)waterList.get(i) / (float)waterMax) * (float)farthest_y)));
				gc.strokeLine(initial_x, 
						y_formula(electricList.get(i), elecMax, elecMin, farthest_y),
						x_incrementer * (i + 1),
						y_formula(electricList.get(i + 1), elecMax, elecMin, farthest_y));
			}
			else{
//				System.out.println("equation: " + (int)(farthest_y - (int)(((float)waterList.get(i) / (float)waterMax) * (float)farthest_y)));
				gc.strokeLine(x_incrementer * i, 
						y_formula(electricList.get(i), elecMax, elecMin, farthest_y),
						x_incrementer * (i + 1),
						y_formula(electricList.get(i + 1), elecMax, elecMin, farthest_y));
			}
		}
		
		gc.setStroke(Color.GREEN);
		//Cost Draw
		for (int i = 0; i < costList.size() - 1; i++) 
		{	
			if(i == 0){
//				System.out.println("equation: " +(int)(farthest_y - (int)(((float)waterList.get(i) / (float)waterMax) * (float)farthest_y)));
				gc.strokeLine(initial_x, 
						y_formula(costList.get(i), costMax, costMin, farthest_y),
						x_incrementer * (i + 1),
						y_formula(costList.get(i + 1), costMax, costMin, farthest_y));
			}
			else{
//				System.out.println("equation: " + (int)(farthest_y - (int)(((float)waterList.get(i) / (float)waterMax) * (float)farthest_y)));
				gc.strokeLine(x_incrementer * i, 
						y_formula(costList.get(i), costMax, costMin, farthest_y),
						x_incrementer * (i + 1),
						y_formula(costList.get(i + 1), costMax, costMin, farthest_y));
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
	
	public int y_formula(int num, int max, int min, int y){
		int ans = (int)(y * ( ((float)max - (float)num) / ((float)max - (float)min)));
		System.out.println("ans: " + ans);
		return ans;
	}
	
	
}
