package cap_stone;

public class Window extends Appliance{
	public Window(String name, int xPos, int yPos, boolean isOn, String japName){
		super(name, xPos, yPos,isOn, japName);
		super.type.set("Window");
		super.englishType.set("Window");
		super.japaneseType.set("窓");
	}
}
