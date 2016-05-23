package com.seancheey.imagegen;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.seancheey.AbstractFunctionController;
import com.seancheey.Messages;
import com.seancheey.WeaponCombination;
import com.seancheey.data.RCComponent;
import com.seancheey.data.RCWeapon;

public class StarScoreDIG extends DataImageGen {
	private static final String STAR = "☆";

	private static String getStarString(int num) {
		String buff = "";
		for (int i = 0; i < num; i++) {
			buff += STAR;
		}
		return buff;
	}

	private static int scoreToStar(double score) {
		if (score <= 40) {
			return 1;
		} else if (score <= 60) {
			return 2;
		} else if (score <= 74) {
			return 3;
		} else if (score <= 90) {
			return 4;
		} else if (score <= 100) {
			return 5;
		} else {
			throw new IllegalArgumentException("score too high");
		}
	}

	public StarScoreDIG(String author, String botName, AbstractFunctionController controller) {
		super(author, botName, controller);
	}

	private String getConfig() {
		StringBuffer buff = new StringBuffer();
		buff.append((Messages.getString("rcgui.configuration")) + ":\n"); //$NON-NLS-1$ //$NON-NLS-2$
		for (WeaponCombination c : getController().getWeaponCombinations()) {
			buff.append(Messages.getComponentString((c.getWeapon().name)) + " x " + c.getCount() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		for (RCComponent c : getController().getComponentsInfo().keySet()) {
			if (!(c instanceof RCWeapon))
				buff.append(Messages.getComponentString(c.name) + " x " //$NON-NLS-1$
						+ getController().getComponentsInfo().get(c) + "\n"); //$NON-NLS-1$
		}
		return buff.toString();
	}

	private JPanel getConfigPanel() {
		JPanel infoPanel = new JPanel();
		// TODO implement the panel
		String[] configs = getConfig().split("\n");
		infoPanel.setLayout(new GridLayout(configs.length, 1)); // $NON-NLS-1$
		infoPanel.setBackground(Color.WHITE);
		infoPanel.setBorder(BorderFactory.createEtchedBorder());
		ArrayList<JLabel> infoLabels = new ArrayList<>();
		{
			for (String s : configs) { // $NON-NLS-1$
				JLabel label = new JLabel(s);
				infoLabels.add(label);
				label.setVerticalAlignment(SwingConstants.TOP);
			}
		}
		for (JLabel l : infoLabels) {
			infoPanel.add(l);
		}
		return infoPanel;
	}

	private JPanel getScorePanel() {
		JPanel scorepanel = new JPanel();
		scorepanel.setBackground(Color.WHITE);
		scorepanel.setBorder(BorderFactory.createEtchedBorder());
		String[] stars = getScoreStarString().split("\n");
		JLabel[] labels = new JLabel[stars.length];
		scorepanel.setLayout(new GridLayout(stars.length, 1));
		for (int i = 0; i < stars.length; i++) {
			labels[i] = new JLabel(stars[i]);
			labels[i].setVerticalAlignment(SwingConstants.TOP);
			scorepanel.add(labels[i]);
		}
		return scorepanel;
	}

	private String getScoreStarString() {
		StringBuffer buff = new StringBuffer();
		int[][] scores = getWeaponScoreGrids();
		{
			double mean = (scores[0][0] + scores[1][0] * 1.5 + scores[2][0] * 1.5) / 4.0;
			int starnum = scoreToStar(mean);
			buff.append(Messages.getString("rcgui.to_land") + ":" + getStarString(starnum) + "\n");
		}
		{
			double mean = (scores[0][1] + scores[1][1] * 1.5 + scores[2][1] * 1.5) / 4.0;
			int starnum = scoreToStar(mean);
			buff.append(Messages.getString("rcgui.to_sky") + ":" + getStarString(starnum) + "\n");
		}
		{
			double mean = (scores[0][0] + scores[0][1]) / 2.0;
			int starnum = scoreToStar(mean);
			buff.append(Messages.getString("rcgui.close") + ":" + getStarString(starnum) + "\n");
		}
		{
			double mean = (scores[1][0] + scores[1][1]) / 2.0;
			int starnum = scoreToStar(mean);
			buff.append(Messages.getString("rcgui.middle") + ":" + getStarString(starnum) + "\n");
		}
		{
			double mean = (scores[2][0] + scores[2][1]) / 2.0;
			int starnum = scoreToStar(mean);
			buff.append(Messages.getString("rcgui.far") + ":" + getStarString(starnum) + "\n");
		}
		return buff.toString();
	}

	private JPanel getTitlePanel() {
		JPanel panel = new JPanel();
		JLabel authorLabel = new JLabel(Messages.getString("rcgui.author") + ":" + getAuthor());
		JLabel botLabel = new JLabel(Messages.getString("rcgui.bot_name") + ":" + getBotName());
		panel.add(authorLabel);
		panel.add(botLabel);
		return panel;
	}

	@Override
	protected JPanel initPanel() {
		JPanel panel = new JPanel();

		JPanel configPanel = getConfigPanel(), scorePanel = getScorePanel(), titlePanel = getTitlePanel();

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		layout.setConstraints(titlePanel, c);
		c.ipadx = 200;
		c.ipady = 200;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		layout.setConstraints(configPanel, c);
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		layout.setConstraints(scorePanel, c);
		panel.setLayout(layout);
		panel.add(titlePanel);
		panel.add(configPanel);
		panel.add(scorePanel);
		return panel;
	}
}
