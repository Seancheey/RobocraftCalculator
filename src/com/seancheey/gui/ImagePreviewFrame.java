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
import com.seancheey.imagegen.DataImageGen;

public class ImagePreviewFrame extends JFrame {
	private static final long serialVersionUID = 8202432493435137827L;
	private DataImageGen gen;
	private JButton genButton, cancelButton;
	private JPanel panel;

	public ImagePreviewFrame(String filename, String type, DataImageGen gen) {
		super((Messages.getString("ImagePreviewFrame.0"))); //$NON-NLS-1$
		this.gen = gen;
		setSize(gen.getPanel().getSize());
		setLocationRelativeTo(null);

		panel = new JPanel();
		{
			getContentPane().add(panel);
			genButton = new JButton((Messages.getString("ImagePreviewFrame.1"))); //$NON-NLS-1$
			{
				genButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						File file = gen.generateAndSave(filename, type);
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
			cancelButton = new JButton((Messages.getString("ImagePreviewFrame.2"))); //$NON-NLS-1$
			{
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setBackground(Color.WHITE);
			}
			JPanel imagePanel = this.gen.getPanel();
			GridBagLayout layout = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.anchor = GridBagConstraints.CENTER;
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 2;
			c.gridheight = 5;
			c.weightx = 1;
			c.weighty = 1;
			layout.setConstraints(imagePanel, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.5;
			c.weighty = 0;
			c.gridx = 0;
			c.gridy = 5;
			c.gridwidth = 1;
			c.gridheight = 1;
			layout.setConstraints(genButton, c);
			c.gridx = 1;
			layout.setConstraints(cancelButton, c);

			panel.setLayout(layout);
			panel.add(imagePanel);
			panel.add(genButton);
			panel.add(cancelButton);
		}
		setVisible(true);
	}
}
