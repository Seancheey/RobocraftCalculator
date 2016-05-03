package com.seancheey.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.seancheey.data.RCComponent;

public class SearchPopupMenu extends JPopupMenu {
	private static final long serialVersionUID = 7217175080222631763L;
	private ArrayList<? extends RCComponent> components = new ArrayList<>();
	private JList<String> list;
	private SearchTextField searchField;
	private JScrollPane scroll;
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
		list = new JList<>();
		{
			list.addMouseListener(mouseListener);
		}
		scroll = new JScrollPane(list);
		{
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
		add(scroll);
	}

	public RCComponent getSelectedComponent() {
		int index = list.getSelectedIndex();
		if (index == -1)
			selectNextItem();
		return components.get(list.getSelectedIndex());
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
		if (components.size() == 0) {
			setVisible(false);
			return;
		}
		// set the list's model
		{
			DefaultListModel<String> defaultListModel = new DefaultListModel<>();
			for (RCComponent c : components)
				defaultListModel.addElement(c.name);
			list.setModel(defaultListModel);
		}

		this.show(searchField, searchField.getX(), searchField.getY() + searchField.getHeight());
	}
}
