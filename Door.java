package cap_stone;

public class Door extends Appliance{
	public Door(String name, int xPos, int yPos, boolean isOn, String japName){
		super(name, xPos, yPos,isOn, japName);
		super.type.set("Door");
		super.englishType.set("Door");
		super.japaneseType.set("ドア");
	}
}
