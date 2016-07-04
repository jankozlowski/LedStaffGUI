package application;
	
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import spinner.SpinnerControl;
import spinner.SpinnerIncrementationValueContextMenu;


public class Main extends Application {

	ArrayList<ColorPicker> ledsArray;
	ArrayList<HBox> hboxArray;
	ArrayList<VBox> vboxArray;

	VBox centerLayout;
	ColorPicker autoColorPicker;
	CheckBox cb1;
	SpinnerControl sc;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			ledsArray = new ArrayList<ColorPicker>();
			hboxArray = new ArrayList<HBox>();
			vboxArray = new ArrayList<VBox>();
			
			 MenuBar menuBar = new MenuBar();
			 
		        // --- Menu File
		        Menu menuFile = new Menu("File");
		 
		 
		        menuBar.getMenus().add(menuFile);
		 
		        MenuItem Open = new MenuItem("Open");
		        MenuItem Save = new MenuItem("Save");
		        MenuItem Exit = new MenuItem("Exit");
		        
		        
		        Open.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
		        Save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		        
		        Open.setOnAction(new EventHandler<ActionEvent>() {
		            public void handle(ActionEvent t) {
		            	FileChooser fileChooser = new FileChooser();
		    			fileChooser.setTitle("Open Project");
		    			
		    			fileChooser.getExtensionFilters().addAll(
		    					new FileChooser.ExtensionFilter("Text documents", "*.txt"),
		    					new FileChooser.ExtensionFilter("All files", "*.*"));
		                
		                
		                File file = fileChooser.showOpenDialog(primaryStage);
		    			
		                if(file != null){
		                	
		                	try {
		                		BufferedReader br = new BufferedReader(new FileReader(file));
			                	        	    
		                	    String firstLine = br.readLine();
		                	    setLeds(Integer.valueOf(firstLine));
		                	    sc.setValue(Integer.valueOf(firstLine));
		                	    
		                	    int led =0;
		                	    String line = br.readLine();

		                	    while (line != null) {

		                	    	double a = Integer.parseInt(line.substring(2,4),16)/255f;
		                	    	double r = Integer.parseInt(line.substring(4,6),16)/255f;
		                	    	double g = Integer.parseInt(line.substring(6,8),16)/255f;
		                	    	double b = Integer.parseInt(line.substring(8),16)/255f;
		                	    
		                	        ledsArray.get(led).setValue(new Color(a,r,g,b));
		                	        line = br.readLine();
		                	        led++;
		                	    }

		                	    br.close();
		                	} catch (IOException e) {
								e.printStackTrace();
							}
		                	
		                }
		            }
		    });
		        
		        Save.setOnAction(new EventHandler<ActionEvent>() {
		            public void handle(ActionEvent t) {
		            	FileChooser fileChooser = new FileChooser();
		    			fileChooser.setTitle("Save Project");
		    			
		    			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		                fileChooser.getExtensionFilters().add(extFilter);
		    			
		                File file = fileChooser.showSaveDialog(primaryStage);
		                
		                
		                if(file != null){
		                
		                	String content = String.valueOf(ledsArray.size());
		                	for(int a=0; a<ledsArray.size(); a++){
		                		content+=System.getProperty("line.separator")+ledsArray.get(a).getValue();
		                	}
		                
		                FileWriter fileWriter = null;
		                
		                try {
							fileWriter = new FileWriter(file);
							fileWriter.write(content);
			                fileWriter.close();
			                
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		                
		               
		                }
		            }
		    });
		        
		        
		        Exit.setOnAction(actionEvent -> Platform.exit());
			
		        menuFile.getItems().addAll(Open,Save,Exit);
			
			primaryStage.setTitle("Led Staff GUI");
			
			Label label1 = new Label("Number of leds:");
	
			Label label2 = new Label("Set all leds to color:");
			Label label3 = new Label("Set all leds from:");
			label3.setPadding(new Insets(0,0,0,80));
			Label label4 = new Label("to");
			Label label5 = new Label("on color");
			Label label6 = new Label("Functions:");
			Label label7 = new Label("Time:");
			Label label8 = new Label("Speed:");
			Label label9 = new Label("Auto Select Color:");
		//	Label label10 = new Label("Brightnes:");
		//	Label brightnesValue = new Label("255");
		//	label10.setPadding(new Insets(0,0,0,80));
			Label space = new Label(" ");
			space.setPadding(new Insets(0,0,0,80));
			
			sc = new SpinnerControl();
			sc.setDecimalsPlaces(0);
			sc.setMaxValue(256);
			sc.setValue(112);
			sc.setMaxWidth(80);
			sc.getTextField().setContextMenu(new SpinnerIncrementationValueContextMenu(sc, new double[] {10.0, 1.0, 0.1, 0.01}));
			sc.getTextField().textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable,
			            String oldValue, String newValue) {
			    	setLeds(sc.getValue());
			    }
			});
			
			
			HBox hboxtop = new HBox();
			hboxtop.setSpacing(10);
			hboxtop.getChildren().addAll(label1, sc);
			hboxtop.setAlignment(Pos.CENTER_LEFT);
			
			HBox colorBox = new HBox();
			colorBox.setPadding(new Insets(30,0,0,0));
			colorBox.setSpacing(10);
			
			ColorPicker allColorPicker = new ColorPicker();
			allColorPicker.getStyleClass().add("button");
			
			Button setAllColor = new Button("Set");
			setAllColor.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	               for(int a=0; a<ledsArray.size(); a++){
	            	   ledsArray.get(a).setValue(allColorPicker.getValue());
	               }
	            }
	        });
			

			ColorPicker scopeColorPicker = new ColorPicker();
			scopeColorPicker.getStyleClass().add("button");
			
			SpinnerControl scope1 = new SpinnerControl();
			scope1.setDecimalsPlaces(0);
			scope1.setValue(1);
			scope1.setMaxWidth(80);
			scope1.getTextField().setContextMenu(new SpinnerIncrementationValueContextMenu(sc, new double[] {10.0, 1.0, 0.1, 0.01}));
			
			SpinnerControl scope2 = new SpinnerControl();
			scope2.setDecimalsPlaces(0);
			scope2.setValue(10);
			scope2.setMaxWidth(80);
			scope2.getTextField().setContextMenu(new SpinnerIncrementationValueContextMenu(sc, new double[] {10.0, 1.0, 0.1, 0.01}));
			
			Button setScopeColor = new Button("Set");
			setScopeColor.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	               for(int a=((int)scope1.getValue()-1); a<((int)scope2.getValue()); a++){
	            	   ledsArray.get(a).setValue(scopeColorPicker.getValue());
	               }
	            }
	        });
			
			
			
			 cb1 = new CheckBox();
			 cb1.selectedProperty().addListener(new ChangeListener<Boolean>() {
	                public void changed(ObservableValue ov,Boolean old_val, Boolean new_val) {
	                        if(new_val==true){
	                        	justListen();
	                        }
	                        else{
	                        	silenceIKillYou();
	                        }
	                }
	            });
	        
			
			
			autoColorPicker = new ColorPicker();
			autoColorPicker.getStyleClass().add("button");
			
		/*	Slider brightnesSlider = new Slider();
			
			brightnesSlider.setMax(255);
			brightnesSlider.setMin(0);
			brightnesSlider.setValue(255);
			brightnesSlider.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov,
	                    Number old_val, Number new_val) {
	                        brightnesValue.setText(String.valueOf(new_val.intValue()));
	                for(int a=0; a<ledsArray.size(); a++){
	               	Color c = ledsArray.get(a).getValue();
	               //	c.
	                }
	            
	            	}
	            });*/
			
			colorBox.setAlignment(Pos.CENTER);
			colorBox.getChildren().addAll(label2,allColorPicker,setAllColor,label3,scope1,label4,scope2,label5,scopeColorPicker,setScopeColor,space,label9,cb1,autoColorPicker);
			
			
			HBox functionBox = new HBox();
			functionBox.setPadding(new Insets(30,0,30,0));
			functionBox.setSpacing(10);
			functionBox.setAlignment(Pos.CENTER);
			ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList(
				    "First", "Second", "Third")
				);
			choiceBox.getSelectionModel().selectFirst();
			
			SpinnerControl timeSpinner = new SpinnerControl();
			timeSpinner.setDecimalsPlaces(3);
			timeSpinner.setValue(0);
			timeSpinner.setMaxWidth(160);
			timeSpinner.getTextField().setContextMenu(new SpinnerIncrementationValueContextMenu(sc, new double[] {10.0, 1.0, 0.1, 0.01}));
			
			SpinnerControl speedSpinner = new SpinnerControl();
			speedSpinner.setDecimalsPlaces(3);
			speedSpinner.setValue(0);
			speedSpinner.setMaxWidth(160);
			speedSpinner.getTextField().setContextMenu(new SpinnerIncrementationValueContextMenu(sc, new double[] {10.0, 1.0, 0.1, 0.01}));
			
			//functionBox.getChildren().addAll(label6,choiceBox,label7,timeSpinner,label8,speedSpinner);
			
			
			centerLayout = new VBox();
			centerLayout.setPadding(new Insets(10,0,0,0));
			
			
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			double value=(primaryScreenBounds.getWidth()-40)/29;
			
			int num=-1;
			
			for(int a=0; a<sc.getValue(); a++){
				if(a==0||((a%(int)value)==0&&a!=1)){
					HBox ledsbox = new HBox();
					ledsbox.setPadding(new Insets(10,0,0,0));
					hboxArray.add(ledsbox);
					num++;
				}
				Label label = new Label(Integer.toString(a+1));
				VBox vbox = new VBox();
				vbox.setAlignment(Pos.CENTER);
				
				ColorPicker colorPicker = new ColorPicker();
				colorPicker.getStyleClass().add("button");
				colorPicker.requestFocus();
				ledsArray.add(colorPicker);
				
				vbox.getChildren().addAll(label,colorPicker);
				hboxArray.get(num).getChildren().add(vbox);
				vboxArray.add(vbox);
				
			}
			
			centerLayout.setPadding(new Insets(20));
			centerLayout.getChildren().add(hboxtop);
			
			for(int a=0; a < hboxArray.size(); a++){
				centerLayout.getChildren().add(hboxArray.get(a));
			}

			centerLayout.getChildren().addAll(colorBox,functionBox);
			
			Canvas canvas = new Canvas(primaryScreenBounds.getWidth()-40,100);
			GraphicsContext gc = canvas.getGraphicsContext2D();
			gc.setFill(Color.WHITE);
			gc.fillRect(0, 0,primaryScreenBounds.getWidth(),100);
			
			Slider canvasSlider = new Slider();
			
			
			
			//centerLayout.getChildren().addAll(canvas,canvasSlider);
			
			
			
			Button generate = new Button("Generate");
			generate.setMaxWidth(Double.MAX_VALUE);
			
			generate.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            		BorderPane raport = new BorderPane();
	        			Stage stage = new Stage();
	                    stage.setTitle("Generated Code");
	                    stage.setScene(new Scene(raport, 600, 600));
	                    TextArea htmlEditor = new TextArea();
	                   
	                    String tab = "     ";
	                    
	                    StringBuilder code = new StringBuilder();
	                    
	                    boolean oneColor=true;
	                    Color first = ledsArray.get(0).getValue();
	                    for(int a=0; a<ledsArray.size(); a++){
	                    	if(ledsArray.get(a).getValue()!=first){
	                    		oneColor=false;
	                    	}
	                    }

	                    code.append("#include <Adafruit_NeoPixel.h>");
	                    code.append(System.lineSeparator());
	                    code.append("#define PIN 11");
	                    code.append(System.lineSeparator());
	                    code.append(System.lineSeparator());
	                    
	                    if(oneColor==false){
	                    	StringBuilder avalue = new StringBuilder();
	                    	StringBuilder rvalue = new StringBuilder();
	                    	StringBuilder gvalue = new StringBuilder();
	                    	StringBuilder bvalue = new StringBuilder();
	                    	
	                    	for(int i=0; i<ledsArray.size(); i++){
	                    		
	                    		String line = ledsArray.get(i).getValue().toString();
	                    		
	                    		int r = Integer.parseInt(line.substring(2,4),16);
	                	    	int g = Integer.parseInt(line.substring(4,6),16);
	                	    	int b = Integer.parseInt(line.substring(6,8),16);
	                	    	int a = Integer.parseInt(line.substring(8),16);
	                    		
	           
	                    		avalue.append(a+",");
	                    		rvalue.append(r+",");
	                    		gvalue.append(g+",");
	                    		bvalue.append(b+",");
	                    		
	                    	}
	                    	
	                    	code.append("short R[] = {"+rvalue.toString().substring(0,rvalue.length()-1)+"};");
	                    	code.append(System.lineSeparator());
	                    	code.append("short G[] = {"+gvalue.toString().substring(0,gvalue.length()-1)+"};");
	                    	code.append(System.lineSeparator());
	                    	code.append("short B[] = {"+bvalue.toString().substring(0,bvalue.length()-1)+"};");
	                    	code.append(System.lineSeparator());
	                    	code.append(System.lineSeparator());
	                    }
	                    
	                    code.append("Adafruit_NeoPixel strip = Adafruit_NeoPixel("+(int)sc.getValue()+", PIN, NEO_GRB + NEO_KHZ800);");
	                    code.append(System.lineSeparator());
	                    code.append(System.lineSeparator());
	                    code.append("void setup() {");
	                    code.append(System.lineSeparator());
	                    code.append(tab+"strip.begin();");
	                  //  code.append(System.lineSeparator());
	                 //   code.append(tab+"strip.setBrightness("+brightnesValue.getText()+");");
	                    code.append(System.lineSeparator());
	                    code.append(tab+"strip.show();");
	                    code.append(System.lineSeparator());
	                    code.append("}");
	                    code.append(System.lineSeparator());
	                    code.append(System.lineSeparator());
	                    code.append("void loop() {");
	                    code.append(System.lineSeparator());
	                    
	                    if(oneColor==true){
		                    code.append(tab+"setOneColor(strip.Color("+(int)(first.getRed()*255)+","+(int)(first.getGreen()*255)+","+(int)(first.getBlue()*255)+"));");
		                    code.append(System.lineSeparator());
		                    code.append("}");
		                    
		                    
		                    code.append(System.lineSeparator());
		                    code.append(System.lineSeparator());
		                    code.append("void setOneColor(uint32_t c){");
		                    code.append(System.lineSeparator());
		                    code.append(tab+"for(uint16_t i=0; i<strip.numPixels(); i++) {");
		                    code.append(System.lineSeparator());
		                    code.append(tab+tab+"strip.setPixelColor(i, c);");
		                    code.append(System.lineSeparator());
		                   
		                    code.append(tab+"}");
		                    code.append(System.lineSeparator());
		                    code.append(tab+"strip.show();");
		                    code.append(System.lineSeparator());
		                    code.append("}");
	                    
	                    }
	                    
	                    else{
	                    	code.append(tab+"setManyColors(R,G,B);");
		                    code.append(System.lineSeparator());
		                    code.append("}");
		                    code.append(System.lineSeparator());
		                    code.append(System.lineSeparator());
		                    code.append("void setManyColors(short R[], short G[], short B[]){");
		                    code.append(System.lineSeparator());
		                    
		                    code.append(tab+"for(uint16_t i=0; i<strip.numPixels(); i++) {");
		                    code.append(System.lineSeparator());
		                    code.append(tab+tab+"strip.setPixelColor(i, strip.Color(R[i],G[i],B[i]));");
		                    code.append(System.lineSeparator());
		                    code.append(tab+"}");
		                    code.append(System.lineSeparator());
		                    code.append(tab+"strip.show();");
		                    code.append(System.lineSeparator());
		                    code.append("}");
	                    
	                    
	                    }

	                    htmlEditor.setText(code.toString());

	                    raport.setCenter(htmlEditor);
	                   
	                    Image image = new Image(getClass().getResourceAsStream("hal_9000.png"));
	                    stage.getIcons().add(image);
	                    stage.show();
	            }
	        });
			
		
			
			
			BorderPane root = new BorderPane();
			root.setTop(menuBar);
			root.setCenter(centerLayout);
			root.setBottom(generate);
			
			Scene scene = new Scene(root,primaryScreenBounds.getWidth(),primaryScreenBounds.getHeight()-40);

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			
			Image image = new Image(getClass().getResourceAsStream("hal_9000.png"));
			primaryStage.getIcons().add(image);
			primaryStage.setMinWidth(primaryScreenBounds.getWidth());

			
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void justListen(){
		for(int a=0; a<ledsArray.size(); a++){
			
			ColorPicker temp = ledsArray.get(a);
			temp.setOnMouseClicked(new EventHandler<MouseEvent>() {
			    public void handle(MouseEvent me) {
			    	temp.setValue(autoColorPicker.getValue());
			    }
			});
	}
	}
	
	public void silenceIKillYou(){
		for(int a=0; a<ledsArray.size(); a++){
			
			ColorPicker temp = ledsArray.get(a);
			temp.setOnMouseClicked(null);
	}

}
		
		
	
	public void setLeds(double leds){
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		double value=(primaryScreenBounds.getWidth()-40)/29;
		value = Math.floor(value);

		
		if(ledsArray.size()>leds){
			for(int a=ledsArray.size(); a>leds; a--){
				
				
				ledsArray.remove(a-1);
				vboxArray.remove(a-1);		
				
				
				HBox hbox= (HBox) centerLayout.getChildren().get((int)(ledsArray.size()/value)+1);
				hbox.getChildren().remove(hbox.getChildren().size()-1);

				System.out.println(centerLayout.getChildren().size());
			
				if((ledsArray.size())%value==0){
					centerLayout.getChildren().remove((int)((ledsArray.size())/value)+1);
				}
				
			}
		}
		else if(ledsArray.size()<leds){
			
			for(int a=ledsArray.size(); a<leds; a++){
				
				Label label = new Label(Integer.toString(a+1));
				VBox vbox = new VBox();
				vbox.setAlignment(Pos.CENTER);
				
				ColorPicker colorPicker = new ColorPicker();
				colorPicker.getStyleClass().add("button");
			
				vbox.getChildren().addAll(label,colorPicker);
				ledsArray.add(colorPicker);
				vboxArray.add(vbox);
				
				
				if((ledsArray.size()-1)%value==0){
					HBox newHbox = new HBox();
					newHbox.getChildren().add(vbox);
					centerLayout.getChildren().add((int)(ledsArray.size()/value)+1, newHbox);

				}
				else{
					HBox hbox = (HBox) centerLayout.getChildren().get((int)((ledsArray.size()-1)/value)+1);
					hbox.setPadding(new Insets(10,0,0,0));
					hbox.getChildren().add(vbox);
				}
				
			}
			
			if(cb1.isSelected()){
				justListen();
			}
			else{
				silenceIKillYou();
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
