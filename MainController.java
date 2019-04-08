package cap_stone;

import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private Label midGraphVal;
    
    @FXML
    private Label halfGraphTimeLabel;

    @FXML
    private Label graphTimeLabel;
    
    @FXML
    private Label modeSelectionLabel;

    @FXML
    private Label langaugeSelectionLabel;
    
    @FXML
    private TableView<IAppliance> sensorTable;

    @FXML
    private TableColumn<IAppliance, String> typeColumn;

    @FXML
    private TableColumn<IAppliance, String> descriptionColumn;

    @FXML
    private TableColumn<IAppliance, Boolean> statusColumn;

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


//    @FXML
//    void languageSelectionAction(ActionEvent event) {
//    	RadioButton r = (RadioButton)event.getTarget();
//    	if(r.equals(englishRadioButton)){
//    			System.out.println("English button selected");
//    			japIsSelected = false;
//    			setToEnglish();
//    			setTimeLabels(selectedGraphTimeFrame);
//    		}
//    	if(r.isSelected()){
//    		if(r.equals(japaneseRadioButton)){
//    			System.out.println("Japanese button selected");
//    			japIsSelected = true;
//    			setToJapanese();
//    			setTimeLabels(selectedGraphTimeFrame);
//    		}
//    	}
//    }
    

    @FXML
    void onSelectionModeSwitch(ActionEvent event) {
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
    
//    private ObjectProperty<String> HVACint = new SimpleObjectProperty<String>("69");
    private int selectedGraphTimeFrame = 1;
    private Boolean japIsSelected = false;
    private Boolean dragIsEnabled = false;
    private IAppliance nullLight = new clickableLight("Null", 0, 0, true);
    private IAppliance selectedAppliance;
    private IAppliance currentAppliance = new clickableLight("Null", 0, 0, true);
//    private ArrayList<IAppliance> applianceList;
    private ObservableList<IAppliance> applianceList = FXCollections.observableArrayList();
    private GraphDrawingClass graphDrawing = new GraphDrawingClass();
    
    private ArrayList<Integer> Wtest28values = new ArrayList<Integer>();
    private ArrayList<Integer> Ctest28values = new ArrayList<Integer>();
    private ArrayList<Integer> Etest28values = new ArrayList<Integer>();
    
    public ObservableList<IAppliance> getApplianceList(){
    	return this.applianceList;
    }
    
    
///////////////////////////////////////////////////////////////////////////////////
  //initialize block
    
    public void initialize()
    {
    	//Creates graphics objects to draw in the canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gc_bl = bottom_left_canvas.getGraphicsContext2D();
        GraphicsContext gc_br = bottom_right_canvas.getGraphicsContext2D();
        GraphicsContext graph_canvas = graphCanvas.getGraphicsContext2D();
        GraphicsContext bgGraphCanvas = backgroundGraphCanvas.getGraphicsContext2D();
        //Creates graphics objects to draw in the canvas

        
    	this.internalTempTextField.setText("69");
    	this.internalTempTextField.editableProperty().set(false);
    	this.externalTempTextField.setText("62");
    	this.externalTempTextField.editableProperty().set(false);
    	this.HVACTextField.setText("69");
    	
    	
    	setGraphArraysWithRandomNumber();
    	updateGraphValLabels();
    	
    	this.sensorTable.setItems(applianceList);
    	this.descriptionColumn.setCellValueFactory(rowData -> rowData.getValue().nameProperty());
    	this.statusColumn.setCellValueFactory(rowData -> rowData.getValue().isOnProperty());
    	this.typeColumn.setCellValueFactory(rowData -> rowData.getValue().typeProperty());
    	
    	this.sensorTable.getSelectionModel().selectedItemProperty().addListener(
    			(ObservableValue<? extends IAppliance> observable, IAppliance newValue, IAppliance oldValue)->
    			{ currentAppliance = oldValue;
    			System.out.println(oldValue.getName());
    			System.out.println(this.currentAppliance.getName());});
    	
    	
    	/////////////////////////////////////////////////////////////////////////////////////////
    	//Button Event Handlers
    	this.minusButton.setOnAction(event->{
        	System.out.println("Clicked");
        	int i = Integer.parseInt(HVACTextField.textProperty().get());
        	i = i - 1;
        	HVACTextField.textProperty().set(""+i);
    	});
    	
    	this.plusButton.setOnAction(event->{
        	System.out.println("Clicked");
        	int i = Integer.parseInt(HVACTextField.textProperty().get());
        	i = i + 1;
        	HVACTextField.textProperty().set(""+i);
    	});
        this.switchSelectedButton.setOnAction(event ->{
        	this.currentAppliance.switch_();
        	drawHouse(gc);
        	drawAllAppliances(gc, applianceList);
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
    	applianceList.add(new clickableLight("Master Bedroom - Top Right", 77, 47, true));
    	applianceList.add(new clickableLight("Master Bedroom - Bottom Right", 76,116,true));
    	applianceList.add(new clickableLight("Master Bedroom - Middle", 116,76,true));
    	applianceList.add(new clickableLight("Living Room - Center", 342, 200,true));
    	applianceList.add(new clickableLight("Kids Bedroom - Bottom Left", 94, 257, true));
    	applianceList.add(new clickableLight("Kids Bedroom - Bottom Right", 131, 257, true));
    	applianceList.add(new clickableLight("Kids Bedroom - Middle", 104, 206, true));
    	applianceList.add(new clickableLight("Closet - Living Room - Left", 170, 259, true));
    	applianceList.add(new clickableLight("Bottom Bathroom", 186, 191, true));
    	applianceList.add(new clickableLight("Living Room - Top Left", 278, 162, true));
    	applianceList.add(new clickableLight("Kitchen", 345, 87, true));
    	applianceList.add(new clickableLight("Top Bathroom", 209, 31 ,true));
    	applianceList.add(new clickableLight("Closet Office", 185, 87, true));
    	applianceList.add(new clickableLight("Office Top", 282, 45, true));
    	applianceList.add(new clickableLight("Office Bottom", 246,80,true));
    	applianceList.add(new clickableLight("Garage", 476,200,true));
    	applianceList.add(new Television("TV - Living Room", 225, 178, true));
    	applianceList.add(new Television("TV - Master Bedroom Room", 163, 52, true));
    	applianceList.add(new GarageDoor("Garagedoor - Right", 510, 258, true));
    	applianceList.add(new GarageDoor("Garagedoor - Left", 409, 258, true));
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
        
        
        
 		///////////////////////////////////////////////////////////////////////
        //Event Handlers for graph radio buttons
        this.sevenDayRadioButton.selectedProperty().addListener(event->{
        	if(this.sevenDayRadioButton.isSelected()){
        		System.out.println("7 days selected");
        		this.setGraphArraysWithRandomNumber();
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
        		this.setGraphArraysWithRandomNumber();
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
        		this.setGraphArraysWithRandomNumber();
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
        		this.setGraphArraysWithRandomNumber();
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
    	String imagePath = "file:/Users/micahgiles/Desktop/Documents/workspace/CapStone_House_Project/src/cap_stone/resized_House_closeup.png";
    	Image image = new Image(imagePath);
    	gc.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
    }
    
    public IAppliance applianceThatWasSelected(ObservableList<IAppliance> lst, int x, int y){
    	IAppliance appliance = nullLight;
    	for (int i = 0; i < lst.size(); i++) {
    		IAppliance a = lst.get(i);
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
    				System.out.println("GD HIT");
    			appliance = a;
    			return a;
    			}
    		}
		}
    	return appliance;
    }

    public void updateAppliances(ObservableList<IAppliance> lst, int x, int y){
    	IAppliance a = applianceThatWasSelected(lst, x, y);
    	if(!a.equals(this.nullLight)){
    		a.switch_();
    	}
    }
      
    public void drawAllAppliances(GraphicsContext gc, ObservableList<IAppliance> lst){
    	for (int i = 0; i < lst.size(); i++) {
    		IAppliance a = lst.get(i);
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
    	gc.setFill(Color.YELLOW);
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
    	englishRadioButton.setText("English");
    	japaneseRadioButton.setText("Japanese");
    	this.modeSelectionLabel.setText("Mode Selection");
    	this.langaugeSelectionLabel.setText("Language Selection");
    	this.switchSelectedButton.setText("Switch Selected");
    	this.descriptionColumn.setText("Description");
    	this.statusColumn.setText("On/Open");
    	this.typeColumn.setText("Type");
    	this.houseViewTab.setText("House View");
    	this.adminTab.setText("Administration");
    	this.graphTab.setText("Spending Graph");
    	
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
    	englishRadioButton.setText("英語");
    	japaneseRadioButton.setText("日本語");
    	this.modeSelectionLabel.setText("モード選択");
    	this.langaugeSelectionLabel.setText("言語選択");
    	this.switchSelectedButton.setText("択したスイッチ");
    	this.descriptionColumn.setText("説明");
    	this.statusColumn.setText("オン/オープン");
    	this.typeColumn.setText("タイプ");
    	this.houseViewTab.setText("ハウスビュー");
    	this.adminTab.setText("管理");
    	this.graphTab.setText("支出グラフ");
    	
    	for (int i = 0; i < this.applianceList.size(); i++) {
    		applianceList.get(i).switchLangauge();
		}
    }
    
    public void updateGraphValLabels(){
    	int waterMax = graphDrawing.getMax(Wtest28values);
    	int elecMax = graphDrawing.getMax(Etest28values);
    	int costMax = graphDrawing.getMax(Ctest28values);
    	
    	this.maxGraphValues.setText(waterMax + "gal, " + elecMax + "kwh, " + "$" + costMax + "----");
    	this.midGraphVal.setText(waterMax/2 + "gal, " + elecMax/2 + "kwh, " + "$" + costMax/2 + "----");
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