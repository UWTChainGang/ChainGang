package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import member.Member;

/**
 * The login activity provides a context for all user account related operations.
 *
 * @author Michael Quandt
 * @author James E Johnston
 * @author Denis Yakovlev
 * @version 23 May 2017
 */
public class LoginActivity extends AppCompatActivity
        implements LoginFragment.OnLoginFragmentInteractionListener,
        MemberAddEditFragment.OnAddEditInteractionListener,
        LoginCredentialsFragment.OnLoginCredentialsFragmentInteractionListener {

    /**
     * Constant of positive response if a member exists
     */
    public static final String USER = "USER";
    /**
     * the user logging in
     */
    private Member mMember;
    private String mEmail;

    /**
     * The activity name for Logging.
     */
    private final static String LOGIN_ACTIVITY = "LoginActivity: ";

    // ************************************************************************
    /**
     * Variable to handle local storing of a member credentials
     */
    private SharedPreferences mSharedPreferences;
    // ************************************************************************

    /**
     * Call for the login fragment
     *
     * @param savedInstanceState current state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSharedPreferences =
                getSharedPreferences(getString(R.string.LOGIN_PREFS)
                        , Context.MODE_PRIVATE);
        if (!mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN)
                , false)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.login_fragment_container,new LoginFragment())
                    .commit();
        } else {
            launchChains();
        }

        // ************************************************************************
    }

    /**
     * Add/edit a member task
     *
     * @param url for POST to server
     */
    @Override
    public void addEditMember(String url) {
        AuthenticateMemberTask task = new AuthenticateMemberTask();
        task.execute(new String[]{url.toString()});

    }

    /**
     * Launch the fragment to add/edit a member
     */
    @Override
    public void launchAddNewMember() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.login_fragment_container,new MemberAddEditFragment())
                .addToBackStack(null)
                .commit();
    }

    /**
     * Launch the fragment to check a member credentials
     */
    @Override
    public void launchLoginCredentials() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.login_fragment_container,new LoginCredentialsFragment())
                .addToBackStack(null)
                .commit();
    }

    /**
     * Create an intent for the Chain Activity
     */
    @Override
    public void launchChains() {
        SharedPreferences sharedPref = getSharedPreferences("user_id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("memberEmail", mEmail);
        editor.commit();
        Intent intent = new Intent(this, ChainActivity.class);
        intent.putExtra(USER, mEmail);
        startActivity(intent);
    }

    /**
     * Validate a member credentials with the webservice after app-side validation.
     *
     * @param url a member input data
     * @param email the identifier used to make unique chains.
     */
    @Override
    public void validateCredentials(String url, String email) {
        Log.i(LOGIN_ACTIVITY,email);
        this.mEmail = email;
        AuthenticateMemberTask task = new AuthenticateMemberTask();
        task.execute(new String[]{url.toString()});

    }

    /**
     * Class to authenticate a member, synchronize with the member database
     */
    private class AuthenticateMemberTask extends AsyncTask<String, Void, String> {
        /**
         * Call for super method onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        /**
         * Setup a connection with the URL(Network)
         *
         * @param urls the given URL
         * @return server response
         */
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    response = "Unable to authenticate Member. Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        /**
         * It checks if credentials are correct and responds accordingly.
         *
         * @param result the JSON obtained from the webservice.
         */
        @Override
        protected void onPostExecute(String result) {
            Log.i("postLogin", result);
            try {
                mMember = Member.parseMemberJSON(result);
                if (mMember.getmStatus().equals(Member.USER_AUTHENTICATED)) {
                    Toast.makeText(getApplicationContext(), "User Authenticated "
                            , Toast.LENGTH_LONG)
                            .show();

                    launchChains();
                } else if (mMember.getmStatus().equals(Member.USER_DOES_NOT_EXIST)){
                    Toast.makeText(getApplicationContext(), "Member Does Not Exist "
                            , Toast.LENGTH_LONG)
                            .show();
                    launchLoginCredentials();
                } else if (mMember.getmStatus().equals(Member.USER_INVALID_PASSWORD)){
                    Toast.makeText(getApplicationContext(), "Invalid Password "
                            , Toast.LENGTH_LONG)
                            .show();
                    launchLoginCredentials();
                } else if (mMember.getmStatus().equals(Member.SUCCESS)) {
                    Toast.makeText(getApplicationContext(), "Account Created "
                            , Toast.LENGTH_LONG)
                            .show();
                    launchLoginCredentials();
                } else if (mMember.getmStatus().equals((Member.USER_ALREADY_EXISTS))) {
                    Toast.makeText(getApplicationContext(), "Email already in use "
                            , Toast.LENGTH_LONG)
                            .show();
                    launchAddNewMember();
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong "
                            , Toast.LENGTH_LONG)
                            .show();
                    getSupportFragmentManager().popBackStackImmediate();

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    }


}
