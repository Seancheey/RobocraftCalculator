package com.seancheey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.seancheey.data.RCComponent;
import com.seancheey.data.RCMovement;
import com.seancheey.data.RCWeapon;

public class RCDateReader extends BufferedReader {
	public static ArrayList<RCComponent> COMPONENTS;
	public static ArrayList<RCMovement> MOVEMENTS;
	public static ArrayList<RCWeapon> WEAPONS;

	static {
		try {
			RCDateReader compReader = new RCDateReader(new File("res/Components")),
					moveReader = new RCDateReader(new File("res/Movements")),
					weaponReader = new RCDateReader(new File("res/Weapons"));
			COMPONENTS = compReader.readAllComponents();
			WEAPONS = weaponReader.readAllWeapon();
			MOVEMENTS = moveReader.readAllMovements();
			System.out.println("read " + WEAPONS.size() + " weapons");
			compReader.close();
			moveReader.close();
			weaponReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static ArrayList<String> params(String s) {
		ArrayList<String> list = new ArrayList<String>();
		String[] stringlist = s.split("\t");
		for (String str : stringlist) {
			if (str.length() != 0) {
				list.add(str);
			}
		}
		System.out.println();
		return list;
	}

	public RCDateReader(File file) throws IOException {
		super(new FileReader(file));
	}

	public ArrayList<RCComponent> readAllComponents() {
		ArrayList<RCComponent> cs = new ArrayList<RCComponent>();
		while (true) {
			try {
				cs.add(readComponent());
			} catch (Exception e) {
				break;
			}
		}
		try {
			close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cs;
	}

	public ArrayList<RCMovement> readAllMovements() {
		ArrayList<RCMovement> cs = new ArrayList<>();
		while (true) {
			try {
				cs.add(readMovement());
			} catch (Exception e) {
				break;
			}
		}
		try {
			close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cs;
	}

	public ArrayList<RCWeapon> readAllWeapon() {
		ArrayList<RCWeapon> cs = new ArrayList<>();
		while (true) {
			try {
				cs.add(readWeapon());
			} catch (Exception e) {
				break;
			}
		}
		try {
			close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cs;
	}

	public RCMovement readMovement() {
		String line = "";
		try {
			line = readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new RCMovement(params(line));
		} catch (IndexOutOfBoundsException i) {
			System.out.println(line + " is not read properly");
			return new RCMovement(new RCComponent(params(line)));
		}
	}

	public RCWeapon readWeapon() {
		String line = "";
		try {
			line = readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new RCWeapon(params(line));
		} catch (IndexOutOfBoundsException i) {
			System.out.println(line + " is not read properly");
			return new RCWeapon(new RCComponent(params(line)));
		}
	}

	public RCComponent readComponent() {
		try {
			return new RCComponent(params(readLine()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException i) {
			return null;
		}
		return null;
	}
}