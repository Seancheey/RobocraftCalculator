package com.seancheey.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.seancheey.RCDateReader;
import com.seancheey.data.RCComponent;

public class FunctionPanel extends JPanel {
	private static final long serialVersionUID = -2442815361182961114L;
	private JButton autoCubeButton;
	private InfoModifier infos;
	private JTextArea outputArea;

	public FunctionPanel(InfoModifier infos) {
		this.infos = infos;
		outputArea = new JTextArea(20, 0);
		outputArea.setEditable(false);
		
		autoCubeButton = new JButton("Cube Number");
		autoCubeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HashMap<RCComponent, Integer> info = infos.getSelectedComponentsInfo();
				RCComponent cube = RCDateReader.COMPONENTS.get(0);
				int cubeNum = 0;
				if (info.containsKey(cube))
					cubeNum = info.get(cube);
				int remain = 1750 - getCPUSum() + cubeNum;
				infos.addCube(remain);
				infos.updateInfo();
			}
		});
		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 5;
		c.weightx = 0;
		c.fill = GridBagConstraints.BOTH;
		bagLayout.setConstraints(outputArea, c);
		c.gridheight = 1;
		c.gridy = 6;
		bagLayout.setConstraints(autoCubeButton, c);
		updateInfo();
		;
		setLayout(bagLayout);
		add(outputArea);
		add(autoCubeButton);
	}

	public int getCPUSum() {
		int cpu = 0;
		HashMap<RCComponent, Integer> info = infos.getSelectedComponentsInfo();
		for (RCComponent c : info.keySet()) {
			int number = info.get(c);
			cpu += c.cpu * number;
		}
		return cpu;
	}

	public void updateInfo() {
		int cpu = 0, hp = 0, sheild = 0;
		double mass = 0;
		HashMap<RCComponent, Integer> info = infos.getSelectedComponentsInfo();
		for (RCComponent c : info.keySet()) {
			int number = info.get(c);
			cpu += c.cpu * number;
			mass += c.mass * number;
			hp += c.hp * number;
			sheild += c.shield * number;
		}
		StringBuffer text = new StringBuffer();
		text.append("cpu:" + cpu + "\n");
		text.append("hp:" + hp + "\n");
		text.append("mass:" + mass + "kg" + "\n");
		text.append("sheild:" + sheild + "\n");
		outputArea.setText(text.toString());
	}

}
