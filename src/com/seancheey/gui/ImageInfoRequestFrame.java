package com.seancheey.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.seancheey.DataImageGen;
import com.seancheey.LanguageConverter;

public class ImageInfoRequestFrame extends JFrame {
	private static final long serialVersionUID = -5897217686567470362L;
	private JPanel mainPanel;
	private JLabel nameLabel, authLabel, fileLabel;
	private JTextField nameField, authField, fileField;
	private JComboBox<String> fileTypeBox;
	private JButton saveButton;

	public ImageInfoRequestFrame() {
		super(LanguageConverter.defaultCvt().convertString("New Data Generator"));
		setSize(500, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		mainPanel = new JPanel();
		{
			mainPanel.setBackground(Color.WHITE);
		}
		nameLabel = new JLabel(LanguageConverter.defaultCvt().convertString("Bot Name"));
		authLabel = new JLabel(LanguageConverter.defaultCvt().convertString("Author"));
		fileLabel = new JLabel(LanguageConverter.defaultCvt().convertString("File Name"));
		nameField = new JTextField(5);
		authField = new JTextField(5);
		fileField = new JTextField(5);
		fileTypeBox = new JComboBox<>(new String[] { "png", "jpg", "gif" });
		saveButton = new JButton(LanguageConverter.defaultCvt().convertString("Save"));
		{
			saveButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String type = fileTypeBox.getItemAt(fileTypeBox.getSelectedIndex()), name = nameField.getText(),
							author = authField.getText(), filename = fileField.getText();
					DataImageGen gen = new DataImageGen(name, author);
					gen.generateAndSave(filename + "." + type, type);
				}
			});
			saveButton.setBackground(Color.WHITE);
		}
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		{
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.gridheight = 1;
			c.weightx = 0;
			c.weighty = 0;
			layout.setConstraints(nameLabel, c);
			c.gridx = 1;
			c.weightx = 1;
			layout.setConstraints(nameField, c);

			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 0;
			layout.setConstraints(authLabel, c);
			c.gridx = 1;
			c.weightx = 1;
			layout.setConstraints(authField, c);

			c.gridx = 0;
			c.gridy = 2;
			c.weightx = 0;
			layout.setConstraints(fileLabel, c);
			c.gridx = 1;
			c.weightx = 1;
			layout.setConstraints(fileField, c);
			c.gridx = 0;
			c.gridy = 3;
			c.gridwidth = 2;
			layout.setConstraints(fileTypeBox, c);
			c.gridy = 4;
			layout.setConstraints(saveButton, c);
		}
		getContentPane().add(mainPanel);
		mainPanel.setLayout(layout);
		add(nameLabel);
		add(nameField);
		add(authLabel);
		add(authField);
		add(fileLabel);
		add(fileField);
		add(fileTypeBox);
		add(saveButton);
		setVisible(true);
	}

	@Override
	public Component add(Component comp) {
		return mainPanel.add(comp);
	}

}
