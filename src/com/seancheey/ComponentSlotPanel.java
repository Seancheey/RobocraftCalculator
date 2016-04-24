package com.seancheey;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.seancheey.data.RCComponent;

public class ComponentSlotPanel extends JPanel {
	private static final long serialVersionUID = -556229872806327399L;
	private InfoModifier mod;
	private SearchTextField searchfield;
	private ArrayList<ItemSlot> slots = new ArrayList<ItemSlot>();

	public ComponentSlotPanel(ArrayList<RCComponent> components, InfoModifier mod) {
		super(new GridBagLayout());
		this.mod = mod;
		searchfield = new SearchTextField(this, components, mod);
		setLayout(new GridLayout(8, 0));
		add(searchfield);
		setBackground(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
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

	public void addSlot(RCComponent component) {
		for (ItemSlot s : slots) {
			if (s.getComponent() == component)
				return;
		}
		slots.add(new ItemSlot(component, mod));
		add(slots.get(slots.size() - 1));
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
}