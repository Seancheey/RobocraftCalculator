package com.seancheey.data;

import java.util.ArrayList;

public class RCMovement extends RCComponent {
	public final int maxspeed, carrymass;

	public RCMovement(RCComponent component) {
		super(component.name, component.cpu, component.hp, component.shield, component.mass);
		maxspeed = 0;
		carrymass = 0;
	}

	public RCMovement(ArrayList<String> paramList) {
		super(paramList);
		maxspeed = Integer.parseInt(paramList.get(5));
		carrymass = Integer.parseInt(paramList.get(6));
	}

	public RCMovement(String name, int cpu, int hp, int shield, double mass, int maxspeed, int carrymass) {
		super(name, cpu, hp, shield, mass);
		this.maxspeed = maxspeed;
		this.carrymass = carrymass;
	}

}
