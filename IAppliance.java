package cap_stone;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface IAppliance {
	
	public String getType();
	public void setType(String type);
	public StringProperty typeProperty();

	public String getName();
	public void setName(String name);
	public StringProperty nameProperty();
	
	public String getIsOnString();
	public void setIsOnString(String name);
	public StringProperty isOnStringProperty();
	
	public int getxPos();
	public void setxPos(int xPos);

	public int getyPos();
	public void setyPos(int yPos);

	public boolean isOn();
	public void setOn(boolean isOn);
	public ObjectProperty<Boolean> isOnProperty();
	
	public void switch_();
	
	public void switchLangauge();
}
