package com.seancheey.gui;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.seancheey.data.RCComponent;

public class ComponentSlotPanel extends JPanel {
	private static final long serialVersionUID = -556229872806327399L;
	private SearchTextField searchfield;
	private ArrayList<ItemSlot> slots = new ArrayList<ItemSlot>();

	public ComponentSlotPanel(ArrayList<? extends RCComponent> components) {
		super(new GridBagLayout());
		searchfield = new SearchTextField(components);
		setLayout(new GridLayout(8, 0));
		add(searchfield);
		setBackground(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
	}

	public void addSlot(RCComponent component, int number) {
		for (ItemSlot s : slots) {
			if (s.getComponent() == component)
				return;
		}
		ItemSlot slot = new ItemSlot(component);
		slots.add(slot);
		add(slots.get(slots.size() - 1));
		slot.setNumber(number);
		setVisible(false);
		setVisible(true);
		// TODO adjust the layout
	}

	public ItemSlot getSlot(RCComponent component) {
		for (ItemSlot s : slots) {
			if (s.getComponent() == component)
				return s;
		}
		return null;
	}

	public ArrayList<ItemSlot> getSlots() {
		return slots;
	}

	public void removeSlot(RCComponent component) {
		all: for (ItemSlot s : slots) {
			if (s.getComponent() == component) {
				slots.remove(s);
				remove(s);
				setVisible(false);
				setVisible(true);
				break all;
			}
		}
	}
}