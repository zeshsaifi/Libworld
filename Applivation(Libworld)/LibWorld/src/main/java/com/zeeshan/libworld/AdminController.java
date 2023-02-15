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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    private Connection con=DatabaseConnection.Data();
    @FXML
    private Label dateTimeLogin;
    @FXML
    private Button booksButton,userDataButton,userAddButton,issueListButton,allUserButton,listBooksButton,signOutButton;
    @FXML
    private GridPane booksPanel,userDataPanel,issueListPanel,allUserPanel,listBookPanel;
    @FXML
    private TextField bBookID,bBookName,bQuantity,bAvailability,uUsername,uBookID,uBookName,findByUsername,findByEmail,findByIDField,findByNameField;
    @FXML
    private DatePicker uDateTimeOfAdept,uDateTimeOfSubmit;
    @FXML
    private TableView<AdminBookTable> listBookTable;
    @FXML
    private TableColumn<AdminBookTable,String> abi,abn,aq,aa;
    @FXML
    private TableView<AdminUserTable> allUserTable;
    @FXML
    private TableColumn<AdminUserTable,String> uu,ue;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateTimeFormatter fmt=DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDateTime dt=LocalDateTime.now();
        System.out.println("Date & Time of Login : "+fmt.format(dt));
        dateTimeLogin.setText(fmt.format(dt));

        hu.setCellValueFactory(new PropertyValueFactory<HistoryTable, String>("hu"));
        hbi.setCellValueFactory(new PropertyValueFactory<HistoryTable, String>("hbi"));
        hbn.setCellValueFactory(new PropertyValueFactory<HistoryTable, String>("hbn"));
        hdta.setCellValueFactory(new PropertyValueFactory<HistoryTable, String>("hdta"));
        hdts.setCellValueFactory(new PropertyValueFactory<HistoryTable, String>("hdts"));
        ObservableList<HistoryTable> historyList= FXCollections.observableArrayList();
        try {
            ResultSet rs=con.prepareStatement("SELECT `username`,`book_id`,`name_of_book`,`date_of_adept`,`date_of_submit` FROM `issue_book` WHERE 1").executeQuery();
            while(rs.next()){
                historyList.add(new HistoryTable(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }
            historyTable.setItems(historyList);
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    // - - - - - Books Actions - - - - -
    public void Books(){
        booksButton.setId("anotherButton");

        userDataButton.setId("LIbutton");
        allUserButton.setId("LIbutton");
        listBooksButton.setId("LIbutton");
        issueListButton.setId("LIbutton");

        booksPanel.setDisable(false);
        booksPanel.setVisible(true);

        userDataPanel.setDisable(true);
        userDataPanel.setVisible(false);
        issueListPanel.setDisable(true);
        issueListPanel.setVisible(false);
        allUserPanel.setDisable(true);
        allUserPanel.setVisible(false);
        listBookPanel.setDisable(true);
        listBookPanel.setVisible(false);
    }
    public void addBook(){
        if(bBookID.getText().equals("")){
            bBookID.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        } else if (bBookName.getText().equals("")) {
            bBookName.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        } else if (bQuantity.getText().equals("")) {
            bQuantity.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        } else if (bAvailability.getText().equals("")) {
            bAvailability.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        } else{
            bBookID.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
            bBookName.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
            bQuantity.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
            bAvailability.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");

            try {
                Statement stmt = con.createStatement();
                stmt.execute("INSERT INTO `books`(`Book_ID`, `Name_of_Book`, `Quantity`, `Available`) VALUES ('"+bBookID.getText()+"','"+bBookName.getText()+"','"+bQuantity.getText()+"','"+bAvailability.getText()+"')");
                Alert a=new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Book Added");
                a.setContentText("Book ID: '"+bBookID.getText()+"' has been added...!");
                a.setHeaderText(null);
                a.show();
                bBookID.setText("");
                bBookName.setText("");
                bQuantity.setText("");
                bAvailability.setText("");
            }
            catch (Exception e){
                Alert a=new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Invalid Book ID");
                a.setContentText("Book ID: '"+bBookID.getText()+"' already enrolled...!");
                a.setHeaderText(null);
                a.show();
                System.out.println(e);
            }
        }
    }
    public void deleteBook(){
        if(bBookID.getText().equals("")){
            bBookID.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Book?");
            alert.setHeaderText("");
            alert.setContentText("Are you sure for delete Book ID: " + bBookID.getText() + "?");
            if (alert.showAndWait().get().equals(ButtonType.OK)) {
                bBookID.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
                try {
                    Statement stmt = con.createStatement();
                    stmt.execute("DELETE FROM `books` WHERE `Book_ID`='" + bBookID.getText() + "'");
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Book Deleted");
                    a.setContentText("Book ID: '" + bBookID.getText() + "' has been deleted...!");
                    a.setHeaderText(null);
                    a.show();
                    bBookID.setText("");
                } catch (Exception e) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Invalid Book ID");
                    a.setContentText("Book ID: '" + bBookID.getText() + "' not found...!");
                    a.setHeaderText(null);
                    a.show();
                    System.out.println(e);
                }
            }
            else
            {
                System.out.println("Do not 'Delete Book'...!");
            }
        }
    }
    public void updateBook(){
        if(bBookID.getText().equals("")){
            bBookID.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        } else if (bBookName.getText().equals("")) {
            bBookName.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        } else if (bQuantity.getText().equals("")) {
            bQuantity.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        } else if (bAvailability.getText().equals("")) {
            bAvailability.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        } else{
            bBookID.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
            bBookName.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
            bQuantity.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
            bAvailability.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");

            try {
                Statement stmt = con.createStatement();
                stmt.execute("UPDATE `books` SET `Name_of_Book`='"+bBookName.getText()+"',`Quantity`='"+bQuantity.getText()+"',`Available`='"+bAvailability.getText()+"' WHERE `Book_ID`='"+bBookID.getText()+"';");
                Alert a=new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Book Updated");
                a.setContentText("Book ID: '"+bBookID.getText()+"' has been updated...!");
                a.setHeaderText(null);
                a.show();
                bBookID.setText("");
                bBookName.setText("");
                bQuantity.setText("");
                bAvailability.setText("");
            }
            catch (Exception e){
                Alert a=new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Invalid Book ID");
                a.setContentText("Book ID: '"+bBookID.getText()+"' not found...!");
                a.setHeaderText(null);
                a.show();
                System.out.println(e);
            }
        }
    }

    // - - - - - Issue Book Actions - - - - -
    public void issueBook(){
        userDataButton.setId("anotherButton");

        booksButton.setId("LIbutton");
        allUserButton.setId("LIbutton");
        listBooksButton.setId("LIbutton");
        issueListButton.setId("LIbutton");

        userDataPanel.setDisable(false);
        userDataPanel.setVisible(true);

        booksPanel.setDisable(true);
        booksPanel.setVisible(false);
        issueListPanel.setDisable(true);
        issueListPanel.setVisible(false);
        allUserPanel.setDisable(true);
        allUserPanel.setVisible(false);
        listBookPanel.setDisable(true);
        listBookPanel.setVisible(false);
    }
    public void userCheck(){
        if(uUsername.getText().equals("")){
            uUsername.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        }
        else{
            uUsername.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
            try{
                ResultSet rs=con.prepareStatement("select `username` from `users` where `username`='"+uUsername.getText()+"'").executeQuery();
                String user=null;
                while(rs.next()){
                    user=rs.getString(1);
                }

                if(user==null){
                    Alert a=new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Invalid Username");
                    a.setContentText("There is no user for Username: "+uUsername.getText()+".");
                    a.setHeaderText(null);
                    a.show();
                } else{
                    uBookID.setDisable(false);
                }

            }catch (Exception e){
                System.out.println(e);
            }
        }
    }
    public void bookCheck(){
        if(uBookID.getText().equals("")){
            uBookID.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        }
        else{
            uBookID.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
            try{
                ResultSet rs=con.prepareStatement("select `Book_ID`,`Name_of_Book` from `books` where `Book_ID`='"+uBookID.getText()+"'").executeQuery();
                String bID=null,bName=null;
                while(rs.next()){
                    bID=rs.getString(1);
                    bName=rs.getString(2);
                }

                if(bID==null){
                    Alert a=new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Invalid Book ID");
                    a.setContentText("There is no book for Book ID: "+uBookID.getText()+".");
                    a.setHeaderText(null);
                    a.show();
                } else{
                    uBookName.setText(bName);
                    uDateTimeOfAdept.setEditable(true);
                    uDateTimeOfSubmit.setEditable(true);
                    userAddButton.setDisable(false);
                }

            }catch (Exception e){
                System.out.println(e);
            }
        }
    }
    public void userAddData(){
        uUsername.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
        uBookID.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
        uBookName.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
        uDateTimeOfAdept.setStyle("-fx-background-color:#00b8f1;-fx-text-fill:#00b8f1;");
        uDateTimeOfSubmit.setStyle("-fx-background-color:#00b8f1;-fx-text-fill:#00b8f1;");

        if(uUsername.getText().equals("")){
            uUsername.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        }else if(uBookID.getText().equals("")){
            uBookID.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        }else if(uBookName.getText().equals("")){
            uBookName.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        }else if(uDateTimeOfAdept.getValue()==null){
            uDateTimeOfAdept.setStyle("-fx-background-color:red;-fx-text-fill:red;");
        }else if(uDateTimeOfSubmit.getValue()==null){
            uDateTimeOfSubmit.setStyle("-fx-background-color:red;-fx-text-fill:red;");
        }else{
            uUsername.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
            uBookID.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
            uBookName.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
            uDateTimeOfAdept.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
            uDateTimeOfSubmit.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");

            try{
                Statement stmt=con.createStatement();
                stmt.execute("INSERT INTO `issue_book`(`username`, `book_id`, `name_of_book`, `date_of_adept`, `date_of_submit`) VALUES ('"+uUsername.getText()+"','"+uBookID.getText()+"','"+uBookName.getText()+"','"+uDateTimeOfAdept.getValue()+"','"+uDateTimeOfSubmit.getValue()+"')");
                Alert a=new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Data Added!");
                a.setContentText("Data added on username: "+uUsername.getText()+"...!");
                a.setHeaderText(null);
                a.show();

                uBookID.setText(null);
                uBookName.setText(null);
                uDateTimeOfAdept.setValue(null);
                uDateTimeOfSubmit.setValue(null);

                uDateTimeOfAdept.setEditable(false);
                uDateTimeOfSubmit.setEditable(false);
                userAddButton.setDisable(true);
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
    }
    // - - - - - Issue List Actions - - - - -
    public void issueList(){
        issueListButton.setId("anotherButton");

        userDataButton.setId("LIbutton");
        allUserButton.setId("LIbutton");
        booksButton.setId("LIbutton");
        listBookPanel.setId("LIbutton");

        issueListPanel.setDisable(false);
        issueListPanel.setVisible(true);

        userDataPanel.setDisable(true);
        userDataPanel.setVisible(false);
        listBookPanel.setDisable(true);
        listBookPanel.setVisible(false);
        allUserPanel.setDisable(true);
        allUserPanel.setVisible(false);
        booksPanel.setDisable(true);
        booksPanel.setVisible(false);
    }
    // - - - - - All Users Actions - - - - -
    public void Alluser(){
        allUserButton.setId("anotherButton");

        userDataButton.setId("LIbutton");
        booksButton.setId("LIbutton");
        listBooksButton.setId("LIbutton");
        issueListButton.setId("LIbutton");

        allUserPanel.setDisable(false);
        allUserPanel.setVisible(true);

        userDataPanel.setDisable(true);
        userDataPanel.setVisible(false);
        booksPanel.setDisable(true);
        booksPanel.setVisible(false);
        issueListPanel.setDisable(true);
        issueListPanel.setVisible(false);
        listBookPanel.setDisable(true);
        listBookPanel.setVisible(false);
    }
    public void findByUsername(){
        if(findByUsername.getText().equals("")){
            findByUsername.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        }
        else {
            findByUsername.setStyle("-fx-border-color:green;-fx-text-fill:green;");
            findByEmail.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
            findByEmail.setText("");
            uu.setCellValueFactory(new PropertyValueFactory<AdminUserTable,String>("uu"));
            ue.setCellValueFactory(new PropertyValueFactory<AdminUserTable,String>("ue"));
            ObservableList<AdminUserTable> userList= FXCollections.observableArrayList();
            try {
                ResultSet rs=con.prepareStatement("SELECT `Username`,`Email` FROM `users` where `Username`='"+findByUsername.getText()+"';").executeQuery();
                while(rs.next()){
                    userList.add(new AdminUserTable(rs.getString(1),rs.getString(2)));
                }
                allUserTable.setItems(userList);
            }
            catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }
    public void findByEmail(){
        if(findByEmail.getText().equals("")){
            findByEmail.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        }
        else {
            findByEmail.setStyle("-fx-border-color:green;-fx-text-fill:green;");
            findByUsername.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
            findByUsername.setText("");
            uu.setCellValueFactory(new PropertyValueFactory<AdminUserTable,String>("uu"));
            ue.setCellValueFactory(new PropertyValueFactory<AdminUserTable,String>("ue"));
            ObservableList<AdminUserTable> userList= FXCollections.observableArrayList();
            try {
                ResultSet rs=con.prepareStatement("SELECT `Username`,`Email` FROM `users` where `Email`='"+findByEmail.getText()+"';").executeQuery();
                while(rs.next()){
                    userList.add(new AdminUserTable(rs.getString(1),rs.getString(2)));
                }
                allUserTable.setItems(userList);
            }
            catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }
    public void allUserList(){
        findByUsername.setText("");
        findByEmail.setText("");
        findByUsername.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
        findByEmail.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
        uu.setCellValueFactory(new PropertyValueFactory<AdminUserTable,String>("uu"));
        ue.setCellValueFactory(new PropertyValueFactory<AdminUserTable,String>("ue"));
        ObservableList<AdminUserTable> userList= FXCollections.observableArrayList();
        try {
            ResultSet rs=con.prepareStatement("SELECT `Username`,`Email` FROM `users`;").executeQuery();
            while(rs.next()){
                userList.add(new AdminUserTable(rs.getString(1),rs.getString(2)));
            }
            allUserTable.setItems(userList);
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    // - - - - - List Books Actions - - - - -
    public void Allbooks(){
        listBooksButton.setId("anotherButton");

        userDataButton.setId("LIbutton");
        allUserButton.setId("LIbutton");
        booksButton.setId("LIbutton");
        issueListButton.setId("LIbutton");

        listBookPanel.setDisable(false);
        listBookPanel.setVisible(true);

        userDataPanel.setDisable(true);
        userDataPanel.setVisible(false);
        issueListPanel.setDisable(true);
        issueListPanel.setVisible(false);
        allUserPanel.setDisable(true);
        allUserPanel.setVisible(false);
        booksPanel.setDisable(true);
        booksPanel.setVisible(false);
    }
    public void findByID(){
        if(findByIDField.getText().equals("")){
            findByIDField.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        }
        else {
            findByIDField.setStyle("-fx-border-color:green;-fx-text-fill:green;");
            findByNameField.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
            findByNameField.setText("");
            abi.setCellValueFactory(new PropertyValueFactory<AdminBookTable, String>("abi"));
            abn.setCellValueFactory(new PropertyValueFactory<AdminBookTable, String>("abn"));
            aq.setCellValueFactory(new PropertyValueFactory<AdminBookTable, String>("aq"));
            aa.setCellValueFactory(new PropertyValueFactory<AdminBookTable, String>("aa"));
            ObservableList<AdminBookTable> bookList = FXCollections.observableArrayList();
            try {
                ResultSet rs = con.prepareStatement("SELECT `Book_ID`,`Name_of_Book`,`Quantity`,`Available` FROM `books` where `Book_ID`='" + findByIDField.getText() + "';").executeQuery();
                while (rs.next()) {
                    bookList.add(new AdminBookTable(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                }
                listBookTable.setItems(bookList);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }
    public void findByName(){
        if(findByNameField.getText().equals("")){
            findByNameField.setStyle("-fx-border-color:red;-fx-text-fill:red;");
        }
        else {
            findByNameField.setStyle("-fx-border-color:green;-fx-text-fill:green;");
            findByIDField.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
            findByIDField.setText("");
            findByIDField.setId("field");
            abi.setCellValueFactory(new PropertyValueFactory<AdminBookTable, String>("abi"));
            abn.setCellValueFactory(new PropertyValueFactory<AdminBookTable, String>("abn"));
            aq.setCellValueFactory(new PropertyValueFactory<AdminBookTable, String>("aq"));
            aa.setCellValueFactory(new PropertyValueFactory<AdminBookTable, String>("aa"));
            ObservableList<AdminBookTable> bookList = FXCollections.observableArrayList();
            try {
                ResultSet rs = con.prepareStatement("SELECT `Book_ID`,`Name_of_Book`,`Quantity`,`Available` FROM `books` where `Name_of_Book`='" + findByNameField.getText() + "';").executeQuery();
                while (rs.next()) {
                    bookList.add(new AdminBookTable(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                }
                listBookTable.setItems(bookList);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }
    public void listBook(){
        findByIDField.setText("");
        findByNameField.setText("");
        findByIDField.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
        findByNameField.setStyle("-fx-border-color:#00b8f1;-fx-text-fill:#00b8f1;");
        abi.setCellValueFactory(new PropertyValueFactory<AdminBookTable,String>("abi"));
        abn.setCellValueFactory(new PropertyValueFactory<AdminBookTable,String>("abn"));
        aq.setCellValueFactory(new PropertyValueFactory<AdminBookTable,String>("aq"));
        aa.setCellValueFactory(new PropertyValueFactory<AdminBookTable,String>("aa"));
        ObservableList<AdminBookTable> bookList= FXCollections.observableArrayList();
        try {
            ResultSet rs=con.prepareStatement("SELECT `Book_ID`,`Name_of_Book`,`Quantity`,`Available` FROM `books`;").executeQuery();
            while(rs.next()){
                bookList.add(new AdminBookTable(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
            listBookTable.setItems(bookList);
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
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
