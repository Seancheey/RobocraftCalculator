package com.seancheey.gui;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Comparator;

import com.seancheey.GuiController;
import com.seancheey.data.RCComponent;

public class SearchTextField extends HintTextField {
	private static final long serialVersionUID = 1L;
	private ArrayList<? extends RCComponent> components;
	private SearchPopupMenu popmenu;
	private KeyListener keyListener = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {

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
			} else {
				updateSearch();
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {

		}
	};

	public SearchTextField(ArrayList<? extends RCComponent> components) {
		super(20);
		this.components = components;
		popmenu = new SearchPopupMenu(this);
		add(popmenu);
		setHintText("Search");
		setComponentPopupMenu(popmenu);
		setHorizontalAlignment(LEFT);
		setAutoClearText(true);
		setMaximumSize(new Dimension(200, 20));
		addKeyListener(keyListener);
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

	public void selectComponent() {
		if (popmenu.isVisible()) {
			RCComponent selected = popmenu.getSelectedComponent();
			GuiController.controller.addComponent(selected, 1);
			setPopmenuInvisible();
			popmenu.close();
		}
	}

	public void setPopmenuInvisible() {
		popmenu.setVisible(false);
	}

	private int textCompareMatchDegree(String componentName) {
		char[] text = getText().toLowerCase().toCharArray(), lowName = componentName.toLowerCase().toCharArray();
		int rank = 0;
		if (text[0] == lowName[0]) {
			rank += 100;
		}
		for (char c : text) {
			boolean match = false;
			for (char x : lowName) {
				if (c == x) {
					rank += 1;
					match = true;
				}
			}
			if (!match) {
				return 0;
			}
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