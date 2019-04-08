package cap_stone;

public interface IAppliance {
	
	public String getType();
	public void setType(String type);

	public String getName();
	public void setName(String name);
	
	public int getxPos();
	public void setxPos(int xPos);

	public int getyPos();
	public void setyPos(int yPos);

	public boolean isOn();
	public void setOn(boolean isOn);
	
	public void switch_();
}
