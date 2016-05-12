package com.seancheey.data;

import java.util.ArrayList;

public class RCWeapon extends RCComponent {
	public final int damage, nominalCount, range;
	public final double powerConsumption, singleRate, nominalRate, accuracyLost, skyMultiplier;

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
		i++;
		range = Integer.parseInt(paramList.get(i));
		i++;
		accuracyLost = Double.parseDouble(paramList.get(i));
		i++;
		skyMultiplier = Double.parseDouble(paramList.get(i));
	}

	public RCWeapon(String name, int cpu, int hp, int shield, double mass, int price, int rarity, int damage,
			int nominalCount, int range, double powerConsumption, double singleRate, double nominalRate,
			double accuracyLost, double skyMultiplier) {
		super(name, cpu, hp, shield, mass, price, rarity);
		this.damage = damage;
		this.nominalCount = nominalCount;
		this.range = range;
		this.powerConsumption = powerConsumption;
		this.singleRate = singleRate;
		this.nominalRate = nominalRate;
		this.accuracyLost = accuracyLost;
		this.skyMultiplier = skyMultiplier;
	}
}
