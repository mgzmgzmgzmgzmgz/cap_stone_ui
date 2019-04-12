package cap_stone;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Optional;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

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
    private Canvas graphCanvas;
    
    @FXML
    private Canvas backgroundGraphCanvas;

    @FXML
    private RadioButton sevenDayRadioButton;

    @FXML
    private ToggleGroup graphToggle;

    @FXML
    private RadioButton fourWeeksRadioButton;

    @FXML
    private RadioButton sixMonthsRadioButton;

    @FXML
    private RadioButton twelveMonthsRadioButton;

    @FXML
    private RadioButton dragModeRadioButton;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private RadioButton onOffRadioButton;
    
    @FXML
    private RadioButton englishRadioButton;

    @FXML
    private ToggleGroup languageToggle;

    @FXML
    private RadioButton japaneseRadioButton;
    
    @FXML
    private Label maxGraphValues;

    @FXML
    private Label minGraphVal;
    
    @FXML
    private Label halfGraphTimeLabel;

    @FXML
    private Label graphTimeLabel;
    
    @FXML
    private Label modeSelectionLabel;

    @FXML
    private Label langaugeSelectionLabel;
    
    @FXML
    private TableView<Appliance> sensorTable;

    @FXML
    private TableColumn<Appliance, String> typeColumn;

    @FXML
    private TableColumn<Appliance, String> descriptionColumn;

    @FXML
    private TableColumn<Appliance, String> statusColumn;

    @FXML
    private Button switchSelectedButton;
    
    @FXML
    private Tab adminTab;
    
    @FXML
    private Tab graphTab;
    
    @FXML
    private Tab houseViewTab;
    
    @FXML
    private Button plusButton;
    
    @FXML
    private Button minusButton;
    
    @FXML
    private Button lightsOnButton;

    @FXML
    private Button lightsOffButton;

    @FXML
    private Button waterOffButton;

    @FXML
    private Button doorsCloseButton;
    

    @FXML
    void onSelectionModeSwitch(ActionEvent event) {
    	RadioButton r = (RadioButton)event.getTarget();
    	
    	if(r.isSelected()){
    		if(r.getText().equals("On/Off Mode") || r.getText().equals("オン/オフモード")){
    			dragIsEnabled = false;
    			System.out.println(this.dragIsEnabled);
    		}
    		else{
    			Alert alert = new Alert(AlertType.CONFIRMATION);
    			if(japIsSelected)
    			{
    				alert.setHeaderText("ドラッグモードを有効にしてよろしいですか?");
        			alert.setTitle("ドラッグモードを有効にする");
        			alert.getButtonTypes().remove(0,2);
        			alert.getButtonTypes().add(0, ButtonType.YES);
        			alert.getButtonTypes().add(1, ButtonType.NO);
    			}
    			else
    			{
    				alert.setHeaderText("Are you sure you want to enable Drag Mode?");
        			alert.setTitle("Enable Drag Mode");
        			alert.getButtonTypes().remove(0,2);
        			alert.getButtonTypes().add(0, ButtonType.YES);
        			alert.getButtonTypes().add(1, ButtonType.NO);
    			}
    			Optional<ButtonType> confirmationResponse = alert.showAndWait();
    			if(confirmationResponse.get() == ButtonType.YES){
    				dragIsEnabled = true;
        			System.out.println(this.dragIsEnabled);
    			}
    			else{
    				this.onOffRadioButton.setSelected(true);
    			}
    		}
    	}
    }
    
    private HVAC hvac;
    
    private int selectedGraphTimeFrame = 1;
    private Boolean japIsSelected = false;
    private Boolean dragIsEnabled = false;
    private Appliance nullLight = new clickableLight("Null", 0, 0, true, "Null");
    private Appliance selectedAppliance;
    private Appliance currentAppliance = new clickableLight("Null", 0, 0, true, "Null");
    private ObservableList<Appliance> applianceList = FXCollections.observableArrayList();
    private GraphDrawingClass graphDrawing = new GraphDrawingClass();
    
    private ArrayList<Integer> Wtest28values = new ArrayList<Integer>();
    private ArrayList<Integer> Ctest28values = new ArrayList<Integer>();
    private ArrayList<Integer> Etest28values = new ArrayList<Integer>();
    
    public ObservableList<Appliance> getApplianceList(){
    	return this.applianceList;
    }
    
    
///////////////////////////////////////////////////////////////////////////////////
  //initialize block
    
    public void initialize() throws Exception
    {
    	//Creates graphics objects to draw in the canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gc_bl = bottom_left_canvas.getGraphicsContext2D();
        GraphicsContext gc_br = bottom_right_canvas.getGraphicsContext2D();
        GraphicsContext graph_canvas = graphCanvas.getGraphicsContext2D();
        GraphicsContext bgGraphCanvas = backgroundGraphCanvas.getGraphicsContext2D();
        //Creates graphics objects to draw in the canvas

        
        hvac = new HVAC();
        hvac.watchHouse();
        hvac.watchOutside();
        hvac.getCalculator().calcMonthlyCosts();
        hvac.getCalculator().calcWeeklyCosts();
        hvac.getCalculator().calcYearlyCosts();
        
//       System.out.println(hvac.getCalculator().getTotalYearlyEstimatedCost());
        
//    	this.internalTempTextField.textProperty().bindBidirectional(hvac.currentTemperature);
        internalTempTextField.textProperty().bindBidirectional(hvac.currentTemperature);
        HVACTextField.textProperty().bindBidirectional(hvac.defaultTemperature);
        
        
//        this.internalTempTextField.setText("69");
    	this.internalTempTextField.editableProperty().set(false);
    	this.externalTempTextField.setText("62");
    	this.externalTempTextField.editableProperty().set(false);
    	
    	this.HVACTextField.setText(hvac.getDefaultTemp() + "");
    	
    	this.weeklyCostTextField.editableProperty().set(false);
    	this.monthlyCostTextField.editableProperty().set(false);
    	this.yearlyCostTextField.editableProperty().set(false);
    	//(float)(((int)Math.pow(10,2)*this.calulator.getTotalWeeklyEstimatedCost()))/Math.pow(10,2))
    	
//    	float a = (float) ((float)(((int)Math.pow(10,2)*this.calulator.getTotalWeeklyEstimatedCost()))/Math.pow(10,2));
    	
    	 DecimalFormat twoDForm = new DecimalFormat("#.##");
    	 
    	 Float a = Float.valueOf(twoDForm.format(hvac.getCalculator().getTotalWeeklyEstimatedCost())); 
    	 Float b = Float.valueOf(twoDForm.format(hvac.getCalculator().getTotalMonthlyEstimatedCost()));
    	 Float c = Float.valueOf(twoDForm.format(hvac.getCalculator().getTotalYearlyEstimatedCost()));
    	
    	
    	this.weeklyCostTextField.setText("$" + a);
    	this.monthlyCostTextField.setText("$" + b);
    	this.yearlyCostTextField.setText("$" + c);
 	
    	

//    	setGraphArraysWithRandomNumber();
//    	updateGraphValLabels();
    	
    	this.sensorTable.setItems(applianceList);
    	this.descriptionColumn.setCellValueFactory(rowData -> rowData.getValue().nameProperty());
    	this.statusColumn.setCellValueFactory(rowData -> rowData.getValue().isOnStringProperty());
    	this.typeColumn.setCellValueFactory(rowData -> rowData.getValue().typeProperty());
    	
    	this.sensorTable.getSelectionModel().selectedItemProperty().addListener(
    			(ObservableValue<? extends Appliance> observable, Appliance newValue, Appliance oldValue)->
    			{ currentAppliance = oldValue;
    			System.out.println(oldValue.getName());
    			System.out.println(this.currentAppliance.getName());});
    	
    	
    	/////////////////////////////////////////////////////////////////////////////////////////
    	//Button Event Handlers
    	this.minusButton.setOnAction(event->{
    		int newVal = Integer.parseInt(HVACTextField.getText()) - 1;
    		HVACTextField.textProperty().set(""+newVal);
//    		System.out.println(hvac.defaultTemp.get());
    		
    	});
    	
    	this.plusButton.setOnAction(event->{
    		int newVal = Integer.parseInt(HVACTextField.getText()) + 1;
    		HVACTextField.textProperty().set(""+newVal);
//    		System.out.println(hvac.defaultTemp.get());
    	});
        this.switchSelectedButton.setOnAction(event ->{
        	this.currentAppliance.switch_();
        	drawHouse(gc);
        	drawAllAppliances(gc, applianceList);
        	this.sensorTable.refresh();
        });
        this.lightsOnButton.setOnAction(event ->{
        	for (int i = 0; i < this.applianceList.size(); i++) {
			if(this.applianceList.get(i).englishType.get().equals("Light")){
				System.out.println("A light was found");
				if(!this.applianceList.get(i).isOn()){
					this.applianceList.get(i).switch_();
					Connection conn2;
					try {
						conn2 = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "TrafalgarLaw18");
						ResultSet result = conn2.createStatement().executeQuery("select * from public.appliances order by id;");
						conn2.createStatement().executeQuery("update public.appliances set ison = true where id = " + (i + 1) + ";");
						conn2.close();
					}
					catch(Exception e) {
						System.out.println("Light off");
					}
				}
			}
		}
        	drawHouse(gc);
        	drawAllAppliances(gc, applianceList);
        //	this.sensorTable.refresh();
        });
        this.lightsOffButton.setOnAction(event ->{
        	for (int i = 0; i < this.applianceList.size(); i++) {
			if(this.applianceList.get(i).englishType.get().equals("Light")){
				if(this.applianceList.get(i).isOn()){
					this.applianceList.get(i).switch_();
					Connection conn2;
					try {
						conn2 = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "TrafalgarLaw18");
						ResultSet result = conn2.createStatement().executeQuery("select * from public.appliances order by id;");
						conn2.createStatement().executeQuery("update public.appliances set ison = false where id = " + (i + 1) + ";");
						conn2.close();
					}
					catch(Exception e) {
						System.out.println("Light off");
					}
				}
			}
		}
        	drawHouse(gc);
        	drawAllAppliances(gc, applianceList);
        //	this.sensorTable.refresh();
        });
        this.doorsCloseButton.setOnAction(event ->{
        	for (int i = 0; i < this.applianceList.size(); i++) {
			if(this.applianceList.get(i).englishType.get().equals("Door") || this.applianceList.get(i).englishType.get().equals("Window")){
				if(this.applianceList.get(i).isOn()){
					this.applianceList.get(i).switch_();
					Connection conn2;
					try {
						conn2 = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "TrafalgarLaw18");
						ResultSet result = conn2.createStatement().executeQuery("select * from public.appliances order by id;");
						conn2.createStatement().executeQuery("update public.appliances set ison = false where id = " + (i + 1) + ";");
						conn2.close();
					}
					catch(Exception e) {
						System.out.println("Door/Window closed");
					}
				}
			}
		}
        	drawHouse(gc);
        	drawAllAppliances(gc, applianceList);
        //	this.sensorTable.refresh();
        });
        this.waterOffButton.setOnAction(event ->{
        	for (int i = 0; i < this.applianceList.size(); i++) {
			if(this.applianceList.get(i).englishType.get().equals("Water")){
				if(this.applianceList.get(i).isOn()){
					this.applianceList.get(i).switch_();
					Connection conn2;
					try {
						conn2 = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "TrafalgarLaw18");
						ResultSet result = conn2.createStatement().executeQuery("select * from public.appliances order by id;");
						conn2.createStatement().executeQuery("update public.appliances set ison = false where id = " + (i + 1) + ";");
						conn2.close();
					}
					catch(Exception e) {
						System.out.println("Water off");
					}
				}
			}
		}
        	drawHouse(gc);
        	drawAllAppliances(gc, applianceList);
        //	this.sensorTable.refresh();
        });
    	//Button Event Handlers
    	/////////////////////////////////////////////////////////////////////////////////////////
    	
    	
    	
    	//Starts the application off with a radio button selected for each toggle group
    	sixMonthsRadioButton.setSelected(true);
    	onOffRadioButton.setSelected(true);
    	englishRadioButton.setSelected(true);
    	this.graphTimeLabel.setText("6 Months");
		this.halfGraphTimeLabel.setText("3 Months");
    	//Starts the application off with a radio button selected for each toggle group
    	
		
    	
    	////////////////////////////////////////////////////////////////////////
    	//Initializes the appliances	
		Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "TrafalgarLaw18");
		ResultSet result = conn.createStatement().executeQuery("select * from public.appliances order by id;");
	    	conn.close();
			
	        result.next();
	    	applianceList.add(new clickableLight("Master Bedroom - Top Right", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "マスターベッドルーム - 右上"));
	    	result.next();
	    	applianceList.add(new clickableLight("Master Bedroom - Bottom Right", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "マスターベッドルーム - 右下"));
	    	result.next();
	    	applianceList.add(new clickableLight("Master Bedroom - Middle", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "マスターベッドルーム - ミドル"));
	    	result.next();
	    	applianceList.add(new clickableLight("Living Room - Center", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "リビングルーム - センター"));
	    	result.next();
	    	applianceList.add(new clickableLight("Kids Bedroom - Bottom Left", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "キッズベッドルーム - 左下"));
	    	result.next();
	    	applianceList.add(new clickableLight("Kids Bedroom - Bottom Right", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "キッズベッドルーム - 右下"));
	    	result.next();
	    	applianceList.add(new clickableLight("Kids Bedroom - Middle", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "キッズベッドルーム - ミドル"));
	    	result.next();
	    	applianceList.add(new clickableLight("Closet - Living Room - Left", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "クローゼット - リビングルーム - 左"));
	    	result.next();
	    	applianceList.add(new clickableLight("Bottom Bathroom", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "下のバスルーム"));
	    	result.next();
	    	applianceList.add(new clickableLight("Living Room - Top Left", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "リビングルーム - 左上"));
	    	result.next();
	    	applianceList.add(new clickableLight("Kitchen", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "キッチン"));
	    	result.next();
	    	applianceList.add(new clickableLight("Top Bathroom", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "トップバスルーム"));
	    	result.next();
	    	applianceList.add(new clickableLight("Closet Office", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "クローゼット事務所"));
	    	result.next();
	    	applianceList.add(new clickableLight("Office Top", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "オフィストップ"));
	    	result.next();
	    	applianceList.add(new clickableLight("Office Bottom", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "オフィスの下"));
	    	result.next();
	    	applianceList.add(new clickableLight("Garage", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "ガレージ"));
	    	result.next();
	    	applianceList.add(new Television("TV - Living Room", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "テレビ - リビングルーム"));
	    	result.next();
	    	applianceList.add(new Television("TV - Master Bedroom Room", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "テレビ - マスターベッドルーム"));
	    	result.next();
	    	applianceList.add(new GarageDoor("Garagedoor - Right", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "ガレージドア - 右"));
	    	result.next();
	    	applianceList.add(new GarageDoor("Garagedoor - Left", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "ガレージドア - 左"));
	    	
	    	result.next();
	    	applianceList.add(new Window("Window - Kitchen", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "窓 - キッチン"));
	    	result.next();
	    	applianceList.add(new Window("Window - Master Bedroom", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "窓 - マスターベッドルーム"));
	    	result.next();
	    	applianceList.add(new Window("Window - Kids Bedroom Top", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "窓 - 子供部屋トップ"));
	    	result.next();
	    	applianceList.add(new Window("Window - Master Bedroom Left", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "窓 - マスターベッドルーム"));
	    	result.next();
	    	applianceList.add(new Window("Window - Garage", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "窓 - ガレージ"));
	    	result.next();
	    	applianceList.add(new Window("Window - Living Room Right", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "窓 - リビングルーム右"));
	    	result.next();
	    	applianceList.add(new Window("Window - Living Room Left", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "窓 - 左の居間"));
	    	result.next();
	    	applianceList.add(new Window("Window - Kids Bedroom Bottom", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "窓 - キッズベッドルームボトム"));
	    	result.next();
	    	applianceList.add(new Window("Window - Office", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "事務所"));
	    	
	    	result.next();
	    	applianceList.add(new Door("Door - Living Room", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "ドア - リビングルーム"));
	    	result.next();
	    	applianceList.add(new Door("Door - Kitchen", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "ドア - キッチン"));
	    	
	    	result.next();
	    	applianceList.add(new Water("Water - Kitchen", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "水 - キッチン"));
	    	result.next();
	    	applianceList.add(new Water("Water - Master Bath Sink", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "水 - マスターバスシンク"));
	    	result.next();
	    	applianceList.add(new Water("Water - Master Bath Shower", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "水 - マスターバスシャワー"));
	    	result.next();
	    	applianceList.add(new Water("Water - Living Room Bath", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "水 - リビングルームバス"));
	    	result.next();
	    	applianceList.add(new Water("Water - Living Room Sink", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "水 - 居間の流し"));
	    	
	    	result.next();
	    	applianceList.add(new CookingAppliance("Oven", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "オーブン"));
	    	result.next();
	    	applianceList.add(new CookingAppliance("Stove", result.getInt("xpos"), result.getInt("ypos"), result.getBoolean("ison"), "レンジ"));
    	//Initializes the appliances
    	////////////////////////////////////////////////////////////////////////	

    	
        
        ////////////////////////////////////////////////////////////////////////
        //Event handlers from click/drag appliances
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, 
        	       new EventHandler<MouseEvent>() {
        	           @Override
        	           public void handle(MouseEvent e) {
//        	        	   updateSaveFiles();
        	        	   if (dragIsEnabled){
        	        		   selectedAppliance = applianceThatWasSelected(applianceList, (int)e.getX(), (int) e.getY());
        	        		   System.out.println(selectedAppliance.getName());
        	        		   System.out.println("x");
        	        		   System.out.println(selectedAppliance.getxPos());
        	        		   System.out.println("y");
        	        		   System.out.println(selectedAppliance.getyPos());
        	        	   }
        	        	   else{
        	        		   updateAppliances(applianceList, (int)e.getX(), (int) e.getY());
        	        		   drawAllAppliances(gc, applianceList);
        	        	   }
        	           }
        	       });
        
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, 
        		new EventHandler<MouseEvent>(){
					@Override
					public void handle(MouseEvent event) {
						if(dragIsEnabled){
							if(!selectedAppliance.equals(nullLight)){
								selectedAppliance.setxPos((int) event.getX());
								selectedAppliance.setyPos((int) event.getY());
								resetHouseCanvas(gc);
								drawHouse(gc);
								drawAllAppliances(gc, applianceList);
							}
						}
						else{}
					}
        });
        //Event handlers from click/drag appliances
        ////////////////////////////////////////////////////////////////////////
        
        
        
      //Event Handlers for graph radio buttons
        this.sevenDayRadioButton.selectedProperty().addListener(event->{
        	if(this.sevenDayRadioButton.isSelected()){
        		System.out.println("7 days selected");
        		Wtest28values.clear();
        		Ctest28values.clear();
        		Etest28values.clear();
        		Connection connection1;
				try {
					connection1 = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "TrafalgarLaw18");
					ResultSet result1 = connection1.createStatement().executeQuery("select * from public.fourweekevaluation;");
	                connection1.close();
	                int i = 0;
	                while(i < 4) {
	                	result1.next();
	                	for (int j = 0; j < 4; j++) {
	                		Wtest28values.add(result1.getInt("waterusage"));
	                		Etest28values.add(result1.getInt("powerusage"));
	                		Ctest28values.add((int) Math.ceil(result1.getDouble("cost")));
	                		System.out.println(Wtest28values.get(i));
	                	}
	                	i++;
	                }
				} catch (Exception e1) {
					System.out.println("Error Occurred");
					this.setGraphArraysWithRandomNumber();
				}
        		graphDrawing.drawGraph(graph_canvas, (int)graphCanvas.getWidth(), (int)graphCanvas.getHeight(), 
                		Wtest28values,
                		Etest28values,
                		Ctest28values,
                		japIsSelected);
        		updateGraphValLabels();
        		selectedGraphTimeFrame = 1;
        		setTimeLabels(selectedGraphTimeFrame);
        	}
        });
        this.sixMonthsRadioButton.selectedProperty().addListener(event->{
        	if(this.sixMonthsRadioButton.isSelected()){
        		System.out.println("6 Month selected");
        		//this.setGraphArraysWithRandomNumber();
        		Wtest28values.clear();
        		Ctest28values.clear();
        		Etest28values.clear();
        		Connection connection1;
				try {
					connection1 = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "TrafalgarLaw18");
					ResultSet result1 = connection1.createStatement().executeQuery("select * from public.yearlyevaluation;");
	                connection1.close();
	                int i = 0;
	                while(i < 14) {
	                	result1.next();
	                	for (int j = 0; j < 2; j++) {
	                		Wtest28values.add(result1.getInt("waterusage"));
	                		Etest28values.add(result1.getInt("powerusage"));
	                		Ctest28values.add((int) Math.ceil(result1.getDouble("costs")));
	                		System.out.println(Wtest28values.get(i));
	                	}
	                	i++;
	                }
				} catch (Exception e1) {
					this.setGraphArraysWithRandomNumber();
				}
        		graphDrawing.drawGraph(graph_canvas, (int)graphCanvas.getWidth(), (int)graphCanvas.getHeight(), 
                		Wtest28values,
                		Etest28values,
                		Ctest28values,
                		japIsSelected);
        	}
        	updateGraphValLabels();
        	selectedGraphTimeFrame = 2;
        	setTimeLabels(selectedGraphTimeFrame);
        });
        this.fourWeeksRadioButton.selectedProperty().addListener(event->{
        	if(this.fourWeeksRadioButton.isSelected()){
        		System.out.println("4 weeks selected");
        		//this.setGraphArraysWithRandomNumber();
        		Wtest28values.clear();
        		Ctest28values.clear();
        		Etest28values.clear();
        		Connection connection1;
				try {
					connection1 = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "TrafalgarLaw18");
					ResultSet result1 = connection1.createStatement().executeQuery("select * from public.fourweekevaluation;");
	                connection1.close();
	                int i = 0;
	                while(result1.next()) {
	                	Wtest28values.add(result1.getInt("waterusage"));
	                	Etest28values.add(result1.getInt("powerusage"));
	                	Ctest28values.add((int) Math.ceil(result1.getDouble("cost")));
	                	System.out.println(Wtest28values.get(i));
	                	i++;
	                }
				} catch (Exception e1) {
					this.setGraphArraysWithRandomNumber();
				}
        		graphDrawing.drawGraph(graph_canvas, (int)graphCanvas.getWidth(), (int)graphCanvas.getHeight(), 
                		Wtest28values,
                		Etest28values,
                		Ctest28values,
                		japIsSelected);
        	}
        	updateGraphValLabels();
        	selectedGraphTimeFrame = 3;
        	setTimeLabels(selectedGraphTimeFrame);
        });
        this.twelveMonthsRadioButton.selectedProperty().addListener(event->{
        	if(this.twelveMonthsRadioButton.isSelected()){
        		System.out.println("12 months selected");
        		//this.setGraphArraysWithRandomNumber();
        		Wtest28values.clear();
        		Ctest28values.clear();
        		Etest28values.clear();
        		Connection connection1;
				try {
					connection1 = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "TrafalgarLaw18");
					ResultSet result1 = connection1.createStatement().executeQuery("select * from public.yearlyevaluation;");
	                connection1.close();
	                int i = 0;
	                while(result1.next()) {
	                	Wtest28values.add(result1.getInt("waterusage"));
	                	Etest28values.add(result1.getInt("powerusage"));
	                	Ctest28values.add((int) Math.ceil(result1.getDouble("costs")));
	                	System.out.println(Wtest28values.get(i));
	                	i++;
	                }
				} catch (Exception e1) {
					this.setGraphArraysWithRandomNumber();
				}
        		graphDrawing.drawGraph(graph_canvas, (int)graphCanvas.getWidth(), (int)graphCanvas.getHeight(), 
                		Wtest28values,
                		Etest28values,
                		Ctest28values,
                		japIsSelected);
        	}
        	updateGraphValLabels();
        	selectedGraphTimeFrame = 4;
        	setTimeLabels(selectedGraphTimeFrame);
        });
        //Event Handlers for graph radio buttons
        ///////////////////////////////////////////////////////////////////////
        
        
        
        ///////////////////////////////////////////////////////////////////////
        //Event Handler for language toggles
        this.languageToggle.selectedToggleProperty().addListener(event ->{
        	if(englishRadioButton.isSelected()){
    			System.out.println("English button selected");
    			japIsSelected = false;
    			setToEnglish();
    			setTimeLabels(selectedGraphTimeFrame);
    			graphDrawing.drawGraph(graph_canvas, (int)graphCanvas.getWidth(), (int)graphCanvas.getHeight(), 
    	        		Wtest28values,
    	        		Etest28values,
    	        		Ctest28values,
    	        		japIsSelected);
    			this.sensorTable.refresh();
    		}
        	else{
    			System.out.println("Japanese button selected");
    			japIsSelected = true;
    			setToJapanese();
    			setTimeLabels(selectedGraphTimeFrame);
    			graphDrawing.drawGraph(graph_canvas, (int)graphCanvas.getWidth(), (int)graphCanvas.getHeight(), 
    	        		Wtest28values,
    	        		Etest28values,
    	        		Ctest28values,
    	        		japIsSelected);
    			this.sensorTable.refresh();
        	}
        });
        //Event Handler for language toggles
        ///////////////////////////////////////////////////////////////////////


        
        //////////////////////////////////////////////////////////////
        //Drawing
        bgGraphCanvas.setFill(new LinearGradient(0, 0, 1, 1, true,
                CycleMethod.REFLECT,
                new Stop(0, Color.LIGHTBLUE),
                new Stop(1, Color.BISQUE)));
        
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
        bgGraphCanvas.fillRect(0, 0, backgroundGraphCanvas.getWidth(), backgroundGraphCanvas.getHeight());
        
        
        //Interface drawing
        drawHouse(gc);
        drawAllAppliances(gc, applianceList);
        //Interface drawing
        
        
        //Graph page drawing
        graphDrawing.drawGraph(graph_canvas, (int)graphCanvas.getWidth(), (int)graphCanvas.getHeight(), 
        		Wtest28values,
        		Etest28values,
        		Ctest28values,
        		japIsSelected);
        //Graph Page drawing
        
        
        //Drawing
        ////////////////////////////////////////////////////////////////////
	}
    
    //initialize block
///////////////////////////////////////////////////////////////////////////////////
    
    
    
///////////////////////////////////////////////////////////////////////////////////
    //Various Methods
    
    public void drawHouse(GraphicsContext gc){
    	URL url = this.getClass().getClassLoader().getResource("resized_House_closeup.png");
    	try {
			System.out.println(url.toURI());
			File file = new File(url.toURI());
			String imagePath = "File:" + file.getAbsolutePath();
			Image image = new Image(imagePath);
			gc.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public Appliance applianceThatWasSelected(ObservableList<Appliance> lst, int x, int y){
    	Appliance appliance = nullLight;
    	for (int i = 0; i < lst.size(); i++) {
    		Appliance a = lst.get(i);
    		System.out.println(a.getType());
    		if(a.getType().equals("Light") || a.getType().equals("光")){
    			if((a.getxPos() <= x) 
    				&& 
    				(a.getxPos() + 20 >= x)
    				&& 
    				(a.getyPos() <= y)
    				&&
    				(a.getyPos() + 20 >= y )){
    			appliance = a;
    			return a;
    			}
    		}
    		if(a.getType().equals("Water") || a.getType().equals("水")){
    			if((a.getxPos() <= x) 
    				&& 
    				(a.getxPos() + 20 >= x)
    				&& 
    				(a.getyPos() <= y)
    				&&
    				(a.getyPos() + 20 >= y )){
    			appliance = a;
    			return a;
    			}
    		}
    		if(a.getType().equals("TV") || a.getType().equals("テレビ")){
    			if((a.getxPos() <= x) 
    				&& 
    				(a.getxPos() + 20 >= x)
    				&& 
    				(a.getyPos() <= y)
    				&&
    				(a.getyPos() + 49 >= y )){
    			appliance = a;
    			return a;
    			}
    		}
    		if(a.getType().equals("GarageDoor") || a.getType().equals("ガレージのドア")){
    			if((a.getyPos() <= y) 
    				&& 
    				(a.getyPos() + 20 >= y)
    				&& 
    				(a.getxPos() <= x)
    				&&
    				(a.getxPos() + 49 >= x )){
    			appliance = a;
    			return a;
    			}
    		}
    		if(a.getType().equals("Window") || a.getType().equals("窓")){
    			if((a.getyPos() <= y) 
    				&& 
    				(a.getyPos() + 14 >= y)
    				&& 
    				(a.getxPos() <= x)
    				&&
    				(a.getxPos() + 23 >= x )){
    			appliance = a;
    			return a;
    			}
    		}
    		if(a.getType().equals("Door") || a.getType().equals("ドア")){
    			if((a.getyPos() <= y) 
    				&& 
    				(a.getyPos() + 14 >= y)
    				&& 
    				(a.getxPos() <= x)
    				&&
    				(a.getxPos() + 23 >= x )){
    			appliance = a;
    			return a;
    			}
    		}
    		if(a.getType().equals("CookingAppliance") || a.getType().equals("調理器具")){
    			if((a.getyPos() <= y) 
    				&& 
    				(a.getyPos() + 14 >= y)
    				&& 
    				(a.getxPos() <= x)
    				&&
    				(a.getxPos() + 23 >= x )){
    			appliance = a;
    			return a;
    			}
    		}
		}
    	return appliance;
    }

    public void updateAppliances(ObservableList<Appliance> lst, int x, int y){
    	Appliance a = applianceThatWasSelected(lst, x, y);
    	if(!a.equals(this.nullLight)){
    		if(a.englishType.get().equals("CookingAppliance") && !a.isOn())
    		{
    			Alert alert = new Alert(AlertType.INFORMATION);
    			if(this.japIsSelected)
    			{
        			alert.setHeaderText("あなたは手動でストーブ/オーブンをつけなければなりません。");
        			alert.setTitle("キッチン家電規制");
        			alert.showAndWait();
    			}
    			else
    			{
        			alert.setHeaderText("You must turn the stove/oven on manually.");
        			alert.setTitle("Kitchen Appliance Restriction");
        			alert.showAndWait();
    			}

    		}
    		else
    		{
        		a.switch_();
        		Connection conn;
    			try {
    				conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "TrafalgarLaw18");
    				if (a.isOn()) {
    	        		conn.createStatement().executeQuery("UPDATE public.appliances SET ison = true WHERE xpos = " + a.xPos + "AND ypos = " + a.yPos + ";");
    	        	}
    	        	else {
    	        		conn.createStatement().executeQuery("UPDATE public.appliances SET ison = false WHERE xpos = " + a.xPos + "AND ypos = " + a.yPos + ";");
    	        	}
    	        	conn.close();
    			} 
    			catch (Exception e) {
    				System.out.println("Updated");
    			}
        		this.sensorTable.refresh();
    		}
    	}
    }
      
    public void drawAllAppliances(GraphicsContext gc, ObservableList<Appliance> lst){
    	for (int i = 0; i < lst.size(); i++) {
    		Appliance a = lst.get(i);
    		if(a.getType().equals("Light") || a.getType().equals("光")){
    			if(a.isOn()){drawOnLight(gc, a.getxPos(), a.getyPos());}
    			else{drawOffLight(gc, a.getxPos(), a.getyPos());}
    		}
    		if(a.getType().equals("TV") || a.getType().equals("テレビ")){
    			if(a.isOn()){drawOnTV(gc, a.getxPos(), a.getyPos());}
    			else{drawOffTV(gc, a.getxPos(), a.getyPos());}
    		}
    		if(a.getType().equals("GarageDoor") || a.getType().equals("ガレージのドア")){
    			if(a.isOn()){drawOpenGarageDoor(gc, a.getxPos(), a.getyPos());}
    			else{drawClosedGarageDoor(gc, a.getxPos(), a.getyPos());}
    		}
    		if(a.getType().equals("Window") || a.getType().equals("窓")){
    			if(a.isOn()){drawOpenWindow(gc, a.getxPos(), a.getyPos());}
    			else{drawClosedWindow(gc, a.getxPos(), a.getyPos());}
    		}
    		if(a.getType().equals("Door") || a.getType().equals("ドア")){
    			if(a.isOn()){drawOpenDoor(gc, a.getxPos(), a.getyPos());}
    			else{drawClosedDoor(gc, a.getxPos(), a.getyPos());}
    		}
    		if(a.getType().equals("Water") || a.getType().equals("水")){
    			if(a.isOn()){drawOnWater(gc, a.getxPos(), a.getyPos());}
    			else{drawOffWater(gc, a.getxPos(), a.getyPos());}
    		}
    		if(a.getType().equals("CookingAppliance") || a.getType().equals("調理器具")){
    			if(a.isOn()){drawOnCookingAppliance(gc, a.getxPos(), a.getyPos());}
    			else{drawOffCookingAppliance(gc, a.getxPos(), a.getyPos());}
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
    
    public void drawOnWater(GraphicsContext gc, int x_pos, int y_pos){
    	gc.setFill(Color.BLACK);
    	gc.fillOval(x_pos-1, y_pos-1, 19, 19);
    	gc.setFill(Color.CORNFLOWERBLUE);
    	gc.fillOval(x_pos, y_pos, 17, 17);
    	gc.setFill(Color.BLACK);
    	gc.fillText("W", x_pos + 3, y_pos + 13); 
    }
    
    public void drawOffWater(GraphicsContext gc, int x_pos, int y_pos){
    	gc.setFill(Color.CORNFLOWERBLUE);
    	gc.fillOval(x_pos-1, y_pos-1, 19, 19);
    	gc.setFill(Color.BLACK);
    	gc.fillOval(x_pos, y_pos, 17, 17);
    	gc.setFill(Color.CORNFLOWERBLUE);
    	gc.fillText("W", x_pos + 3, y_pos + 13);
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
    
    public void drawOpenGarageDoor(GraphicsContext gc, int x_pos, int y_pos){
    	gc.setFill(Color.BLACK);
    	gc.fillRect(x_pos-1, y_pos-1, 49, 19);
    	gc.setFill(Color.WHITE);
    	gc.fillRect(x_pos, y_pos, 47, 17);
    	gc.setFill(Color.BLACK);
    	gc.fillText("G", x_pos + 12, y_pos + 15);
    	gc.fillText("D", x_pos + 24, y_pos + 15);
    }
    
    public void drawClosedGarageDoor(GraphicsContext gc, int x_pos, int y_pos){
    	gc.setFill(Color.WHITE);
    	gc.fillRect(x_pos-1, y_pos-1, 49, 19);
    	gc.setFill(Color.BLACK);
    	gc.fillRect(x_pos, y_pos, 47, 17);
    	gc.setFill(Color.WHITE);
    	gc.fillText("G", x_pos + 12, y_pos + 15);
    	gc.fillText("D", x_pos + 24, y_pos + 15);
    }
    
    public void drawOpenWindow(GraphicsContext gc, int x_pos, int y_pos){
    	gc.setFill(Color.BLACK);
    	gc.fillRect(x_pos-1, y_pos-1, 23, 14);
    	gc.setFill(Color.WHITE);
    	gc.fillRect(x_pos, y_pos, 21, 12);
    	gc.setFill(Color.BLACK);
    	gc.fillText("W", x_pos + 5, y_pos + 11);
    }
    
    public void drawClosedWindow(GraphicsContext gc, int x_pos, int y_pos){
    	gc.setFill(Color.WHITE);
    	gc.fillRect(x_pos-1, y_pos-1, 23, 14);
    	gc.setFill(Color.BLACK);
    	gc.fillRect(x_pos, y_pos, 21, 12);
    	gc.setFill(Color.WHITE);
    	gc.fillText("W", x_pos+5 , y_pos +11);
    }
    
    public void drawOpenDoor(GraphicsContext gc, int x_pos, int y_pos){
    	gc.setFill(Color.BLACK);
    	gc.fillRect(x_pos-1, y_pos-1, 23, 14);
    	gc.setFill(Color.WHITE);
    	gc.fillRect(x_pos, y_pos, 21, 12);
    	gc.setFill(Color.BLACK);
    	gc.fillText("D", x_pos + 5, y_pos + 11);
    }
    public void drawClosedDoor(GraphicsContext gc, int x_pos, int y_pos){
    	gc.setFill(Color.WHITE);
    	gc.fillRect(x_pos-1, y_pos-1, 23, 14);
    	gc.setFill(Color.BLACK);
    	gc.fillRect(x_pos, y_pos, 21, 12);
    	gc.setFill(Color.WHITE);
    	gc.fillText("D", x_pos + 5, y_pos + 11);
    }
    public void drawOnCookingAppliance(GraphicsContext gc, int x_pos, int y_pos){
    	gc.setFill(Color.BLACK);
    	gc.fillRect(x_pos-1, y_pos-1, 14, 14);
    	gc.setFill(Color.RED);
    	gc.fillRect(x_pos, y_pos, 12, 12);
    	gc.setFill(Color.BLACK);
    	gc.fillText("C", x_pos, y_pos + 11);
    }
    public void drawOffCookingAppliance(GraphicsContext gc, int x_pos, int y_pos){
    	gc.setFill(Color.RED);
    	gc.fillRect(x_pos-1, y_pos-1, 14, 14);
    	gc.setFill(Color.BLACK);
    	gc.fillRect(x_pos, y_pos, 12, 12);
    	gc.setFill(Color.RED);
    	gc.fillText("C", x_pos, y_pos +11);
    }
    
    public void setToEnglish(){
    	TemperatureLabel.setText("Temperature");
    	InternalLabel.setText("Internal");
    	ExternalLabel.setText("External");
    	HVACLabel.setText("HVAC");
    	powerCostEstimationLabel.setText("Power Cost Estimation");
    	monthlyLabel.setText("Monthly");
    	weeklyLabel.setText("Weekly");
    	yearlyLabel.setText("Yearly");
    	sevenDayRadioButton.setText("7 Days");
    	fourWeeksRadioButton.setText("4 Weeks");
    	sixMonthsRadioButton.setText("6 Months");
    	twelveMonthsRadioButton.setText("12 Months");
    	dragModeRadioButton.setText("Drag Mode");
    	onOffRadioButton.setText("On/Off Mode");
//    	englishRadioButton.setText("English");
//    	japaneseRadioButton.setText("Japanese");
    	this.modeSelectionLabel.setText("Mode Selection");
    	this.langaugeSelectionLabel.setText("Language Selection");
    	this.switchSelectedButton.setText("Switch Selected");
    	this.descriptionColumn.setText("Description");
    	this.statusColumn.setText("On/Open");
    	this.typeColumn.setText("Type");
    	this.houseViewTab.setText("House View");
    	this.adminTab.setText("Administration");
    	this.graphTab.setText("Spending Graph");
    	this.doorsCloseButton.setText("D/W Close");
    	this.lightsOffButton.setText("Lights Off");
    	this.lightsOnButton.setText("Lights On");
    	this.waterOffButton.setText("Water Off");
    	
    	for (int i = 0; i < this.applianceList.size(); i++) {
    		applianceList.get(i).switchLangauge();
		}
    }
    
    public void setToJapanese(){
    	TemperatureLabel.setText("気温");
    	InternalLabel.setText("内");
    	ExternalLabel.setText("外");
    	HVACLabel.setText("冷暖房");
    	powerCostEstimationLabel.setText("電力コストの見積もり");
    	monthlyLabel.setText("毎月");
    	weeklyLabel.setText("毎週");
    	yearlyLabel.setText("毎年");
    	sevenDayRadioButton.setText("週間");
    	fourWeeksRadioButton.setText("ヶ月");
    	sixMonthsRadioButton.setText("6ヶ月");
    	twelveMonthsRadioButton.setText("年間");
    	dragModeRadioButton.setText("ラッグモード");
    	onOffRadioButton.setText("オン/オフモード");
//    	englishRadioButton.setText("英語");
//    	japaneseRadioButton.setText("日本語");
    	this.modeSelectionLabel.setText("モード選択");
    	this.langaugeSelectionLabel.setText("言語選択");
    	this.switchSelectedButton.setText("択したスイッチ");
    	this.descriptionColumn.setText("説明");
    	this.statusColumn.setText("オン/オープン");
    	this.typeColumn.setText("タイプ");
    	this.houseViewTab.setText("ハウスビュー");
    	this.adminTab.setText("管理");
    	this.graphTab.setText("支出グラフ");
    	this.doorsCloseButton.setText("D/W 閉じる");
    	this.lightsOffButton.setText("消灯");
    	this.lightsOnButton.setText("点灯");
    	this.waterOffButton.setText("zウォーターオフ");
    	
    	for (int i = 0; i < this.applianceList.size(); i++) {
    		applianceList.get(i).switchLangauge();
		}
    }
    
    public void updateGraphValLabels(){
    	int waterMax = graphDrawing.getMax(Wtest28values);
    	int elecMax = graphDrawing.getMax(Etest28values);
    	int costMax = graphDrawing.getMax(Ctest28values);
    	
    	int waterMin = graphDrawing.getMin(Wtest28values);
    	int elecMin = graphDrawing.getMin(Etest28values);
    	int costMin = graphDrawing.getMin(Ctest28values);
    	
    	this.maxGraphValues.setText(waterMax + "gal, " + elecMax + "kwh, " + "$" + costMax);
    	this.minGraphVal.setText(waterMin + "gal, " + elecMin + "kwh, " + "$" + costMin);
    }
    
    public void setGraphArraysWithRandomNumber(){
    	Wtest28values.clear();
    	Ctest28values.clear();
    	Etest28values.clear();
		
    	for (int i = 0; i < 28; i++) {
    		Wtest28values.add((int)(Math.random() * 100));
		}
    	for (int i = 0; i < 28; i++) {
    		Ctest28values.add((int)(Math.random() * 100));
		}
    	for (int i = 0; i < 28; i++) {
    		Etest28values.add((int)(Math.random() * 100));
		}
    }
    
    public void setTimeLabels(int i){
    	if(i == 1){
    		if(this.japIsSelected){
        		this.graphTimeLabel.setText("7日");
        		this.halfGraphTimeLabel.setText("3.5日");
    		}
    		else{
        		this.graphTimeLabel.setText("7 Days");
        		this.halfGraphTimeLabel.setText("3.5 Days");
    		}
    	}
    	if(i == 2){
    		if(this.japIsSelected){
            	this.graphTimeLabel.setText("6ヵ月");
        		this.halfGraphTimeLabel.setText("12か月");
    		}
    		else{
            	this.graphTimeLabel.setText("6 Months");
        		this.halfGraphTimeLabel.setText("3 Months");
    		}
    	}
    	if(i == 3){
    		if(this.japIsSelected){
    			this.graphTimeLabel.setText("4週間");
        		this.halfGraphTimeLabel.setText("2週間");
    		}
    		else{
    			this.graphTimeLabel.setText("4 Weeks");
        		this.halfGraphTimeLabel.setText("2 Weeks");
    		}
    	}
    	if(i == 4){
    		if(this.japIsSelected){
    			this.graphTimeLabel.setText("12ヵ月");
        		this.halfGraphTimeLabel.setText("6ヵ月");
    		}
    		else{
    			this.graphTimeLabel.setText("12 Months");
        		this.halfGraphTimeLabel.setText("6 Months");
    		}
    	}
    }
    
    public void resetHouseCanvas(GraphicsContext gc){
    	gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(new LinearGradient(0, 0, 1, 1, true,
                CycleMethod.REFLECT,
                new Stop(0, Color.LIGHTBLUE),
                new Stop(1, Color.BISQUE)));
        
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

//End of Class
}