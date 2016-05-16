package com.seancheey.imagegen;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import com.seancheey.AbstractFunctionController;
import com.seancheey.LanguageConverter;
import com.seancheey.WeaponCombination;
import com.seancheey.data.RCComponent;
import com.seancheey.data.RCWeapon;

public class ColorGridDIG extends DataImageGen {
	private static final Dimension IMAGE_SIZE = new Dimension(800, 500);
	private JPanel infoPanel, scorePanel;
	private ArrayList<JLabel> infoLabels;

	public ColorGridDIG(String author, String botName, AbstractFunctionController controller) {
		super(author, botName, controller);
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

	@Override
	protected JPanel initPanel() {
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
			JLabel[] upLabels = new JLabel[4];
			JLabel landLabel, skyLabel;
			upLabels[0] = new JLabel(LanguageConverter.defaultCvt().convertString("Score"));
			upLabels[1] = new JLabel(LanguageConverter.defaultCvt().convertString("Close"));
			upLabels[2] = new JLabel(LanguageConverter.defaultCvt().convertString("Middle"));
			upLabels[3] = new JLabel(LanguageConverter.defaultCvt().convertString("Far"));
			{
				for (int x = 0; x < upLabels.length; x++) {
					upLabels[x].setHorizontalAlignment(SwingConstants.CENTER);
					upLabels[x].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				}
			}
			landLabel = new JLabel(LanguageConverter.defaultCvt().convertString("To Land"));
			{
				landLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
			skyLabel = new JLabel(LanguageConverter.defaultCvt().convertString("To Sky"));
			{
				skyLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
			int[][] scores = getScoreGrids();
			Color[][] colors = getColorGrids();
			JPanel[][] colorPanels = new JPanel[3][2];
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 2; y++) {
					colorPanels[x][y] = new JPanel();
					colorPanels[x][y].setBackground(colors[x][y]);
					colorPanels[x][y].setLayout(new GridLayout(1, 1));
					colorPanels[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
					JLabel score = new JLabel(String.valueOf(scores[x][y]));
					score.setHorizontalAlignment(SwingConstants.CENTER);
					colorPanels[x][y].add(score);
				}
			}

			GridBagLayout layout = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 1, 1, 0), 0, 0);
			for (int x = 0; x < upLabels.length; x++) {
				c.gridx = x;
				layout.setConstraints(upLabels[x], c);
			}

			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 1;
			c.gridheight = 2;
			c.ipadx = 0;
			layout.setConstraints(skyLabel, c);
			c.ipadx = 100;
			c.ipady = 100;
			for (int x = 0; x < 3; x++) {
				c.gridx = x + 1;
				layout.setConstraints(colorPanels[x][1], c);
			}

			c.gridx = 0;
			c.gridy = 3;
			c.ipadx = 0;
			layout.setConstraints(landLabel, c);
			c.ipadx = 100;
			for (int x = 0; x < 3; x++) {
				c.gridx = x + 1;
				layout.setConstraints(colorPanels[x][0], c);
			}

			scorePanel.setLayout(layout);
			scorePanel.add(upLabels[0]);
			scorePanel.add(upLabels[1]);
			scorePanel.add(upLabels[2]);
			scorePanel.add(upLabels[3]);
			scorePanel.add(skyLabel);
			scorePanel.add(landLabel);
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 2; y++) {
					scorePanel.add(colorPanels[x][y]);
				}
			}
		}
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 50, 0, 50), 0, 0);
		layout.setConstraints(infoPanel, c);
		c.gridx = 1;
		c.gridwidth = 2;
		layout.setConstraints(scorePanel, c);
		panel.setLayout(layout);
		panel.add(infoPanel);
		panel.add(scorePanel);
		return panel;
	}
}
