package com.seancheey.data;

import java.util.ArrayList;

public class RCWeapon extends RCComponent {
	public final int damage, nominalCount;
	public final double powerConsumption, singleRate, nominalRate;

	public RCWeapon(RCComponent component) {
		super(component.name, component.cpu, component.hp, component.shield, component.mass, component.price,
				component.rarity);
		damage = 0;
		nominalCount = 0;
		powerConsumption = 0;
		singleRate = 0;
		nominalRate = 0;
	}

	public RCWeapon(ArrayList<String> paramList) {
		super(paramList);
		int i = 7;
		damage = Integer.parseInt(paramList.get(i));
		i++;
		nominalCount = Integer.parseInt(paramList.get(i));
		i++;
		powerConsumption = Double.parseDouble(paramList.get(i));
		i++;
		singleRate = Double.parseDouble(paramList.get(i));
		i++;
		nominalRate = Double.parseDouble(paramList.get(i));
	}

	public RCWeapon(String name, int cpu, int hp, int shield, double mass, int price, int rarity, int damage,
			int nominalCount, double powerConsumption, double singleRate, double nominalRate) {
		super(name, cpu, hp, shield, mass, price, rarity);
		this.damage = damage;
		this.nominalCount = nominalCount;
		this.powerConsumption = powerConsumption;
		this.singleRate = singleRate;
		this.nominalRate = nominalRate;
	}

}
