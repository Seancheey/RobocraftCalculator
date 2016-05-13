package com.seancheey;

import com.seancheey.data.RCWeapon;

public class WeaponCombination {
	private double fireRate = 0;
	private int count = 0;
	private RCWeapon weapon;
	public static final int CLOSE = 20, MID = 65, FAR = 100;
	private static final int closeRank = 5500, midRank = 3600, farRank = 1600;

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

	public int getRank(int range, boolean sky) {
		int startrange, endrange;
		switch (range) {
		case CLOSE:
			startrange = 0;
			endrange = CLOSE;
			break;
		case MID:
			startrange = CLOSE;
			endrange = MID;
			break;
		case FAR:
			startrange = MID;
			endrange = FAR;
			break;
		default:
			return 0;
		}
		int rank = getRankinRange(startrange, endrange);
		if (sky) {
			rank *= weapon.skyMultiplier;
		}
		return rank;
	}

	public int[][] getAllRanks() {
		int[][] ranks = new int[3][2];
		ranks[0][0] = getRank(CLOSE, false);
		ranks[1][0] = getRank(MID, false);
		ranks[2][0] = getRank(FAR, false);
		ranks[0][1] = getRank(CLOSE, true);
		ranks[1][1] = getRank(MID, true);
		ranks[2][1] = getRank(FAR, true);
		return ranks;
	}

	public int getScore(int range, boolean sky) {
		return toScoreInRange(getRank(range, sky), range, sky);
	}

	public int[][] getAllScores() {
		int[][] ranks = new int[3][2];
		ranks[0][0] = getScore(CLOSE, false);
		ranks[1][0] = getScore(MID, false);
		ranks[2][0] = getScore(FAR, false);
		ranks[0][1] = getScore(CLOSE, true);
		ranks[1][1] = getScore(MID, true);
		ranks[2][1] = getScore(FAR, true);
		return ranks;
	}

	private int toScoreInRange(int rank, int range, boolean sky) {
		double score = 0;
		switch (range) {
		case CLOSE:
			score = rank / (double) closeRank;
			break;
		case MID:
			score = rank / (double) midRank;
			break;
		case FAR:
			score = rank / (double) farRank;
			break;
		}
		score *= 100;
		if (sky) {
			score *= 1.05;
		}
		if (score > 100) {
			score = 100;
		}
		return (int) score;
	}

	private int getRankinRange(int a, int b) {
		double DPR = getDPS() / getPPS() * 100, DPS = getDPS();
		if (DPR > 200000000) {
			DPR = 1000000;
		}
		if (a > weapon.range) {
			return 0;
		}
		double rangeCovered = 1;
		if (b > weapon.range) {
			rangeCovered = (double) (weapon.range - a) / (b - a);
			b = weapon.range;
		}
		double percentA = (100 - weapon.accuracyLost * a) / 100, percentB = (100 - weapon.accuracyLost * b) / 100;
		double percent = (percentA + percentB) / 2;
		double rank = (DPR / 1000) * Math.pow(percent * (DPS / 1000) * percent, 0.5) * rangeCovered;
		return (int) (rank);
	}
}