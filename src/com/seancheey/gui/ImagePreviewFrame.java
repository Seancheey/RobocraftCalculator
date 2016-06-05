package com.seancheey.gui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.seancheey.Messages;
import com.seancheey.imagegen.ColorGridDIG;
import com.seancheey.imagegen.DataImageGen;
import com.seancheey.imagegen.StarScoreDIG;

public class ImagePreviewFrame extends JFrame {
	private static final long serialVersionUID = 8202432493435137827L;
	private DataImageGen generator;
	private JButton switchButton, genButton, cancelButton;
	private JPanel panel, imagePanel;
	private GridBagLayout layout;

	public ImagePreviewFrame(String filename, String type, DataImageGen gen) {
		super((Messages.getString("rcgui.image_preview"))); //$NON-NLS-1$
		this.generator = gen;
		setSize(generator.getPanel().getSize());
		setLocationRelativeTo(null);

		panel = new JPanel();
		switchButton = new JButton(Messages.getString("rcgui.switch_model"));
		{
			switchButton.setBackground(Color.WHITE);
			switchButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (generator instanceof ColorGridDIG) {
						generator = new StarScoreDIG(generator.getAuthor(), generator.getBotName(),
								generator.getController());
					} else {
						generator = new ColorGridDIG(generator.getAuthor(), generator.getBotName(),
								generator.getController());
					}
					setImagePanel(generator.getPanel());
				}
			});
		}
		{
			getContentPane().add(panel);
			genButton = new JButton((Messages.getString("rcgui.generate"))); //$NON-NLS-1$
			{
				genButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						File file = generator.generateAndSave(filename, type);
						setVisible(false);
						Desktop d = Desktop.getDesktop();
						try {
							d.open(file);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				genButton.setBackground(Color.WHITE);
			}
			cancelButton = new JButton((Messages.getString("rcgui.cancel"))); //$NON-NLS-1$
			{
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setBackground(Color.WHITE);
			}
			imagePanel = generator.getPanel();
			layout = new GridBagLayout();
			setImagePanel(imagePanel);
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.CENTER;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.5;
			c.weighty = 0;
			c.gridx = 0;
			c.gridy = 5;
			c.gridwidth = 1;
			c.gridheight = 1;
			layout.setConstraints(switchButton, c);
			c.gridx = 1;
			layout.setConstraints(genButton, c);
			c.gridx = 2;
			layout.setConstraints(cancelButton, c);
			panel.setLayout(layout);
			panel.add(switchButton);
			panel.add(genButton);
			panel.add(cancelButton);
		}
		setVisible(true);
	}

	public void setImagePanel(JPanel imagePanel) {
		if (this.imagePanel != null) {
			panel.remove(this.imagePanel);
			layout.removeLayoutComponent(this.imagePanel);
		}
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 5;
		c.weightx = 1;
		c.weighty = 1;
		layout.setConstraints(imagePanel, c);
		panel.add(imagePanel);
		this.imagePanel = imagePanel;
		setVisible(false);
		setVisible(true);
	}
}
