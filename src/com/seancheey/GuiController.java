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

	private static final String TAB = ":\t", NL = "\n";

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
			text.append((Messages.getString("rcgui.cpu")) + TAB + getCpu() + NL); //$NON-NLS-1$ 
		if (getHp() != 0)
			text.append((Messages.getString("rcgui.hp")) + TAB + toKiloFormat(getHp(), 1) + NL); //$NON-NLS-1$ 
		if (getMass() != 0)
			text.append((Messages.getString("rcgui.mass")) + TAB + toKiloFormat(getMass(), 2) + NL); //$NON-NLS-1$ 
		if (getShield() != 0)
			text.append((Messages.getString("rcgui.shield")) + TAB + toKiloFormat(getShield(), 1) //$NON-NLS-1$ 
					+ NL); // $NON-NLS-1$
		if (getPrice() != 0)
			text.append((Messages.getString("rcgui.forge_cost")) + TAB + getPrice() + NL); //$NON-NLS-1$ 
		if (getSellprice() != 0)
			text.append((Messages.getString("rcgui.sell_price")) + TAB + getSellprice() + NL); //$NON-NLS-1$ 
		ArrayList<WeaponCombination> combinations = getWeaponCombinations();
		if (combinations.size() > 0) {
			for (WeaponCombination c : combinations) {
				text.append(c.getCount() + "x " + (Messages.getComponentString(c.getWeapon().name)) + NL); //$NON-NLS-1$ 
				text.append((Messages.getString("rcgui.fire_rate")) + TAB //$NON-NLS-1$ 
						+ String.format("%.2f", c.getFireRate()) + NL); //$NON-NLS-1$ 
				text.append((Messages.getString("rcgui.damage_rate")) + TAB //$NON-NLS-1$ 
						+ toKiloFormat(c.getDPS(), 2) + NL); // $NON-NLS-1$
				text.append((Messages.getString("rcgui.power_rate")) + TAB //$NON-NLS-1$ 
						+ String.format("%.2f", c.getPPS()) + NL); //$NON-NLS-1$ 
				text.append((Messages.getString("rcgui.damage_per_round")) + TAB //$NON-NLS-1$ 
						+ toKiloFormat(c.getDPR(), 2) + NL); // $NON-NLS-1$
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
