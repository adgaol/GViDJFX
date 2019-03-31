/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebagrafojfxml;

import com.sun.javafx.application.HostServicesDelegate;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.application.HostServices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventDispatcher;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private void handleMasMenosZoom(ActionEvent event) {
        if(event.getSource().equals(masZoom)){
            
            zoom((sliderZoom.getValue()+10)/100);
            sliderZoom.setValue((int)(sliderZoom.getValue()+10));
            inputZoom.setText(sliderZoom.getValue()+"");
        }
        else{
          
           zoom((sliderZoom.getValue()-10)/100);
           sliderZoom.setValue((int)(sliderZoom.getValue()-10));
           inputZoom.setText(sliderZoom.getValue()+"");
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
          
           zoomGrammar-=10;
            zoomGrammar(zoomGrammar/100);
            
            inputZoomGrammar.setText(zoomGrammar+"");
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
          
           zoomChain-=10;
            zoomChain(zoomChain/100);
            
            inputZoomGrammar.setText(zoomChain+"");
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
           //configuration.onCloseRequestProperty().addListener();
            // Set position of second window, related to primary window.
            //newWindow.setX(primaryStage.getX() + 200);
            //newWindow.setY(primaryStage.getY() + 100);
        configurationScreen.show();
//        configurationScreen.setOnHiding(new EventHandler<WindowEvent>() {
//          @Override
//          public void handle(WindowEvent we) {
//            //int cont=contador;
//            configuration.cargarConfiguracion("./config/configActual.xml");
//            graph.updateGraph();
//            grammar.updateGrammar();
//            entryChain.actualizarCadena(graph.getContador());
//            
//          }
//      }); 

    
    
    }
    @FXML
    private void handleInputZoom(ActionEvent event) {
        try{
        Double value= Double.parseDouble(inputZoom.getText()); 
        zoom((value)/100);
        sliderZoom.setValue(value);
        
        configuration.guardarConfiguracion(".//config//configActual.xml",
                         configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
                         configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),
                         configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),
                         configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),
                         (int)sliderZoom.getValue(),zoomGrammar.intValue(),zoomChain.intValue());
        
        }
        catch(Exception e){
            inputZoom.setText("no valido");
        }
           
    }
     @FXML
    private void handleInputZoomGrammar(ActionEvent event) {
        try{
        Double value= Double.parseDouble(inputZoomGrammar.getText()); 
        zoomGrammar((value)/100);
        zoomGrammar=value;
        
        configuration.guardarConfiguracion(".//config//configActual.xml",
             configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
             configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),
             configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),
             configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),
             (int)sliderZoom.getValue(),zoomGrammar.intValue(),zoomChain.intValue());

        }
        catch(Exception e){
            inputZoomGrammar.setText("no valido");
        }
           
    }
     @FXML
    private void handleInputZoomChain(ActionEvent event) {
        try{
        Double value= Double.parseDouble(inputZoomChain.getText()); 
        zoomChain((value)/100);
        zoomChain=value;
        
        configuration.guardarConfiguracion(".//config//configActual.xml",
             configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
             configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),
             configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),
             configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),
             (int)sliderZoom.getValue(),zoomGrammar.intValue(),zoomChain.intValue());

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
        Nodo n= graph.getNodos().get(graph.getContador()-1);
        grafo.setHvalue(n.getPosX());
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
            grafo.setHvalue(n.getPosX());
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
        grafo.setHvalue(n.getPosX());
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
            Nodo n= graph.getNodos().get(graph.getContador()-1);
            grafo.setHvalue(n.getPosX());
            //contador--;
            // graphPane.requestFocus();
            }
            
        }
        else if (event.getCode()==KeyCode.RIGHT){
            if(ejemplo.getNumNodos()>graph.getContador()){
                System.out.println("RIGHT");
                graph.construir(graph.getContador()+1);
                Nodo n= graph.getNodos().get(graph.getContador()-1);
                grafo.setHvalue(n.getPosX());
                //this.contador+=1;
             //graphPane.requestFocus();
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
            grafo.setHvalue(n.getPosX());
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      grafo.requestFocus();
      grafo.setFocusTraversable(true);
      
      
      elegirArchivo("xml");  
      grafo.addEventFilter(KeyEvent.KEY_RELEASED,
                event ->handleKeyAction(event));
//      ejemplo = new FicheroXML();
//      ejemplo.cargarXml("C:\\Users\\adgao\\Documents\\universidad\\TFG\\TFG-Anterior\\TFG-Anterior\\VisTDS\\traductores\\descend.xml"); 
//  //    HashMap rectangles=new HashMap();
//      configuration=new Configuracion();
//      configuration.cargarConfiguracion("./config/configActual.xml");
//      paneGrafo=new Pane();  
//      graphGroup=new Group();
//      graphGroup.getChildren().add(paneGrafo);
//      grafo.setContent(graphGroup);
//      paneGramatica=new Pane();  
//      gramatica.setContent(paneGramatica);
//      paneCadenaEntrada=new Pane();  
//      cadenaEntrada.setContent(paneCadenaEntrada);
////       EventDispatcher scrollPaneEventDispatcher = grafo.getEventDispatcher();
////       grafo.setEventDispatcher((event, tail) -> {
////            if (KeyEvent.ANY.equals(event.getEventType().getSuperType())) {
////                System.out.println("DISPATCH\tScrollPane\tevent=" + event.getEventType());
////            }
////            Event eventToDispatch = scrollPaneEventDispatcher.dispatchEvent(event, tail);
////            if (KeyEvent.KEY_PRESSED.equals(event.getEventType())) {
////                if (KeyCode.LEFT.equals(((KeyEvent) event).getCode()) || KeyCode.RIGHT.equals(((KeyEvent) event).getCode())) {
////                    if (eventToDispatch == null) {
////                        return event;
////                    }
////                }
////            }
////            return eventToDispatch;
////        });
//        grafo.addEventFilter(KeyEvent.KEY_RELEASED,
//                event ->handleKeyAction(event));
////         grafo.addEventHandler(KeyEvent.KEY_TYPED,
////                event -> handleKeyAction(event));
//        
////      Nodo n1=new Nodo(ejemplo.getListaPasos().get(0).getElemento());
////      Label label1=new Label("patata");
////      Rectangle r1=new Rectangle(100, 100);
////      r1.setLayoutX(500);
////      
////      label1.setLayoutX(500+r1.getWidth()/3);
////      label1.setLayoutY(r1.getWidth()/3);
////      r1.setFill(Paint.valueOf("ff00ff"));
////      Rectangle r2=new Rectangle(100, 100);
////      r2.setLayoutX(r1.getLayoutX()+500);
////      r2.setLayoutY(300);
////      r2.setFill(Paint.valueOf("ff00ff"));
////      Line line=new Line(r1.getLayoutX()+r1.getWidth(), r1.getLayoutY()+r1.getHeight(), r2.getLayoutX(),r2.getLayoutY());
////      r1.setOnMouseEntered((event) -> {
////
////        Rectangle r=new Rectangle(100, 100);
////        r.setLayoutX(r1.getLayoutX());
////        r.setLayoutY(600);
////        r.setOpacity(0.5);
////        rectangles.put(r1, r);
////        pane.getChildren().add(r);
////      });
////      r1.setOnMouseExited((event) -> {
////
////        pane.getChildren().remove(rectangles.get(r1));
////      });
//      //pane.getChildren().addAll(n1.getRectangle(),r2,line,label1);
//      entryChain=new CadenaEntrada(ejemplo.getCadena(),paneCadenaEntrada,configuration);
//      entryChain.construir();
//      grammar=new Gramatica(ejemplo,paneGramatica,configuration);
//      grammar.construir(/*paneGramatica*/);
//      
//      graph=new Grafo(ejemplo,grammar,entryChain,paneGrafo,configuration);
//      sliderZoom.valueChangingProperty().addListener(new ChangeListener<Boolean>(){
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//               System.out.println(sliderZoom.getValue());
//                zoom((sliderZoom.getValue())/100);
//                
//                configuration.guardarConfiguracion(".//config//configActual.xml",
//                         configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
//                         configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),(int)sliderZoom.getValue());
//        
//            }
//           
//       });
//        System.out.println(sliderZoom.getValue());
//        sliderZoom.setValue(configuration.getZoom());
//        zoom((sliderZoom.getValue())/100);
//        
////        paneGrafo.setScaleX((sliderZoom.getValue())/100);
////        paneGrafo.setScaleY((sliderZoom.getValue())/100);
////        sliderZoom.setValue((int)(sliderZoom.getValue()));
////      graph.construir(contador, 8, pane);
////      contador=8;
//      
////      graph.eliminar(contador, 0, pane);
////      contador=0;
////      graph.construir(contador, 16, pane);
////    n1=  graph.insertarNodo(null, pane, n1.getSimbolo(), 500, 0);
////    
////    Nodo n=graph.getNodos().get(Integer.parseInt(ejemplo.getListaPasos().get(contador+1).getElemento().split(" ")[1]));
////      graph.insertarNodo(n, pane, ejemplo.getListaPasos().get(contador+1).getElemento().split(" ")[0], 250, 500);
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File(".//traductores"));
        String path=null;
        //if(tipo.equals("xml")){
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("xml", "*.xml"));
        File file=fileChooser.showOpenDialog(PruebaGrafoJFXML.getStage());
        path=file.getAbsolutePath();
        //}
        configuration=new Configuracion();
        ejemplo = new FicheroXML();
        //ejemplo.cargarXml("C:\\Users\\adgao\\Documents\\universidad\\TFG\\TFG-Anterior\\TFG-Anterior\\VisTDS\\traductores\\descend.xml"); 
        ejemplo.cargarXml(path); 

        
        configuration.cargarConfiguracion("./config/configActual.xml");
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
                zoom((sliderZoom.getValue())/100);
                inputZoom.setText(sliderZoom.getValue()+"");
                configuration.guardarConfiguracion(".//config//configActual.xml",
                         configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
                         configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),
                         configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),
                         configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),
                         (int)sliderZoom.getValue(),zoomGrammar.intValue(),zoomChain.intValue());

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
        
    }
    /**
     * Add the events to the entryChain
     */
     public void addHandlingListennerChain(){
        
        HashMap<String,Rectangle> elements=entryChain.getRectanglesChain();
        HashMap<String,Label> labels=entryChain.getLabels();
        for(Rectangle r:elements.values()){
            
            r.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
                        step= graph.getStepProcess().get(rect.getId()/*cadena.getRectanglesText().get(rect.getId())*/);
                        finButton.setTextFill(Color.BLACK);
                        siguienteButton.setTextFill(Color.BLACK);
                    }    
                    System.out.println(step);
                    if(graph.getContador()<step){
                        graph.setContador(graph.construir(step));
                        Nodo n= graph.getNodos().get(graph.getContador()-1);
                        grafo.setHvalue(n.getPosX());
                    }
                    else if(graph.getContador()>step){
                        graph.eliminar(step);
                        Nodo n= graph.getNodos().get(graph.getContador()-1);
                        grafo.setHvalue(n.getPosX());
                    }

                    inicioButton.setTextFill(Color.BLACK);
                    anteriorButton.setTextFill(Color.BLACK);
                }

            }); 
             r.setOnKeyPressed(new EventHandler<KeyEvent>(){

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
                        step= graph.getStepProcess().get(rect.getId()/*cadena.getRectanglesText().get(rect.getId())*/);
                        finButton.setTextFill(Color.BLACK);
                        siguienteButton.setTextFill(Color.BLACK);
                    }    
                    System.out.println(step);
                    if(graph.getContador()<step){
                        graph.setContador(graph.construir(step));
                        Nodo n= graph.getNodos().get(graph.getContador()-1);
                        grafo.setHvalue(n.getPosX());
                    }
                    else if(graph.getContador()>step){
                        graph.eliminar(step);
                        Nodo n= graph.getNodos().get(graph.getContador()-1);
                        grafo.setHvalue(n.getPosX());
                    }
                    inicioButton.setTextFill(Color.BLACK);
                    anteriorButton.setTextFill(Color.BLACK);
                }
                }

            }); 
        }
        for(Label l:labels.values()){
            l.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
                        step= graph.getStepProcess().get(l.getText()/*cadena.getRectanglesText().get(rect.getId())*/);
                        siguienteButton.setTextFill(Color.BLACK);
                        finButton.setTextFill(Color.BLACK); 
                    }
                    System.out.println(step);
                    if(graph.getContador()<step) { 
                        graph.setContador(graph.construir(step));
                        Nodo n= graph.getNodos().get(graph.getContador()-1);
                        grafo.setHvalue(n.getPosX());
                    }
                    else if(graph.getContador()>step){
                        graph.eliminar(step);
                        Nodo n= graph.getNodos().get(graph.getContador()-1);
                        grafo.setHvalue(n.getPosX());
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
