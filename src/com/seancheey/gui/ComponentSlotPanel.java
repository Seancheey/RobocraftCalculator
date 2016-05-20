package com.seancheey.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.seancheey.data.RCComponent;

public class ComponentSlotPanel extends JPanel {
	private static final long serialVersionUID = -556229872806327399L;
	private SearchTextField searchfield;
	private ArrayList<ItemSlot> slots = new ArrayList<ItemSlot>();
	private JPanel slotPanel;
	private JButton listButton;

	public ComponentSlotPanel(ArrayList<? extends RCComponent> components) {
		super(new GridBagLayout());
		setBackground(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));

		searchfield = new SearchTextField(components);
		listButton = new JButton("▼");
		{

			listButton.setBorderPainted(false);
			SearchPopupMenu menu = new SearchPopupMenu(true);
			listButton.setComponentPopupMenu(menu);
			listButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					SearchPopupMenu menu = (SearchPopupMenu) listButton.getComponentPopupMenu();
					if (!menu.hasContent()) {
						menu.setComponents(components);
						menu.show(listButton, 0, listButton.getHeight());
						menu.setVisible(true);
					} else {
						menu.setVisible(false);
						menu.clearContent();
					}
				}
			});
		}
		slotPanel = new JPanel(new GridLayout(6, 0));
		{
			slotPanel.setBackground(getBackground());
		}
		GridBagLayout gridlayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0;
		gridlayout.setConstraints(searchfield, c);
		c.gridx = 4;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0;
		gridlayout.setConstraints(listButton, c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 5;
		c.gridheight = 6;
		c.weightx = 1;
		c.weighty = 1;
		gridlayout.setConstraints(slotPanel, c);
		setLayout(gridlayout);
		add(searchfield);
		add(listButton);
		add(slotPanel);
	}

	public void addSlot(RCComponent component, int number) {
		for (ItemSlot s : slots) {
			if (s.getComponent() == component)
				return;
		}
		ItemSlot slot = new ItemSlot(component);
		slots.add(slot);
		slotPanel.add(slots.get(slots.size() - 1));
		slot.setNumber(number);
		setVisible(false);
		setVisible(true);
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
				slotPanel.remove(s);
				setVisible(false);
				setVisible(true);
				break all;
			}
		}
	}

	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		if (slotPanel != null)
			slotPanel.setBackground(bg);
	}

	public void setHintText(String s) {
		searchfield.setHintText(s);
	}
}