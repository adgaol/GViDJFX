/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebagrafojfxml;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author adgao
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private TextField inputZoom;
    @FXML
    private TextField inputZoomGrammar;
    @FXML
    private TextField inputZoomChain;
    @FXML
    private ScrollPane grafo;
    @FXML
    private ScrollPane gramatica;
    @FXML
    private ScrollPane cadenaEntrada;
    @FXML
    private Button masZoom;
    @FXML
    private Button masZoomGrammar;
    @FXML
    private Button masZoomChain;
    @FXML
    private Button inicioButton;
    @FXML
    private Button finButton;
    @FXML
    private Button anteriorButton;
    @FXML
    private Button siguienteButton;
    @FXML
    private Slider sliderZoom;
    @FXML
    private Menu archivo;
    @FXML
    private Menu ejecucion;
    @FXML
    private Menu configuracion;
    @FXML
    private Menu ayuda;
    @FXML
    private MenuBar menuBar;
    private Double maxPosition;
    private Double maxZoom;
    private Double minZoom;
    //private Double zoomTamañoArbol;
    @FXML
    public void handleGrafoFocus(MouseEvent event) throws IOException {
        
            //int cont=contador;
            grafo.requestFocus();
    }
    @FXML
    public void adaptarZoomArbol() throws IOException {
        Double zoomTamanioArbol=0.0;
        if(maxPosition.equals(graph.getPosXMax())){
            
           Double aux1=grafo.getWidth();
            
           Double aux2=maxPosition+100+20;
           zoomTamanioArbol= grafo.getWidth()/(maxPosition+100+20)*100;
            
            
        }
        else{
            
            zoomTamanioArbol=grafo.getHeight()/(maxPosition+50+20)*100;
            
            
        }
        zoom(zoomTamanioArbol/100);
        sliderZoom.setValue(zoomTamanioArbol);
        inputZoom.setText(zoomTamanioArbol.toString());
        configuration.guardarConfiguracion(".//config//configActual.xml",
                                 configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
                                 configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),
                                 configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),
                                 configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),
                                 (int)sliderZoom.getValue(),zoomGrammar.intValue(),zoomChain.intValue());
        
            
        
    }
    @FXML
    public void adaptarZoomGramatica() throws IOException {
        Double zoomTamanio=0.0;
        double maxX=0.0;
        double maxY=0.0;
        for(ArrayList<Label> rule:grammar.getReglaLabel().values()){
            maxX=Math.max(maxX, rule.get(rule.size()-1).getLayoutX()+rule.get(rule.size()-1).getWidth());
            maxY=Math.max(maxX, rule.get(rule.size()-1).getLayoutY()+rule.get(rule.size()-1).getHeight());
        }
        if(maxY>maxX){
            
           Double aux1=gramatica.getWidth();
            
           
           zoomTamanio= gramatica.getHeight()/(maxY+10)*100;
            
            
        }
        else{
            
           zoomTamanio=gramatica.getWidth()/(maxX+10)*100;
            
            
        }
        zoomGrammar(zoomTamanio/100);
        zoomGrammar=zoomTamanio;
        inputZoomGrammar.setText(zoomTamanio.toString());
        configuration.guardarConfiguracion(".//config//configActual.xml",
                                 configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
                                 configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),
                                 configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),
                                 configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),
                                 (int)sliderZoom.getValue(),zoomGrammar.intValue(),zoomChain.intValue());
        
            
        
    }
    @FXML
    public void adaptarZoomCadena() throws IOException {
        Double zoomTamanio;
        Double maxPositionChain=entryChain.getRectanglesChain().get("EOF").getLayoutX()+entryChain.getRectanglesChain().get("EOF").getWidth();
        
            
        Double aux1=cadenaEntrada.getWidth();

        
        zoomTamanio= cadenaEntrada.getWidth()/(maxPositionChain+10)*100;
        zoomChain=zoomTamanio;   
            
        
       
        zoomChain(zoomTamanio/100);
        
        inputZoomChain.setText(zoomTamanio.toString());
        configuration.guardarConfiguracion(".//config//configActual.xml",
                                 configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
                                 configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),
                                 configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),
                                 configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),
                                 (int)sliderZoom.getValue(),zoomGrammar.intValue(),zoomChain.intValue());
        
            
        
    }
    @FXML
    public void verTutorial() throws IOException {
        
        
        
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocumentVerTutorial.fxml"));

            Scene scene = new Scene(root);
            Stage configurationScreen=new Stage();
            configurationScreen.setScene(scene);
            configurationScreen.initModality(Modality.WINDOW_MODAL);
            configurationScreen.setTitle("Ver Tutorial");
                // Specifies the owner Window (parent) for new window
            Stage p1=PruebaGrafoJFXML.getStage();
            configurationScreen.initOwner(PruebaGrafoJFXML.getStage());
               //configuration.onCloseRequestProperty().addListener();
                // Set position of second window, related to primary window.
                //newWindow.setX(primaryStage.getX() + 200);
                //newWindow.setY(primaryStage.getY() + 100);
            configurationScreen.show();
        
    }
    @FXML
    private void handleAcerdaDe(ActionEvent event) {
        Alert acercaDeDialog=new Alert(Alert.AlertType.INFORMATION);
        acercaDeDialog.setTitle("Mensaje");
        acercaDeDialog.setContentText("• Adrián García Oller – ad.ga.ol2@gmail.com (Trabajo fin  de grado, Grado de Ingeniería Informática, XXXXX de 2019)"+"\n\n"
                +"• José Manuel Loeches Ruiz - chemapkmn@gmail.com (Trabajo fin de grado, Grado de Ingeniería Informática, diciembre de 2018)"+"\n\n"
                +"• Jaime Urquiza - jaime.urquiza@urjc.es (Tutor del trabajo de fin de grado)"+"\n\n"
                +"Última revisión: xx/xx/2019");
        acercaDeDialog.initStyle(StageStyle.UTILITY);
        acercaDeDialog.showAndWait();
    }
    @FXML
    private void handleMasMenosZoom(ActionEvent event) {
        if(event.getSource().equals(masZoom)){
            if(sliderZoom.getValue()+10<=maxZoom){ 
                zoom((sliderZoom.getValue()+10)/100);
                sliderZoom.setValue((int)(sliderZoom.getValue()+10));
                inputZoom.setText(sliderZoom.getValue()+"");
            }
        }
        else{
          if((sliderZoom.getValue()-10)>=minZoom){
                zoom((sliderZoom.getValue()-10)/100);
                sliderZoom.setValue((int)(sliderZoom.getValue()-10));
                inputZoom.setText(sliderZoom.getValue()+"");
          }
        }
        configuration.guardarConfiguracion(".//config//configActual.xml",
                         configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
                         configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),
                         configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),
                         configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),
                         (int)sliderZoom.getValue(),zoomGrammar.intValue(),zoomChain.intValue());
           
    }
    @FXML
    private void handleMasMenosZoomGrammar(ActionEvent event) {
        if(event.getSource().equals(masZoomGrammar)){
            zoomGrammar+=10;
            zoomGrammar(zoomGrammar/100);
            
            inputZoomGrammar.setText(zoomGrammar+"");
        }
        else{
            if((zoomGrammar-10)>=0){
                
          
                zoomGrammar-=10;
                zoomGrammar(zoomGrammar/100);

                inputZoomGrammar.setText(zoomGrammar+"");
            }
        }
        configuration.guardarConfiguracion(".//config//configActual.xml",
                         configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
                         configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),
                         configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),
                         configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),
                         (int)sliderZoom.getValue(),zoomGrammar.intValue(),zoomChain.intValue());
           
    }
    @FXML
    private void handleMasMenosZoomChain(ActionEvent event) {
        if(event.getSource().equals(masZoomChain)){
            zoomChain+=10;
            zoomChain(zoomChain/100);
            
            inputZoomChain.setText(zoomChain+"");
        }
        else{
            if((zoomChain-10)>=0){
                zoomChain-=10;
                zoomChain(zoomChain/100);

                inputZoomChain.setText(zoomChain+"");
            }
        }
        configuration.guardarConfiguracion(".//config//configActual.xml",
                         configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
                         configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),
                         configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),
                         configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),
                         (int)sliderZoom.getValue(),zoomGrammar.intValue(),zoomChain.intValue());
           
    }
    @FXML
    private void handleNewConfiguration(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLNewConfiguration.fxml"));

        Scene scene = new Scene(root);
        Stage configurationScreen=new Stage();
        configurationScreen.setScene(scene);
        configurationScreen.initModality(Modality.WINDOW_MODAL);
        configurationScreen.setTitle("Configuración");
            // Specifies the owner Window (parent) for new window
        configurationScreen.initOwner(PruebaGrafoJFXML.getStage());          
        configurationScreen.show();
 

    
    
    }
    @FXML
    private void handleInputZoom(ActionEvent event) {
        try{
        Double value= Double.parseDouble(inputZoom.getText()); 
            if(value>=minZoom && value<=maxZoom){
                zoom((value)/100);
                sliderZoom.setValue(value);

                configuration.guardarConfiguracion(".//config//configActual.xml",
                                 configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
                                 configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),
                                 configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),
                                 configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),
                                 (int)sliderZoom.getValue(),zoomGrammar.intValue(),zoomChain.intValue());
            }
            else
                inputZoom.setText("no valido");
        }
        catch(Exception e){
            inputZoom.setText("no valido");
        }
           
    }
     @FXML
    private void handleInputZoomGrammar(ActionEvent event) {
        try{
            Double value= Double.parseDouble(inputZoomGrammar.getText()); 
            if(value>=0){
                zoomGrammar((value)/100);
                zoomGrammar=value;

                configuration.guardarConfiguracion(".//config//configActual.xml",
                     configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
                     configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),
                     configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),
                     configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),
                     (int)sliderZoom.getValue(),zoomGrammar.intValue(),zoomChain.intValue());
            }
            else
                inputZoomGrammar.setText("no valido");
        }
        catch(Exception e){
            inputZoomGrammar.setText("no valido");
        }
           
    }
     @FXML
    private void handleInputZoomChain(ActionEvent event) {
        try{
            Double value= Double.parseDouble(inputZoomChain.getText());
            if(value>=0){
                zoomChain((value)/100);
                zoomChain=value;

                configuration.guardarConfiguracion(".//config//configActual.xml",
                     configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
                     configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),
                     configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),
                     configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),
                     (int)sliderZoom.getValue(),zoomGrammar.intValue(),zoomChain.intValue());
            }
            else
                inputZoomChain.setText("no valido");
        }
        catch(Exception e){
            inputZoomChain.setText("no valido");
        }
           
    }
    @FXML
    public void handleOpenHelp(ActionEvent event) throws IOException {
        
            //int cont=contador;
        File file = new File("./manual/manualUsuario.pdf");
        Desktop.getDesktop().open(file);
//        HostServices hostServices = ;
//        hostServices.showDocument(file.getAbsolutePath());


    }

    @FXML
    public void handleOpenConfiguration(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocumentConfig.fxml"));

        Scene scene = new Scene(root);
        Stage configurationScreen=new Stage();
        configurationScreen.setScene(scene);
        configurationScreen.initModality(Modality.WINDOW_MODAL);
        configurationScreen.setTitle("Configuración");
            // Specifies the owner Window (parent) for new window
        configurationScreen.initOwner(PruebaGrafoJFXML.getStage());
           //configuration.onCloseRequestProperty().addListener();
            // Set position of second window, related to primary window.
            //newWindow.setX(primaryStage.getX() + 200);
            //newWindow.setY(primaryStage.getY() + 100);
        configurationScreen.show();
        configurationScreen.setOnHiding(new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent we) {
            //int cont=contador;
            configuration.cargarConfiguracion("./config/configActual.xml");
            graph.updateGraph();
            grammar.updateGrammar();
            entryChain.actualizarCadena(graph.getContador());
            
          }
      }); 


    }
    @FXML
    public void handleDefaultConfiguration(ActionEvent event) throws IOException {
        
            //int cont=contador;
            configuration.cargarConfiguracion("./config/configDefecto.xml");
            graph.updateGraph();
            grammar.updateGrammar();
            entryChain.actualizarCadena(graph.getContador());
            zoom(configuration.getZoomGraph()/100);
            inputZoom.setText(configuration.getZoomGraph()+"");
            zoomGrammar=configuration.getZoomGrammar()+0.0;
            zoomGrammar(zoomGrammar/100);
            inputZoomGrammar.setText(zoomGrammar.toString());
            zoomChain=configuration.getZoomChain()+0.0;
            zoomChain(zoomChain/100);
            inputZoomChain.setText(zoomChain.toString());
            sliderZoom.setValue(configuration.getZoomGraph());
            configuration.guardarConfiguracion(".//config//configActual.xml",
                         configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
                         configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),
                         configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),
                         configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),
                         configuration.getZoomGraph(),configuration.getZoomGrammar(),configuration.getZoomChain());
            configuration.guardarConfiguracionOcultarTutorial(".//config//configActual.xml", configuration.getOcultarTutorial());

    }
    @FXML
    private void handleCargarArchivo(ActionEvent event) {
        elegirArchivo("xml");
                 

    }
    @FXML
    private void handleCargarConfiguracion(ActionEvent event) {
        elegirConfiguracion("xml");
                 

    }
    @FXML
    private void handleAnteriorAction(ActionEvent event) {
        if(0<graph.getContador()){
        System.out.println("LEFT");
        graph.eliminar( graph.getContador()-1);
        if(graph.getContador()-1>0){
            Nodo n= graph.getNodos().get(graph.getContador()-1);
            Double hValue=(n.getPosX()+sliderZoom.getValue())/maxXPos;
            Double vValue=(n.getPosY()+sliderZoom.getValue()/50)/maxYPos;
            if(n.getPosX()*sliderZoom.getValue()/100<grafo.getPrefWidth()){
               grafo.setHvalue(0); 
            }
            else{
                if(grafo.getHvalue()!=hValue)
                grafo.setHvalue(hValue);
            }


            if(grafo.getVvalue()!=vValue)
                grafo.setVvalue(vValue);
            
        }
        
        }
        if(0==graph.getContador()){

            anteriorButton.setTextFill(Color.GRAY);
            inicioButton.setTextFill(Color.GRAY);
        }
        finButton.setTextFill(Color.BLACK);
        siguienteButton.setTextFill(Color.BLACK);
//        label.setText("Hello World!");
    }
    @FXML
    private void handleSiguienteAction(ActionEvent event) {
        if(ejemplo.getNumNodos()>graph.getContador()){
            System.out.println("RIGHT");
            graph.construir(graph.getContador()+1);
            Nodo n= graph.getNodos().get(graph.getContador()-1);
            Double hValue=(n.getPosX()+sliderZoom.getValue())/maxXPos;
            Double vValue=(Math.abs(n.getPosY()))/maxYPos;
            if(grafo.getHvalue()!=hValue)
                grafo.setHvalue(hValue);
            if(grafo.getVvalue()!=vValue)
                grafo.setVvalue(vValue);
        }
        if(ejemplo.getNumNodos()==graph.getContador()){

            siguienteButton.setTextFill(Color.GRAY);
            finButton.setTextFill(Color.GRAY);
        }
        inicioButton.setTextFill(Color.BLACK);
        anteriorButton.setTextFill(Color.BLACK);
        
//        label.setText("Hello World!");
    }
    @FXML
    private void handleIrFinAction(ActionEvent event) {
        System.out.println("Fin");
        graph.construir(ejemplo.getNumNodos());
        Nodo n= graph.getNodos().get(graph.getContador()-1);
        Double hValue=(n.getPosX()+100*sliderZoom.getValue()/100)/maxXPos;
        Double vValue=(n.getPosY()+50*sliderZoom.getValue()/100)/maxYPos;
        if(grafo.getHvalue()!=hValue)
            grafo.setHvalue(hValue);
        if(grafo.getVvalue()!=vValue)
            grafo.setVvalue(vValue);
        if(ejemplo.getNumNodos()==graph.getContador()){

             siguienteButton.setTextFill(Color.GRAY);
             finButton.setTextFill(Color.GRAY);
        }
        inicioButton.setTextFill(Color.BLACK);
        anteriorButton.setTextFill(Color.BLACK);
//        label.setText("Hello World!");
    }
    @FXML
    private void handleIrInicioAction(ActionEvent event) {
        System.out.println("Inicio");
        graph.eliminar(0);
        Nodo n= graph.getNodos().get(graph.getContador()-1);
        grafo.setHvalue(0);
        if(0==graph.getContador()){
            
            anteriorButton.setTextFill(Color.GRAY);
            inicioButton.setTextFill(Color.GRAY);
        }
        finButton.setTextFill(Color.BLACK);
        siguienteButton.setTextFill(Color.BLACK);
//        label.setText("Hello World!");
    }

//    @FXML
//    private void handleMouseAction(MouseEvent event) {
//        System.out.println("You clicked me!");
//        //panel.requestFocus();
////        label.setText("Hello World!");
//    }
    
    private void handleKeyAction(KeyEvent event) {
        
        if (event.getCode()==KeyCode.LEFT){
            if(0<graph.getContador()){
            System.out.println("LEFT");
            graph.eliminar( graph.getContador()-1);
            if(0==graph.getContador()){

                anteriorButton.setTextFill(Color.GRAY);
                inicioButton.setTextFill(Color.GRAY);
            }
            finButton.setTextFill(Color.BLACK);
            siguienteButton.setTextFill(Color.BLACK);
            double d=grafo.getHvalue();
            if(graph.getContador()-1>0 ){
                
                Nodo n= graph.getNodos().get(graph.getContador()-1);
                Double hValue=(n.getPosX()+sliderZoom.getValue())/maxXPos;
                Double vValue=(n.getPosY()+sliderZoom.getValue()/50)/maxYPos;
                if(n.getPosX()*sliderZoom.getValue()/100<grafo.getPrefWidth()){
                   grafo.setHvalue(0); 
                }
                else{
                    if(grafo.getHvalue()!=hValue)
                    grafo.setHvalue(hValue);
                }
                
                
                if(grafo.getVvalue()!=vValue)
                    grafo.setVvalue(vValue);
                
            }
           
            
            }
            
        }
        else if (event.getCode()==KeyCode.RIGHT){
            if(ejemplo.getNumNodos()>graph.getContador()){
                System.out.println("RIGHT");
                graph.construir(graph.getContador()+1);
                Nodo n= graph.getNodos().get(graph.getContador()-1);
                
                Double hValue=(n.getPosX()+sliderZoom.getValue())/maxXPos;
                Double vValue=(Math.abs(n.getPosY()))/maxYPos;
                
                if(grafo.getHvalue()!=hValue)
                    grafo.setHvalue(hValue);
                if(grafo.getVvalue()!=vValue)
                    grafo.setVvalue(vValue);
               
                //this.contador+=1;
             //graphPane.requestFocus();
                System.out.println("");
            }
            if(ejemplo.getNumNodos()==graph.getContador()){
            
                siguienteButton.setTextFill(Color.GRAY);
                finButton.setTextFill(Color.GRAY);
            }
            inicioButton.setTextFill(Color.BLACK);
            anteriorButton.setTextFill(Color.BLACK);
        }
        else if (event.getCode()==KeyCode.HOME){
            System.out.println("Inicio");
            graph.eliminar(0);
            Nodo n= graph.getNodos().get(graph.getContador()-1);
            grafo.setHvalue(0);
            if(0==graph.getContador()){
            
                anteriorButton.setTextFill(Color.GRAY);
                inicioButton.setTextFill(Color.GRAY);
            }
            finButton.setTextFill(Color.BLACK);
            siguienteButton.setTextFill(Color.BLACK);
           // irInicio();
             //graphPane.requestFocus();
            
        }
        else if (event.getCode()==KeyCode.END){
            System.out.println("Fin");
            graph.construir(ejemplo.getNumNodos());
            Nodo n= graph.getNodos().get(graph.getContador()-1);
            Double hValue=(n.getPosX()+100*sliderZoom.getValue()/100)/maxXPos;
            Double vValue=(n.getPosY()+50*sliderZoom.getValue()/100)/maxYPos;
            if(grafo.getHvalue()!=hValue)
                grafo.setHvalue(hValue);
            if(grafo.getVvalue()!=vValue)
                grafo.setVvalue(vValue);
            if(ejemplo.getNumNodos()==graph.getContador()){
            
                siguienteButton.setTextFill(Color.GRAY);
                finButton.setTextFill(Color.GRAY);
            }
            inicioButton.setTextFill(Color.BLACK);
            anteriorButton.setTextFill(Color.BLACK);
            //irFin();
             //graphPane.requestFocus();
            
        }
        else{
            System.out.println(event.getCode());
        }
    }
    private FicheroXML ejemplo;
    private Grafo graph;
    private Gramatica grammar;
    private CadenaEntrada entryChain;
    private Pane paneGrafo;
    private Pane paneGramatica;
    private Pane paneCadenaEntrada;
    private static Configuracion configuration;
    private Group graphGroup;
    private Group grammarGroup;
    private Group chainGroup;
    private Double zoomGrammar;
    private Double zoomChain;
    double maxYPos;
    double maxXPos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grafo.requestFocus();
        grafo.setFocusTraversable(true);
        menuBar.setFocusTraversable(true);

        elegirArchivo("xml");
        

        grafo.addEventFilter(KeyEvent.KEY_RELEASED,
                event ->handleKeyAction(event));
        
 } 
    /**
     * scale the pane with the graph
     * @param zoom 
     * percentage of scaling
     */
    public void zoom(double zoom ){
        paneGrafo.setScaleX(zoom);
        paneGrafo.setScaleY(zoom);
        //sliderZoom.setValue((int)(sliderZoom.getValue()));
    }
    public void zoomGrammar(double zoom ){
        paneGramatica.setScaleX(zoom);
        paneGramatica.setScaleY(zoom);
        //sliderZoom.setValue((int)(sliderZoom.getValue()));
    }
    public void zoomChain(double zoom ){
        paneCadenaEntrada.setScaleX(zoom);
        paneCadenaEntrada.setScaleY(zoom);
        //sliderZoom.setValue((int)(sliderZoom.getValue()));
    }

    private void elegirArchivo(String tipo) {
        configuration=new Configuracion();
        configuration.cargarConfiguracion("./config/configActual.xml");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File(".//traductores"));
        String path=null;
        //if(tipo.equals("xml")){
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("xml", "*.xml"));
        File file=fileChooser.showOpenDialog(PruebaGrafoJFXML.getStage());
        path=file.getAbsolutePath();
        //}
        
        ejemplo = new FicheroXML();
        //ejemplo.cargarXml("C:\\Users\\adgao\\Documents\\universidad\\TFG\\TFG-Anterior\\TFG-Anterior\\VisTDS\\traductores\\descend.xml"); 
        ejemplo.cargarXml(path); 

        
        
        inicioButton.setTextFill(Color.GRAY);
        anteriorButton.setTextFill(Color.GRAY);
        finButton.setTextFill(Color.BLACK);
        siguienteButton.setTextFill(Color.BLACK);
        paneGrafo=new Pane(); 
        paneGrafo.requestFocus();
        graphGroup=new Group();
        graphGroup.getChildren().add(paneGrafo);
        grafo.setContent(graphGroup);
        paneGramatica=new Pane(); 
        grammarGroup=new Group();
        grammarGroup.getChildren().add(paneGramatica);
        gramatica.setContent(grammarGroup);
        paneCadenaEntrada=new Pane();  
        chainGroup=new Group();
        chainGroup.getChildren().add(paneCadenaEntrada);
        cadenaEntrada.setContent(chainGroup);
//       
//      
//        grafo.removeEventFilter(KeyEvent.KEY_RELEASED,
//                event ->handleKeyAction(event));
//        grafo.addEventFilter(KeyEvent.KEY_RELEASED,
//                event ->handleKeyAction(event));
        
//      
        entryChain=new CadenaEntrada(ejemplo.getCadena(),paneCadenaEntrada,configuration);
        entryChain.construir();
        grammar=new Gramatica(ejemplo,paneGramatica,configuration);
        grammar.construir(/*paneGramatica*/);
      
        graph=new Grafo(ejemplo,grammar,entryChain,paneGrafo,configuration,ejemplo.getTipoTraductor());
        sliderZoom.valueChangingProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println(sliderZoom.getValue());
                if(sliderZoom.getValue()>=minZoom && sliderZoom.getValue()<=maxZoom){
                    zoom((sliderZoom.getValue())/100);
                    inputZoom.setText(sliderZoom.getValue()+"");
                    configuration.guardarConfiguracion(".//config//configActual.xml",
                             configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
                             configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),
                             configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),
                             configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),
                             (int)sliderZoom.getValue(),zoomGrammar.intValue(),zoomChain.intValue());
                }
                else{
                    zoom(minZoom/100);
                    sliderZoom.setValue(minZoom);
                }
            }

        });
        addHandlingListennerChain();
        
        System.out.println(sliderZoom.getValue());
        sliderZoom.setValue(configuration.getZoomGraph());
        zoomChain=configuration.getZoomChain()+0.0;
        zoomGrammar=configuration.getZoomGrammar()+0.0;
        inputZoom.setText(sliderZoom.getValue()+"");
        inputZoomGrammar.setText(zoomGrammar+"");
        inputZoomChain.setText(zoomChain+"");
        zoom((sliderZoom.getValue())/100);
        zoomGrammar(zoomGrammar/100);
        zoomChain(zoomChain/100);
        graph.construir(ejemplo.getNumNodos());
        maxYPos=0.0;
        double minYPos=0.0;
        for(Nodo nodo:graph.getNodos().values()){
            maxYPos=Math.max(nodo.getRectangle().getY(),maxYPos);
            minYPos=Math.min(nodo.getRectangle().getY(),minYPos);
        }
        maxXPos=graph.getPosXMax();
        maxPosition=Math.max(graph.getPosXMax(), maxYPos+Math.abs(minYPos));
        graph.eliminar(0);
        maxZoom=400.0;
        minZoom=(1332/4)/maxPosition*100;
        
    }
    /**
     * Add the events to the entryChain
     */
     public void addHandlingListennerChain(){
        
        ArrayList<CadenaEntradaNode> elements=entryChain.getRectanglesList();
        HashMap<String,Label> labels=entryChain.getLabels();
        for(CadenaEntradaNode r:elements){
            
            r.getRectangle().setOnMouseClicked(new EventHandler<MouseEvent>(){

                @Override
                public void handle(MouseEvent event) {
                    Rectangle rect=(Rectangle)event.getSource();
                    int step=0;
                    if(rect.getId().equals("EOF")){
                        step=ejemplo.getNumNodos();
                        siguienteButton.setTextFill(Color.GRAY);
                        finButton.setTextFill(Color.GRAY); 
                    }    
                    else{
                        step= graph.getStepProcess().get(Integer.parseInt(rect.getId()));
                        finButton.setTextFill(Color.BLACK);
                        siguienteButton.setTextFill(Color.BLACK);
                    }    
                    System.out.println(step);
                    if(graph.getContador()<step){
                        graph.setContador(graph.construir(step));
                        Nodo n= graph.getNodos().get(graph.getContador()-1);
                        Double hValue=(n.getPosX()+sliderZoom.getValue())/maxXPos;
                        Double vValue=(Math.abs(n.getPosY()))/maxYPos;
                
                        if(grafo.getHvalue()!=hValue)
                            grafo.setHvalue(hValue);
                        if(grafo.getVvalue()!=vValue)
                            grafo.setVvalue(vValue);
                    }
                    else if(graph.getContador()>step){
                        graph.eliminar(step);
                        Nodo n= graph.getNodos().get(graph.getContador()-1);
                        
                        Double hValue=(n.getPosX()+sliderZoom.getValue())/maxXPos;
                        Double vValue=(n.getPosY()+sliderZoom.getValue()/2)/maxYPos;
                        if(n.getPosX()*sliderZoom.getValue()/100<grafo.getPrefWidth()){
                           grafo.setHvalue(0); 
                        }
                        else{
                            if(grafo.getHvalue()!=hValue)
                            grafo.setHvalue(hValue);
                        }


                        if(grafo.getVvalue()!=vValue)
                            grafo.setVvalue(vValue);
                        
                    }

                    inicioButton.setTextFill(Color.BLACK);
                    anteriorButton.setTextFill(Color.BLACK);
                }

            }); 
             r.getRectangle().setOnKeyPressed(new EventHandler<KeyEvent>(){

                @Override
                public void handle(KeyEvent event) {
                    Rectangle rect=(Rectangle)event.getSource();
                    if(event.getCode()==KeyCode.ENTER){
                        int step=0;
                        if(rect.getId().equals("EOF")){
                        step=ejemplo.getNumNodos();
                        siguienteButton.setTextFill(Color.GRAY);
                        finButton.setTextFill(Color.GRAY); 
                    }    
                    else{
                        step= graph.getStepProcess().get(Integer.parseInt(rect.getId()));
                        finButton.setTextFill(Color.BLACK);
                        siguienteButton.setTextFill(Color.BLACK);
                    }    
                    System.out.println(step);
                    if(graph.getContador()<step){
                        graph.setContador(graph.construir(step));
                        Nodo n= graph.getNodos().get(graph.getContador()-1);
                        Double hValue=(n.getPosX()+sliderZoom.getValue())/maxXPos;
                        Double vValue=(Math.abs(n.getPosY()))/maxYPos;
                
                        if(grafo.getHvalue()!=hValue)
                            grafo.setHvalue(hValue);
                        if(grafo.getVvalue()!=vValue)
                            grafo.setVvalue(vValue);
                    }
                    else if(graph.getContador()>step){
                        graph.eliminar(step);
                        Nodo n= graph.getNodos().get(graph.getContador()-1);
                        Double hValue=(n.getPosX()+sliderZoom.getValue())/maxXPos;
                        Double vValue=(n.getPosY()+sliderZoom.getValue()/2)/maxYPos;
                        if(n.getPosX()*sliderZoom.getValue()/100<grafo.getPrefWidth()){
                           grafo.setHvalue(0); 
                        }
                        else{
                            if(grafo.getHvalue()!=hValue)
                            grafo.setHvalue(hValue);
                        }


                        if(grafo.getVvalue()!=vValue)
                            grafo.setVvalue(vValue);
                    }
                    inicioButton.setTextFill(Color.BLACK);
                    anteriorButton.setTextFill(Color.BLACK);
                }
                }

            }); 
        }
        for(CadenaEntradaNode l:elements){
            l.getLabel().setOnMouseClicked(new EventHandler<MouseEvent>(){

                @Override
                public void handle(MouseEvent event) {
                    Label l=(Label)event.getSource();
                    int step=0;
                    if(l.getText().equals("EOF")){
                        step=ejemplo.getNumNodos();
                        siguienteButton.setTextFill(Color.GRAY);
                        finButton.setTextFill(Color.GRAY); 
                    }
                    else{
                        step= graph.getStepProcess().get(Integer.parseInt(l.getId()));
                        siguienteButton.setTextFill(Color.BLACK);
                        finButton.setTextFill(Color.BLACK); 
                    }
                    System.out.println(step);
                    if(graph.getContador()<step) { 
                        graph.setContador(graph.construir(step));
                        Nodo n= graph.getNodos().get(graph.getContador()-1);
                        Double hValue=(n.getPosX()+sliderZoom.getValue())/maxXPos;
                        Double vValue=(Math.abs(n.getPosY()))/maxYPos;
                
                        if(grafo.getHvalue()!=hValue)
                            grafo.setHvalue(hValue);
                        if(grafo.getVvalue()!=vValue)
                            grafo.setVvalue(vValue);
                    }
                    else if(graph.getContador()>step){
                        graph.eliminar(step);
                        Nodo n= graph.getNodos().get(graph.getContador()-1);
                        Double hValue=(n.getPosX()+sliderZoom.getValue())/maxXPos;
                        Double vValue=(n.getPosY()+sliderZoom.getValue()/2)/maxYPos;
                        if(n.getPosX()*sliderZoom.getValue()/100<grafo.getPrefWidth()){
                           grafo.setHvalue(0); 
                        }
                        else{
                            if(grafo.getHvalue()!=hValue)
                            grafo.setHvalue(hValue);
                        }


                        if(grafo.getVvalue()!=vValue)
                            grafo.setVvalue(vValue);
                    }
                    inicioButton.setTextFill(Color.BLACK);
                    anteriorButton.setTextFill(Color.BLACK);
                }

            }); 
        }
        
    }

    public static Configuracion getConfiguration() {
        return configuration;
    }

    public static void setConfiguration(Configuracion configuration) {
        FXMLDocumentController.configuration = configuration;
    }
    /**
     * Permit load a configuration
     * @param xml 
     * extension of the configuration files
     */
    private void elegirConfiguracion(String xml) {
         FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File(".//config"));
        //if(tipo.equals("xml")){
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("xml", "*.xml"));
        File file=fileChooser.showOpenDialog(PruebaGrafoJFXML.getStage());
        String path=file.getAbsolutePath();
        configuration.cargarConfiguracion(path);
        zoom(configuration.getZoomGraph()/100);
        sliderZoom.setValue(configuration.getZoomGraph());
        zoomGrammar=configuration.getZoomGrammar()+0.0;
        zoomGrammar(zoomGrammar/100);
        zoomChain=configuration.getZoomChain()+0.0;
        zoomChain(zoomChain/100);
        inputZoom.setText(configuration.getZoomGraph()+"");
        inputZoomGrammar.setText(zoomGrammar.toString());
        inputZoomChain.setText(zoomChain.toString());

        configuration.guardarConfiguracion(".//config//configActual.xml",
                     configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
                     configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),
                     configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),
                     configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),
                     configuration.getZoomGraph(),configuration.getZoomGrammar(),configuration.getZoomChain());
        graph.updateGraph();
        grammar.updateGrammar();
        entryChain.actualizarCadena(graph.getContador());
    }
    


    
}
