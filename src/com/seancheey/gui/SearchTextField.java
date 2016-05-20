package com.seancheey.gui;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Comparator;

import com.seancheey.GuiController;
import com.seancheey.Messages;
import com.seancheey.data.RCComponent;

public class SearchTextField extends HintTextField {
	private static final long serialVersionUID = 1L;
	private ArrayList<? extends RCComponent> components;
	private SearchPopupMenu popmenu;
	private KeyListener keyListener = new KeyAdapter() {
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
			} else {
				updateSearch();
			}
		}
	};

	public SearchTextField(ArrayList<? extends RCComponent> components) {
		super(20);
		this.components = components;
		popmenu = new SearchPopupMenu(false);
		add(popmenu);
		setHintText(Messages.getString("rcgui.search")); //$NON-NLS-1$
		setComponentPopupMenu(popmenu);
		setHorizontalAlignment(LEFT);
		setAutoClearText(true);
		setMaximumSize(new Dimension(200, 20));
		addKeyListener(keyListener);
	}

	private int matchDegree(String componentName) {
		int rank = 0;
		String patternString = getText().toLowerCase(), matchString = componentName.toLowerCase();
		if (matchString.contains(patternString)) {
			rank += 200;
		}
		char[] pattern = patternString.toCharArray(), match = matchString.toCharArray();
		if (pattern[0] == match[0]) {
			rank += 100;
		}
		for (char c : pattern) {
			boolean matched = false;
			for (char x : match) {
				if (c == x) {
					rank += 1;
					matched = true;
				}
			}
			if (!matched) {
				return 0;
			}
		}
		return rank;
	}

	private ArrayList<RCComponent> matchedComponents() {
		ArrayList<RCComponent> xcomponents = new ArrayList<RCComponent>();
		for (RCComponent c : components) {
			int rank = matchDegree(Messages.getComponentString(c.name));
			if (rank != 0) {
				xcomponents.add(c);
			}
		}
		xcomponents.sort(new Comparator<RCComponent>() {
			@Override
			public int compare(RCComponent o1, RCComponent o2) {
				int rank1 = matchDegree(Messages.getComponentString(o1.name));
				int rank2 = matchDegree(Messages.getComponentString(o2.name));
				if (rank1 > rank2)
					return -1;
				else if (rank2 > rank1)
					return 1;
				return 0;
			}
		});
		return xcomponents;
	}

	public void selectComponent() {
		if (popmenu.isVisible()) {
			RCComponent selected = popmenu.getSelectedComponent();
			GuiController.controller.addComponent(selected, 1);
			popmenu.setVisible(false);
		}
	}

	private void updateSearch() {
		if (getText().length() == 0) {
			if (popmenu.isVisible())
				popmenu.setVisible(false);
			return;
		}
		popmenu.setComponents(matchedComponents());
		popmenu.show(this, 0, getHeight());
	}
}