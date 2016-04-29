package com.seancheey;

import java.util.ArrayList;

import com.seancheey.data.RCComponent;
import com.seancheey.data.RCMovement;
import com.seancheey.data.RCWeapon;
import com.seancheey.gui.ComponentSlotPanel;
import com.seancheey.gui.FunctionPanel;
import com.seancheey.gui.MainWindow;

public class GuiController extends AbstractFunctionController {
	public static GuiController controller;

	public static GuiController getInstance(MainWindow window) {
		if (controller == null) {
			controller = new GuiController(window);
		}
		return controller;
	}

	private FunctionPanel funcPanel;
	private ComponentSlotPanel weaponPanel, movementPanel, componentPanel;
	private MainWindow window;

	private GuiController(MainWindow guiWindow) {
		super();
		this.window = guiWindow;
		this.weaponPanel = guiWindow.weaponPanel;
		this.movementPanel = guiWindow.movementPanel;
		this.componentPanel = guiWindow.componentPanel;
		this.funcPanel = guiWindow.funcPanel;
	}

	@Override
	public void addComponent(RCComponent component, int number) {
		super.addComponent(component, number);
		if (component instanceof RCWeapon) {
			weaponPanel.addSlot(component, number);
		} else if (component instanceof RCMovement) {
			movementPanel.addSlot(component, number);
		} else {
			componentPanel.addSlot(component, number);
		}
		updateInfo();
	}

	public MainWindow getWindow() {
		return window;
	}

	@Override
	public void removeComponent(RCComponent component) {
		super.removeComponent(component);
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
		super.setComponentNumber(component, number);
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

	@Override
	public void updateInfo() {
		super.updateInfo();
		StringBuffer text = new StringBuffer();
		if (getCpu() != 0)
			text.append("CPU:\t" + getCpu() + "\n");
		if (getHp() != 0)
			text.append("HP:\t" + toKiloFormat(getHp(), 1) + "\n");
		if (getMass() != 0)
			text.append("Mass:\t" + toKiloFormat(getMass(), 2) + "\n");
		if (getShield() != 0)
			text.append("Sheild:\t" + toKiloFormat(getShield(), 1) + "\n");
		ArrayList<WeaponCombination> combinations = getWeaponCombinations();
		if (combinations.size() > 0) {
			for (WeaponCombination c : combinations) {
				text.append("--" + c.getWeapon().name + "\n");
				text.append("Rate:\t" + String.format("%.2f", c.getFireRate()) + "\n");
				text.append("DMD Rate:\t" + toKiloFormat(c.getDPS(), 2) + "\n");
				text.append("Power Rate:\t" + String.format("%.2f", c.getPPS()) + "\n");
				text.append("DMG/Round:\t" + toKiloFormat(c.getDPS() / c.getPPS() * 100, 2) + "\n");
			}
		}
		funcPanel.setDisplayText(text.toString());
	}

	private static String toKiloFormat(double num, int precision) {
		StringBuffer text = new StringBuffer();
		int kform = (int) num / 1000;
		if (kform > 0) {
			text.append(String.valueOf(kform));
			if (precision > 0) {
				text.append(".");
				int left = (int) (num - kform * 1000);
				for (int i = 2; i > 2 - precision; i--) {
					int index = left / (int) (Math.pow(10, i));
					text.append(String.valueOf(index));
					left = left - index * (int) (Math.pow(10, i));
				}
			}
			text.append("k");
		} else {
			text.append(String.format("%." + precision + "f", num));
		}
		return text.toString();
	}
}
