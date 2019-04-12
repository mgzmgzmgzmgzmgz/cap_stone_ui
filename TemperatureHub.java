package cap_stone;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class TemperatureHub {

	public TemperatureHub(int externalTemp, int internalTemp, int HVAC, int currentSetTemp){
		this.setExternalTemp(externalTemp);
		this.setInternalTemp(internalTemp);
		this.setHVAC(HVAC);
		this.setCurrentSetTemp(currentSetTemp);
	}
	
	private ObjectProperty<Integer> externalTemp = new SimpleObjectProperty<Integer>(null);
	public Integer getExternalTemp(){return externalTemp.get();}
	public void setExternalTemp(Integer integer){externalTemp.set(integer);};
	public ObjectProperty<Integer> externalTempProperty(){return externalTemp;};
	
	private ObjectProperty<Integer> HVAC = new SimpleObjectProperty<Integer>(null);
	public Integer getHVAC(){return HVAC.get();}
	public void setHVAC(Integer integer){HVAC.set(integer);};
	public ObjectProperty<Integer> HVACProperty(){return HVAC;};
	
	private ObjectProperty<Integer> internalTemp = new SimpleObjectProperty<Integer>(null);
	public Integer getInternalTemp(){return internalTemp.get();}
	public void setInternalTemp(Integer integer){internalTemp.set(integer);};
	public ObjectProperty<Integer> internalTempProperty(){return internalTemp;};
	
	private ObjectProperty<Integer> currentSetTemp = new SimpleObjectProperty<Integer>(null);
	public Integer getCurrentSetTemp(){return currentSetTemp.get();}
	public void setCurrentSetTemp(Integer integer){currentSetTemp.set(integer);};
	public ObjectProperty<Integer> currentSetTempProperty(){return currentSetTemp;};
	


}
