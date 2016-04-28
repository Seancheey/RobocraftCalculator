package com.seancheey.data;

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
