package com.seancheey.data;

import java.util.ArrayList;

public class RCMovement extends RCComponent {
	public final int maxspeed, carrymass;

	public RCMovement(ArrayList<String> paramList) {
		super(paramList);
		int i = 7;
		maxspeed = Integer.parseInt(paramList.get(i));
		i++;
		carrymass = Integer.parseInt(paramList.get(i));
	}

	public RCMovement(RCComponent component) {
		super(component.name, component.cpu, component.hp, component.shield, component.mass, component.price,
				component.rarity);
		maxspeed = 0;
		carrymass = 0;
	}

	public RCMovement(String name, int cpu, int hp, int shield, double mass, int price, int rarity, int maxspeed,
			int carrymass) {
		super(name, cpu, hp, shield, mass, price, rarity);
		this.maxspeed = maxspeed;
		this.carrymass = carrymass;
	}

}
