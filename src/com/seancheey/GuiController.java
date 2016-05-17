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

	private static String toKiloFormat(double num, int precision) {
		StringBuffer text = new StringBuffer();
		int kform = (int) num / 1000;
		if (kform > 0) {
			text.append(String.valueOf(kform));
			if (precision > 0) {
				text.append("."); //$NON-NLS-1$
				int left = (int) (num - kform * 1000);
				for (int i = 2; i > 2 - precision; i--) {
					int index = left / (int) (Math.pow(10, i));
					text.append(String.valueOf(index));
					left = left - index * (int) (Math.pow(10, i));
				}
			}
			text.append("k"); //$NON-NLS-1$
		} else {
			text.append(String.format("%." + precision + "f", num)); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return text.toString();
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

	public String getUpdateInfo() {
		super.updateInfo();
		StringBuffer text = new StringBuffer();
		if (getCpu() != 0)
			text.append((Messages.getString("GuiController.4")) + ":\t" + getCpu() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		if (getHp() != 0)
			text.append((Messages.getString("GuiController.7")) + ":\t" + toKiloFormat(getHp(), 1) + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		if (getMass() != 0)
			text.append((Messages.getString("GuiController.10")) + ":\t" + toKiloFormat(getMass(), 2) + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		if (getShield() != 0)
			text.append((Messages.getString("GuiController.13")) + ":\t" + toKiloFormat(getShield(), 1) //$NON-NLS-1$ //$NON-NLS-2$
					+ "\n"); //$NON-NLS-1$
		if (getPrice() != 0)
			text.append((Messages.getString("GuiController.16")) + ":\t" + getPrice() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		if (getSellprice() != 0)
			text.append((Messages.getString("GuiController.19")) + ":\t" + getSellprice() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		ArrayList<WeaponCombination> combinations = getWeaponCombinations();
		if (combinations.size() > 0) {
			for (WeaponCombination c : combinations) {
				text.append(c.getCount() + "x " + (c.getWeapon().name) + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
				text.append((Messages.getString("GuiController.24")) + ":\t" //$NON-NLS-1$ //$NON-NLS-2$
						+ String.format("%.2f", c.getFireRate()) + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
				text.append((Messages.getString("GuiController.28")) + ":\t" //$NON-NLS-1$ //$NON-NLS-2$
						+ toKiloFormat(c.getDPS(), 2) + "\n"); //$NON-NLS-1$
				text.append(("Power Rate") + ":\t" //$NON-NLS-1$ //$NON-NLS-2$
						+ String.format("%.2f", c.getPPS()) + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
				text.append((Messages.getString("GuiController.35")) + ":\t" //$NON-NLS-1$ //$NON-NLS-2$
						+ toKiloFormat(c.getDPR(), 2) + "\n"); //$NON-NLS-1$
			}
		}
		return text.toString();
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
		funcPanel.setDisplayText(getUpdateInfo());
	}
}
