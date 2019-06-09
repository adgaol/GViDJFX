/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebagrafojfxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author adgao
 */
public class FXMLTutorialController implements Initializable {
    @FXML
    public void handleSiguiente(ActionEvent event) throws IOException {
        if(pagina<imagenes.length-1){
            anteriorButton.setTextFill(Color.BLACK);
            pagina++;
            Image image=null;
            try {
                image = new Image(new FileInputStream("./tutorial/"+imagenes[pagina])); //"./tutorial/"+imagenes[pagina]);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLTutorialController.class.getName()).log(Level.SEVERE, null, ex);
            }
            imageViwer.setImage(image);
        }
        if(pagina==imagenes.length-1){
            siguienteButton.setTextFill(Color.GRAY);
        }
    }
    @FXML
    public void handleAnterior(ActionEvent event) throws IOException {
        if(pagina>0){
            siguienteButton.setTextFill(Color.BLACK);
            pagina--;
            Image image=null;
            try {
                image = new Image(new FileInputStream("./tutorial/"+imagenes[pagina])); //"./tutorial/"+imagenes[pagina]);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLTutorialController.class.getName()).log(Level.SEVERE, null, ex);
            }
            imageViwer.setImage(image);
        }
        if(pagina==0){
            anteriorButton.setTextFill(Color.GRAY);
        }
    }
    @FXML
    public void handleCancelar(ActionEvent event) throws IOException {
        
        Stage stage = (Stage) salirButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    public ImageView imageViwer;
    @FXML
    public Button anteriorButton;
    @FXML
    public Button siguienteButton;
    @FXML
    public Button salirButton;
    private Integer pagina;
    private String[] imagenes;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pagina=0;
        File directorio=new File("./tutorial");
        imagenes=directorio.list();
        
        Image first=null;
        try {
            first = new Image(new FileInputStream("./tutorial/"+imagenes[pagina])); //"./tutorial/"+imagenes[pagina]);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLTutorialController.class.getName()).log(Level.SEVERE, null, ex);
        }
        imageViwer.setImage(first);
        anteriorButton.setTextFill(Color.GRAY);
    } 
    
}
