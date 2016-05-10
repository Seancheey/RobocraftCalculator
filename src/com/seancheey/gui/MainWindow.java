package com.seancheey.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.seancheey.GuiController;
import com.seancheey.LanguageConverter;
import com.seancheey.RCDateReader;

public class MainWindow extends JFrame {
	private final static Dimension DEFAULTSIZE = new Dimension(800, 400);
	private static MainWindow self;
	private static final long serialVersionUID = -7526688286751521433L;

	public static synchronized MainWindow getInstance() {
		if (self == null) {
			return new MainWindow();
		} else
			return self;
	}

	public FunctionPanel funcPanel;

	private JPanel mainPanel = new JPanel();

	public ComponentSlotPanel weaponPanel, movementPanel, componentPanel;

	private MainWindow() {
		setTitle(LanguageConverter.defaultCvt().convertString("Robocraft Calculater"));
		setSize(DEFAULTSIZE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("res/RCCalculator.png"));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		mainPanel.setSize(DEFAULTSIZE);
		getContentPane().add(mainPanel);
		mainPanel.setLayout(new GridLayout(1, 4));
		weaponPanel = new ComponentSlotPanel(RCDateReader.WEAPONS);
		weaponPanel.setHintText(LanguageConverter.defaultCvt().convertString("Search Weapons"));
		movementPanel = new ComponentSlotPanel(RCDateReader.MOVEMENTS);
		movementPanel.setHintText(LanguageConverter.defaultCvt().convertString("Search Movements"));
		componentPanel = new ComponentSlotPanel(RCDateReader.COMPONENTS);
		componentPanel.setHintText(LanguageConverter.defaultCvt().convertString("Search Components"));
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
