/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebagrafojfxml;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 *
 * @author adgao
 */
public class Gramatica {
    
private FicheroXML ejemplo;
private ArrayList<Regla> reglas;
private int posYAnterior;
private HashMap<Rectangle,Regla>relRectRegla;
private HashMap<String,Rectangle> ruleRectGramm;
private HashMap<String,ArrayList<String>> ruleActions;
private HashMap<String,ArrayList<Label>> reglaLabel;
private HashMap<String,Boolean>formaReglas;//true=forma larga con las acciones.false forma corta
private HashMap<String,Regla> idRegla;//para poder obtener las reglas con simbolos
private Pane panelPadre;
private Configuracion config;
    /**
     * Builder
     * @param ejemplo
     * XML with the data of the translator
     * @param panelPadre 
     * Panel where draw the grammar
     * @param config
     * configuration
     */
    public Gramatica(FicheroXML ejemplo,Pane panelPadre,Configuracion config) {
        this.ejemplo = ejemplo;
        this.reglas = ejemplo.getListaGramatica();
        posYAnterior=10;
        this.panelPadre=panelPadre;
        relRectRegla=new HashMap<>();
        reglaLabel=new HashMap<>();
        formaReglas=new HashMap<>();
        idRegla=new HashMap<>();
        ruleRectGramm=new HashMap<>();
        this.config=config;
        ruleActions=new HashMap<>();
    }
    /**
     * from a rule produce the String correspondent without the actions
     * @param regla
     * rule origing
     * @return 
     * String from the rule
     */
    public ArrayList<Label> formarReglaCorta(Regla regla){
        ArrayList<Label> result=new ArrayList<>();
        regla=idRegla.get(regla.getId());
        for (Simbolo s: regla.getRegla()){
            
            Label l=new Label(s.getValor());
            l.setFont(new Font(config.getLetraTraductor()));
            if(!s.isTerminal()){
                    
                Color colorText=Color.web(config.getLetraNoTerminal());
                l.setTextFill(colorText);

            }
            else{

                Color colorText=Color.web(config.getLetraTerminal());
                l.setTextFill(colorText);
            }
            result.add(l);
        }
        return result;
    }
    /**
     * from a rule produce the String correspondent with the actions 
     * @param regla
     * rule origing
     * @return 
     * String from the rule
     */
    public ArrayList<Label> formarReglaLarga(Regla regla){
        ArrayList<Label> result=new ArrayList<>();
        ArrayList<String> actions=new ArrayList<>();
        regla=idRegla.get(regla.getId());
        int i=1;
        int posAccion=0;
        //regla.getAcciones().get(0).;
        for (Simbolo s: regla.getRegla()){
            if((posAccion<regla.getAcciones().size())&&(i==regla.getAcciones().get(posAccion).getPos())){
                Label ls=new Label(s.getValor());
                Label l=new Label(regla.getAcciones().get(posAccion).getValor());
                l.setFont(new Font(config.getTipoLetra(),config.getSizeAcciones()));
                Color colorAct=Color.web(config.getColorAccSem());
                l.setTextFill(colorAct);
                //result+=+s.getValor()+"  ";
                ls.setFont(new Font(config.getLetraTraductor()));
                if(!s.isTerminal()){

                    Color colorText=Color.web(config.getLetraNoTerminal());
                    ls.setTextFill(colorText);

                }
                else{

                    Color colorText=Color.web(config.getLetraTerminal());
                    ls.setTextFill(colorText);
                }
                result.add(ls);
                result.add(l);
                actions.add(regla.getAcciones().get(posAccion).getValor());
                
                posAccion++;
                
            }
            else{
                Label l=new Label(s.getValor());
                result.add(l);
                l.setFont(new Font(config.getLetraTraductor()));
                if(!s.isTerminal()){

                    Color colorText=Color.web(config.getLetraNoTerminal());
                    l.setTextFill(colorText);

                }
                else{

                    Color colorText=Color.web(config.getLetraTerminal());
                    l.setTextFill(colorText);
                }
            }
            ruleActions.put(regla.getId(),actions);
            i++;
        }
        
        
        return result;
    }
    /**
     * evaluate if the symbol is terminal
     * @param regla
     * rule of the symbol
     * @param simbolo
     * symbol to evaluate
     * @return 
     * true if is terminal false if not
     */
    public boolean isTerminal(Regla regla,String simbolo){
        for (Simbolo s:regla.getRegla()){
            if(s.getValor().equals(simbolo))
                return s.isTerminal();
        }
        return false;
    }
    /**
     * build and draw the grammar
     */
    public void construir(/*Pane panelPadre*/){
        for(Regla r: reglas){
            idRegla.put(r.getId(), r);
            double posXAnterior=10;
            ArrayList<Label> labels=formarReglaLarga(r);
            for(Label l:labels){
                l.setLayoutX(posXAnterior);
                l.setLayoutY(posYAnterior);
               
                panelPadre.getChildren().add(l);
                posXAnterior=posXAnterior+l.getFont().getSize()/2*l.getText().length()+20;
            }
            
            posYAnterior+=40;
            
            reglaLabel.put(r.getId(), labels);
            formaReglas.put(r.getId(), true);
            
        }
    }
    /**
     * change the form of the rule to the other form
     * @param regla 
     * rule to change
     */
    public void cambiarFormaRegla(Regla regla){
        ArrayList<Label> l=reglaLabel.get(regla.getId());
        
        double posXAnterior=10;
        Double posY=l.get(0).getLayoutY();
        panelPadre.getChildren().removeAll(l);
        double width=0;
        if(formaReglas.get(regla.getId())){   
            l=formarReglaCorta(regla);
            for(Label ls:l){
                ls.setLayoutX(posXAnterior);
                ls.setLayoutY(posY);
                panelPadre.getChildren().add(ls);
                posXAnterior=posXAnterior+ls.getFont().getSize()/2*ls.getText().length()+20;
                width+=ls.getText().length()*ls.getFont().getSize()/2+20;
            }
            if(!ruleRectGramm.isEmpty())
                ruleRectGramm.get(regla.getId()).setWidth(width);
            formaReglas.put(regla.getId(), false);
            reglaLabel.put(regla.getId(), l);
        }
        else{
            l=formarReglaLarga(regla);
            for(Label ls:l){
                ls.setLayoutX(posXAnterior);
                ls.setLayoutY(posY);
                panelPadre.getChildren().add(ls);
                posXAnterior=posXAnterior+ls.getFont().getSize()/2*ls.getText().length()+20;
                width+=ls.getText().length()*ls.getFont().getSize()/2+20;
            }
            if(!ruleRectGramm.isEmpty())
                ruleRectGramm.get(regla.getId()).setWidth(width);
            formaReglas.put(regla.getId(), true); 
            reglaLabel.put(regla.getId(), l);
        }
    }
    /**
     * Draw the rectangle that englove the rule
     * @param regla 
     * Rule where correspond the rectangle
     */
    public void drawRectangle(Regla regla){
        ArrayList<Label> l=reglaLabel.get(regla.getId());
        Double posY=l.get(0).getLayoutY();
        Double posX=l.get(0).getLayoutX()-5;
        Double width=0.0;
        for(Label label:l){
            width+=label.getText().length()*label.getFont().getSize()/2+20;
        }
        Rectangle rect=new Rectangle(posX, posY, width,l.get(0).getFont().getSize()+5);
        rect.setOpacity(0.3);
        rect.setFill(Color.YELLOW);
        //if (ruleRectGramm.get(regla.getId())==null)
        ruleRectGramm.put(regla.getId(), rect);
        panelPadre.getChildren().add(rect);
    }
    public Rectangle erasedRectangle(Regla regla){
        Rectangle rect=ruleRectGramm.get(regla.getId());
        ruleRectGramm.remove(regla.getId());

        panelPadre.getChildren().remove(rect);
        return rect;
    }
    /**
     * update the color and the size of the letter of the grammar
     */
    public void updateGrammar(){
        for(String ids:reglaLabel.keySet()){
            Regla regla=idRegla.get(ids);
            cambiarFormaRegla(regla);
            cambiarFormaRegla(regla);
            for(Label l:reglaLabel.get(ids)){
                Color color=null;
                if(isActions(ruleActions.get(ids),l.getText())){
                    color=Color.web(config.getColorAccSem());
                    l.setFont(new Font(config.getTipoLetra(),config.getSizeAcciones()));
                }
                else if(isTerminal(idRegla.get(ids),l.getText())){
                    color=Color.web(config.getLetraTerminal());
                    l.setFont(new Font(config.getLetraTraductor()));
                }
                else{
                    color=Color.web(config.getLetraNoTerminal());
                    l.setFont(new Font(config.getLetraTraductor()));
                
                }
                l.setTextFill(color);
                
            }
        }
    }
    /**
     * evaluate if is a action 
     * @param actions
     * action existing
     * @param actionSearched
     * action to evaluate
     * @return 
     * true if is an action false if not
     */
    public boolean isActions(ArrayList<String> actions,String actionSearched){
        if(actions!=null){
            for(String action:actions){
                if((action.equals(actionSearched))){
                    return true;
                }
            }
        }
        return false;
        
    }
    public FicheroXML getEjemplo() {
        return ejemplo;
    }

    public void setEjemplo(FicheroXML ejemplo) {
        this.ejemplo = ejemplo;
    }

    public ArrayList<Regla> getReglas() {
        return reglas;
    }

    public void setReglas(ArrayList<Regla> reglas) {
        this.reglas = reglas;
    }

    public int getPosYAnterior() {
        return posYAnterior;
    }

    public void setPosYAnterior(int posYAnterior) {
        this.posYAnterior = posYAnterior;
    }

    public HashMap<Rectangle, Regla> getRelRectRegla() {
        return relRectRegla;
    }

    public void setRelRectRegla(HashMap<Rectangle, Regla> relRectRegla) {
        this.relRectRegla = relRectRegla;
    }

    public HashMap<String, ArrayList<Label>> getReglaLabel() {
        return reglaLabel;
    }
    
}
