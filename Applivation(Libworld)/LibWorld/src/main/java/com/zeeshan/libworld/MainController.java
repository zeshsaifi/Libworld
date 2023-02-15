package com.zeeshan.libworld;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    int r;
    //- - - - - Main/Login Variables - - - - -
    @FXML
    private PasswordField hidePassword;
    @FXML
    private TextField showPassword, usernameField;
    @FXML
    private Button showButton, hideButton, loginButton, signupPageButton, adminPageButton;

    //- - - - - Forget Password Variables - - - - -
    @FXML
    private Pane forgetPanelLower, forgetPanelUpper;
    @FXML
    private Button OTPSendButton, forgetButtonShow, forgetButtonHide,changePasswordButton;
    @FXML
    private TextField forgetUsername, forgetOTPField, confirmPasswordShow;
    @FXML
    private PasswordField newPassword, confirmPasswordHide;

    //- - - - - Sign Up Page Variables - - - - -
    @FXML
    private Pane signupUpperPanel, signupLowerPanel;
    @FXML
    private Button signupOTPSendButton, signupButtonShow, signupButtonHide, loginPageButton;
    @FXML
    private TextField signupUsername, signupEmail, signupOTPField, signupFieldShow;
    @FXML
    private PasswordField signupFieldHide, signupPasswordField;

    //- - - - - Admin Page variables - - - - -
    @FXML
    private TextField adminUsernameField;
    @FXML
    private PasswordField adminPasswordField;
    @FXML
    private Button adminLoginButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    //- - - - - Login Events - - - - -
    public void showPassword() {
        showPassword.setText(hidePassword.getText());

        hidePassword.setDisable(true);
        hidePassword.setVisible(false);

        showPassword.setDisable(false);
        showPassword.setVisible(true);

        showButton.setDisable(true);
        showButton.setVisible(false);

        hideButton.setVisible(true);
        hideButton.setDisable(false);
    }

    public void hidePassword() {
        hidePassword.setText(showPassword.getText());

        hidePassword.setDisable(false);
        hidePassword.setVisible(true);

        showPassword.setDisable(true);
        showPassword.setVisible(false);

        showButton.setDisable(false);
        showButton.setVisible(true);

        hideButton.setVisible(false);
        hideButton.setDisable(true);
    }

    public void logIn() {
        System.out.println("Login Service...!");
        if (usernameField.getText().equals("") || hidePassword.getText().equals("") || showPassword.equals("")) {
            usernameField.setStyle("-fx-border-color:red;-fx-text-fill:red;");
            hidePassword.setStyle("-fx-border-color:red;-fx-text-fill:red;");
            showPassword.setStyle("-fx-border-color:red;-fx-text-fill:red;");
            showButton.setStyle("-fx-text-fill:red;");
            hideButton.setStyle("-fx-text-fill:red;");
        } else {
            usernameField.setStyle("-fx-border-color:#00b8f1;");
            hidePassword.setStyle("-fx-border-color:#00b8f1;");
            showPassword.setStyle("-fx-border-color:#00b8f1;");
            showButton.setStyle("-fx-text-fill:#00b8f1;");
            hideButton.setStyle("-fx-text-fill:#00b8f1;");

            Connection con = null;
            con = DatabaseConnection.Data();
            try {
                ResultSet rs = con.prepareStatement("SELECT `username`,`password` FROM `users` WHERE `username`='" + usernameField.getText() + "'").executeQuery();
                String user = null, pass = null;
                while (rs.next()) {
                    user = rs.getString(1);
                    pass = rs.getString(2);
                }
                System.out.println(user + " " + pass);
                if (user == null) {
                    usernameField.setStyle("-fx-border-color:red;-fx-text-fill:red;");
                } else {
                    usernameField.setStyle("-fx-border-color:green;-fx-text-fill:green;");
                    if (pass.equals(showPassword.getText()) || pass.equals(hidePassword.getText())) {
                        showPassword.setStyle("-fx-border-color:green;-fx-text-fill:green;");
                        hidePassword.setStyle("-fx-border-color:green;-fx-text-fill:green;");
                        showButton.setStyle("-fx-text-fill:green;");
                        hideButton.setStyle("-fx-text-fill:green;");
                        System.out.println("Login Successful");

                        //Calling User-panel...!

                        FileWriter fw=new FileWriter("Data\\file.information.user");
                        fw.write(usernameField.getText());
                        System.out.println("file create true...!");
                        fw.close();

                        Stage stage=(Stage) loginButton.getScene().getWindow();
                        stage.close();

                        Stage s=new Stage();
                        s.getIcons().add(new Image("file:Data\\file.information.view"));
                        Parent root = FXMLLoader.load(getClass().getResource("User_panel_fxml.fxml"));
                        Scene scene=new Scene(root);
                        s.setScene(scene);
                        s.setMinHeight(600);
                        s.setMinWidth(1000);
                        s.setTitle("Libworld");
                        s.show();

                    } else {
                        hidePassword.setStyle("-fx-border-color:red;-fx-text-fill:red;");
                        showPassword.setStyle("-fx-border-color:red;-fx-text-fill:red;");
                        showButton.setStyle("-fx-text-fill:red;");
                        hideButton.setStyle("-fx-text-fill:red;");
                    }
                }
            } catch (NullPointerException ne) {
                System.out.println("Null value " + ne);
            } catch (SQLException se) {
                System.out.println(se);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void forgetPassword() throws Exception {
        System.out.println("Forget password service...!");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ForgetPassword_fxml.fxml"));
        Stage stage = new Stage();
        stage.getIcons().add(new Image("file:Data\\file.information.view"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        stage.setResizable(false);
        stage.setTitle("Lib World");
        stage.setScene(scene);
        stage.show();
    }

    public void dontHaveAccount() throws Exception {
        System.out.println("Calling Signup Page...!");
        Parent root = FXMLLoader.load(getClass().getResource("Signup_fxml.fxml"));
        Scene scene = signupPageButton.getScene();
        scene.setRoot(root);
    }

    public void adminPage() throws Exception {
        System.out.println("Calling Admin Page...!");
        Parent root = FXMLLoader.load(getClass().getResource("Admin_fxml.fxml"));
        Scene scene = adminPageButton.getScene();
        scene.setRoot(root);
    }

    //- - - - - Forget Password Events - - - - -
    public void forgetPasswordOTPSend() {
        System.out.println("Forget Password OTP Send Service...!");
        Random random = new Random();
        r = random.nextInt(9999);

        if (forgetUsername.getText().isEmpty()) {
            forgetUsername.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        } else {
            Connection con = null;
            con = DatabaseConnection.Data();

            try {
                ResultSet rs = con.prepareStatement("SELECT `email` FROM `users` WHERE `email`='" + forgetUsername.getText() + "'").executeQuery();
                String user = null;
                while (rs.next()) {
                    user = rs.getString(1);
                }
                if (user == null) {
                    forgetUsername.setStyle("-fx-border-color:red;-fx-text-fill:red;");
                } else {
                    forgetUsername.setStyle("-fx-border-color:green;-fx-text-fill:green;");
                    System.out.println("Sending OTP :" + r + " to " + forgetUsername.getText());
                    EmailSender emailSender = new EmailSender(forgetUsername.getText(), r);
                    OTPSendButton.setText("Resend OTP");
                }
            } catch (NullPointerException ne) {
                System.out.println("Null value " + ne);
            } catch (SQLException se) {
                System.out.println(se);
            }
        }
    }

    public void forgetPasswordOTPCheck() {
        System.out.println("Forget Password OTP Check Service...!");
        int otp = Integer.parseInt(forgetOTPField.getText());
        if (otp == r) {
            forgetOTPField.setStyle("-fx-border-color:green;-fx-text-fill:green;");
            forgetPanelUpper.setDisable(true);
            forgetPanelLower.setDisable(false);
        } else {
            forgetOTPField.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        }
    }

    public void forgetPasswordShow() {
        confirmPasswordShow.setText(confirmPasswordHide.getText());

        confirmPasswordHide.setDisable(true);
        confirmPasswordHide.setVisible(false);

        confirmPasswordShow.setDisable(false);
        confirmPasswordShow.setVisible(true);

        forgetButtonShow.setDisable(true);
        forgetButtonShow.setVisible(false);

        forgetButtonHide.setVisible(true);
        forgetButtonHide.setDisable(false);
    }

    public void forgetPasswordHide() {
        confirmPasswordHide.setText(confirmPasswordShow.getText());

        confirmPasswordHide.setDisable(false);
        confirmPasswordHide.setVisible(true);

        confirmPasswordShow.setDisable(true);
        confirmPasswordShow.setVisible(false);

        forgetButtonShow.setDisable(false);
        forgetButtonShow.setVisible(true);

        forgetButtonHide.setVisible(false);
        forgetButtonHide.setDisable(true);
    }

    public void changePassword() {
        System.out.println("Change Password Service...!");

        if (newPassword.getText().matches(".*[0-9]{1,}.*") && newPassword.getText().matches(".*[@#$]{1,}.*") && newPassword.getText().length() >= 6 && newPassword.getText().length() <= 20) {
            if (newPassword.getText().equals(confirmPasswordShow.getText()) || newPassword.getText().equals(confirmPasswordHide.getText())) {

                //Connection of Database for update Password...!

                Connection con = null;
                con = DatabaseConnection.Data();
                try {
                    Statement stmt = con.createStatement();
                    stmt.execute("UPDATE `users` SET `password`='" + newPassword.getText() + "' WHERE `email`='" + forgetUsername.getText() + "';");
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Password changed");
                    a.setContentText("Your password has been changed.\n\nThank-you");
                    a.setHeaderText(null);
                    a.show();
                    System.out.println("The password " + newPassword.getText() + " of " + forgetUsername.getText() + " has been changed...!");
                    Stage stage= (Stage) changePasswordButton.getScene().getWindow();
                    stage.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Mismatch passwords");
                a.setContentText("Both password does not match\nwith each other.\n\nThank-you");
                a.setHeaderText(null);
                a.show();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Invalid password pattern");
            a.setContentText("Your password must be contain.\n1. At least one digit.\n2. At least one of the following special characters @, #, $\n3. The length should be between 6 to 20 characters.\nExample Password@1234.\n\nThank-you");
            a.setHeaderText(null);
            a.show();
        }
    }

    //- - - - - Sign Up Events - - - - -
    public void checkUser() {
        System.out.println("Valid User Check Service...!");

        Connection con = null;
        con = DatabaseConnection.Data();

        if (signupUsername.getText().equals("") || signupUsername.getText().length() < 4 || signupUsername.getText().length() > 20) {
            signupUsername.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        } else {
            try {
                ResultSet rs = con.prepareStatement("SELECT `username` FROM `users` WHERE `username`='" + signupUsername.getText() + "';").executeQuery();
                String user = null;
                while (rs.next()) {
                    user = rs.getString(1);
                }
                System.out.println(user);
                if (user == null) {
                    signupUsername.setStyle("-fx-border-color:green;-fx-text-fill:green;");
                    signupEmail.setEditable(true);
                } else {
                    signupUsername.setStyle("-fx-border-color:red;-fx-text-fill:red;");
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Invalid E-mail");
                    a.setHeaderText(null);
                    a.setContentText(signupUsername.getText() + " is already in use.\nPlease try again with another username.\n\nThank-you.");
                    a.show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void signupOTPSend() {
        System.out.println("Signup OTP Send Service...!");

        if (signupUsername.getText().equals("") || signupUsername.getText().length() < 4 || signupUsername.getText().length() > 20) {
            signupUsername.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        } else {
            if (signupEmail.getText().equals("")) {
                signupEmail.setStyle("-fx-border-color:red;-fx-text-fill:red;");
            } else {
                Connection con = null;
                con = DatabaseConnection.Data();
                try {
                    ResultSet rs = con.prepareStatement("select `email` from `users` where `email`='" + signupEmail.getText() + "'").executeQuery();
                    String email = null;
                    while (rs.next()) {
                        email = rs.getString(1);
                    }
                    System.out.println(email);

                    if (email == null) {
                        signupEmail.setStyle("-fx-border-color:green;-fx-text-fill:green;");
                        Random random = new Random();
                        r = random.nextInt(9999);
                        System.out.println("Sending OTP: " + r + " to " + signupEmail.getText());
                        EmailSender emailSender = new EmailSender(signupEmail.getText(), r);
                        signupOTPField.setEditable(true);
                        signupUsername.setEditable(false);
                        signupOTPSendButton.setText("Resend OTP");
                    } else {
                        signupEmail.setStyle("-fx-border-color:red;-fx-text-fill:red;");
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("Invalid E-mail");
                        a.setHeaderText(null);
                        a.setContentText(signupEmail.getText() + " is already in use.\nPlease try again with another email\n\nThank-you.");
                        a.show();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void signupOTPCheck() {
        System.out.println("Signup OTP Check Service...!");
        if (signupOTPField.getText().equals("")) {
            signupOTPField.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        } else {
            int otp = Integer.parseInt(signupOTPField.getText());
            if (otp == r) {
                signupOTPField.setStyle("-fx-border-color:green;-fx-text-fill:green;");
                signupUpperPanel.setDisable(true);
                signupLowerPanel.setDisable(false);
            } else {
                signupOTPField.setStyle("-fx-border-color:red;-fx-text-fill:red;");
            }
        }
    }

    public void signupButtonShow() {
        signupFieldShow.setText(signupFieldHide.getText());

        signupFieldHide.setDisable(true);
        signupFieldHide.setVisible(false);

        signupFieldShow.setDisable(false);
        signupFieldShow.setVisible(true);

        signupButtonShow.setDisable(true);
        signupButtonShow.setVisible(false);

        signupButtonHide.setVisible(true);
        signupButtonHide.setDisable(false);
    }

    public void signupButtonHide() {
        signupFieldHide.setText(signupFieldShow.getText());

        signupFieldHide.setDisable(false);
        signupFieldHide.setVisible(true);

        signupFieldShow.setDisable(true);
        signupFieldShow.setVisible(false);

        signupButtonShow.setDisable(false);
        signupButtonShow.setVisible(true);

        signupButtonHide.setVisible(false);
        signupButtonHide.setDisable(true);
    }

    public void createAccount() throws IOException {
        System.out.println("Created Account Service...!");

        if (signupPasswordField.getText().matches(".*[0-9]{1,}.*") && signupPasswordField.getText().matches(".*[@#$]{1,}.*") && signupPasswordField.getText().length() >= 6 && signupPasswordField.getText().length() <= 20) {
            if (signupPasswordField.getText().equals(signupFieldShow.getText()) || signupPasswordField.getText().equals(signupFieldHide.getText())) {
                signupPasswordField.setStyle("-fx-border-color:green;-fx-text-fill:green;");
                signupFieldShow.setStyle("-fx-border-color:green;-fx-text-fill:green;");
                signupFieldHide.setStyle("-fx-border-color:green;-fx-text-fill:green;");
                signupButtonShow.setStyle("-fx-text-fill:green;");
                signupButtonHide.setStyle("-fx-text-fill:green;");

                Connection con = null;
                con = DatabaseConnection.Data();
                try {
                    Statement stmt=con.createStatement();
                    stmt.execute("INSERT INTO `users`(`username`, `password`, `email`) VALUES ('"+signupUsername.getText()+"','"+signupPasswordField.getText()+"','"+signupEmail.getText()+"')");
                    Alert a = new Alert(Alert.AlertType.INFORMATION);

                    a.setTitle("Account Created");
                    a.setContentText("Congratulation your account\nhas been created.\n\nThank-you");
                    a.setHeaderText(null);
                    a.show();

                    System.out.println("Calling Login Page...!");
                    Parent root = FXMLLoader.load(getClass().getResource("Main_fxml.fxml"));
                    Scene scene = loginPageButton.getScene();
                    scene.setRoot(root);
                }
                catch (SQLException e) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Error");
                    a.setContentText("There is some technical error.\nPlease try after sometime.\nSorry for interrupt.\n\nThank-you");
                    a.setHeaderText(null);
                    a.show();
                    throw new RuntimeException(e);
                }
        }
            else{
                signupPasswordField.setStyle("-fx-border-color:red;-fx-text-fill:red;");
                signupFieldShow.setStyle("-fx-border-color:red;-fx-text-fill:red;");
                signupFieldHide.setStyle("-fx-border-color:red;-fx-text-fill:red;");
                signupButtonShow.setStyle("-fx-text-fill:red;");
                signupButtonHide.setStyle("-fx-text-fill:red;");
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Mismatch passwords");
                a.setContentText("Both password does not match\nwith each other.\n\nThank-you");
                a.setHeaderText(null);
                a.show();
        }
    }
        else{
            signupPasswordField.setStyle("-fx-border-color:red;-fx-text-fill:red;");
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Invalid password pattern");
            a.setContentText("Your password must be contain.\n1. At least one digit.\n2. At least one of the following special characters @, #, $\n3. The length should be between 6 to 20 characters.\nExample Password@1234.\n\nThank-you");
            a.setHeaderText(null);
            a.show();
    }
}
    public void logInPanel()throws Exception{
        System.out.println("Calling Login Page...!");
        Parent root = FXMLLoader.load(getClass().getResource("Main_fxml.fxml"));
        Scene scene = loginPageButton.getScene();
        scene.setRoot(root);
    }

    //- - - - - Admin Page Event - - - - -
    public void adminLogin(){
        System.out.println("Login as Admin...!");
        if(adminUsernameField.getText().equals("")){
            adminUsernameField.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        }
        else if (adminPasswordField.getText().equals("")) {
            adminPasswordField.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        }
        else{
            Connection con=null;
            con=DatabaseConnection.Data();
            try {
                ResultSet rs=con.prepareStatement("SELECT `username`,`password` FROM `admin` WHERE `username`='"+adminUsernameField.getText()+"';").executeQuery();
                String user=null,pass=null;
                while(rs.next()){
                    user= rs.getString(1);
                    pass=rs.getString(2);
                }
                if(user == null){
                    adminUsernameField.setStyle("-fx-border-color:red;-fx-text-fill:red;");
                    Alert a=new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Invalid username");
                    a.setHeaderText(null);
                    a.setContentText(adminUsernameField.getText()+" is Invalid.\nPlease try with another username.\n\nThank-you");
                    a.show();
                }
                else{
                    if(pass.equals(adminPasswordField.getText())){
                        System.out.println("Calling Admin Panel...!");
                        adminUsernameField.setStyle("-fx-border-color:green;-fx-text-fill:green;");
                        adminPasswordField.setStyle("-fx-border-color:green;-fx-text-fill:green;");

                        Stage stage=(Stage) adminLoginButton.getScene().getWindow();
                        stage.close();

                        Stage s=new Stage();
                        s.getIcons().add(new Image("file:Data\\file.information.view"));
                        Parent root = FXMLLoader.load(getClass().getResource("Admin_panel_fxml.fxml"));
                        Scene scene=new Scene(root);
                        s.setScene(scene);
                        s.setMinHeight(600);
                        s.setMinWidth(1000);
                        s.setTitle("Libworld");
                        s.show();
                    }
                    else{
                        adminPasswordField.setStyle("-fx-border-color:red;-fx-text-fill:red;");
                    }
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //- - - - - Exit Event - - - - -adminLogin
    public void exit(){
        System.exit(0);
    }
}