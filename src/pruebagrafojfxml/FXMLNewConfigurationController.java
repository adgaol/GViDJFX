/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebagrafojfxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author adgao
 */
public class FXMLNewConfigurationController implements Initializable {
    @FXML
        public void handleSave(ActionEvent event) throws IOException {
            copyFile(FXMLDocumentController.getConfiguration().getRuta(), "./config/"+newName.getText()+".xml");
            FXMLDocumentController.getConfiguration().cargarConfiguracion( "./config/"+newName.getText()+".xml");
            Stage stage = (Stage) aceptButton.getScene().getWindow();
            stage.close();
    }
    @FXML
    public TextField newName;
    @FXML
    public Button aceptButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    } 
    /**
     * Copy a configuration with a new name
     * @param fromFile
     * origin file
     * @param toFile
     * destination file
     * @return 
     * if the operation was successful
     */
     public boolean copyFile(String fromFile, String toFile) {
        File origin = new File(fromFile);
        File destination = new File(toFile);
        if (origin.exists()) {
            try {
                InputStream in = new FileInputStream(origin);
                OutputStream out = new FileOutputStream(destination);
                // We use a buffer for the copy (Usamos un buffer para la copia).
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                return true;
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
}
