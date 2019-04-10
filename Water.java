package cap_stone;

public class Water extends Appliance{
	public Water(String name, int xPos, int yPos, boolean isOn, String japName){
		super(name, xPos, yPos,isOn, japName);
		super.type.set("Water");
		super.englishType.set("Water");
		super.japaneseType.set("水");
	}
}