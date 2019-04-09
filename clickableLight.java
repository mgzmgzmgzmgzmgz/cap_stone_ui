package cap_stone;

public class clickableLight extends Appliance{
	public clickableLight(String name, int xPos, int yPos, boolean isOn, String japName){
		super(name, xPos, yPos,isOn, japName);
		super.type.set("Light");
		super.englishType.set("Light");
		super.japaneseType.set("光");
	}	
}
