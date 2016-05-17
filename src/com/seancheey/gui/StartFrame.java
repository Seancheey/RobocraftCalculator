package com.seancheey.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.seancheey.Messages;

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
		setSize(250, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		ImageIcon icon = new ImageIcon(this.getClass().getResource("res/RCCalculator.png")); //$NON-NLS-1$
		setIconImage(icon.getImage());
		try {
			Class.forName("com.apple.eawt.Application", false, null); //$NON-NLS-1$
			com.apple.eawt.Application.getApplication().setDockIconImage(icon.getImage());
		} catch (ClassNotFoundException exception) {
		}

		panel = new JPanel();
		getContentPane().add(panel);
		languageBox = new JComboBox<>();
		{
			languageBox.addItem("English"); //$NON-NLS-1$
			languageBox.addItem("中文"); //$NON-NLS-1$
			languageBox.addKeyListener(new KeyAdapter() {

				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER)
						selectLanguage();
				}
			});
			languageBox.setAlignmentX(0.5f);
		}
		confirmButton = new JButton("Confirm");
		{
			confirmButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selectLanguage();
				}
			});
			confirmButton.setBackground(Color.WHITE);
		}
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;
		layout.setConstraints(languageBox, c);
		c.gridy = 1;
		layout.setConstraints(confirmButton, c);
		panel.setLayout(layout);
		panel.add(languageBox);
		panel.add(confirmButton);
		confirmButton.requestFocus();
	}

	private void selectLanguage() {
		String lan = languageBox.getItemAt(languageBox.getSelectedIndex());
		switch (lan) {
		case "中文": //$NON-NLS-1$
			Messages.setLocale(Messages.zh_CN);
			break;
		case "English": //$NON-NLS-1$
			Messages.setLocale(Messages.en_US);
			break;
		}
		setVisible(false);
		MainWindow m = MainWindow.getInstance();
		m.setVisible(true);
	}
}
