package com.seancheey;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.seancheey.data.RCComponent;
import com.seancheey.data.RCWeapon;

public class DataImageGen {
	private String botName, author;
	private BufferedImage image;
	private Graphics2D graphics;

	public DataImageGen(String botName, String author) {
		super();
		this.botName = botName;
		this.author = author;
		this.image = new BufferedImage(1000, 1000, BufferedImage.TYPE_4BYTE_ABGR);
		this.graphics = (Graphics2D) (image.getGraphics());
	}

	public BufferedImage generate() {
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("serif", Font.PLAIN, 22));
		// draw strings
		{
			int y = 30, ydiff = 40;
			StringBuffer buff = new StringBuffer();
			if (botName.length() != 0) {
				buff.append(LanguageConverter.defaultCvt().convertString("Bot Name") + ":" + botName + "\n");
			}
			if (author.length() != 0) {
				buff.append(LanguageConverter.defaultCvt().convertString("Author") + ":" + author + "\n");
			}
			buff.append(LanguageConverter.defaultCvt().convertString("Configuration") + ":\n");
			for (WeaponCombination c : GuiController.controller.getWeaponCombinations()) {
				buff.append(
						LanguageConverter.defaultCvt().convertString(c.getWeapon().name) + " x " + c.getCount() + "\n");
			}
			for (RCComponent c : GuiController.controller.getComponentsInfo().keySet()) {
				if (!(c instanceof RCWeapon))
					buff.append(LanguageConverter.defaultCvt().convertString(c.name) + " x "
							+ GuiController.controller.getComponentsInfo().get(c) + "\n");
			}
			String info = buff.toString();
			String[] strs = info.split("\n");
			for (String s : strs) {
				graphics.drawString(s, 50, y);
				y += ydiff;
			}
		}
		// draw graph
		// TODO draw
		{
			int basex = 400, basey = 20, grid = 150;
			{
				// get score grids
				int[][] maxscores = new int[3][2];
				for (WeaponCombination weaponc : GuiController.controller.getWeaponCombinations()) {
					int[][] allscore = weaponc.getAllScores();
					for (int x = 0; x < 3; x++) {
						for (int y = 0; y < 2; y++) {
							if (allscore[x][y] > maxscores[x][y])
								maxscores[x][y] = allscore[x][y];
						}
					}
				}
				// to 255-based
				int[][] colorscores = new int[3][2];
				for (int x = 0; x < 3; x++) {
					for (int y = 0; y < 2; y++) {
						colorscores[x][y] = (int) (maxscores[x][y] * 2.55);
					}
				}
				// draw color grids
				for (int x = 0; x < 3; x++) {
					for (int y = 0; y < 2; y++) {
						Color c;
						if (colorscores[x][y] < 127) {
							c = new Color(colorscores[x][y] * 2, 255, 0);
						} else {
							c = new Color(255, 255 - colorscores[x][y], 0);
						}
						graphics.setColor(c);
						graphics.fillRect(basex + grid * x, basey + grid - grid * y, grid, grid);
						graphics.setColor(Color.BLACK);
					}
				}
				// draw scores
				for (int x = 0; x < 3; x++) {
					for (int y = 0; y < 2; y++) {
						graphics.setColor(Color.BLACK);
						graphics.drawString(String.valueOf(maxscores[x][y]), basex + grid * x + ((int) (grid * 0.42)),
								basey + ((int) (grid * 0.5)) + grid - grid * y);
					}
				}
				// draw frame
				{
					graphics.drawRect(basex, basey, grid * 3, grid * 2);
					graphics.drawLine(basex, basey + grid, basex + grid * 3, basey + grid);
					graphics.drawLine(basex + grid * 1, basey, basex + grid * 1, basey + grid * 2);
					graphics.drawLine(basex + grid * 2, basey, basex + grid * 2, basey + grid * 2);
				}
			}
		}
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
}
