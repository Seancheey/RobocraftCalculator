package com.seancheey.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.seancheey.GuiController;
import com.seancheey.RCDateReader;
import com.seancheey.data.RCComponent;

public class FunctionPanel extends JPanel {
	private static final long serialVersionUID = -2442815361182961114L;
	private JButton autoCubeButton, clearButton;
	private JTextArea outputArea;

	public FunctionPanel() {
		outputArea = new JTextArea(20, 0);
		{
			outputArea.setEditable(false);
		}
		autoCubeButton = new JButton("Cube Number");
		{
			autoCubeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					HashMap<RCComponent, Integer> info = GuiController.controller.getComponentsInfo();
					RCComponent cube = RCDateReader.COMPONENTS.get(0);
					int cubeNum = 0;
					if (info.containsKey(cube))
						cubeNum = info.get(cube);
					int remain = 1750 - getCPUSum() + cubeNum;
					GuiController.controller.setComponentNumber(cube, remain);
					;
				}
			});
		}
		clearButton = new JButton("Clear Components");
		{
			clearButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ArrayList<RCComponent> list = new ArrayList<>();
					for (RCComponent c : GuiController.controller.getComponentsInfo().keySet()) {
						list.add(c);
					}
					for(RCComponent c:list){
						GuiController.controller.removeComponent(c);
					}
				}
			});
		}
		GridBagLayout bagLayout = new GridBagLayout();
		{
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
			c.gridy = 7;
			bagLayout.setConstraints(clearButton, c);
		}
		setDisplayText("DisplayArea");
		setLayout(bagLayout);
		add(outputArea);
		add(autoCubeButton);
		add(clearButton);
	}

	private int getCPUSum() {
		int cpu = 0;
		HashMap<RCComponent, Integer> info = GuiController.controller.getComponentsInfo();
		for (RCComponent c : info.keySet()) {
			int number = info.get(c);
			cpu += c.cpu * number;
		}
		return cpu;
	}

	public void setDisplayText(String s) {
		outputArea.setText(s);
	}
}
