package cap_stone;

public class clickableLight implements IAppliance{

	public clickableLight(String name, int xPos, int yPos, boolean isOn){
		this.setName(name);
		this.xPos = xPos;
		this.yPos = yPos;
		this.isOn = isOn;
	}
	
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public int getxPos() {return xPos;}
	public void setxPos(int xPos) {this.xPos = xPos;}

	public int getyPos() {return yPos;}
	public void setyPos(int yPos) {this.yPos = yPos;}

	public boolean isOn() {return isOn;}
	public void setOn(boolean isOn) {this.isOn = isOn;}
	
	public void switch_(){
		if(this.isOn){this.isOn = false;}
		else{this.isOn = true;}
	}

	



	private int xPos;
	private int yPos;
	private boolean isOn;
	private String name;
}
