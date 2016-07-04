package spinner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;

public class SpinnerIncrementationValueContextMenu extends ContextMenu {

	private SpinnerControl spinnerControl;
	private double[] incrementationValues;
	
	public SpinnerIncrementationValueContextMenu(SpinnerControl sc, double[] incrValues) {
		super();
		
		this.spinnerControl = sc;
		this.incrementationValues = incrValues;
		
		CheckMenuItem[] checkMenuItems = new CheckMenuItem[incrementationValues.length];
		
		for (int i = 0; i < checkMenuItems.length; i++) {
			int iteration = i;
			checkMenuItems[i] = new CheckMenuItem(String.valueOf(incrementationValues[i]).replace(",", "."));
			checkMenuItems[i].setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent ae) {
					checkMenuItems[iteration].setSelected(true);
					try {
						for (int j = 0; j < checkMenuItems.length; j++) {
							if (iteration != j) {
								checkMenuItems[j].setSelected(false);
							}
						}
					} catch (NullPointerException ex) {
						System.out.println(ex);
					}
					
					spinnerControl.setIncrementationValue(incrementationValues[iteration]);
				}
			});
		}
		this.getItems().addAll(checkMenuItems);
	}
}