package com.seancheey.data;

import java.util.ArrayList;

public class RCComponent {
	public final int cpu, hp, shield, price;
	public final double mass;
	public final String name;

	public RCComponent(String name, int cpu, int hp, int shield, double mass, int price) {
		super();
		this.name = name;
		this.cpu = cpu;
		this.hp = hp;
		this.shield = shield;
		this.mass = mass;
		this.price = price;
	}

	public RCComponent(ArrayList<String> paramList) {
		name = paramList.get(0);
		cpu = Integer.parseInt(paramList.get(1));
		hp = Integer.parseInt(paramList.get(2));
		shield = Integer.parseInt(paramList.get(3));
		mass = Double.parseDouble(paramList.get(4));
		price = Integer.parseInt(paramList.get(5));
	}

	@Override
	public String toString() {
		return "RCComponent [cpu=" + cpu + ", hp=" + hp + ", shield=" + shield + ", mass=" + mass + ", name=" + name
				+ "]";
	}
}
