/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package aviation2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

/**
 * FXML Controller class
 *
 * @author andri
 */
public class UserLoginController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private CheckBox showPasswordCheckBox;
    @FXML
    private Label reussiteLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onclickLogOutbtn(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

    // Créer une nouvelle scène avec la vue chargée 
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
    // Obtenir la référence de la scène actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    // Changer la scène pour la nouvelle scène 
        stage.setScene(scene);
        stage.show();
    }
    
     private boolean authenticateUser(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/aviation2";
        String user = "root";
        String passwd = "";

        try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
            String sql = "SELECT * FROM users WHERE id_user = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'authentification : " + e.getMessage());
            return false;
        }
    }

    @FXML
    private void onClickConnex(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if(username.isEmpty() || password.isEmpty())
        {
            errorLabel.setText("Veuillez remplir tous les champs!");
        }
        else{
            if (authenticateUser(username, password)) {
            errorLabel.setText("");
            reussiteLabel.setText("Connexion réussie . . .");
            System.out.println("Authentification réussie");
        // Continuer à votre interface utilisateur principale
            } else {
                errorLabel.setText("Nom d'Admin Ou Mot de passe Invalide !");
                System.out.println("Nom d'utilisateur ou mot de passe invalide");
            }
        }
        
    }

    @FXML
    private void onclickAnnuler(ActionEvent event) {
        usernameField.setText("");
        passwordField.setText("");
    }

    @FXML
    private void showPassword(ActionEvent event) {
        if (showPasswordCheckBox.isSelected()) {
            passwordField.setPromptText(passwordField.getText());
            passwordField.setText("");
            passwordField.setDisable(true);
        } else {
            passwordField.setText(passwordField.getPromptText());
            passwordField.setPromptText("");
            passwordField.setDisable(false);
        }
    }
    
}
