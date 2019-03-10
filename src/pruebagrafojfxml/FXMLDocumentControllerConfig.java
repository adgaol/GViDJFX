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
@FXML
private Label labelreadChain; 
@FXML
private Label labelpendChain;
@FXML
private Label labelNoTerminal1; 
@FXML
private Label labelNoTerminal2;
@FXML
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
private Grafo arbolEjemplo;
private int posYCadena=300;
private int posYHijos=100;
private int xInicial=100;
private int xFinal=200;
private List<Object> terminales=new ArrayList<>();
private List<Object> nTerminales=new ArrayList<>();
private int xPadre=150;
private int yPadre=50;
private int posRegla=200;
private int incCadena=70;

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
@FXML
/**
 * change the color of the elements using the ColorPickers
 */
    public void handleAcept(ActionEvent event) throws IOException {
        
        Configuracion conf = new Configuracion();
        conf.guardarConfiguracion(".//config//configActual.xml",treeSize,grammarSize,chainSize,colorBackgroundTerminals,colorBackgroundNoTerminals,colorTextTerminals,colorTextNoTerminals,colorReadChain,colorPendChain,colorAction,actionType,actionSize,50);
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
            
        }
        else if(e.equals(fontColorTerminals)){
            javafx.scene.paint.Color coo=fontColorTerminals.getValue();   
            System.out.println(coo);
            labelTerminal.setTextFill(coo);
            grammarTerminal.setTextFill(coo);
            colorTextTerminals=coo.toString();
        }
        else if(e.equals(fontColorNoTerminals)){
            javafx.scene.paint.Color coo=fontColorNoTerminals.getValue();   
            System.out.println(coo);
            labelNoTerminal1.setTextFill(coo);
            labelNoTerminal2.setTextFill(coo);
            grammarNoTerminal1.setTextFill(coo);
            grammarNoTerminal2.setTextFill(coo);
            colorTextNoTerminals=coo.toString();
        }
        
        else if(e.equals(backgroundColorNoTerminals)){
            javafx.scene.paint.Color coo=backgroundColorNoTerminals.getValue();   
            System.out.println(coo);
            noTerminal1.setFill(coo);
            noTerminal2.setFill(coo);
            colorBackgroundNoTerminals=coo.toString();
              
        }
        else if(e.equals(pendPart)){
            javafx.scene.paint.Color coo=pendPart.getValue();   
            System.out.println(coo);
            labelpendChain.setTextFill(coo);
            colorPendChain=coo.toString();
        }
        else if(e.equals(readPart)){
            javafx.scene.paint.Color coo=readPart.getValue();   
            System.out.println(coo);
            labelreadChain.setTextFill(coo);
            colorReadChain=coo.toString();
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
        labelTerminal.setTextFill(color);
        labelTerminal.setFont(new Font(lectConf.getLetraArbol()));
        grammarTerminal.setTextFill(color);
        grammarTerminal.setFont(new Font(lectConf.getLetraTraductor()));
        color=Color.web(lectConf.getLetraNoTerminal());
        
        labelNoTerminal1.setTextFill(color);
        labelNoTerminal1.setFont(new Font(lectConf.getLetraArbol()));
        labelNoTerminal2.setTextFill(color);
        labelNoTerminal2.setFont(new Font(lectConf.getLetraArbol()));
        grammarNoTerminal1.setTextFill(color);
        grammarNoTerminal1.setFont(new Font(lectConf.getLetraTraductor()));
        grammarNoTerminal2.setTextFill(color);
        grammarNoTerminal2.setFont(new Font(lectConf.getLetraTraductor()));
        color=Color.web(lectConf.getColorAccSem());
        action.setTextFill(color);
        action.setFont(new Font(lectConf.getTipoLetra(),lectConf.getLetraTraductor()));
        color=Color.web(lectConf.getColorPend());
        labelpendChain.setTextFill(color);
        labelpendChain.setFont(new Font(lectConf.getLetraCadena()));
        color=Color.web(lectConf.getColorLeido());
        labelreadChain.setTextFill(color);
        labelreadChain.setFont(new Font(lectConf.getLetraCadena()));
        if(lectConf.getLetraArbol()>15){
            noTerminal1.setWidth(50+labelNoTerminal1.getText().length()-3+lectConf.getLetraArbol()); 
                       // noTerminal1.setHeight(50+/*labelNoTerminal1.getText().length()+*/val);
            noTerminal2.setWidth(50+labelNoTerminal1.getText().length()-3+lectConf.getLetraArbol());
            terminal.setWidth(50+labelNoTerminal1.getText().length()-3+lectConf.getLetraArbol());
        }
       
        double heigth=labelpendChain.getFont().getSize()+10;
        double width=labelpendChain.getFont().getSize()+10*labelpendChain.getText().length();
        readChainR.setWidth(width); 
        readChainR.setHeight(heigth);       
        pendChainR.setWidth(width);
        pendChainR.setHeight(heigth);
//        FicheroXML ejemplo = new FicheroXML();
//        //ejemplo.cargarXml("C:\\Users\\adgao\\Documents\\universidad\\TFG\\TFG-Anterior\\TFG-Anterior\\TFGv0\\traductores\\descend2.xml");      
//        mT=inicializarModificaciones();
//        arbolEjemplo=new Grafo(lectConf, new FicheroXML(), arbolEjemplo, new HashMap<>(), new ArrayList<>(), 40);
//        
//      List<Object> hijos= new ArrayList();
//        Regla regla= new Regla();
//        regla.setTam(4);        
//        regla.setValor("");
//        List<String> cadenaS = new ArrayList<>();
//        regla.setValorAtributos(cadenaS);
//        arbolEjemplo.insertarRectangulo(regla, 50, posYHijos,ejemplo);
//        Object e1=arbolEjemplo.insertarElemento(new Simbolo("num",true), xInicial, posYHijos);
//        hijos.add(e1);
//        terminales.add(e1);
//        Object e2=arbolEjemplo.insertarElemento(new Simbolo("A",false), xFinal, posYHijos);
//        hijos.add(e2);
//        nTerminales.add(e2);
//        arbolEjemplo.insertarPadre(new Simbolo("Exp",false), xPadre,yPadre, hijos);
//        nTerminales.add(e2);
//        
//        List<Simbolo> trad = new ArrayList<>();
//        trad.add(new Simbolo("Exp::=",false));
//        trad.add(new Simbolo("num",true));
//        trad.add(new Simbolo("A",false));        
//        List<Object> l=arbolEjemplo.insertarRegla(posRegla, trad);
//        Object[] regInsertadas = new Object[l.size()];
//        regInsertadas=  l.toArray();
//        mT.setTraductor(regInsertadas);
//        
//        Object leido=arbolEjemplo.insertarElemento(new Simbolo("4",true), 10, posYCadena, 40,40,"LEIDO");
//        mT.setLeido(leido);
//        Object pend=arbolEjemplo.insertarElemento(new Simbolo("+",true), incCadena, posYCadena,40,40, "PENDIENTE");
//        mT.setPendiente(pend);
//        
//        Object accSem=arbolEjemplo.insertarElemento(new Simbolo("print(valor)",true), incCadena+100, posRegla,40,40, "ACCIONES");
//        mT.setAccSem(accSem);
//        //Component comp =arbolEjemplo.getGraphComponent();
//        place(treeNode);
//        
//        //change the configuration using the ComboBox
        ObservableList<Integer> optionsSizeLetraArbol = FXCollections.observableArrayList( 8,10,13,15,18,20);       
        sizeFuenteArbol.setItems(optionsSizeLetraArbol);
        sizeFuenteArbol.setValue(lectConf.getLetraArbol());
        sizeFuenteArbol.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override 
            public void changed(ObservableValue ov, Integer t, Integer t1) {
                Integer val= (Integer)sizeFuenteArbol.getValue();
                labelNoTerminal1.setFont(new Font(val));
                labelNoTerminal2.setFont(new Font(val));
                labelTerminal.setFont(new Font(val));
                if(val>15){
                    noTerminal1.setWidth(50+labelNoTerminal1.getText().length()-3+val); 
                   // noTerminal1.setHeight(50+/*labelNoTerminal1.getText().length()+*/val);
                    noTerminal2.setWidth(50+labelNoTerminal1.getText().length()-3+val);
                    terminal.setWidth(50+labelNoTerminal1.getText().length()-3+val); 
                }
                else{
                    noTerminal1.setWidth(50); 
                   // noTerminal1.setHeight(50+/*labelNoTerminal1.getText().length()+*/val);
                    noTerminal2.setWidth(50);
                    terminal.setWidth(50); 
                    
                    
                }
                //noTerminal2.setHeight(50+/*labelNoTerminal1.getText().length()+*/val);
                //terminal.setHeight(50+/*labelNoTerminal1.getText().length()+*/val);        
                treeSize=val;
                //mT.setSizeLetraArbol(val);                
                //modificarArbol();
            }    
        });
        ObservableList<Integer> optionsSizeLetraCadena = FXCollections.observableArrayList( 8,10,13,15,18,20);
        sizeFuenteCadena.setItems(optionsSizeLetraCadena);
        sizeFuenteCadena.setValue(lectConf.getLetraCadena());
        sizeFuenteCadena.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override 
            public void changed(ObservableValue ov, Integer t, Integer t1) {
                Integer val= (Integer)sizeFuenteCadena.getValue();
                labelpendChain.setFont(new Font(val));
                labelreadChain.setFont(new Font(val));
                double heigth=labelpendChain.getFont().getSize()+10;
                double width=labelpendChain.getFont().getSize()+10*labelpendChain.getText().length();
                readChainR.setWidth(width); 
                readChainR.setHeight(heigth);
                pendChainR.setWidth(width);
                pendChainR.setHeight(heigth);
                chainSize=val;
                //mT.setSizeCadena(val);                
                //modificarArbol();
            }    
        });
        ObservableList<Integer> optionsSizeLetraTraductor = FXCollections.observableArrayList( 8,10,13,15,18,20);
        sizeFuenteTraduc.setItems(optionsSizeLetraTraductor);
        sizeFuenteTraduc.setValue(lectConf.getLetraTraductor());
        sizeFuenteTraduc.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override 
            public void changed(ObservableValue ov, Integer t, Integer t1) {
                Integer val= (Integer)sizeFuenteTraduc.getValue();
                grammarNoTerminal1.setFont(new Font(val));
                grammarNoTerminal2.setFont(new Font(val));
                grammarTerminal.setFont(new Font(val));
                grammarSize=val;
                //mT.setSizeLetraTraductor(val);                
                //modificarArbol();
            }    
        });
        actionSize=lectConf.getSizeAcciones();
        ObservableList<Integer> optionsSizeLetraSemanticAct = FXCollections.observableArrayList( 8,10,13,15,18,20);       
        sizeFountSemanticAct.setItems(optionsSizeLetraSemanticAct);
        sizeFountSemanticAct.setValue(lectConf.getSizeAcciones());
        sizeFountSemanticAct.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override 
            public void changed(ObservableValue ov, Integer t, Integer t1) {
                Integer val= (Integer)sizeFountSemanticAct.getValue();
                action.setFont(new Font(val));
                actionSize=val;
                
                //mT.setSizeAcciones(val);                
                //modificarArbol();
            }    
        });
        ObservableList<String> optionsFountType = FXCollections.observableArrayList( "Times new Roman","Arial","Calibri","Courier","Broadway","Informal Roman","Verdana");       
        fountTypeSemanticAct.setItems(optionsFountType);
        fountTypeSemanticAct.setValue(lectConf.getTipoLetra());
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
    }
    /**
     * place the tree in the swing node
     * @param swingNode
     * where the tree will be placed
     */
//     public void place(final SwingNode swingNode) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                
//                 swingNode.setContent(arbolEjemplo.getGraphComponent());
//            }
//        });
//    }
//     /**
//      * place the grammar in the swingNode
//      * @param swingNode 
//      * where the grammar will be placed
//      */
//    public void placeGrammar(final SwingNode swingNode) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//
//                 swingNode.setContent(gramatica.getGraphComponent());
//            }
//        });
//    }
//     /**
//      * place the string in the swingNode
//      * @param swingNode 
//      * where the string will be placed
//      */
//     public void placeString(final SwingNode swingNode) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                
//                 swingNode.setContent(entrada.getGraphComponent());
//            }
//        });
//    }
//    /**
//         * initialize the tree
//         */
//
//    public void inicializarArbol(Configuracion lectConf){
//        //Árbol
//            this.treeElements= new ArrayList<>();    
//            //List<Object> aux = new ArrayList<>();     
//             this.tree= new Grafo(lectConf,ejemplo,gramatica,elemValor,listaReglas,sepRegla);   
//            tree.activarListener(gramatica,mapaGramatica,listaReglas,sepRegla,lectConf.getLetraArbol());
//
//
//        }
    
 /**
      * initialize the value of the modifications
      * @return temporal modifications
      */
//    private  ModificacionesTemp inicializarModificaciones() {
//        ModificacionesTemp m= new ModificacionesTemp();
//        m.setSizeLetraArbol(lectConf.getLetraArbol());
//        m.setSizeLetraTraductor(lectConf.getLetraTraductor());
//        m.setSizeCadena(lectConf.getLetraCadena());
//        m.setColorTerminales(new Color(Integer.parseInt(lectConf.getColorTerminal(),16)));
//        m.setColornTerminales(new Color(Integer.parseInt(lectConf.getColorNoTerminal(),16)));
//        m.setColorLetraTerminales(new Color(Integer.parseInt(lectConf.getLetraTerminal(),16)));
//        m.setColorLetranTerminales(new Color(Integer.parseInt(lectConf.getLetraNoTerminal(),16)));
//        m.setColorCadenaLeido(new Color(Integer.parseInt(lectConf.getColorLeido(),16)));
//        m.setColorCadenaPendiente(new Color(Integer.parseInt(lectConf.getColorPend(),16)));
//        m.setColorAccSem(Color.black);
//        m.setSizeAcciones(13);
//        m.setTipoLetra("Times New Roman");
//        return m;
//    }
    /**
    * modify the example tree of the configuration
    */ 
//     public void modificarArbol() {
//       
//        estilos();
//        
//        arbolEjemplo.insertarElemento(new Simbolo("num",true), xInicial, posYHijos, "MODTER");
//        arbolEjemplo.insertarElemento(new Simbolo("A",false), xFinal, posYHijos, "MODNOTER");
//        arbolEjemplo.insertarElemento(new Simbolo("Exp",false), xPadre, yPadre, "MODNOTER");
//        
//        List<Simbolo> trad = new ArrayList<>();
//        trad.add(new Simbolo("Exp::=",false));
//        trad.add(new Simbolo("num",true));
//        trad.add(new Simbolo("A",false));
//        arbolEjemplo.eliminar(mT.getTraductor());
//        List<Object> l=arbolEjemplo.insertarRegla(posRegla, trad,"MODTRADTER","MODTRADNOTER");
//        Object[] regInsertadas = new Object[l.size()];
//        regInsertadas=  l.toArray();
//        mT.setTraductor(regInsertadas);
//        arbolEjemplo.eliminar(mT.getLeido());
//        arbolEjemplo.eliminar(mT.getPendiente());
//        
//        Object leido=arbolEjemplo.insertarElemento(new Simbolo("4",true), 10, posYCadena, 40,40,"MODLEIDO");
//        mT.setLeido(leido);
//        Object pend=arbolEjemplo.insertarElemento(new Simbolo("+",true), incCadena, posYCadena,40,40, "MODPENDIENTE");
//        mT.setPendiente(pend);
//        
//        arbolEjemplo.eliminar(mT.getAccSem());
//        Object accSem=arbolEjemplo.insertarElemento(new Simbolo("print(valor)",true), incCadena+100, posRegla,40,40, "MODACCIONES");
//        mT.setAccSem(accSem);
//       
//    }
     /**
 *set all styles
 */
//    private  void estilos() {
//        mxStylesheet stylesheet = arbolEjemplo.getGraph().getStylesheet();
//        Hashtable<String, Object> style = new Hashtable<String, Object>();
//        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
//        style.put(mxConstants.STYLE_OPACITY, 100);
//        style.put(mxConstants.STYLE_FONTCOLOR,Integer.toHexString(mT.getColorLetraTerminales().getRGB()));
//        style.put(mxConstants.STYLE_FONTSIZE, mT.getSizeLetraArbol());        
//        style.put(mxConstants.STYLE_FILLCOLOR, Integer.toHexString(mT.getColorTerminales().getRGB()));
//        style.put(mxConstants.STYLE_ROUNDED,"1");
//        style.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
//        stylesheet.putCellStyle("MODTER", style);   
//        
//         
//        Hashtable<String, Object> style2 = new Hashtable<String, Object>();
//        style2.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
//        style2.put(mxConstants.STYLE_OPACITY, 100);
//        style2.put(mxConstants.STYLE_FONTCOLOR,Integer.toHexString(mT.getColorLetranTerminales().getRGB()));
//        style2.put(mxConstants.STYLE_FONTSIZE, mT.getSizeLetraArbol());        
//        style2.put(mxConstants.STYLE_FILLCOLOR, Integer.toHexString(mT.getColornTerminales().getRGB()));
//        style2.put(mxConstants.STYLE_ROUNDED,"1");
//        style2.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
//        stylesheet.putCellStyle("MODNOTER", style2);   
//        
//        Hashtable<String, Object> style3 = new Hashtable<String, Object>();
//        style3.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
//        style3.put(mxConstants.STYLE_OPACITY, 0);
//        style3.put(mxConstants.STYLE_FONTCOLOR,  Integer.toHexString(mT.getColorLetraTerminales().getRGB()));
//        style3.put(mxConstants.STYLE_FONTSIZE, mT.getSizeLetraTraductor());
//        //style3.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcNoTerminales().getRGB()));
//        style3.put(mxConstants.STYLE_ROUNDED,"1");
//        style3.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
//        
//        //style3.put(mxConstants.STYLE_ENDSIZE,10);
//        //style3.put(mxConstants.STYLE_SPACING_RIGHT,10);
//        stylesheet.putCellStyle("MODTRADTER", style3);
//        
//        
//        
//        Hashtable<String, Object> style4 = new Hashtable<String, Object>();
//        style4.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
//        style4.put(mxConstants.STYLE_OPACITY, 0);
//        style4.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(mT.getColorLetranTerminales().getRGB()));
//        style4.put(mxConstants.STYLE_FONTSIZE, mT.getSizeLetraTraductor());
//        style4.put(mxConstants.STYLE_ROUNDED,"1");
//        style4.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
//        //style4.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcTerminales().getRGB()));
//        
//        //style4.put(mxConstants.STYLE_SPACING_LEFT,-20);
//        style4.put(mxConstants.STYLE_SPACING_RIGHT,0);
//         
//        stylesheet.putCellStyle("MODTRADNOTER", style4);
//        
//        Hashtable<String, Object> style11 = new Hashtable<String, Object>();
//        style11.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
//        style11.put(mxConstants.STYLE_OPACITY, 80);     
//        style11.put(mxConstants.STYLE_ROUNDED,"1");
//        style11.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
//        //style2.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
//        style11.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(mT.getColorTerminales().getRGB()));
//        style11.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(mT.getColorCadenaLeido().getRGB()));
//        style11.put(mxConstants.STYLE_FONTSIZE, mT.getSizeCadena());
//        //style.put(mxConstants.STYLE_ALIGN,"ALIGN_CENTER");
//       
//        stylesheet.putCellStyle("MODLEIDO", style11);
//        
//        Hashtable<String, Object> style12 = new Hashtable<String, Object>();
//        style12.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
//        style12.put(mxConstants.STYLE_OPACITY, 10);
//        style12.put(mxConstants.STYLE_TEXT_OPACITY, 20);
//        style12.put(mxConstants.STYLE_ROUNDED,"1");
//        style12.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
//        //style2.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
//        style12.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(mT.getColorTerminales().getRGB()));
//        style12.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(mT.getColorCadenaPendiente().getRGB()));
//        style12.put(mxConstants.STYLE_FONTSIZE, mT.getSizeCadena());
//        // style2.put(mxConstants.STYLE_ALIGN,"ALIGN_CENTER");
//        stylesheet.putCellStyle("MODPENDIENTE", style12);
//        
//        Hashtable<String, Object> style6 = new Hashtable<String, Object>();
//        style6.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
//        style6.put(mxConstants.STYLE_OPACITY, 0); //fondo transparente
//        style6.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(mT.getColorAccSem().getRGB())); 
//        style6.put(mxConstants.STYLE_FONTSIZE, mT.getSizeAcciones());
//        style6.put(mxConstants.STYLE_ROUNDED,"1");
//        style6.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
//        style6.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
//        //style4.put(mxConstants.STYLE_SPACING_LEFT,-20);
//        style6.put(mxConstants.STYLE_SPACING_RIGHT,0);
//        style6.put(mxConstants.STYLE_ALIGN,"ALIGN_LEFT");       
//        style6.put(mxConstants.STYLE_FONTFAMILY,mT.getTipoLetra()); 
//         
//        stylesheet.putCellStyle("MODACCIONES", style6);
//
//    }
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