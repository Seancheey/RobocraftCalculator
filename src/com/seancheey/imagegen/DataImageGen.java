package com.seancheey.imagegen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.seancheey.AbstractFunctionController;
import com.seancheey.WeaponCombination;

public abstract class DataImageGen {
	private String author, botName;
	private AbstractFunctionController controller;
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

	public File generateAndSave(String filename, String type) {
		BufferedImage image = generate();
		if (filename.length() == 0) {
			filename = String.format("%d-%d-%d at %d.%d", Calendar.getInstance().get(Calendar.YEAR),
					Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
					Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE));
		}
		String fullname = filename + "." + type;
		File output = new File(fullname);
		try {
			ImageIO.write(image, type, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

	public String getAuthor() {
		return author;
	}

	public String getBotName() {
		return botName;
	}

	public AbstractFunctionController getController() {
		return controller;
	}

	public JPanel getPanel() {
		return panel;
	}

	protected int[][] getWeaponScoreGrids() {
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

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setBotName(String botName) {
		this.botName = botName;
	}
}
