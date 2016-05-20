package com.seancheey.gui;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.seancheey.GuiController;
import com.seancheey.Messages;
import com.seancheey.data.RCComponent;

public class SearchPopupMenu extends JPopupMenu {
	private static final long serialVersionUID = 7217175080222631763L;
	private ArrayList<? extends RCComponent> components = new ArrayList<>();
	private JList<String> list;
	private JScrollPane scroll;
	private boolean forceDisplay = false;

	public SearchPopupMenu(boolean forceDisplay) {
		setPopupSize(200, 150);
		setFocusable(false);
		this.forceDisplay = forceDisplay;
		list = new JList<>();
		{
			list.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					RCComponent component = getSelectedComponent();
					GuiController.controller.addComponent(component, 1);
					setVisible(false);
					clearContent();
				}
			});
		}
		scroll = new JScrollPane(list);
		{
			scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		}
		add(scroll);
	}

	public void clearContent() {
		components.clear();
		updateList();
	}

	public RCComponent getSelectedComponent() {
		int index = list.getSelectedIndex();
		if (index == -1)
			selectNextItem();
		return components.get(list.getSelectedIndex());
	}

	public boolean hasContent() {
		if (components.size() == 0)
			return false;
		else
			return true;
	}

	public boolean isForceDisplay() {
		return forceDisplay;
	}

	public void selectNextItem() {
		int next = Math.min(list.getSelectedIndex() + 1, list.getModel().getSize() - 1);
		list.setSelectedIndex(next);
		list.ensureIndexIsVisible(next);
	}

	public void selectPreviousItem() {
		int next = Math.max(list.getSelectedIndex() - 1, 0);
		list.setSelectedIndex(next);
		list.ensureIndexIsVisible(next);
	}

	public void setComponents(ArrayList<? extends RCComponent> components) {
		ArrayList<RCComponent> cloned = new ArrayList<>();
		for (RCComponent c : components) {
			cloned.add(c);
		}
		this.components = cloned;
		updateList();
	}

	public void setForceDisplay(boolean forceDisplay) {
		this.forceDisplay = forceDisplay;
	}

	@Override
	public void show(Component invoker, int x, int y) {
		if (components.size() != 0 || forceDisplay)
			super.show(invoker, x, y);
		else
			setVisible(false);
	}

	private void updateList() {
		DefaultListModel<String> defaultListModel = new DefaultListModel<>();
		for (RCComponent c : components)
			defaultListModel.addElement(Messages.getComponentString(c.name));
		list.setModel(defaultListModel);
	}
}
