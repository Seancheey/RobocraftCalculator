package com.seancheey;

import java.util.ArrayList;
import java.util.HashMap;

import com.seancheey.data.RCComponent;
import com.seancheey.data.RCMovement;
import com.seancheey.data.RCWeapon;

public abstract class AbstractFunctionController implements FunctionController {
	private HashMap<RCComponent, Integer> components;
	private int maxCPU = 1750;
	protected HashMap<RCWeapon, Integer> weapons;
	protected HashMap<RCMovement, Integer> movements;
	private int cpu, shield, hp;
	private double mass;

	public AbstractFunctionController() {
		components = new HashMap<>();
		weapons = new HashMap<>();
		movements = new HashMap<>();
	}

	@Override
	public void addComponent(RCComponent component, int number) {
		components.put(component, number);
		if (component instanceof RCWeapon) {
			weapons.put((RCWeapon) component, number);
		}
		if (component instanceof RCMovement) {
			movements.put((RCMovement) component, number);
		}
		updateInfo();
	}

	@Override
	public HashMap<RCComponent, Integer> getComponentsInfo() {
		return components;
	}

	@Override
	public void removeComponent(RCComponent component) {
		components.remove(component);
		if (component instanceof RCWeapon) {
			weapons.remove((RCWeapon) component);
		}
		if (component instanceof RCMovement) {
			movements.remove((RCMovement) component);
		}
		updateInfo();
	}

	@Override
	public void setComponentNumber(RCComponent component, Integer number) {
		components.put(component, number);
		if (component instanceof RCWeapon) {
			weapons.put((RCWeapon) component, number);
		}
		if (component instanceof RCMovement) {
			movements.put((RCMovement) component, number);
		}
		updateInfo();
	}

	@Override
	public void updateInfo() {
		int cpu = 0, hp = 0, shield = 0;
		double mass = 0;
		for (RCComponent c : components.keySet()) {
			int number = components.get(c);
			cpu += c.cpu * number;
			mass += c.mass * number;
			hp += c.hp * number;
			shield += c.shield * number;
		}
		this.cpu = cpu;
		this.hp = hp;
		this.shield = shield;
		this.mass = mass;
	}

	protected int getCpu() {
		return cpu;
	}

	protected int getShield() {
		return shield;
	}

	protected int getHp() {
		return hp;
	}

	protected double getMass() {
		return mass;
	}

	@Override
	public int getCPUSum() {
		return cpu;
	}

	@Override
	public void setMaxCPU(int cpu) {
		this.maxCPU = cpu;
	}

	@Override
	public ArrayList<WeaponCombination> getWeaponCombinations() {
		ArrayList<WeaponCombination> combinations = new ArrayList<>();
		for (RCWeapon weapon : weapons.keySet()) {
			combinations.add(new WeaponCombination(weapon, weapons.get(weapon)));
		}
		return combinations;
	}

	@Override
	public int getMaxCPU() {
		return maxCPU;
	}
}