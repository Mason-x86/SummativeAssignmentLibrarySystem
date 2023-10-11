package com.example;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.IOException;


public class Home extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        //Declare Widgets
        Button home_button = new Button();
        Button sign_in_button = new Button();
        Button search_button = new Button();
        Button account_button = new Button();
        Button loans_button = new Button();
        // enter button properties
        home_button.setTranslateX(0);
        home_button.setTranslateY(0);
        home_button.setMinWidth(600);
        sign_in_button.setTranslateX(0);
        sign_in_button.setTranslateY(25);
        sign_in_button.setMinWidth(300);
        sign_in_button.setText("Sign In");
        search_button.setTranslateX(300);
        search_button.setTranslateY(25);
        search_button.setMinWidth(300);
        search_button.setText("Search Library");

        Customer customer = new Customer("tap to sign in", "",
                "", "", 0, 0);

        Librarian librarian = new Librarian("not signed in", "",
                "", "", 0, 0);

        home_button.setText(customer.get_first_name());
        Group root = new Group(home_button, sign_in_button, search_button);
        Scene scene = new Scene(root, 600, 300);
        SignIn sign_in_scene = new SignIn();
        Search search_scene = new Search();
        CreateAccount create_account_scene = new CreateAccount();
        Account account_scene = new Account();
        ItemView item_view_scene = new ItemView();
        Loans loan_scene = new Loans();
        SceneManager scene_manager = new SceneManager(scene, sign_in_scene, search_scene,
                create_account_scene, account_scene, item_view_scene, loan_scene);

        stage.setTitle("Comic and Movie Library Rentals!");
        stage.setScene(scene_manager.get_main_scene());
        stage.show();

        sign_in_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("select sign in page");
                sign_in_scene.setAccountScene(stage, scene_manager);
            }
        });

        search_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("select search page");
                scene_manager.get_search_scene().setAccountScene(stage, scene_manager);
            }
        });

        loans_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("select search page");
                scene_manager.get_loan_scene().setAccountScene(stage, scene_manager);
            }
        });

        account_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("select search page");
                scene_manager.get_account_scene().setAccountScene(stage, scene_manager);
            }
        });

        home_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (sign_in_scene.getCustomer().get_first_name() != "not signed in") {

                    // reset properties
                    home_button.setText("Hello " + scene_manager.get_sign_in_scene().getCustomer().get_first_name());
                    account_button.setTranslateX(0);
                    account_button.setTranslateY(25);
                    account_button.setMinWidth(300);
                    account_button.setText("Account");
                    loans_button.setTranslateX(0);
                    loans_button.setTranslateY(50);
                    loans_button.setMinWidth(300);
                    loans_button.setText("Loans");
                    Group root = new Group(home_button,loans_button, account_button, search_button);
                    Scene scene = new Scene(root, 600, 300);
                    scene_manager.update_main_scene(scene);
                    System.out.println(home_button);
                    stage.setScene(scene);
                    stage.show();
                }
                else{
                    System.out.println("select account page");
                    sign_in_scene.setAccountScene(stage, scene_manager);

                }

            }
        });

    }
    public static void main (String[]args){
            launch();
    }



}