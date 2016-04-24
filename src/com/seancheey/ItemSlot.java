package com.seancheey;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.seancheey.data.RCComponent;

public class ItemSlot extends JPanel implements KeyListener {
	private static final long serialVersionUID = 3181988613907725289L;
	private RCComponent component;
	private JTextField field;
	private JLabel label;
	private InfoModifier mod;

	public ItemSlot(RCComponent component, InfoModifier mod) {
		this.component = component;
		this.mod = mod;
		setMinimumSize(new Dimension(100, 50));
		label = new JLabel(component.name);
		field = new JTextField("0", 4);
		field.addKeyListener(this);
		add(label);
		add(field);
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
}
