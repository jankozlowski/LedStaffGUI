package spinner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SpinnerControl extends HBox {

	private TextField textField;
	private Button buttonIncrease;
	private Button buttonDecrease;
	
	private double incrementationValue = 1.0;
	private double minValue = 0.0;
	private double maxValue = 100.0;
	
	private int decimalsPlaces = 2;
	
	public SpinnerControl() {
		super();
		
		String style = SpinnerControl.class.getResource("SpinnerControl.css").toExternalForm();
		
		textField = new TextField();
		textField.setMaxWidth(Double.MAX_VALUE);
		textField.setId("textField");
		textField.getStylesheets().add(style);
		textField.setAlignment(Pos.CENTER);
		
		buttonIncrease = new Button("+");
		buttonIncrease.setId("buttonIncrease");
		buttonIncrease.getStylesheets().add(style);
		buttonIncrease.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				double value = getValue();
				if (value < maxValue) {
					setValue(getValue() + incrementationValue);
				} else {
					setValue(maxValue);
				}
			}
		});
		
		buttonDecrease = new Button("-");
		buttonDecrease.setId("buttonDecrease");
		buttonDecrease.getStylesheets().add(style);
		buttonDecrease.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				double value = getValue();
				if (value > minValue) {
					setValue(getValue() - incrementationValue);
				} else {
					setValue(minValue);
				}
			}
		});
		
		VBox vBoxButtons = new VBox();
		vBoxButtons.getChildren().addAll(buttonIncrease, buttonDecrease);
		vBoxButtons.setAlignment(Pos.CENTER);
		
		HBox.setHgrow(textField, Priority.ALWAYS);
		
		this.getChildren().addAll(textField, vBoxButtons);
		this.setAlignment(Pos.CENTER);
	}
	
	public void setIncrementationValue(double value) {
		incrementationValue = value;
	}
	
	public double getIncrementationValue() {
		return incrementationValue;
	}
	
	public void setMinValue(double value) {
		minValue = value;
	}
	
	public double getMinValue() {
		return minValue;
	}
	
	public void setMaxValue(double value) {
		maxValue = value;
	}
	
	public double getMaxValue() {
		return maxValue;
	}
	
	public void setDecimalsPlaces(int value) {
		decimalsPlaces = value;
	}
	
	public int getDecimalsPlaces() {
		return decimalsPlaces;
	}
	
	public void setValue(double value ) {
		String stringValue = String.format("%." + String.valueOf(decimalsPlaces) + "f", value).replace(",", ".");
		textField.setText(stringValue);
	}
	
	public double getValue() {
		return Double.parseDouble(textField.getText().toString().replace(",", "."));
	}
	
	public void setSpinnerEditable(boolean value) {
		textField.setEditable(value);
	}
	
	public boolean isSpinnerEditable() {
		return textField.isEditable();
	}
	
	public void setSpinnerPrefSize(double width, double height) {
		this.setPrefSize(width, height);
	}
	
	public void setSpinnerMinSize(double width, double height) {
		this.setMinSize(width, height);
	}
	
	public void setSpinnerMaxSize(double width, double height) {
		this.setMaxSize(width, height);
	}
	
	public void setSpinnerPrefWidth(double value) {
		this.setPrefWidth(value);
	}
	
	public void setSpinnerMinWidth(double value) {
		this.setMinWidth(value);
	}
	
	public void setSpinnerMaxWidth(double value) {
		this.setMaxWidth(value);
	}
	
	public void setSpinnerPrefHeight(double value) {
		this.setPrefHeight(value);
	}
	
	public void setSpinnerMinHeight(double value) {
		this.setMinHeight(value);
	}
	
	public void setSpinnerMaxHeight(double value) {
		this.setMaxHeight(value);
	}
	
	public void setOnActionButtonIncrease(EventHandler<ActionEvent> event) {
		buttonIncrease.setOnAction(event);
	}
	
	public void setOnActionButtonDecrease(EventHandler<ActionEvent> event) {
		buttonDecrease.setOnAction(event);
	}
	
	public void setTextField(TextField value) {
		textField = value;
	}
	
	public TextField getTextField() {
		return textField;
	}
}