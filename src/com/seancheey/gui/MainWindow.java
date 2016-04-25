package com.seancheey.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.seancheey.GuiController;
import com.seancheey.RCDateReader;

public class MainWindow extends JFrame {
	private final static Dimension DEFAULTSIZE = new Dimension(700, 400);
	private static MainWindow self;
	private static final long serialVersionUID = -7526688286751521433L;

	public static synchronized MainWindow getInstance() {
		if (self == null) {
			return new MainWindow();
		} else
			return self;
	}

	public static void main(String args[]) {
		MainWindow m = MainWindow.getInstance();
		m.setVisible(true);
	}

	public FunctionPanel funcPanel;

	private JPanel mainPanel = new JPanel();

	public ComponentSlotPanel weaponPanel, movementPanel, componentPanel;

	private MainWindow() {
		setTitle("Robocraft CPU Calculater");
		setSize(DEFAULTSIZE);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// setResizable(false);
		mainPanel.setSize(DEFAULTSIZE);
		getContentPane().add(mainPanel);
		mainPanel.setLayout(new GridLayout(1, 4));
		weaponPanel = new ComponentSlotPanel(RCDateReader.WEAPONS);
		movementPanel = new ComponentSlotPanel(RCDateReader.MOVEMENTS);
		componentPanel = new ComponentSlotPanel(RCDateReader.COMPONENTS);
		funcPanel = new FunctionPanel();
		add(weaponPanel);
		add(movementPanel);
		add(componentPanel);
		add(funcPanel);
		GuiController.getInstance(this);
	}

	@Override
	public Component add(Component comp) {
		return mainPanel.add(comp);
	}

}