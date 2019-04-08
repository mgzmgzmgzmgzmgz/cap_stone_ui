package cap_stone;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class clickableLight implements IAppliance{

	public clickableLight(String name, int xPos, int yPos, boolean isOn){
		this.setName(name);
		this.xPos = xPos;
		this.yPos = yPos;
		this.isOn.set(isOn);
		this.englishName.set(name);
	}
	
	public clickableLight(String name, int xPos, int yPos, boolean isOn, String japName){
		this.setName(name);
		this.xPos = xPos;
		this.yPos = yPos;
		this.isOn.set(isOn);
		this.englishName.set(name);
		this.japaneseName.set(japName);
	}
	
	public String getType(){return this.type.get();}
	public void setType(String type){this.type.set(type);}
	public StringProperty typeProperty(){return type;}
	
	public String getName() {return name.get();}
	public void setName(String name) {this.name.set(name);}
	public StringProperty nameProperty(){return name;}
	
	public int getxPos() {return xPos;}
	public void setxPos(int xPos) {this.xPos = xPos;}

	public int getyPos() {return yPos;}
	public void setyPos(int yPos) {this.yPos = yPos;}

	public boolean isOn() {return isOn.get();}
	public void setOn(boolean isOn) {this.isOn.set(isOn);}
	public ObjectProperty<Boolean> isOnProperty(){return isOn;}
	
	public void switch_(){
		if(this.isOn.get()){this.isOn.set(false);}
		else{this.isOn.set(true);}
	}

	public void switchLangauge(){
		if(type.get().equals("Light")){
			type = japaneseType;
//			name = japaneseName;
		}
		else{
			type = englishType;
//			name = englishName;
		}
	}



	private int xPos;
	private int yPos;
	private ObjectProperty<Boolean> isOn = new SimpleObjectProperty<Boolean>(null);
	private StringProperty name = new SimpleStringProperty();
	private StringProperty type = new SimpleStringProperty("Light");
	private StringProperty englishType = new SimpleStringProperty("Light");
	private StringProperty japaneseType = new SimpleStringProperty("光");
	private StringProperty englishName = new SimpleStringProperty();
	private StringProperty japaneseName = new SimpleStringProperty();
}
