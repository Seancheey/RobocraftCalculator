package com.seancheey.data;

public class RCMovement extends RCComponent {
	public final int maxspeed, carrymass;

	public RCMovement(RCComponent component) {
		super(component.name, component.cpu, component.hp, component.shield, component.mass, component.healRate);
		maxspeed = 0;
		carrymass = 0;
	}

	public RCMovement(String name, int cpu, int hp, int mass, int healRate, int shield, int maxspeed, int carrymass) {
		super(name, cpu, hp, mass, healRate, shield);
		this.maxspeed = maxspeed;
		this.carrymass = carrymass;
	}

}
