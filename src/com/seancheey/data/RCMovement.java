package com.seancheey.data;

public class RCMovement extends RCComponent {
	public final int maxspeed, carrymass;

	public RCMovement(String name, int cpu, int rr, int hp, int mass, int healRate, int shield, int maxspeed,
			int carrymass) {
		super(name, cpu, rr, hp, mass, healRate, shield);
		this.maxspeed = maxspeed;
		this.carrymass = carrymass;
	}

}
