/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebagrafojfxml;



import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import java.util.List;

import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 *
 * @author adgao
 */
public class FXMLDocumentControllerConfig implements Initializable {
@FXML
private Rectangle noTerminal1; 
@FXML
private Rectangle noTerminal2; 
@FXML
private Rectangle terminal; 
@FXML
private Rectangle readChainR; 
@FXML
private Rectangle pendChainR;

private Label labelreadChain; 

private Label labelpendChain;

private Label labelNoTerminal1; 

private Label labelNoTerminal2;

private Label labelTerminal;
@FXML
private Label grammarNoTerminal1;
@FXML
private Label grammarTerminal;
@FXML
private Label grammarNoTerminal2;
@FXML
private Label action;
@FXML
private ComboBox sizeFuenteArbol;
@FXML
private ComboBox sizeFuenteTraduc;
@FXML
private ComboBox sizeFuenteCadena;
@FXML
private ComboBox fountTypeSemanticAct;
@FXML
private ComboBox sizeFountSemanticAct;
@FXML
private ColorPicker backgroundColorTerminals;
@FXML
private ColorPicker fontColorTerminals;
@FXML
private ColorPicker backgroundColorNoTerminals;
@FXML
private ColorPicker fontColorNoTerminals;
@FXML
private ColorPicker readPart;
@FXML
private ColorPicker pendPart;
@FXML
private ColorPicker colorFountSemanticAct;
@FXML
private javafx.scene.control.Button aceptButton;
@FXML
private javafx.scene.control.Button cancelButton;
@FXML
private Pane example;

private Grafo arbolEjemplo;
//private int posYCadena=300;
//private int posYHijos=100;
//private int xInicial=100;
//private int xFinal=200;
//private List<Object> terminales=new ArrayList<>();
//private List<Object> nTerminales=new ArrayList<>();
//private int xPadre=150;
//private int yPadre=50;
//private int posRegla=200;
//private int incCadena=70;

private String colorBackgroundTerminals;
private String colorTextTerminals;
private String colorBackgroundNoTerminals;
private String colorTextNoTerminals;
private String colorAction;
private String colorPendChain;
private String colorReadChain;
private int actionSize;
private int treeSize;
private int grammarSize;
private int chainSize;
private String actionType;
//private ModificacionesTemp mT;
private Configuracion lectConf;
private Integer maxPosition;

//@FXML
///**
// * save the new configuration
// */
//    public void handleNewFont(ActionEvent event) throws IOException {
//        Object o=event.getSource();
//        if (o.equals(sizeFuenteArbol)){
//            
//        }
//        
//    }
@FXML
/**
 * save the new configuration
 */
    public void handleAcept(ActionEvent event) throws IOException {
        
        Configuracion conf = new Configuracion();
        conf.guardarConfiguracion(".//config//configActual.xml",treeSize,grammarSize,chainSize,colorBackgroundTerminals,
                colorBackgroundNoTerminals,colorTextTerminals,colorTextNoTerminals,colorReadChain,colorPendChain,
                colorAction,actionType,actionSize,lectConf.getZoomGraph(),lectConf.getZoomGrammar(),lectConf.getZoomChain());
        // get a handle to the stage
        Stage stage = (Stage) aceptButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
@FXML
/**
 * close the window
 */
    public void handleCancel(ActionEvent event) throws IOException {
        
        // get a handle to the stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }  
@FXML
/**
 * change the color of the elements using the ColorPickers
 */
    public void handleChooseColor(ActionEvent event) throws IOException {
        ColorPicker e=(ColorPicker)event.getSource();
        if(e.equals(backgroundColorTerminals)){
            javafx.scene.paint.Color coo=backgroundColorTerminals.getValue();    
            System.out.println(coo);  
            terminal.setFill(coo);
            pendChainR.setFill(coo);
            readChainR.setFill(coo);
            colorBackgroundTerminals=coo.toString();
            if(!compararColores(coo, fontColorTerminals.getValue())){
                fontColorTerminals.setStyle("-fx-border-color:yellow");
                backgroundColorTerminals.setStyle("-fx-border-color:yellow");
                fontColorTerminals.setTooltip(new Tooltip("Colores poco compatibles"));
                backgroundColorTerminals.setTooltip(new Tooltip("Colores poco compatibles"));
            }
            if(!compararColores(coo, pendPart.getValue())){
                pendPart.setStyle("-fx-border-color:yellow");
                backgroundColorTerminals.setStyle("-fx-border-color:yellow");
                pendPart.setTooltip(new Tooltip("Colores poco compatibles"));
                backgroundColorTerminals.setTooltip(new Tooltip("Colores poco compatibles"));
            }
            
            if(!compararColores(coo, readPart.getValue())){
                readPart.setStyle("-fx-border-color:yellow");
                backgroundColorTerminals.setStyle("-fx-border-color:yellow");
                readPart.setTooltip(new Tooltip("Colores poco compatibles"));
                backgroundColorTerminals.setTooltip(new Tooltip("Colores poco compatibles"));
            }
            if(compararColores(coo, readPart.getValue()) && compararColores(coo, pendPart.getValue()) && compararColores(coo, fontColorTerminals.getValue())){
                readPart.setStyle(null);
                backgroundColorTerminals.setStyle(null);
                readPart.setTooltip(null);
                backgroundColorTerminals.setTooltip(null);
                fontColorTerminals.setStyle(null);
                fontColorTerminals.setTooltip(null);
                pendPart.setStyle(null);
                pendPart.setTooltip(null);
            }
        }
        else if(e.equals(fontColorTerminals)){
            javafx.scene.paint.Color coo=fontColorTerminals.getValue();   
            System.out.println(coo);
            labelTerminal.setTextFill(coo);
            grammarTerminal.setTextFill(coo);
            colorTextTerminals=coo.toString();
            if(!compararColores(coo, backgroundColorTerminals.getValue())){
                fontColorTerminals.setStyle("-fx-border-color:yellow");
                backgroundColorTerminals.setStyle("-fx-border-color:yellow");
                fontColorTerminals.setTooltip(new Tooltip("Colores poco compatibles"));
                backgroundColorTerminals.setTooltip(new Tooltip("Colores poco compatibles"));
            }
            else{
                if(compararColores(backgroundColorTerminals.getValue(), pendPart.getValue())&&compararColores(backgroundColorTerminals.getValue(), readPart.getValue())){
                    backgroundColorTerminals.setStyle(null);
                    backgroundColorTerminals.setTooltip(null);
                }
                fontColorTerminals.setStyle(null);
                
                fontColorTerminals.setTooltip(null);
                
            }
        }
        else if(e.equals(fontColorNoTerminals)){
            javafx.scene.paint.Color coo=fontColorNoTerminals.getValue();   
            System.out.println(coo);
            labelNoTerminal1.setTextFill(coo);
            labelNoTerminal2.setTextFill(coo);
            grammarNoTerminal1.setTextFill(coo);
            grammarNoTerminal2.setTextFill(coo);
            colorTextNoTerminals=coo.toString();
            if(!compararColores(coo, backgroundColorNoTerminals.getValue())){
                fontColorNoTerminals.setStyle("-fx-border-color:yellow");
                backgroundColorNoTerminals.setStyle("-fx-border-color:yellow");
                fontColorNoTerminals.setTooltip(new Tooltip("Colores poco compatibles"));
                backgroundColorNoTerminals.setTooltip(new Tooltip("Colores poco compatibles"));
            }
            else{
                fontColorNoTerminals.setStyle(null);
                backgroundColorNoTerminals.setStyle(null);
                fontColorNoTerminals.setTooltip(null);
                backgroundColorNoTerminals.setTooltip(null);
            }
        }
        
        else if(e.equals(backgroundColorNoTerminals)){
            javafx.scene.paint.Color coo=backgroundColorNoTerminals.getValue();   
            System.out.println(coo);
            noTerminal1.setFill(coo);
            noTerminal2.setFill(coo);
            colorBackgroundNoTerminals=coo.toString();
            if(!compararColores(coo, fontColorNoTerminals.getValue())){
                fontColorNoTerminals.setStyle("-fx-border-color:yellow");
                backgroundColorNoTerminals.setStyle("-fx-border-color:yellow");
                fontColorNoTerminals.setTooltip(new Tooltip("Colores poco compatibles"));
                backgroundColorNoTerminals.setTooltip(new Tooltip("Colores poco compatibles"));
            }
            else{
                fontColorNoTerminals.setStyle(null);
                backgroundColorNoTerminals.setStyle(null);
                fontColorNoTerminals.setTooltip(null);
                backgroundColorNoTerminals.setTooltip(null);
            }
              
        }
        else if(e.equals(pendPart)){
            javafx.scene.paint.Color coo=pendPart.getValue();   
            System.out.println(coo);
            labelpendChain.setTextFill(coo);
            colorPendChain=coo.toString();
            if(!compararColores(coo, backgroundColorTerminals.getValue())){
                pendPart.setStyle("-fx-border-color:yellow");
                backgroundColorTerminals.setStyle("-fx-border-color:yellow");
                pendPart.setTooltip(new Tooltip("Colores poco compatibles"));
                backgroundColorTerminals.setTooltip(new Tooltip("Colores poco compatibles"));
            }
            else{
               
                if(compararColores(backgroundColorTerminals.getValue(),fontColorTerminals.getValue())&& compararColores(backgroundColorTerminals.getValue(), readPart.getValue())){
                    backgroundColorTerminals.setStyle(null);
                    backgroundColorTerminals.setTooltip(null);
                }
                
                pendPart.setStyle(null);
                
                pendPart.setTooltip(null);
                
            }
        }
        else if(e.equals(readPart)){
            javafx.scene.paint.Color coo=readPart.getValue();   
            System.out.println(coo);
            labelreadChain.setTextFill(coo);
            colorReadChain=coo.toString();
            if(!compararColores(coo, backgroundColorTerminals.getValue())){
                readPart.setStyle("-fx-border-color:yellow");
                backgroundColorTerminals.setStyle("-fx-border-color:yellow");
                readPart.setTooltip(new Tooltip("Colores poco compatibles"));
                backgroundColorTerminals.setTooltip(new Tooltip("Colores poco compatibles"));
            }
            else{
                if(compararColores(backgroundColorTerminals.getValue(),fontColorTerminals.getValue())&& compararColores(backgroundColorTerminals.getValue(), pendPart.getValue())){
                    backgroundColorTerminals.setStyle(null);
                    backgroundColorTerminals.setTooltip(null);
                }
                readPart.setStyle(null);
                
                readPart.setTooltip(null);
               
            }
        }
        else if(e.equals(colorFountSemanticAct)){
            javafx.scene.paint.Color coo=colorFountSemanticAct.getValue();   
            System.out.println(coo);
            action.setTextFill(coo);
            colorAction=coo.toString();
        }
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        lectConf= new Configuracion();
        lectConf.cargarConfiguracion("./config/configActual.xml");
        colorBackgroundTerminals=lectConf.getColorTerminal();
        colorTextTerminals=lectConf.getLetraTerminal();
        colorBackgroundNoTerminals=lectConf.getColorNoTerminal();
        colorTextNoTerminals=lectConf.getLetraNoTerminal();
        colorAction=lectConf.getColorAccSem();
        colorPendChain=lectConf.getColorPend();
        colorReadChain=lectConf.getColorLeido();
        actionSize=lectConf.getSizeAcciones();
        treeSize=lectConf.getLetraArbol();
        grammarSize=lectConf.getLetraTraductor();
        chainSize=lectConf.getLetraCadena();
        actionType=lectConf.getTipoLetra();
        
        Color color=Color.web(lectConf.getColorTerminal());
        terminal.setFill(color);
        pendChainR.setFill(color);
        pendChainR.setOpacity(0.5);
        readChainR.setFill(color);
        color=Color.web(lectConf.getColorNoTerminal());
        
        noTerminal1.setFill(color);
        noTerminal2.setFill(color);
        color=Color.web(lectConf.getLetraTerminal());
        labelTerminal=new Label("num");
        labelTerminal.setTextFill(color);
        labelTerminal.setFont(new Font(lectConf.getLetraArbol()));
        labelTerminal.setLayoutX(terminal.getLayoutX()+terminal.getWidth()/2-(labelTerminal.getText().length()*(lectConf.getLetraArbol()/2)/2));
        labelTerminal.setLayoutY(terminal.getLayoutY()+terminal.getHeight()/2-lectConf.getLetraArbol());
        grammarTerminal.setTextFill(color);
        grammarTerminal.setFont(new Font(lectConf.getLetraTraductor()));
        color=Color.web(lectConf.getLetraNoTerminal());
        labelNoTerminal1=new Label("A");
        labelNoTerminal1.setTextFill(color);
        labelNoTerminal1.setFont(new Font(lectConf.getLetraArbol()));
        labelNoTerminal1.setLayoutX(noTerminal1.getLayoutX()+noTerminal1.getWidth()/2-(labelNoTerminal1.getText().length()*(lectConf.getLetraArbol()/2)/2));
        labelNoTerminal1.setLayoutY(noTerminal1.getLayoutY()+noTerminal1.getHeight()/2-lectConf.getLetraArbol());
        labelNoTerminal2=new Label("EXP");
        labelNoTerminal2.setTextFill(color);
        labelNoTerminal2.setFont(new Font(lectConf.getLetraArbol()));
        labelNoTerminal2.setLayoutX(noTerminal2.getLayoutX()+noTerminal2.getWidth()/2-(labelNoTerminal2.getText().length()*(lectConf.getLetraArbol()/2)/2));
        labelNoTerminal2.setLayoutY(noTerminal2.getLayoutY()+noTerminal2.getHeight()/2-lectConf.getLetraArbol());
        grammarNoTerminal1.setTextFill(color);
        grammarNoTerminal1.setFont(new Font(lectConf.getLetraTraductor()));
        grammarNoTerminal2.setTextFill(color);
        grammarNoTerminal2.setFont(new Font(lectConf.getLetraTraductor()));
        color=Color.web(lectConf.getColorAccSem());
        action.setTextFill(color);
        action.setFont(new Font(lectConf.getTipoLetra(),lectConf.getLetraTraductor()));
        color=Color.web(lectConf.getColorPend());
        labelpendChain=new Label("+");
        labelpendChain.setTextFill(color);
        labelpendChain.setFont(new Font(lectConf.getLetraCadena()));
        double heigth=labelpendChain.getFont().getSize()+10;
        double width=labelpendChain.getFont().getSize()+10*labelpendChain.getText().length();
        labelpendChain.setLayoutX(pendChainR.getLayoutX()+width/4);
        labelpendChain.setLayoutY(pendChainR.getLayoutY());
        
        color=Color.web(lectConf.getColorLeido());
        labelreadChain=new Label("4");
        labelreadChain.setTextFill(color);
        labelreadChain.setFont(new Font(lectConf.getLetraCadena()));
        Double aux=readChainR.getLayoutX();

        labelreadChain.setLayoutX(readChainR.getLayoutX()+readChainR.getWidth()/2-(labelreadChain.getText().length()*(lectConf.getLetraCadena()/2)/2));
        labelreadChain.setLayoutY(readChainR.getLayoutY()+readChainR.getHeight()/2-lectConf.getLetraCadena());
        
        labelreadChain.setLayoutX(readChainR.getLayoutX()+width/4);
        labelreadChain.setLayoutY(readChainR.getLayoutY());
        example.getChildren().addAll(labelNoTerminal1,labelNoTerminal2,labelTerminal,labelpendChain,labelreadChain);
//        if(lectConf.getLetraArbol()>15){
//            noTerminal1.setWidth(50+labelNoTerminal1.getText().length()-3+lectConf.getLetraArbol()); 
//                       // noTerminal1.setHeight(50+/*labelNoTerminal1.getText().length()+*/val);
//            terminal.setWidth(50+labelNoTerminal1.getText().length()-3+lectConf.getLetraArbol());
//        }
//       

        readChainR.setWidth(width); 
        readChainR.setHeight(heigth);       
        pendChainR.setWidth(width);
        pendChainR.setHeight(heigth);

        ObservableList<String> optionsSizeLetraArbol = FXCollections.observableArrayList( "8","10","13","15","18","20");       
        sizeFuenteArbol.setItems(optionsSizeLetraArbol);
        sizeFuenteArbol.setValue(lectConf.getLetraArbol()+"");
        sizeFuenteArbol.setPromptText(lectConf.getLetraArbol()+"");
        
        sizeFuenteArbol.setEditable(true);
        //sizeFuenteArbol.setOnAction();
        sizeFuenteArbol.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {
                String valAux=sizeFuenteArbol.getValue().toString();
                try{
                    Integer val= Integer.parseInt(valAux);
                    labelNoTerminal1.setFont(new Font(val));
                    labelNoTerminal2.setFont(new Font(val));
                    labelTerminal.setFont(new Font(val));
                    labelNoTerminal2.setLayoutX(noTerminal2.getLayoutX()+noTerminal2.getWidth()/2-(labelNoTerminal2.getText().length()*(val/2)/2));
                    labelNoTerminal2.setLayoutY(noTerminal2.getLayoutY()+noTerminal2.getHeight()/2-val);
                    labelNoTerminal1.setLayoutX(noTerminal1.getLayoutX()+noTerminal1.getWidth()/2-(labelNoTerminal1.getText().length()*(val/2)/2));
                    labelNoTerminal1.setLayoutY(noTerminal1.getLayoutY()+noTerminal1.getHeight()/2-val);
                    labelTerminal.setLayoutX(terminal.getLayoutX()+terminal.getWidth()/2-(labelTerminal.getText().length()*(val/2)/2));
                    labelTerminal.setLayoutY(terminal.getLayoutY()+terminal.getHeight()/2-val);
        
                    /*if(treeSize<val){
                    labelNoTerminal1.setLayoutX(labelNoTerminal1.getLayoutX()+(labelNoTerminal1.getText().length()*(val/2)/2));
                    labelNoTerminal2.setLayoutX(labelNoTerminal2.getLayoutX()+(labelNoTerminal2.getText().length()*(val/2)/2));
                    labelTerminal.setLayoutX(labelTerminal.getLayoutX()+(labelTerminal.getText().length()*(val/2)/2));
                    }
                    else{
                    labelNoTerminal1.setLayoutX(labelNoTerminal1.getLayoutX()-(labelNoTerminal1.getText().length()*(val/2)/2));
                    labelNoTerminal2.setLayoutX(labelNoTerminal2.getLayoutX()-(labelNoTerminal2.getText().length()*(val/2)/2));
                    labelTerminal.setLayoutX(labelTerminal.getLayoutX()-(labelTerminal.getText().length()*(val/2)/2));
                    }*/
//                    if(val>15){
//                        noTerminal1.setWidth(50+labelNoTerminal1.getText().length()-3+val); 
//                       // noTerminal1.setHeight(50+/*labelNoTerminal1.getText().length()+*/val);
//                        noTerminal2.setWidth(50+labelNoTerminal1.getText().length()-3+val);
//                        terminal.setWidth(50+labelNoTerminal1.getText().length()-3+val); 
//                    }
//                    else{
//                        noTerminal1.setWidth(50); 
//                       // noTerminal1.setHeight(50+/*labelNoTerminal1.getText().length()+*/val);
//                        noTerminal2.setWidth(50);
//                        terminal.setWidth(50); 
//
//
//                    }
                    //noTerminal2.setHeight(50+/*labelNoTerminal1.getText().length()+*/val);
                    //terminal.setHeight(50+/*labelNoTerminal1.getText().length()+*/val);        
                    treeSize=val;
                }
                catch(Exception e){
                    
                }
                //mT.setSizeLetraArbol(val);                
                //modificarArbol();
            }    
        });
        ObservableList<String> optionsSizeLetraCadena = FXCollections.observableArrayList( "8","10","13","15","18","20");
        sizeFuenteCadena.setItems(optionsSizeLetraCadena);
        sizeFuenteCadena.setValue(lectConf.getLetraCadena()+"");
        sizeFuenteCadena.setPromptText(lectConf.getLetraCadena()+"");
        
        sizeFuenteCadena.setEditable(true);
        sizeFuenteCadena.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {
                String valAux=sizeFuenteCadena.getValue().toString();
                try{
                    Integer val= Integer.parseInt(valAux);
                    labelpendChain.setFont(new Font(val));
                    labelreadChain.setFont(new Font(val));
                    double heigth=labelpendChain.getFont().getSize()+10;
                    double width=labelpendChain.getFont().getSize()+10*labelpendChain.getText().length();
                    readChainR.setWidth(width); 
                    readChainR.setHeight(heigth);
                    pendChainR.setWidth(width);
                    pendChainR.setHeight(heigth);
                    chainSize=val;
                    labelpendChain.setLayoutX(pendChainR.getLayoutX()+width/4);
                    labelpendChain.setLayoutY(pendChainR.getLayoutY());
                    labelreadChain.setLayoutX(readChainR.getLayoutX()+width/4);
                    labelreadChain.setLayoutY(readChainR.getLayoutY());
                }
                catch(Exception e){
                    
                }
                //mT.setSizeCadena(val);                
                //modificarArbol();
            }    
        });
        ObservableList<String> optionsSizeLetraTraductor = FXCollections.observableArrayList( "8","10","13","15","18","20");
        sizeFuenteTraduc.setItems(optionsSizeLetraTraductor);
        sizeFuenteTraduc.setValue(lectConf.getLetraTraductor()+"");
        sizeFuenteTraduc.setPromptText(lectConf.getLetraTraductor()+"");
        
        sizeFuenteTraduc.setEditable(true);
        sizeFuenteTraduc.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {
                String valAux=sizeFuenteTraduc.getValue().toString();
                try{
                    Integer val= Integer.parseInt(valAux);
                    grammarNoTerminal1.setFont(new Font(val));
                    grammarNoTerminal2.setFont(new Font(val));
                    grammarTerminal.setFont(new Font(val));
                    grammarSize=val;
                }
                catch(Exception e){
                    
                }
                //mT.setSizeLetraTraductor(val);                
                //modificarArbol();
            }    
        });
        actionSize=lectConf.getSizeAcciones();
        ObservableList<String> optionsSizeLetraSemanticAct = FXCollections.observableArrayList( "8","10","13","15","18","20");       
        sizeFountSemanticAct.setItems(optionsSizeLetraSemanticAct);
        sizeFountSemanticAct.setValue(lectConf.getSizeAcciones()+"");
        sizeFountSemanticAct.setPromptText(lectConf.getLetraTraductor()+"");
        
        sizeFountSemanticAct.setEditable(true);
        sizeFountSemanticAct.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {
                String valAux=sizeFountSemanticAct.getValue().toString();
                try{
                    Integer val= Integer.parseInt(valAux);
                    action.setFont(new Font(val));
                    actionSize=val;
                }
                catch(Exception e){
                    
                }
                //mT.setSizeAcciones(val);                
                //modificarArbol();
            }    
        });
        ObservableList<String> optionsFountType = FXCollections.observableArrayList( "Times new Roman","Arial","Calibri","Courier","Broadway","Informal Roman","Verdana");       
        fountTypeSemanticAct.setItems(optionsFountType);
        fountTypeSemanticAct.setValue(lectConf.getTipoLetra()+"");
        fountTypeSemanticAct.setEditable(true);
        fountTypeSemanticAct.setPromptText(lectConf.getTipoLetra());
        fountTypeSemanticAct.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {
                String val=(String)fountTypeSemanticAct.getValue();
                action.setFont(new Font(val,actionSize));
                actionType=val;
                //mT.setTipoLetra(val);                
                //modificarArbol();
            }    
        });
        
        color=Color.web(lectConf.getColorTerminal());
        backgroundColorTerminals.setValue(color);
        color=Color.web(lectConf.getLetraTerminal());
        fontColorTerminals.setValue(color);
        color=Color.web(lectConf.getColorNoTerminal());
        backgroundColorNoTerminals.setValue(color);
        color=Color.web(lectConf.getLetraNoTerminal());
        fontColorNoTerminals.setValue(color);
        color=Color.web(lectConf.getColorPend());
        pendPart.setValue(color);
        color=Color.web(lectConf.getColorLeido());
        readPart.setValue(color);
        color=Color.web(lectConf.getColorAccSem());
        colorFountSemanticAct.setValue(color);
        fountTypeSemanticAct.setValue(lectConf.getTipoLetra());
        if(!compararColores(backgroundColorTerminals.getValue(), fontColorTerminals.getValue())){
            fontColorTerminals.setStyle("-fx-border-color:yellow");
            backgroundColorTerminals.setStyle("-fx-border-color:red");
            fontColorTerminals.setTooltip(new Tooltip("Colores poco compatibles"));
            backgroundColorTerminals.setTooltip(new Tooltip("Colores poco compatibles"));
        }
        if(!compararColores(backgroundColorTerminals.getValue(), pendPart.getValue())){
            pendPart.setStyle("-fx-border-color:yellow");
            backgroundColorTerminals.setStyle("-fx-border-color:red");
            pendPart.setTooltip(new Tooltip("Colores poco compatibles"));
            backgroundColorTerminals.setTooltip(new Tooltip("Colores poco compatibles"));
        }
            
        if(!compararColores(backgroundColorTerminals.getValue(), readPart.getValue())){
            readPart.setStyle("-fx-border-color:yellow");
            backgroundColorTerminals.setStyle("-fx-border-color:red");
            readPart.setTooltip(new Tooltip("Colores poco compatibles"));
            backgroundColorTerminals.setTooltip(new Tooltip("Colores poco compatibles"));
        }
        
        if(!compararColores(fontColorNoTerminals.getValue(), backgroundColorNoTerminals.getValue())){
            fontColorNoTerminals.setStyle("-fx-border-color:yellow");
            backgroundColorNoTerminals.setStyle("-fx-border-color:red");
            fontColorNoTerminals.setTooltip(new Tooltip("Colores poco compatibles"));
            backgroundColorNoTerminals.setTooltip(new Tooltip("Colores poco compatibles"));
        }
        
    }
    /**
     * 
     * @param c1
     * @param c2
     * @return 
     */
    public boolean compararColores(Color c1,Color c2){
        boolean noSePuede=true;
        if( !(Math.abs(c1.getHue()-c2.getHue())>50)||!(Math.abs(c1.getHue()+34-360-c2.getHue())>50)||!(Math.abs(c1.getHue()-c2.getHue()+34-360)>50))  {
            
            if(Math.abs(c1.getSaturation()-c2.getSaturation())>0.70)
//                if (Math.abs(c1.getBrightness()-c2.getBrightness())>0.16)   
       
                        noSePuede=true;
//                else
//                    noSePuede=false;
            else 
                noSePuede=false;
        }
//        else{
//            if( c1.getSaturation()<50 && c2.getSaturation()<50 && !(Math.abs(c1.getSaturation()-c2.getSaturation())>20))  {
////                if((Math.abs(c1.getSaturation()-c2.getSaturation())>0.79)&&(Math.abs(c1.getBrightness()-c2.getBrightness())>0.16))            
////                    noSePuede=true;
////                else 
//                    noSePuede=false;
//            }
//        }
        return noSePuede;
        
    }
 /**
  * Recive a color in format Swing and transform it into a format javaFX
  * @param color
  * String with the color in format Swing
  * @return 
  * Color in format javafx.scene.paint.Color
  */
// public javafx.scene.paint.Color newColorFX(String color){
//    int col=Integer.parseInt(color,16);
//    Color color1=new Color(col);
//    double red=color1.getRed()/255.0;
//    double green=color1.getGreen()/255.0;
//    double blue=color1.getBlue()/255.0;
//    double opacity=color1.getAlpha()/255.0;
//    return new javafx.scene.paint.Color(red, green, blue, opacity);
// }
 /**
  * recive the color of a ColorPicker and transform it in a color for swing
  * @param color
  * Color in format javafx.scene.paint.Color
  * @return 
  * Color in format Swing
  */
// public Color newColorSw(javafx.scene.paint.Color color){
// 
//    float red=(float)(color.getRed());
//    float green=(float) (color.getGreen());
//    float blue=(float) (color.getBlue());
//   // float alpha=(float) (color.getOpacity()*255);
//    return new Color(red, green, blue);
// }
 /**
  * recive the color of a ColorPicker and transform it in a color for swing
  * @param color
  * Color in format javafx.scene.paint.Color
  * @return 
  * Color in format Swing
  */
// public String colorToSave(javafx.scene.paint.Color color){
//    color.
//    float red=(float)(color.getRed())*255;
//    float green=(float) (color.getGreen())*255;
//    float blue=(float) (color.getBlue())*255;
//   // float alpha=(float) (color.getOpacity()*255);
//    return ;
// }
        }
