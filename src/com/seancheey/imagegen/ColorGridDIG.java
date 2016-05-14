package com.seancheey.imagegen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

import com.seancheey.AbstractFunctionController;
import com.seancheey.LanguageConverter;
import com.seancheey.WeaponCombination;
import com.seancheey.data.RCComponent;
import com.seancheey.data.RCWeapon;

public class ColorGridDIG extends DataImageGen {
	private static final Dimension IMAGE_SIZE = new Dimension(800, 500);
	private JPanel panel, infoPanel, scorePanel;
	private ArrayList<JLabel> infoLabels;

	public ColorGridDIG(String author, String botName, AbstractFunctionController controller) {
		super(author, botName, controller);
	}

	@Override
	public BufferedImage generate() {
		panel = new JPanel();
		{
			panel.setSize(IMAGE_SIZE);
			panel.setBackground(Color.WHITE);
		}
		infoPanel = new JPanel();
		{
			String info = getInfos();
			infoPanel.setLayout(new GridLayout(info.split("\n").length, 1));
			infoPanel.setBackground(Color.WHITE);
			infoLabels = new ArrayList<>();
			{
				for (String s : info.split("\n")) {
					JLabel label = new JLabel(s);
					infoLabels.add(label);
				}
			}
			for (JLabel l : infoLabels) {
				infoPanel.add(l);
			}
		}
		scorePanel = new JPanel();
		{
			scorePanel.setBackground(Color.WHITE);
			JLabel scoreLabel, closeLabel, midLabel, farLabel, landLabel, skyLabel;
			scoreLabel = new JLabel(LanguageConverter.defaultCvt().convertString("Score"));
			closeLabel = new JLabel(LanguageConverter.defaultCvt().convertString("Close"));
			midLabel = new JLabel(LanguageConverter.defaultCvt().convertString("Middle"));
			farLabel = new JLabel(LanguageConverter.defaultCvt().convertString("Far"));
			landLabel = new JLabel(LanguageConverter.defaultCvt().convertString("To Land"));
			skyLabel = new JLabel(LanguageConverter.defaultCvt().convertString("To Sky"));
			int[][] scores = getScoreGrids();
			Color[][] colors = getColorGrids();
			JPanel[][] colorPanels = new JPanel[3][2];
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 2; y++) {
					colorPanels[x][y] = new JPanel();
					colorPanels[x][y].setBackground(colors[x][y]);
					colorPanels[x][y].setLayout(new GridLayout(1, 1));
					JLabel score = new JLabel(String.valueOf(scores[x][y]));
					score.setHorizontalAlignment(SwingConstants.CENTER);
					colorPanels[x][y].add(score);
				}
			}

			GridBagLayout layout = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 1;
			c.weighty = 0;
			c.insets = new Insets(1, 1, 1, 1);

			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.gridheight = 1;
			layout.setConstraints(scoreLabel, c);
			c.gridx = 1;
			layout.setConstraints(closeLabel, c);
			c.gridx = 2;
			layout.setConstraints(midLabel, c);
			c.gridx = 3;
			layout.setConstraints(farLabel, c);

			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 1;
			c.gridheight = 2;
			c.ipadx = 0;
			layout.setConstraints(skyLabel, c);
			c.ipadx = 100;
			c.ipady = 100;
			c.gridx = 1;
			layout.setConstraints(colorPanels[0][1], c);
			c.gridx = 2;
			layout.setConstraints(colorPanels[1][1], c);
			c.gridx = 3;
			layout.setConstraints(colorPanels[2][1], c);

			c.gridx = 0;
			c.gridy = 3;
			c.ipadx = 0;
			layout.setConstraints(landLabel, c);
			c.ipadx = 100;
			c.gridx = 1;
			layout.setConstraints(colorPanels[0][0], c);
			c.gridx = 2;
			layout.setConstraints(colorPanels[1][0], c);
			c.gridx = 3;
			layout.setConstraints(colorPanels[2][0], c);
			scorePanel.setLayout(layout);
			scorePanel.add(scoreLabel);
			scorePanel.add(closeLabel);
			scorePanel.add(midLabel);
			scorePanel.add(farLabel);
			scorePanel.add(skyLabel);
			scorePanel.add(landLabel);
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 2; y++) {
					scorePanel.add(colorPanels[x][y]);
				}
			}
		}
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 50, 0, 50);
		c.gridwidth = 1;
		c.gridheight = 1;
		layout.setConstraints(infoPanel, c);
		c.gridx = 1;
		c.gridwidth = 2;
		layout.setConstraints(scorePanel, c);
		panel.setLayout(layout);
		panel.add(infoPanel);
		panel.add(scorePanel);

		JFrame f = new JFrame("test");
		f.setSize(IMAGE_SIZE);
		f.setLocationRelativeTo(null);
		f.add(panel);
		f.setVisible(true);
		BufferedImage image = new BufferedImage(f.getWidth(), f.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		panel.paintAll(g);
		g.dispose();
		f.setVisible(false);
		return image;
	}

	private Color[][] getColorGrids() {
		int[][] maxscores = getScoreGrids();
		int[][] colorscores = new int[3][2];
		Color[][] colors = new Color[3][2];
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 2; y++) {
				colorscores[x][y] = (int) (maxscores[x][y] * 2.55);
				Color c;
				if (colorscores[x][y] < 127) {
					c = new Color(colorscores[x][y] * 2, 255, 0);
				} else {
					c = new Color(255, 255 - 2 * (colorscores[x][y] - 127), 0);
				}
				colors[x][y] = c;
			}
		}
		return colors;
	}

	private String getInfos() {
		StringBuffer buff = new StringBuffer();
		if (botName.length() != 0) {
			buff.append(LanguageConverter.defaultCvt().convertString("Bot Name") + ":" + botName + "\n");
		}
		if (author.length() != 0) {
			buff.append(LanguageConverter.defaultCvt().convertString("Author") + ":" + author + "\n");
		}
		buff.append(LanguageConverter.defaultCvt().convertString("Configuration") + ":\n");
		for (WeaponCombination c : controller.getWeaponCombinations()) {
			buff.append(LanguageConverter.defaultCvt().convertString(c.getWeapon().name) + " x " + c.getCount() + "\n");
		}
		for (RCComponent c : controller.getComponentsInfo().keySet()) {
			if (!(c instanceof RCWeapon))
				buff.append(LanguageConverter.defaultCvt().convertString(c.name) + " x "
						+ controller.getComponentsInfo().get(c) + "\n");
		}
		return buff.toString();
	}
}
