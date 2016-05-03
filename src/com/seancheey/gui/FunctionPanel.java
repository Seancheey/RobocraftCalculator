package com.seancheey.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.seancheey.Controller;
import com.seancheey.GuiController;
import com.seancheey.RCDateReader;
import com.seancheey.data.RCComponent;

public class FunctionPanel extends JPanel {
	private static final long serialVersionUID = -2442815361182961114L;
	private JButton autoCubeButton, clearButton;
	private JTextArea outputArea;
	private HintTextField maxCPUField;
	private JScrollPane scrollPane;

	public FunctionPanel() {
		
		outputArea = new JTextArea();
		{
			outputArea.setEditable(false);
		}
		scrollPane = new JScrollPane(outputArea);
		{
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}
		maxCPUField = new HintTextField();
		{
			maxCPUField.setHintText("Max CPU");
			maxCPUField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					GuiController.controller.setMaxCPU(getInputCPU());
				}
			});
			maxCPUField.addFocusListener(new FocusListener() {

				@Override
				public void focusLost(FocusEvent e) {
					if (maxCPUField.getText().length() == 0) {
						GuiController.controller.setMaxCPU(Controller.MAX_CPU);
						maxCPUField.setHintText("Max CPU");
					}
				}

				@Override
				public void focusGained(FocusEvent e) {
				}
			});
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
					int remain = GuiController.controller.getMaxCPU() - getCPUSum() + cubeNum;
					GuiController.controller.setComponentNumber(cube, remain);
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
					for (RCComponent c : list) {
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
			c.gridheight = 4;
			c.weightx = 0;
			c.fill = GridBagConstraints.BOTH;
			c.weighty = 1;
			bagLayout.setConstraints(scrollPane, c);
			c.gridx = 0;
			c.gridy = 5;
			c.weighty = 0;
			c.gridheight = 1;
			bagLayout.setConstraints(maxCPUField, c);
			c.gridy = 6;
			bagLayout.setConstraints(autoCubeButton, c);
			c.gridy = 7;
			bagLayout.setConstraints(clearButton, c);
		}
		setDisplayText("DisplayArea");
		setLayout(bagLayout);
		add(maxCPUField);
		add(autoCubeButton);
		add(clearButton);
		add(scrollPane);
	}

	public int getInputCPU() {
		if (maxCPUField.getText().length() == 0) {
			return Controller.MAX_CPU;
		}
		try {
			return Integer.parseInt(maxCPUField.getText());
		} catch (NumberFormatException e) {
			maxCPUField.setText(String.valueOf(Controller.MAX_CPU));
			return Controller.MAX_CPU;
		}
	}

	public void setInputCPU(int cpu) {
		maxCPUField.setText(String.valueOf(cpu));
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
