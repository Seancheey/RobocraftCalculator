package com.seancheey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.seancheey.data.RCComponent;

public class RCDateReader extends BufferedReader {
	public static ArrayList<RCComponent> COMPONENTS, MOVEMENTS, WEAPONS;

	static {
		try {
			RCDateReader compReader = new RCDateReader(new File("res/Components")),
					moveReader = new RCDateReader(new File("res/Movements")),
					weaponReader = new RCDateReader(new File("res/Weapons"));
			COMPONENTS = compReader.readAll();
			WEAPONS = weaponReader.readAll();
			MOVEMENTS = moveReader.readAll();
			compReader.close();
			moveReader.close();
			weaponReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static ArrayList<String> params(String s) {
		int index = -1;
		int endindex = -1;
		all: for (int i = s.length() - 1; i >= 0; i--) {
			if (s.charAt(i) == '\t') {
				endindex = i + 1;
				for (int j = i; j >= 0; j--) {
					if (s.charAt(j) != '\t') {
						index = j + 1;
						break all;
					}
				}
			}
		}
		if (endindex == -1) {
			ArrayList<String> list = new ArrayList<String>();
			list.add(s);
			return list;
		} else {
			ArrayList<String> list = params(s.substring(0, index));
			list.add(s.substring(endindex));
			return list;
		}
	}

	public RCDateReader(File file) throws IOException {
		super(new FileReader(file));
	}

	public ArrayList<RCComponent> readAll() {
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

	public RCComponent readComponent() {
		try {
			ArrayList<String> paramList = params(readLine());
			String name = paramList.get(0);
			int cpu = Integer.parseInt(paramList.get(1));
			int rr = Integer.parseInt(paramList.get(2));
			int hp = Integer.parseInt(paramList.get(3));
			int shield = Integer.parseInt(paramList.get(4));
			double mass = Double.parseDouble(paramList.get(5));
			double healRate = Double.parseDouble(paramList.get(6));
			return new RCComponent(name, cpu, rr, hp, shield, mass, healRate);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException i) {
			return null;
		}
		return null;
	}
}