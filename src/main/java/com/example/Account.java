package com.example;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.*;

public class Account {
    public Account(){

    }
    public void setAccountScene(Stage stage, SceneManager scene_manager){
        //Declare Widgets
        Button home_button = new Button();
        Button commit_button = new Button();
        Label email_label = new Label();
        Label first_name = new Label();
        Label last_name = new Label();
        Label credit = new Label();
        TextField credit_text_field = new TextField();
        // enter button properties
        home_button.setTranslateX(0);
        home_button.setTranslateY(0);
        home_button.setMinWidth(600);
        home_button.setText("Home");
        commit_button.setTranslateX(0);
        commit_button.setTranslateY(150);
        commit_button.setMinWidth(600);
        commit_button.setText("Add credit to account");
        // enter label properties
        email_label.setTranslateX(0);
        email_label.setTranslateY(25);
        email_label.setMinWidth(300);
        email_label.setText("email: " + scene_manager.get_sign_in_scene().getCustomer().get_email());
        first_name.setTranslateX(0);
        first_name.setTranslateY(50);
        first_name.setMinWidth(300);
        first_name.setText("First Name: " + scene_manager.get_sign_in_scene().getCustomer().get_first_name());
        last_name.setTranslateX(0);
        last_name.setTranslateY(75);
        last_name.setMinWidth(300);
        last_name.setText("Last Name: " + scene_manager.get_sign_in_scene().getCustomer().get_last_name());
        credit.setTranslateX(0);
        credit.setTranslateY(100);
        credit.setMinWidth(300);
        credit.setText("Credit: " + Integer.toString( scene_manager.get_sign_in_scene().getCustomer().return_credit()));

        // enter text field properties
        credit_text_field.setTranslateX(300);
        credit_text_field.setTranslateY(100);
        credit_text_field.setMinWidth(300);
        credit_text_field.setText(String.valueOf(scene_manager.get_sign_in_scene().getCustomer().return_credit()));

        Group root = new Group(home_button, email_label,
                first_name, last_name, credit_text_field, credit, commit_button);

        Scene scene = new Scene(root, 600, 300);
        stage.setTitle("Comic and Movie Library Create Account!");
        stage.setScene(scene);
        stage.show();

        home_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setTitle("Comic and Movie Library Home!");
                System.out.println("main scene");
                stage.setScene(scene_manager.get_main_scene());
            }
        });

        commit_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    add_credit(Integer.parseInt(credit_text_field.getText()), scene_manager.get_sign_in_scene().getCustomer().return_id());
                    scene_manager.get_sign_in_scene().getCustomer().change_credit(scene_manager.get_sign_in_scene().getCustomer().return_credit()
                            + Integer.parseInt(credit_text_field.getText()));
                }catch(Exception e){

                }
                Account new_account_scene = new Account();
                scene_manager.get_account_scene().setAccountScene(stage, scene_manager);
            }
        });
    }
    static void add_credit(int added, int customer_id){
        String url = "jdbc:mysql://localhost:3306/librarydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // location of database – update for your own implementation
        String db_username = "root"; // username for database – update for your own implementation
        String db_password = "root"; // password for database – update for your own implementation
        Connection connection = null;

        try{
            connection = DriverManager.getConnection(url, db_username, db_password);
            String sql = "UPDATE customers SET customers.Credit = customers.Credit + ? WHERE customers.idCustomers = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, added);
            pstmt.setInt(2, customer_id);
            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1)
            {

                int idCustomers = 0;
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next())
                    idCustomers = rs.getInt(1);
            }
        }catch (SQLException sqlE)
        {
            System.out.println("failure to connect db");
            System.out.println(sqlE.toString());
        }
        finally
        {
            // close connection
            System.out.println("Closing connection...");
            if(connection != null)
            {
                try
                {
                    connection.close();
                }
                catch (SQLException ignore)
                {}
                connection = null;
            }
        }
    }
}
