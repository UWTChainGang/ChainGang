package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity
        implements LoginFragment.OnLoginFragmentInteractionListener,
        MemberAddEditFragment.OnAddEditInteractionListener {

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
                .commit();
    }
}
