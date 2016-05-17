package com.seancheey.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.ScrollPaneConstants;

import com.seancheey.GuiController;
import com.seancheey.Messages;
import com.seancheey.RCConstants;
import com.seancheey.RCDateReader;
import com.seancheey.data.RCComponent;

public class FunctionPanel extends JPanel {
	private static final long serialVersionUID = -2442815361182961114L;
	private JButton autoCubeButton, clearButton, genDataButton;
	private JTextArea outputArea;
	private HintTextField maxCPUField;
	private JScrollPane scrollPane;

	public FunctionPanel() {

		outputArea = new JTextArea();
		{
			outputArea.setEditable(false);
			outputArea.setText((Messages.getString("FunctionPanel.0"))); //$NON-NLS-1$
		}
		scrollPane = new JScrollPane(outputArea);
		{
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}
		maxCPUField = new HintTextField();
		{
			maxCPUField.setHintText((Messages.getString("FunctionPanel.1"))); //$NON-NLS-1$
			maxCPUField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					GuiController.controller.setMaxCPU(getInputCPU());
				}
			});
			maxCPUField.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (maxCPUField.getText().length() == 0) {
						GuiController.controller.setMaxCPU(RCConstants.MAX_CPU);
						maxCPUField.setHintText((Messages.getString("FunctionPanel.2"))); //$NON-NLS-1$
					}
				}
			});
		}
		genDataButton = new JButton((Messages.getString("FunctionPanel.3"))); //$NON-NLS-1$
		{
			genDataButton.setBackground(Color.WHITE);
			genDataButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new ImageInfoRequestFrame();
				}
			});
		}
		autoCubeButton = new JButton((Messages.getString("FunctionPanel.4"))); //$NON-NLS-1$
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
			autoCubeButton.setBackground(Color.WHITE);
		}
		clearButton = new JButton((Messages.getString("FunctionPanel.5"))); //$NON-NLS-1$
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
			clearButton.setBackground(Color.WHITE);
		}
		GridBagLayout bagLayout = new GridBagLayout();
		{
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.gridheight = 4;
			c.weightx = 1;
			c.weighty = 1;
			c.insets = new Insets(1, 5, 1, 5);
			bagLayout.setConstraints(scrollPane, c);
			c.gridy = 5;
			c.weighty = 0;
			c.gridheight = 1;
			c.insets = new Insets(0, 0, 0, 0);
			bagLayout.setConstraints(maxCPUField, c);
			c.gridy = 6;
			bagLayout.setConstraints(genDataButton, c);
			c.gridy = 7;
			bagLayout.setConstraints(autoCubeButton, c);
			c.gridy = 8;
			bagLayout.setConstraints(clearButton, c);
		}
		setLayout(bagLayout);
		add(maxCPUField);
		add(genDataButton);
		add(autoCubeButton);
		add(clearButton);
		add(scrollPane);
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

	public int getInputCPU() {
		if (maxCPUField.getText().length() == 0) {
			return RCConstants.MAX_CPU;
		}
		try {
			return Integer.parseInt(maxCPUField.getText());
		} catch (NumberFormatException e) {
			maxCPUField.setText(String.valueOf(RCConstants.MAX_CPU));
			return RCConstants.MAX_CPU;
		}
	}

	public void setDisplayText(String s) {
		outputArea.setText(s);
	}

	public void setInputCPU(int cpu) {
		maxCPUField.setText(String.valueOf(cpu));
	}
}
