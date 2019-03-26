/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebagrafojfxml;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 *
 * @author adgao
 */
public class CadenaEntrada {
private List<String> cadenaPorPaso;
private Configuracion config;
private double posX;
private Pane panelPadre;
private HashMap<String,Rectangle> rectanglesChain;
//private HashMap<String,String> rectanglesText;
private HashMap<String,Label>labels;
private int font;

    /**
     * Builder
     * @param cadenaPorPaso
     * list of the state of the chain for each step
     * @param panelPadre 
     * Panel where draw the chain
     */
    public CadenaEntrada(List<String> cadenaPorPaso,Pane panelPadre,Configuracion config) {
        this.cadenaPorPaso = cadenaPorPaso;
        this.config=config;
        posX=10;
        this.panelPadre=panelPadre;
        rectanglesChain=new HashMap<>();
        //rectanglesText=new HashMap<>();
        labels=new HashMap<>();
        font=config.getLetraCadena();
    }
    /**
     * Build the chain
     */
    public void construir(){
        String[] chain=cadenaPorPaso.get(/*step*/0).split("pend");
        String completeChain=chain[0]+chain[1]+" EOF";
        String[] chainToRepresent=completeChain.split(" ");
        double posY=50/*panelPadre.getHeight()/2*/;
        for(int i=0;i<chainToRepresent.length;i++){
            String elem=chainToRepresent[i];
            Label l=new Label(elem);
            l.setFont(new Font(config.getLetraCadena()));
            double heigth=l.getFont().getSize()+10;
            double width=l.getFont().getSize()+10*l.getText().length();
            Rectangle r=new Rectangle(width,heigth);
            r.setFocusTraversable(true);
            r.setOnMouseEntered(new EventHandler<MouseEvent>(){

                @Override
                public void handle(MouseEvent event) {

              
                      r.setStroke(Paint.valueOf("0000ff"));  
                      
                    
                
                }

            });
            r.setOnMouseExited(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {


                      r.setStroke(Paint.valueOf("ffffff"));  



            }

            });
            r.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
                {
                    if (newPropertyValue)
                    {
                      r.setStroke(Paint.valueOf("0000ff"));  
                      
                    }
                    else
                    {
                      r.setStroke(Paint.valueOf("ffffff"));  
                    }
                }
            });
//            if(r.isFocused()){
//                r.setStroke(Paint.valueOf("33ff3f"));
//            }
//            else
            r.setOpacity(0.5);
            l.setLayoutX(posX+width/4);
            l.setLayoutY(posY);
            Color colorAct=Color.web(config.getColorPend());
            l.setTextFill(colorAct);
            
            r.setLayoutX(posX);
            r.setLayoutY(posY);
            r.setId(elem);
            Color colorRectangle=Color.web(config.getColorTerminal());
            r.setFill(colorRectangle);
            rectanglesChain.put(elem, r);
            //rectanglesText.put(r.getId(), elem);
            labels.put(elem, l);
            panelPadre.getChildren().addAll(r,l);
            posX+=width+20;
        }
    }/**
     * update the appearance of the chain 
     * @param step 
     * step to update
     */
    public void actualizarCadena(int step){
        String[] pendExec=cadenaPorPaso.get(step).split("pend");
        String[] exec=pendExec[0].split(" ");
        //int aux=0;
//        posX=10;
//        double posY=50/*panelPadre.getHeight()/2*/;
        HashSet<String> execToCompare=new HashSet<>();
        for(int i=0;i<exec.length;i++){
           execToCompare.add(exec[i]); 
        }
        Color colorAct=null;
//        int posChain=0;
//        Label labelPast=null;
        for (String elem:rectanglesChain.keySet()){
            Label label=labels.get(elem);
            
            if(execToCompare.contains(elem)){
                rectanglesChain.get(elem).setOpacity(1.0);
                colorAct=Color.web(config.getColorLeido());
                label.setTextFill(colorAct);
                label.setFont(new Font(config.getLetraCadena()));
            }
            else{
                rectanglesChain.get(elem).setOpacity(0.5);
                colorAct=Color.web(config.getColorPend());
                labels.get(elem).setTextFill(colorAct);
                label.setFont(new Font(config.getLetraCadena()));
            }
            colorAct=Color.web(config.getColorTerminal());
            rectanglesChain.get(elem).setFill(colorAct);
            //if(config.getLetraCadena()>15){
            double heigth=label.getFont().getSize()+10;
            double width=label.getFont().getSize()+10*label.getText().length();
            //Double diff=Math.abs(width-rectanglesChain.get(elem).getWidth());
            rectanglesChain.get(elem).setWidth(width);
            rectanglesChain.get(elem).setHeight(heigth);
            
//            if(posChain!=0){
//                
//                if(font<config.getLetraCadena()){
//                Double x=label.getLayoutX();
//                label.setLayoutX(label.getLayoutX()+diff/*config.getLetraCadena()*/);
//                x=label.getLayoutX()+diff;
//                rectanglesChain.get(elem).setLayoutX(rectanglesChain.get(elem).getLayoutX()+diff);
//                }
//                else if(font>config.getLetraCadena()){
//                    Double x=label.getLayoutX();
//                    label.setLayoutX(label.getLayoutX()-config.getLetraCadena()-diff);
//                    x=label.getLayoutX()-config.getLetraCadena()-diff;
//
//                rectanglesChain.get(elem).setLayoutX(rectanglesChain.get(elem).getLayoutX()-config.getLetraCadena()-diff);  
//                }
//            }
            
            //}
       // }
            //posChain++;
        }
        font=config.getLetraCadena();
        Label label=labels.get("EOF");
        if(step==cadenaPorPaso.size()-1){
            rectanglesChain.get("EOF").setOpacity(1.0);
            colorAct=Color.web(config.getColorLeido());
            label.setTextFill(colorAct);
            label.setFont(new Font(config.getLetraCadena()));
            
        }
        else{
            rectanglesChain.get("EOF").setOpacity(0.5);
            colorAct=Color.web(config.getColorPend());
            label.setTextFill(colorAct);
            label.setFont(new Font(config.getLetraCadena()));
            
        }
        font=config.getLetraCadena();
    }

    public HashMap<String, Rectangle> getRectanglesChain() {
        return rectanglesChain;
    }

    public void setRectanglesChain(HashMap<String, Rectangle> rectanglesChain) {
        this.rectanglesChain = rectanglesChain;
    }
    
//    public HashMap<String, String> getRectanglesText() {
//        return rectanglesText;
//    }
//
//    public void setRectanglesText(HashMap<String, String> rectanglesText) {
//        this.rectanglesText = rectanglesText;
//    }

    public HashMap<String, Label> getLabels() {
        return labels;
    }

    public void setLabels(HashMap<String, Label> labels) {
        this.labels = labels;
    }
    
}
