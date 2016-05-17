package com.seancheey.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.seancheey.GuiController;
import com.seancheey.Messages;
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

	private JPanel mainPanel;

	public ComponentSlotPanel weaponPanel, movementPanel, componentPanel;

	private MainWindow() {
		setTitle((Messages.getString("rcgui.rc_calculator"))); //$NON-NLS-1$
		setSize(DEFAULTSIZE);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(400, 200));
		ImageIcon icon = new ImageIcon(this.getClass().getResource("res/RCCalculator.png")); //$NON-NLS-1$
		setIconImage(icon.getImage());

		mainPanel = new JPanel();
		{
			mainPanel.setSize(DEFAULTSIZE);
			getContentPane().add(mainPanel);
			mainPanel.setLayout(new GridLayout(1, 4));

			weaponPanel = new ComponentSlotPanel(RCDateReader.WEAPONS);
			{
				weaponPanel.setHintText((Messages.getString("rcgui.search_weapon"))); //$NON-NLS-1$
			}
			movementPanel = new ComponentSlotPanel(RCDateReader.MOVEMENTS);
			{
				movementPanel.setHintText((Messages.getString("rcgui.search_movement"))); //$NON-NLS-1$
			}
			componentPanel = new ComponentSlotPanel(RCDateReader.COMPONENTS);
			{
				componentPanel.setHintText((Messages.getString("rcgui.search_component"))); //$NON-NLS-1$
			}
			funcPanel = new FunctionPanel();
			add(weaponPanel);
			add(movementPanel);
			add(componentPanel);
			add(funcPanel);
		}
		GuiController.getInstance(this);
	}

	@Override
	public Component add(Component comp) {
		return mainPanel.add(comp);
	}

}
