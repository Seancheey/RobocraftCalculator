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
		public void focusGained(FocusEvent e) {
			setForeground(normalColor);
			setText("");
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (autoClearText || getText().length() == 0) {
				becomeHint();
			}
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

	public HintTextField(String text) {
		super(text);
		hintTextInit();
	}

	public HintTextField(String text, int columns) {
		super(text, columns);
		hintTextInit();
	}

	private void becomeHint() {
		setForeground(hintColor);
		setText(hintText);
	}

	public Color getHintColor() {
		return hintColor;
	}

	public String getHintText() {
		return hintText;
	}

	private void hintTextInit() {
		addFocusListener(hintFocusListener);
		becomeHint();
	}

	public boolean isAutoClearText() {
		return autoClearText;
	}

	public void setAutoClearText(boolean autoClearText) {
		this.autoClearText = autoClearText;
	}

	public void setHintColor(Color hintColor) {
		this.hintColor = hintColor;
		if (!hasFocus()) {
			becomeHint();
		}
	}

	public void setHintText(String hintText) {
		this.hintText = hintText;
		if (!hasFocus()) {
			becomeHint();
		}
	}
}
