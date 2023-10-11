package com.example;
import javafx.scene.Scene;

public class SceneManager {
    private Scene main_scene;
    private Search search_scene;
    private SignIn sign_in_scene;
    private CreateAccount create_account_scene;
    private Loans loan_scene;
    private ItemView item_view_scene;
    private Account account_scene;



    public SceneManager(Scene new_main_scene, SignIn new_sign_in_scene, Search new_search_scene,
                        CreateAccount new_create_account_scene, Account _account_scene, ItemView _item_view_scene,
                        Loans _loan_scene){
        main_scene = new_main_scene;
        sign_in_scene = new_sign_in_scene;
        search_scene = new_search_scene;
        create_account_scene = new_create_account_scene;
        account_scene = _account_scene;
        item_view_scene = _item_view_scene;
        loan_scene = _loan_scene;
    }

    public void update_main_scene(Scene _main_scene){
        main_scene = _main_scene;
    }

    public void update_account_scene(Account new_account){account_scene = new_account;}

    public Scene get_main_scene() {
        return main_scene;
    }

    public Search get_search_scene(){
        return search_scene;
    }

    public SignIn get_sign_in_scene(){
        return sign_in_scene;
    }

    public CreateAccount get_create_account_scene(){
        return create_account_scene;
    }

    public Loans get_loan_scene(){
        return loan_scene;
    }

    public ItemView get_item_view(){
        return item_view_scene;
    }

    public Account get_account_scene(){
        return account_scene;
    }

}
