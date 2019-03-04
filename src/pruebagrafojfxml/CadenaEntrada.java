/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebagrafojfxml;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author adgao
 */
public class CadenaEntrada {
private List<String> cadenaPorPaso;

private double posX;
private Pane panelPadre;
private HashMap<String,Rectangle> rectanglesChain;
private HashMap<String,String> rectanglesText;
    /**
     * Builder
     * @param cadenaPorPaso
     * list of the state of the chain for each step
     * @param panelPadre 
     * Panel where draw the chain
     */
    public CadenaEntrada(List<String> cadenaPorPaso,Pane panelPadre) {
        this.cadenaPorPaso = cadenaPorPaso;
       
        posX=10;
        this.panelPadre=panelPadre;
        rectanglesChain=new HashMap<>();
        rectanglesText=new HashMap<>();
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
            
            double heigth=l.getFont().getSize()+10;
            double width=l.getFont().getSize()+10*l.getText().length();
            Rectangle r=new Rectangle(width,heigth);
            r.setOpacity(0.5);
            l.setLayoutX(posX+width/4);
            l.setLayoutY(posY);
            r.setLayoutX(posX);
            r.setLayoutY(posY);
            r.setId(elem);
            rectanglesChain.put(elem, r);
            rectanglesText.put(r.getId(), elem);
            panelPadre.getChildren().addAll(r,l);
            posX+=width+10;
        }
    }/**
     * update the appearance of the chain 
     * @param step 
     * step to update
     */
    public void actualizarCadena(int step){
        String[] pendExec=cadenaPorPaso.get(step).split("pend");
        String[] exec=null;
        //if(pendExec.length==2){
            exec=pendExec[0].split(" ");
        
            
            HashSet<String> execToCompare=new HashSet<>();
            for(int i=0;i<exec.length;i++){
               execToCompare.add(exec[i]); 
            }
            for (String elem:rectanglesChain.keySet()){
                if(execToCompare.contains(elem))
                    rectanglesChain.get(elem).setOpacity(1.0);

                else
                    rectanglesChain.get(elem).setOpacity(0.5);
           // }
        }
        if(step==cadenaPorPaso.size()-1){
            rectanglesChain.get("EOF").setOpacity(1.0);
        }
        else
            rectanglesChain.get("EOF").setOpacity(0.5);
    }

    public HashMap<String, Rectangle> getRectanglesChain() {
        return rectanglesChain;
    }

    public void setRectanglesChain(HashMap<String, Rectangle> rectanglesChain) {
        this.rectanglesChain = rectanglesChain;
    }

    public HashMap<String, String> getRectanglesText() {
        return rectanglesText;
    }

    public void setRectanglesText(HashMap<String, String> rectanglesText) {
        this.rectanglesText = rectanglesText;
    }
    
}
