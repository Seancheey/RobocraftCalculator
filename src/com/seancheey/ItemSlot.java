package com.seancheey;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.seancheey.data.RCComponent;

public class ItemSlot extends JPanel implements KeyListener, ActionListener {
	private static final long serialVersionUID = 3181988613907725289L;
	private RCComponent component;
	private JTextField field;
	private JLabel label;
	private JButton deleteButton = new JButton("x");

	{
		deleteButton.setBorderPainted(false);
		deleteButton.setBackground(Color.ORANGE);
		deleteButton.addActionListener(this);
	}

	private InfoModifier mod;

	public ItemSlot(RCComponent component, InfoModifier mod) {
		this.component = component;
		this.mod = mod;
		setMinimumSize(new Dimension(100, 50));
		label = new JLabel(component.name);
		field = new JTextField("1", 3);
		{
			field.addKeyListener(this);
		}
		add(label);
		add(field);
		add(deleteButton);
		setBagLayout();
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

	public RCComponent getComponent() {
		return component;
	}

	public int getNumber() {
		int num;
		try {
			num = Integer.parseInt(field.getText());
		} catch (NumberFormatException e) {
			return 0;
		}
		return num;
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		mod.updateInfo();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public void setNumber(int a) {
		field.setText(String.valueOf(a));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mod.deleteSlot(this);
	}
}
