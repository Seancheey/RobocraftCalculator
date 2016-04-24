package com.seancheey.data;

public class RCWeapon {
	public final int damage, nominalCount;
	public final double powerConsumption, singleRate, nominalRate;

	public RCWeapon(int damage, int nominalCount, double powerConsumption, double singleRate, double nominalRate) {
		super();
		this.damage = damage;
		this.nominalCount = nominalCount;
		this.powerConsumption = powerConsumption;
		this.singleRate = singleRate;
		this.nominalRate = nominalRate;
	}

}
