package com.seancheey.imagegen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.seancheey.AbstractFunctionController;
import com.seancheey.WeaponCombination;

public abstract class DataImageGen {
	protected String author, botName;
	protected AbstractFunctionController controller;
	protected JPanel panel;

	public DataImageGen(String author, String botName, AbstractFunctionController controller) {
		super();
		this.author = author;
		this.botName = botName;
		this.controller = controller;
		this.panel = initPanel();
	}

	public BufferedImage generate() {
		JFrame f = new JFrame("test");
		f.setSize(panel.getSize());
		f.setLocationRelativeTo(null);
		f.add(panel);
		f.setVisible(true);
		BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		panel.paintAll(g);
		g.dispose();
		f.setVisible(false);
		return image;
	}

	public void generateAndSave(String filename, String type) {
		BufferedImage i = generate();
		File output = new File(filename);
		try {
			ImageIO.write(i, type, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public JPanel getPanel() {
		return panel;
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

	protected abstract JPanel initPanel();
}
