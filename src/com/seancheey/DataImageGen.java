package com.seancheey;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
		int y = 0, ydiff = 40;
		String info = (botName.length() == 0 ? ("Name: " + botName) : (""))
				+ (author.length() == 0 ? (" by " + author + "\n") : ("")) + GuiController.controller.getUpdateInfo();
		String[] strs = info.split("\n");
		for (String s : strs) {
			graphics.drawString(s, 50, y);
			y += ydiff;
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
