package cap_stone;

public class CookingAppliance extends Appliance{
	public CookingAppliance(String name, int xPos, int yPos, boolean isOn, String japName){
		super(name, xPos, yPos,isOn, japName);
		super.type.set("CookingAppliance");
		super.englishType.set("CookingAppliance");
		super.japaneseType.set("調理器具");
	}	
}
