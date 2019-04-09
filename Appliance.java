package cap_stone;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Appliance{

	public Appliance(String name, int xPos, int yPos, boolean isOn, String japName){
		this.setName(name);
		this.xPos = xPos;
		this.yPos = yPos;
		this.isOn.set(isOn);
		this.englishName.set(name);
		this.japaneseName.set(japName);
		if(isOn){
			isOnStringProperty.set("True");
		}
		else{
			isOnStringProperty.set("False");
		}
	}
	
	public String getType(){return this.type.get();}
	public void setType(String type){this.type.set(type);}
	public StringProperty typeProperty(){return type;}
	
	public String getIsOnString(){return isOnStringProperty.get();}
	public void setIsOnString(String name){isOnStringProperty.set(name);;}
	public StringProperty isOnStringProperty(){return isOnStringProperty;}
	
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
		System.out.println("Swicth gets called");
		if(this.isOn.get())
		{
			this.isOn.set(false);
//			this.isOnStringProperty.set("False");
			if(langIsEng){
				setStringBoolEng();
			}
			else{
				setStringBoolJap();
			}
		}
		else
		{
			this.isOn.set(true);
//			this.isOnStringProperty.set("True");
			if(langIsEng){
				setStringBoolEng();
			}
			else{
				setStringBoolJap();
			}
		}
	}

	public void switchLangauge(){
		if(type.get().equals(englishType.get())){
			type = japaneseType;
			name = japaneseName;
			setStringBoolJap();
			langIsEng = false;
		}
		else{
			type = englishType;
			name = englishName;
			setStringBoolEng();
			langIsEng = true;
		}
	}

	public void setStringBoolJap(){
		if(isOn.get()){
			isOnStringProperty = japTrue;
		}
		else{
			isOnStringProperty = japFalse;
		}
	}
	
	public void setStringBoolEng(){
		if(isOn.get()){
			isOnStringProperty = engTrue;
		}
		else{
			isOnStringProperty = engFalse;
		}
	}


	protected int xPos;
	protected int yPos;
	
	protected ObjectProperty<Boolean> isOn = new SimpleObjectProperty<Boolean>(null);
	
	protected StringProperty isOnStringProperty = new SimpleStringProperty();
	protected StringProperty japTrue = new SimpleStringProperty("本当の");
	protected StringProperty engTrue = new SimpleStringProperty("True");
	protected StringProperty japFalse = new SimpleStringProperty("偽");
	protected StringProperty engFalse = new SimpleStringProperty("False");
	
	protected StringProperty name = new SimpleStringProperty("");
	protected StringProperty englishName = new SimpleStringProperty("");
	protected StringProperty japaneseName = new SimpleStringProperty("");
	
	protected StringProperty type = new SimpleStringProperty("");
	protected StringProperty englishType = new SimpleStringProperty("");
	protected StringProperty japaneseType = new SimpleStringProperty("");

	protected Boolean langIsEng = true;
	
}
