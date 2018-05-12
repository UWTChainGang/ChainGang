package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
 * The main activity class, takes care for all activities of app
 */
public class LoginActivity extends AppCompatActivity
        implements LoginFragment.OnLoginFragmentInteractionListener,
        MemberAddEditFragment.OnAddEditInteractionListener,
        LoginCredentialsFragment.OnLoginCredentialsFragmentInteractionListener {


    /**
     * Call for the login fragment
     *
     * @param savedInstanceState current state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.login_fragment_container,new LoginFragment())
                .commit();

    }

    /**
     * Add/edit a member task
     *
     * @param url
     */
    @Override
    public void addEditMember(String url) {
        AddMemberTask task = new AddMemberTask();
        task.execute(new String[]{url.toString()});

        // Takes you back to the previous fragment by popping the current fragment out.
        getSupportFragmentManager().popBackStackImmediate();
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
        Intent intent = new Intent(this, ChainActivity.class);
        startActivity(intent);
    }

    /**
     * Validate a member credentials
     *
     * @param url a member input data
     */
    @Override
    public void validateCredentials(String url) {
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
                    response = "Unable to add a member. Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }

            }
            return response;
        }

        /**
         * It checks if credentials are correct
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            try {
                String status = Member.parseMemberJSON(result);
                if (status.equals(Member.USER_AUTHENTICATED)) {
                    Toast.makeText(getApplicationContext(), "Login Success"
                            , Toast.LENGTH_LONG)
                            .show();
                    launchChains();
                } else if (status.equals(Member.USER_DOES_NOT_EXIST)){
                    Toast.makeText(getApplicationContext(), "User not registered "
                            , Toast.LENGTH_LONG)
                            .show();
                    launchLoginCredentials();
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect credential, Try again. "
                            , Toast.LENGTH_LONG)
                            .show();
                    launchLoginCredentials();

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Class to add a member task, synchronize with the chain database
     */
    private class AddMemberTask extends AsyncTask<String, Void, String> {

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
                    response = "Unable to add task. Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }

            }
            return response;
        }

        /**
         * It checks if a user account wsa created
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            try {
                String status = Member.parseMemberAddJSON(result);
                if (status.equals(Member.SUCCESS)) {
                    Toast.makeText(getApplicationContext(), "Operation Successful "
                            , Toast.LENGTH_LONG)
                            .show();
                    launchLoginCredentials();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to Create User Account "
                            , Toast.LENGTH_LONG)
                            .show();
                    launchAddNewMember();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
