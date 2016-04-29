package com.seancheey;

import com.seancheey.data.RCWeapon;

public class WeaponCombination {
	private double fireRate = 0;
	private int count = 0;
	private RCWeapon weapon;

	public WeaponCombination(RCWeapon weapon, int number) {
		this.weapon = weapon;
		this.count = number;
		updateFireRate();
	}

	private void updateFireRate() {
		if (count >= weapon.nominalCount) {
			fireRate = weapon.nominalRate;
		} else if (count <= 0) {
			fireRate = 0;
		} else {
			double increase = (weapon.nominalRate - weapon.singleRate) / (weapon.nominalCount - 1);
			fireRate = increase * (count - 1) + weapon.singleRate;
		}
	}

	public double getDPS() {
		return weapon.damage * fireRate;
	}

	public double getPPS() {
		return weapon.powerConsumption * fireRate;
	}

	public double getFireRate() {
		return fireRate;
	}

	public int getCount() {
		return count;
	}

	public RCWeapon getWeapon() {
		return weapon;
	}
}