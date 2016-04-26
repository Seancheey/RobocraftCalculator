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
	private boolean autoClearText = false;
	private FocusListener hintFocusListener = new FocusListener() {
		@Override
		public void focusLost(FocusEvent e) {
			if (autoClearText || getText().length() == 0) {
				becomeHint();
			}
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

	public boolean isAutoClearText() {
		return autoClearText;
	}

	public void setAutoClearText(boolean autoClearText) {
		this.autoClearText = autoClearText;
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
		becomeHint();
	}

	public void setHintText(String hintText) {
		this.hintText = hintText;
		if (!hasFocus()) {
			becomeHint();
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
		if (!hasFocus()) {
			becomeHint();
		}
	}

	private void becomeHint() {
		setForeground(hintColor);
		setText(hintText);
	}
}
