/**
 * A holder class tracking details of appliances that use electricity and that can be turned on and off.
 * */

package cap_stone.Appliances;

public class SwitchedElectronic extends Electronic {

	private double startTime = 0;
	private double endTime = 0;
	private boolean isOn = false;

	public SwitchedElectronic(String name) {
		super(name);
	}

	public SwitchedElectronic(String name, int wattage, int avg) {
		super(name, wattage, avg);
	}

	public boolean getState() {
		return isOn;
	}

	public void setState(boolean state) {
		this.isOn = state;
	}

	public int turnOn() {
		if (!this.getState()) {
			this.startTime = System.nanoTime();
			this.setState(true);
			return 0;
		}
		return -1;
	}

	public int turnOff() {
		if (this.getState()) {
			this.endTime = System.nanoTime();
			this.calcNewTime();

			this.setStartTime(0);
			this.setEndTime(0);
			this.setState(false);
			return 0;
		}
		return -1;
	}

	private void calcNewTime() {
		double resultTime = (endTime - startTime) / 1E9;
		int newTime = (int) Math.round(resultTime);
		this.setUsageTime(this.getUsageTime() + newTime);
	}

	public String toString() {
		String str = super.toString();
		if (this.isOn) {
			str = str + "This device is on\n";
		} else {
			str = str + "This device is off\n";
		}
		return str;
	}

	public double getStartTime() {
		return startTime;
	}

	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	public double getEndTime() {
		return endTime;
	}

	public void setEndTime(double endTime) {
		this.endTime = endTime;
	}
}