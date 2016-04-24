package com.seancheey.gui;

import java.awt.Color;
import java.awt.Dimension;
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

import com.seancheey.GuiController;
import com.seancheey.data.RCComponent;

public class ItemSlot extends JPanel {
	private static final long serialVersionUID = 3181988613907725289L;
	private RCComponent component;
	private JButton deleteButton = new JButton("x");
	private JTextField field;
	private JLabel label;

	{
		deleteButton.setBorderPainted(false);
		deleteButton.setBackground(Color.ORANGE);
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GuiController.controller.removeComponent(component);
			}
		});
	}

	public ItemSlot(RCComponent component) {
		this.component = component;
		label = new JLabel(component.name);
		field = new JTextField("1", 3);
		{
			field.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {

				}

				@Override
				public void keyReleased(KeyEvent e) {
					if (getNumber() > 2000) {
						String reduced = field.getText().substring(0, 4);
						field.setText(reduced);
						GuiController.controller.setComponentNumber(component, Integer.valueOf(reduced));
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
						int remain = 1750 - cpuSum;
						double percent = getNumber() / 100.0;
						int componentCount = (int) Math.floor(remain * percent / component.cpu);
						GuiController.controller.setComponentNumber(component, componentCount);
					}
					GuiController.controller.setComponentNumber(component, getNumber());
				}

				@Override
				public void keyPressed(KeyEvent e) {

				}
			});
		}
		add(label);
		add(field);
		add(deleteButton);
		setMinimumSize(new Dimension(100, 50));
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
		setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 8;
		c.gridheight = 1;
		layout.setConstraints(label, c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 7;
		c.gridheight = 1;
		layout.setConstraints(field, c);
		c.gridx = 7;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		layout.setConstraints(deleteButton, c);
	}

	public void setNumber(int a) {
		field.setText(String.valueOf(a));
	}
}
