package com.seancheey.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.seancheey.LanguageConverter;

public class StartFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {
		StartFrame s = new StartFrame();
		s.setVisible(true);
	}

	private JComboBox<String> languageBox;
	private JButton confirmButton;

	private JPanel panel;

	private StartFrame() {
		super("Select Language");
		setSize(300, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		ImageIcon icon = new ImageIcon(this.getClass().getResource("res/RCCalculator.png"));
		setIconImage(icon.getImage());
		try {
			Class.forName("com.apple.eawt.Application", false, null);
			com.apple.eawt.Application.getApplication().setDockIconImage(icon.getImage());
		} catch (ClassNotFoundException exception) {
		}

		panel = new JPanel();
		getContentPane().add(panel);
		languageBox = new JComboBox<>();
		{
			for (String lan : LanguageConverter.defaultCvt().getLanguages()) {
				languageBox.addItem(lan);
			}
			languageBox.addKeyListener(new KeyAdapter() {

				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER)
						selectLanguage();
				}
			});
		}
		confirmButton = new JButton("confirm");
		{
			confirmButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selectLanguage();
				}
			});
			confirmButton.setBackground(Color.WHITE);
		}
		panel.add(languageBox);
		panel.add(confirmButton);
		confirmButton.requestFocus();
	}

	private void selectLanguage() {
		LanguageConverter.defaultCvt().setLanguage(languageBox.getItemAt(languageBox.getSelectedIndex()));
		setVisible(false);
		MainWindow m = MainWindow.getInstance();
		m.setVisible(true);
	}
}
