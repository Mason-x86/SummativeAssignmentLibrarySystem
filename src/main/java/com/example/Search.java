package com.example;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.sql.*;


public class Search {
    ArrayList<Movie> movies;
    ArrayList<Comic> comics;
    public void setAccountScene(Stage stage, SceneManager scene_manager){
        //Declare Widgets
        Button home_button = new Button();
        Button search_button = new Button();
        Button[] movie_buttons;
        Button[] comic_buttons;

        movie_buttons = new Button[4];
        comic_buttons = new Button[4];

        for (int i = 0; i < 4; i++){
            movie_buttons[i] = new Button();
        }
        for (int i = 0; i < 4; i++){
            comic_buttons[i] = new Button();
        }

        //Declare Text Field
        TextField search_text_field = new TextField();

        // enter button properties
        home_button.setTranslateX(0);
        home_button.setTranslateY(0);
        home_button.setMinWidth(600);
        home_button.setText("Home");

        search_button.setTranslateX(0);
        search_button.setTranslateY(50);
        search_button.setMinWidth(600);
        search_button.setText("Search");

        // enter text field
        search_text_field.setTranslateX(0);
        search_text_field.setTranslateY(25);
        search_text_field.setMinWidth(600);
        search_text_field.setText("Search Title");

        Group root = new Group(home_button, search_button, search_text_field);
        Scene scene = new Scene(root, 600, 300);
        stage.setTitle("Comic and Movie Library Search!");
        stage.setScene(scene);
        stage.show();
        String keyword = search_text_field.getText();
        movies = get_movies_item_list("");
        comics = get_comics_item_list("");

        movie_buttons[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("movie");
                System.out.println(movies.get(0).get_name());
                System.out.println("select search page");
                scene_manager.get_item_view().set_account_scene_movie(stage, scene_manager, movies.get(0));

            }});

        movie_buttons[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("movie");
                System.out.println(movie_buttons[1].getText());
                scene_manager.get_item_view().set_account_scene_movie(stage, scene_manager, movies.get(1));
            }});

        movie_buttons[2].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("movie");
                System.out.println(movie_buttons[2].getText());
            }});

        movie_buttons[3].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("movie");
                System.out.println(movie_buttons[3].getText());
            }});

        comic_buttons[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("comic");
                System.out.println(comic_buttons[3].getText());
                scene_manager.get_item_view().set_account_scene_comic(stage, scene_manager, comics.get(0));
            }});
        comic_buttons[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("movie");
                System.out.println(comic_buttons[3].getText());
                scene_manager.get_item_view().set_account_scene_comic(stage, scene_manager, comics.get(1));
            }});
        comic_buttons[3].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("movie");
                System.out.println(comic_buttons[3].getText());
            }});

        home_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setTitle("Comic and Movie Library Home!");
                System.out.println("main scene");
                stage.setScene(scene_manager.get_main_scene());
            }
        });

        search_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setTitle("gathering item list");

                Group root = new Group(home_button, search_button, search_text_field);
                stage.setTitle("Comic and Movie Library Search! + 1");
                stage.setScene(scene);
                stage.show();
                String keyword = search_text_field.getText();
                movies = get_movies_item_list(keyword);
                System.out.println("movies");
                for (int i = 0; i < movies.size(); i++){

                    movie_buttons[i].setText(movies.get(i).get_name());
                    movie_buttons[i].setTranslateX(0);
                    if (i != 0){
                        movie_buttons[i].setTranslateY(75 + i*25);
                    }else
                    {
                        movie_buttons[i].setTranslateY(75);
                    }

                    root.getChildren().add(movie_buttons[i]);
                    System.out.println(movies.get(i).get_name());
                }
                comics = get_comics_item_list(keyword);
                for (int i = 0; i < comics.size(); i++){

                    comic_buttons[i].setText(comics.get(i).get_name());
                    comic_buttons[i].setTranslateX(0);

                    if (i != 0){
                        comic_buttons[i].setTranslateY(75 + i*25 + movies.size() * 25);
                    }else
                    {
                        comic_buttons[i].setTranslateY(75  + movies.size() * 25);
                    }

                    root.getChildren().add(comic_buttons[i]);
                }
                Scene scene = new Scene(root, 600, 300);
                stage.setScene(scene);
                stage.show();

            }
        });

    }

    public ArrayList<Movie> get_movies_item_list(String keyword){
        String url = "jdbc:mysql://localhost:3306/librarydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // location of database – update for your own implementation
        String username = "root"; // username for database – update for your own implementation
        String password = "root"; // password for database – update for your own implementation
        Customer customer = new Customer("not signed in", "", "", "", 0, 0);
        Connection connection = null;
        movies = new ArrayList<Movie>();
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url, username, password);

            PreparedStatement statement =
                    connection.prepareStatement("select * from movies where movies.name like ? escape '!';");
            System.out.println();

            statement.setString(1, "%"+ keyword + "%");
            System.out.println( "KEYWORD:" + keyword);
            resultSet = statement.executeQuery();
            System.out.println();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String summary = resultSet.getString("description");
                String directors = resultSet.getString("directors");
                String studio = resultSet.getString("studio");
                String rls = resultSet.getString("release");
                int price = resultSet.getInt("price");
                int instock = resultSet.getInt("instock");
                int id = resultSet.getInt("idMovies");
                System.out.println("name:" + resultSet.getString("name"));
                Movie temp_movie = new Movie(name, summary, directors, studio, rls, price, instock, id);
                movies.add(temp_movie);

            }

        } catch (SQLException ex) {
            System.out.println("SQL exception discovered");
            System.out.println(ex);
        }

        return movies;
    }
    public ArrayList<Comic> get_comics_item_list(String keyword){
        String url = "jdbc:mysql://localhost:3306/librarydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // location of database – update for your own implementation
        String username = "root"; // username for database – update for your own implementation
        String password = "root"; // password for database – update for your own implementation
        Customer customer = new Customer("not signed in", "", "", "", 0, 0);
        Connection connection = null;
        comics = new ArrayList<Comic>();
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url, username, password);

            PreparedStatement statement =
                    connection.prepareStatement("select * from comics where comics.name like ? escape '!';");
            System.out.println();

            statement.setString(1, "%"+ keyword + "%");
            System.out.println( "KEYWORD:" + keyword);
            resultSet = statement.executeQuery();
            System.out.println();
            while (resultSet.next()) {

                String name = resultSet.getString("name");
                String summary = resultSet.getString("description");
                String first_name = resultSet.getString("authorFirstName");
                String last_name = resultSet.getString("authorLastName");
                String serial = resultSet.getString("serial");
                String publisher = resultSet.getString("publisher");
                int price = resultSet.getInt("price");
                int instock = resultSet.getInt("instock");
                int id = resultSet.getInt("idComics");
                System.out.println("name:" + resultSet.getString("name"));
                Comic temp_comic = new Comic(name, summary, first_name, last_name, serial, publisher, price, instock, id);
                comics.add(temp_comic);
            }

        } catch (SQLException ex) {
            System.out.println("SQL exception discovered");
            System.out.println(ex);
        }

        return comics;
    }
}
