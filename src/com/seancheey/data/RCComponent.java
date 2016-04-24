package com.seancheey.data;

public class RCComponent {
	public final int cpu, rr, hp, shield;
	public final double mass, healRate;
	public final String name;

	public RCComponent(String name, int cpu, int rr, int hp, int shield, double mass, double healRate) {
		super();
		this.name = name;
		this.cpu = cpu;
		this.rr = rr;
		this.hp = hp;
		this.shield = shield;
		this.mass = mass;
		this.healRate = healRate;
	}

	@Override
	public String toString() {
		return "Component [name=" + name + ", cpu=" + cpu + ", rr=" + rr + ", hp=" + hp + ", shield=" + shield
				+ ", mass=" + mass + ", healRate=" + healRate + "]";
	}

}
