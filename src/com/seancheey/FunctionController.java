package com.seancheey;

public interface FunctionController extends Controller {
	public int getCPUSum();

	public void setMaxCPU(int cpu);

	public int getMaxCPU();
	
	public void updateInfo();
}
