package com.seancheey.data;

import java.util.ArrayList;

public class RCWeapon extends RCComponent {
	public final int damage, nominalCount;
	public final double powerConsumption, singleRate, nominalRate;

	public RCWeapon(RCComponent component) {
		super(component.name, component.cpu, component.hp, component.shield, component.mass);
		damage = 0;
		nominalCount = 0;
		powerConsumption = 0;
		singleRate = 0;
		nominalRate = 0;
	}

	public RCWeapon(ArrayList<String> paramList) {
		super(paramList);
		damage = Integer.parseInt(paramList.get(5));
		nominalCount = Integer.parseInt(paramList.get(6));
		powerConsumption = Double.parseDouble(paramList.get(7));
		singleRate = Double.parseDouble(paramList.get(8));
		nominalRate = Double.parseDouble(paramList.get(9));
	}

	public RCWeapon(String name, int cpu, int hp, int shield, double mass, int damage, int nominalCount,
			double powerConsumption, double singleRate, double nominalRate) {
		super(name, cpu, hp, shield, mass);
		this.damage = damage;
		this.nominalCount = nominalCount;
		this.powerConsumption = powerConsumption;
		this.singleRate = singleRate;
		this.nominalRate = nominalRate;
	}

}
