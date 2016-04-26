package com.seancheey.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPopupMenu;

import com.seancheey.data.RCComponent;

public class SearchPopupMenu extends JPopupMenu {
	private static final long serialVersionUID = 7217175080222631763L;
	private ArrayList<? extends RCComponent> components = new ArrayList<>();
	private JList<String> list = new JList<String>();
	private SearchTextField searchField;
	private MouseListener mouseListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			searchField.selectComponent();
		}
	};

	public SearchPopupMenu(SearchTextField searchField) {
		this.searchField = searchField;
		setPopupSize(200, 100);
		setInvoker(searchField);
		setFocusable(false);
	}

	public RCComponent getSelectedComponent() {
		int index = list.getSelectedIndex();
		if (index == -1)
			selectNextItem();
		return components.get(list.getSelectedIndex());
	}

	private JList<String> newComponentList(ArrayList<String> strings) {
		String strs[] = new String[strings.size()];
		strings.toArray(strs);
		JList<String> jlist = new JList<String>(strs);
		jlist.addMouseListener(mouseListener);
		return jlist;
	}

	public void close() {
		list.removeAll();
		components = new ArrayList<RCComponent>();
		if (isVisible())
			setVisible(false);
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

	public void updateOptions(ArrayList<RCComponent> components) {
		this.components = components;
		setVisible(false);
		if (components.size() == 0) {
			return;
		}
		removeAll();
		ArrayList<String> namelist = new ArrayList<String>();
		for (RCComponent c : components) {
			namelist.add(c.name);
		}
		list = newComponentList(namelist);
		add(list);
		this.show(searchField, searchField.getX(), searchField.getY() + searchField.getHeight());
	}
}
