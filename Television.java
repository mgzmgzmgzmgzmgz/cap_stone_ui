package cap_stone;

public class Television extends Appliance{
	public Television(String name, int xPos, int yPos, boolean isOn, String japName){
		super(name, xPos, yPos,isOn, japName);
		super.type.set("TV");
		super.englishType.set("TV");
		super.japaneseType.set("テレビ");
	}
}
