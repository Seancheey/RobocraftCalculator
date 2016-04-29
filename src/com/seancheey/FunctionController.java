package com.seancheey;

import java.util.ArrayList;

public interface FunctionController extends Controller {
	public int getCPUSum();

	public void setMaxCPU(int cpu);

	public int getMaxCPU();
	
	public void updateInfo();
	
	public ArrayList<WeaponCombination> getWeaponCombinations();
}
