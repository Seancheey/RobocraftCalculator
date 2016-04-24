package com.seancheey;

import java.util.HashMap;

import com.seancheey.data.RCComponent;

public interface Controller {
	public void addComponent(RCComponent component, int number);

	public HashMap<RCComponent, Integer> getComponentsInfo();

	public void removeComponent(RCComponent component);

	public void setComponentNumber(RCComponent component, Integer number);
}
