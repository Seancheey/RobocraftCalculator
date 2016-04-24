package com.seancheey;

import java.util.HashMap;

import com.seancheey.data.RCComponent;
import com.seancheey.data.RCMovement;
import com.seancheey.data.RCWeapon;
import com.seancheey.gui.ComponentSlotPanel;
import com.seancheey.gui.FunctionPanel;
import com.seancheey.gui.MainWindow;

public class GuiController implements Controller {
	public static GuiController controller;
	public static GuiController getInstance(MainWindow window) {
		if (controller == null) {
			controller = new GuiController(window);
		}
		return controller;
	}
	private HashMap<RCComponent, Integer> components;
	private FunctionPanel funcPanel;
	private ComponentSlotPanel weaponPanel, movementPanel, componentPanel;

	private MainWindow window;

	private GuiController(MainWindow guiWindow) {
		this.window = guiWindow;
		this.weaponPanel = guiWindow.weaponPanel;
		this.movementPanel = guiWindow.movementPanel;
		this.componentPanel = guiWindow.componentPanel;
		this.funcPanel = guiWindow.funcPanel;
		components = new HashMap<>();
	}

	@Override
	public void addComponent(RCComponent component, int number) {
		components.put(component, number);
		if (component instanceof RCWeapon) {
			weaponPanel.addSlot(component, number);
		} else if (component instanceof RCMovement) {
			movementPanel.addSlot(component, number);
		} else {
			componentPanel.addSlot(component, number);
		}
		updateInfo();
	}

	@Override
	public HashMap<RCComponent, Integer> getComponentsInfo() {
		return components;
	}

	public MainWindow getWindow() {
		return window;
	}

	@Override
	public void removeComponent(RCComponent component) {
		components.remove(component);
		if (component instanceof RCWeapon) {
			weaponPanel.removeSlot(component);
		} else if (component instanceof RCMovement) {
			movementPanel.removeSlot(component);
		} else {
			componentPanel.removeSlot(component);
		}
		updateInfo();
	}

	@Override
	public void setComponentNumber(RCComponent component, Integer number) {
		components.put(component, number);
		try {
			if (component instanceof RCWeapon) {
				weaponPanel.getSlot(component).setNumber(number);
			} else if (component instanceof RCMovement) {
				movementPanel.getSlot(component).setNumber(number);
			} else {
				componentPanel.getSlot(component).setNumber(number);
			}
		} catch (NullPointerException e) {
			addComponent(component, number);
		}
		updateInfo();
	}

	private void updateInfo() {
		int cpu = 0, hp = 0, sheild = 0;
		double mass = 0;
		for (RCComponent c : components.keySet()) {
			int number = components.get(c);
			cpu += c.cpu * number;
			mass += c.mass * number;
			hp += c.hp * number;
			sheild += c.shield * number;
		}
		StringBuffer text = new StringBuffer();
		text.append("cpu:" + cpu + "\n");
		text.append("hp:" + hp + "\n");
		text.append("mass:" + mass + "kg" + "\n");
		text.append("sheild:" + sheild + "\n");
		funcPanel.setDisplayText(text.toString());
	}
}
