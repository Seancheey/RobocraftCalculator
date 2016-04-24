package com.seancheey.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.seancheey.GuiController;
import com.seancheey.data.RCComponent;

class SearchPopupMenu extends JPopupMenu implements MouseListener {
	private static final long serialVersionUID = 7217175080222631763L;
	private ArrayList<? extends RCComponent> components = new ArrayList<>();
	private JList<String> list = new JList<String>();
	private SearchTextField searchField;

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

	@Override
	public void mouseClicked(MouseEvent e) {
		searchField.selectComponent();
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	private JList<String> newComponnetList(ArrayList<String> strings) {
		String strs[] = new String[strings.size()];
		strings.toArray(strs);
		JList<String> jlist = new JList<String>(strs);
		jlist.addMouseListener(this);
		return jlist;
	}

	public void restoreStatus() {
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
		int next = list.getSelectedIndex() - 1;
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
		list = newComponnetList(namelist);
		add(list);
		this.show(searchField, searchField.getX(), searchField.getY() + searchField.getHeight());
	}
}

public class SearchTextField extends JTextField implements DocumentListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private ArrayList<? extends RCComponent> components;
	private SearchPopupMenu popmenu;

	public SearchTextField(ArrayList<? extends RCComponent> components) {
		super("search", 20);
		this.components = components;
		setForeground(Color.GRAY);
		popmenu = new SearchPopupMenu(this);
		add(popmenu);
		setComponentPopupMenu(popmenu);
		setHorizontalAlignment(LEFT);
		setMaximumSize(new Dimension(200, 20));
		getDocument().addDocumentListener(this);
		addKeyListener(this);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		updateSearch();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		updateSearch();
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (popmenu.isVisible()) {
				popmenu.selectNextItem();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (popmenu.isVisible()) {
				popmenu.selectPreviousItem();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			selectComponent();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (getForeground() == Color.GRAY) {
			setText("");
			setForeground(Color.BLACK);
		}
	}

	private ArrayList<RCComponent> matchedComponents() {
		ArrayList<RCComponent> xcomponents = new ArrayList<RCComponent>();
		for (RCComponent c : components) {
			int rank = textCompareMatchDegree(c.name);
			if (rank != 0) {
				xcomponents.add(c);
			}
		}
		xcomponents.sort(new Comparator<RCComponent>() {
			@Override
			public int compare(RCComponent o1, RCComponent o2) {
				int rank1 = textCompareMatchDegree(o1.name);
				int rank2 = textCompareMatchDegree(o2.name);
				if (rank1 > rank2)
					return -1;
				else if (rank2 > rank1)
					return 1;
				return 0;
			}
		});
		return xcomponents;
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		updateSearch();
	}

	public void selectComponent() {
		if (popmenu.isVisible()) {
			RCComponent selected = popmenu.getSelectedComponent();
			GuiController.controller.addComponent(selected, 1);
			setText("search");
			setForeground(Color.GRAY);
			popmenu.restoreStatus();
		}
	}

	private int textCompareMatchDegree(String componentName) {
		String text = getText().toLowerCase();
		String lowName = componentName.toLowerCase();
		int rank = 0;
		if (text.charAt(0) == lowName.charAt(0)) {
			rank += 100;
		}
		for (int i = 0; i < text.length(); i++) {
			if (lowName.contains(String.valueOf(text.charAt(i)))) {
				rank += 1;
			} else
				return 0;
		}
		return rank;
	}

	private void updateSearch() {
		if (getText().length() == 0) {
			if (popmenu.isVisible())
				popmenu.setVisible(false);
			return;
		}
		popmenu.updateOptions(matchedComponents());
	}
}