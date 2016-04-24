package com.seancheey;

import java.util.HashMap;

import com.seancheey.data.RCComponent;

public interface InfoModifier {
	public void addCube(int number);

	public HashMap<RCComponent, Integer> getSelectedComponentsInfo();

	public void updateInfo();

	public void deleteSlot(ItemSlot slot);
}
