package com.seancheey.data;

public class RCWeapon extends RCComponent {
	public final int damage, nominalCount;
	public final double powerConsumption, singleRate, nominalRate;

	public RCWeapon(RCComponent component) {
		super(component.name, component.cpu, component.rr, component.hp, component.shield, component.mass,
				component.healRate);
		damage = 0;
		nominalCount = 0;
		powerConsumption = 0;
		singleRate = 0;
		nominalRate = 0;
	}

	public RCWeapon(String name, int cpu, int rr, int hp, int shield, double mass, double healRate, int damage,
			int nominalCount, double powerConsumption, double singleRate, double nominalRate) {
		super(name, cpu, rr, hp, shield, mass, healRate);
		this.damage = damage;
		this.nominalCount = nominalCount;
		this.powerConsumption = powerConsumption;
		this.singleRate = singleRate;
		this.nominalRate = nominalRate;
	}

}
