package com.zeeshan.libworld;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    String realUsername;
    Connection con=DatabaseConnection.Data();
    @FXML
    private Label usernameLabel,dateTimeLogin,bookIDLabel,bookNameLabel,bookQuantityLabel,bookAvailabilityLabel;
    @FXML
    private TextField bookIDField,bookNameField;
    @FXML
    private Button currentStatusButton,bookAvailabilityButton,historyButton,signOutButton;

    @FXML
    private TableView<CurrentTable> currentStatusTable;
    @FXML
    private TableColumn<CurrentTable, String> cu;
    @FXML
    private TableColumn<CurrentTable, String> cbi;
    @FXML
    private TableColumn<CurrentTable, String> cbn;
    @FXML
    private TableColumn<CurrentTable, String> cdta;
    @FXML
    private TableColumn<CurrentTable, String> cdts;

    @FXML
    private TableView<HistoryTable> historyTable;
    @FXML
    private TableColumn<HistoryTable, String> hu;
    @FXML
    private TableColumn<HistoryTable, String> hbi;
    @FXML
    private TableColumn<HistoryTable, String> hbn;
    @FXML
    private TableColumn<HistoryTable, String> hdta;
    @FXML
    private TableColumn<HistoryTable, String> hdts;
    @FXML
    private GridPane bookAvailabilityPanel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        System.out.println("Getting Username...!");
        Path p=Path.of("Data\\file.information.user");
        try {
            realUsername= Files.readString(p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("User : "+realUsername);
        usernameLabel.setText(realUsername);

        DateTimeFormatter fmt=DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDateTime dt=LocalDateTime.now();

        LocalDateTime mDate=dt.minusMonths(1);
        System.out.println("Date & Time of Login : "+fmt.format(dt));
        dateTimeLogin.setText(fmt.format(dt));
        System.out.println("Date & Time of 1 month before : "+fmt.format(mDate));

        // Current Status Table Creation - - - - -
        cu.setCellValueFactory(new PropertyValueFactory<CurrentTable, String>("cu"));
        cbi.setCellValueFactory(new PropertyValueFactory<CurrentTable, String>("cbi"));
        cbn.setCellValueFactory(new PropertyValueFactory<CurrentTable, String>("cbn"));
        cdta.setCellValueFactory(new PropertyValueFactory<CurrentTable, String>("cdta"));
        cdts.setCellValueFactory(new PropertyValueFactory<CurrentTable, String>("cdts"));
        ObservableList<CurrentTable> currentList=FXCollections.observableArrayList();
        try {
            ResultSet rs=con.prepareStatement("SELECT `username`,`book_id`,`name_of_book`,`date_of_adept`,`date_of_submit` FROM `issue_book` WHERE `username`='"+realUsername+"' AND `date_of_adept`>='"+fmt.format(mDate)+"'").executeQuery();
            while(rs.next()){
                currentList.add(new CurrentTable(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }
            currentStatusTable.setItems(currentList);
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }

        // History Table Creation - - - - -
        hu.setCellValueFactory(new PropertyValueFactory<HistoryTable, String>("hu"));
        hbi.setCellValueFactory(new PropertyValueFactory<HistoryTable, String>("hbi"));
        hbn.setCellValueFactory(new PropertyValueFactory<HistoryTable, String>("hbn"));
        hdta.setCellValueFactory(new PropertyValueFactory<HistoryTable, String>("hdta"));
        hdts.setCellValueFactory(new PropertyValueFactory<HistoryTable, String>("hdts"));
        ObservableList<HistoryTable> historyList= FXCollections.observableArrayList();
        try {
            ResultSet rs=con.prepareStatement("SELECT `username`,`book_id`,`name_of_book`,`date_of_adept`,`date_of_submit` FROM `issue_book` WHERE `username`='"+realUsername+"'").executeQuery();
            while(rs.next()){
                historyList.add(new HistoryTable(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }
            historyTable.setItems(historyList);
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    public void currentStatusTable(){
        currentStatusTable.setDisable(false);
        currentStatusTable.setVisible(true);
        currentStatusButton.setId("anotherButton");

        bookAvailabilityPanel.setDisable(true);
        bookAvailabilityPanel.setVisible(false);
        bookAvailabilityButton.setId("LIbutton");

        historyTable.setDisable(true);
        historyTable.setVisible(false);
        historyButton.setId("LIbutton");
    }
    public void bookAvailabilityTable(){
        bookAvailabilityPanel.setDisable(false);
        bookAvailabilityPanel.setVisible(true);
        bookAvailabilityButton.setId("anotherButton");

        currentStatusTable.setDisable(true);
        currentStatusTable.setVisible(false);
        currentStatusButton.setId("LIbutton");

        historyTable.setDisable(true);
        historyTable.setVisible(false);
        historyButton.setId("LIbutton");
    }
    public void searchByID()throws Exception{
        if(bookIDField.getText().equals("")){
            bookIDField.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        }
        else{
            bookNameField.setText(null);
            bookNameField.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
            ResultSet rs = con.prepareStatement("SELECT `Book_ID`,`Name_of_Book`,`Quantity`,`Available` FROM `books` WHERE `Book_ID`='" + Integer.parseInt(bookIDField.getText()) + "';").executeQuery();
            String book_id = null;
            while (rs.next()){
                System.out.println(rs.getString(1));
                book_id= rs.getString(1);
                if(book_id==null){
                    Alert a=new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Invalid Book ID");
                    a.setHeaderText(null);
                    a.setContentText("Book ID: '"+bookIDField.getText()+"' is invalid.");
                    a.show();
                }
                else {
                    bookIDLabel.setText(rs.getString(1));
                    bookNameLabel.setText(rs.getString(2));
                    bookQuantityLabel.setText(rs.getString(3));
                    bookAvailabilityLabel.setText(rs.getString(4));
                }
            }
        }
    }
    public void searchByName() throws SQLException {
        if(bookNameField.getText().equals("")){
            bookNameField.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        }
        else{
            bookIDField.setText(null);
            bookIDField.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
            ResultSet rs=con.prepareStatement("SELECT `Book_ID`,`Name_of_Book`,`Quantity`,`Available` FROM `books` WHERE `Name_of_Book`='"+bookNameField.getText()+"';").executeQuery();
            String name=null;
            while (rs.next()) {
                name=rs.getString(2);
                System.out.println(name);
                if(name==null){
                    Alert a=new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Invalid Book Name");
                    a.setHeaderText(null);
                    a.setContentText("Book name: '"+bookNameField.getText()+"' is invalid.");
                    a.show();
                }
                else {
                    bookIDLabel.setText(rs.getString(1));
                    bookNameLabel.setText(rs.getString(2));
                    bookQuantityLabel.setText(rs.getString(3));
                    bookAvailabilityLabel.setText(rs.getString(4));
                }
            }
        }
    }
    public void historyTable(){
        historyTable.setDisable(false);
        historyTable.setVisible(true);
        historyButton.setId("anotherButton");

        bookAvailabilityPanel.setDisable(true);
        bookAvailabilityPanel.setVisible(false);
        bookAvailabilityButton.setId("LIbutton");

        currentStatusTable.setDisable(true);
        currentStatusTable.setVisible(false);
        currentStatusButton.setId("LIbutton");
    }
    public void signOut(){
        FileWriter fw= null;
        try {
            fw = new FileWriter("Data\\file.information.user");
        fw.write("");
        System.out.println("file create true...!");
        fw.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Exit...!");
        Stage s= (Stage) signOutButton.getScene().getWindow();
        s.close();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main_fxml.fxml"));
        Stage stage=new Stage();
        stage.setResizable(false);
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.getIcons().add(new Image("file:Data\\file.information.view"));
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        stage.setTitle("Lib World");
        stage.setScene(scene);
        stage.show();
    }
}
