package com.seancheey.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.seancheey.GuiController;
import com.seancheey.LanguageConverter;
import com.seancheey.data.RCComponent;

public class ItemSlot extends JPanel {
	private static final long serialVersionUID = 3181988613907725289L;
	private RCComponent component;
	private JButton deleteButton = new JButton("x");
	private JTextField field;
	private JLabel label;

	public ItemSlot(RCComponent component) {
		this.component = component;
		label = new JLabel(LanguageConverter.defaultCvt().convertString(component.name), SwingConstants.CENTER);
		deleteButton = new JButton("x");
		{
			deleteButton.setForeground(Color.GRAY);
			deleteButton.setBackground(Color.ORANGE);
			deleteButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					GuiController.controller.removeComponent(component);
				}
			});
		}
		field = new JTextField("1", 4);
		{
			field.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {

				}

				@Override
				public void keyReleased(KeyEvent e) {
					GuiController.controller.setComponentNumber(component, getNumber());
					if (GuiController.controller.getCPUSum() > GuiController.controller.getMaxCPU()) {
						field.setText("100%");
						e.setKeyChar('%');
					}
					if (e.getKeyChar() == '%') {
						int cpuSum = 0;
						HashMap<RCComponent, Integer> info = GuiController.controller.getComponentsInfo();
						for (RCComponent c : info.keySet()) {
							if (c != component) {
								int number = info.get(c);
								cpuSum += c.cpu * number;
							}
						}
						int remain = GuiController.controller.getMaxCPU() - cpuSum;
						double percent = getNumber() / 100.0;
						int componentCount = (int) Math.floor(remain * percent / component.cpu);
						GuiController.controller.setComponentNumber(component, componentCount);
					}
				}

				@Override
				public void keyPressed(KeyEvent e) {

				}
			});
		}
		add(label);
		add(field);
		add(deleteButton);
		setBagLayout();
	}

	public RCComponent getComponent() {
		return component;
	}

	public int getNumber() {
		String text = field.getText();
		if (text.endsWith("%")) {
			text = text.substring(0, text.length() - 1);
		}
		int num;
		try {
			num = Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return 0;
		}
		return num;
	}

	private void setBagLayout() {
		if (getLayout() instanceof GridBagLayout)
			return;
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 6;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(label, c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 5;
		c.gridheight = 1;
		c.weightx = 1;
		layout.setConstraints(field, c);
		c.gridx = 5;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		layout.setConstraints(deleteButton, c);
		setLayout(layout);
	}

	public void setNumber(int a) {
		field.setText(String.valueOf(a));
	}
}
