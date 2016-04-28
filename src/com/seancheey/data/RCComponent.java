package com.seancheey.data;

public class RCComponent {
	public final int cpu, hp, shield;
	public final double mass;
	public final String name;

	public RCComponent(String name, int cpu, int hp, int shield, double mass) {
		super();
		this.name = name;
		this.cpu = cpu;
		this.hp = hp;
		this.shield = shield;
		this.mass = mass;
	}

	@Override
	public String toString() {
		return "RCComponent [cpu=" + cpu + ", hp=" + hp + ", shield=" + shield + ", mass=" + mass + ", name=" + name
				+ "]";
	}
}
