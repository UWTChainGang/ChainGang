package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity
        implements LoginFragment.OnLoginFragmentInteractionListener,
        MemberAddEditFragment.OnAddEditInteractionListener,
        LoginCredentialsFragment.OnLoginCredentialsFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.login_fragment_container,new LoginFragment())
                .commit();

    }

    @Override
    public void addEditMember(String url) {

    }

    @Override
    public void launchAddNewMember() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.login_fragment_container,new MemberAddEditFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void launchLoginCredentials() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.login_fragment_container,new LoginCredentialsFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void validateCredentials(String memberEmail, String memberPassword) {
        //do logic for validation
        boolean valid = true;
        // login succeeded now goto the ChainActivity
        if (valid) {
            Intent intent = new Intent(this, ChainActivity.class);
            startActivity(intent);
        } else {
            // go back to the landing fragment because login failed
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.login_fragment_container,new LoginFragment())
                    .commit();
        }
    }
}
