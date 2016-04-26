package com.seancheey.gui;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class HintTextField extends JTextField {
	private static final long serialVersionUID = 5188430628216300062L;
	private String hintText = "";
	private Color hintColor = Color.GRAY, normalColor;
	private FocusListener hintFocusListener = new FocusListener() {

		@Override
		public void focusLost(FocusEvent e) {
			setForeground(hintColor);
			setText(hintText);
		}

		@Override
		public void focusGained(FocusEvent e) {
			setForeground(normalColor);
			setText("");
		}
	};

	public HintTextField() {
		super();
		hintTextInit();
	}

	public HintTextField(Document doc, String text, int columns) {
		super(doc, text, columns);
		hintTextInit();
	}

	public HintTextField(int columns) {
		super(columns);
		hintTextInit();
	}

	public HintTextField(String text, int columns) {
		super(text, columns);
		hintTextInit();
	}

	public HintTextField(String text) {
		super(text);
		hintTextInit();
	}

	private void hintTextInit() {
		addFocusListener(hintFocusListener);
		hintFocusListener.focusLost(null);
	}

	public void setHintText(String hintText) {
		this.hintText = hintText;
		if(!hasFocus()){
			setText(hintText);
		}
	}

	public String getHintText() {
		return hintText;
	}

	public Color getHintColor() {
		return hintColor;
	}

	public void setHintColor(Color hintColor) {
		this.hintColor = hintColor;
		if(!hasFocus()){
			setForeground(hintColor);
		}
	}
}
