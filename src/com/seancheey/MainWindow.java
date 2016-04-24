package com.seancheey;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.seancheey.data.RCComponent;

public class MainWindow extends JFrame implements InfoModifier {
	private final static Dimension DEFAULTSIZE = new Dimension(600, 400);
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

	private FunctionPanel funcPanel;

	private JPanel mainPanel = new JPanel();

	private ComponentSlotPanel weaponPanel, movementPanel, componentPanel;

	private MainWindow() {
		setTitle("Robocraft CPU Calculater");
		setSize(DEFAULTSIZE);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainPanel.setSize(DEFAULTSIZE);
		getContentPane().add(mainPanel);
		mainPanel.setLayout(new GridLayout(1, 4));
		weaponPanel = new ComponentSlotPanel(RCDateReader.WEAPONS, this);
		movementPanel = new ComponentSlotPanel(RCDateReader.MOVEMENTS, this);
		componentPanel = new ComponentSlotPanel(RCDateReader.COMPONENTS, this);
		funcPanel = new FunctionPanel(this);
		add(weaponPanel);
		add(movementPanel);
		add(componentPanel);
		add(funcPanel);
	}

	@Override
	public Component add(Component comp) {
		return mainPanel.add(comp);
	}

	@Override
	public void addCube(int number) {
		RCComponent cube = RCDateReader.COMPONENTS.get(0);
		componentPanel.addSlot(cube);
		componentPanel.getSlot(cube).setNumber(number);
	}

	@Override
	public HashMap<RCComponent, Integer> getSelectedComponentsInfo() {
		HashMap<RCComponent, Integer> info = new HashMap<RCComponent, Integer>();
		for (ItemSlot slot : weaponPanel.getSlots()) {
			info.put(slot.getComponent(), slot.getNumber());
		}
		for (ItemSlot slot : movementPanel.getSlots()) {
			info.put(slot.getComponent(), slot.getNumber());
		}
		for (ItemSlot slot : componentPanel.getSlots()) {
			info.put(slot.getComponent(), slot.getNumber());
		}
		return info;
	}

	@Override
	public void updateInfo() {
		funcPanel.updateInfo();
	}

}
