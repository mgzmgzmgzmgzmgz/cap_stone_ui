package cap_stone;

public class GarageDoor extends Appliance{
	public GarageDoor(String name, int xPos, int yPos, boolean isOn, String japName){
		super(name, xPos, yPos,isOn, japName);
		super.type.set("GarageDoor");
		super.englishType.set("GarageDoor");
		super.japaneseType.set("ガレージのドア");
	}
}
