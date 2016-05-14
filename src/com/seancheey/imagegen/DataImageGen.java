package com.seancheey.imagegen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.seancheey.AbstractFunctionController;
import com.seancheey.WeaponCombination;

public abstract class DataImageGen {
	protected String author, botName;
	protected AbstractFunctionController controller;

	public DataImageGen(String author, String botName, AbstractFunctionController controller) {
		super();
		this.author = author;
		this.botName = botName;
		this.controller = controller;
	}

	protected int[][] getScoreGrids() {
		int[][] maxscores = new int[3][2];
		for (WeaponCombination weaponc : controller.getWeaponCombinations()) {
			int[][] allscore = weaponc.getAllScores();
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 2; y++) {
					if (allscore[x][y] > maxscores[x][y])
						maxscores[x][y] = allscore[x][y];
				}
			}
		}
		return maxscores;
	}

	public abstract BufferedImage generate();

	public void generateAndSave(String filename, String type) {
		BufferedImage i = generate();
		File output = new File(filename);
		try {
			ImageIO.write(i, type, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
