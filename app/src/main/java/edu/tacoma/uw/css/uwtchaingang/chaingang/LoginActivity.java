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
        AddMemberTask task = new AddMemberTask();
        task.execute(new String[]{url.toString()});

        // Takes you back to the previous fragment by popping the current fragment out.
        getSupportFragmentManager().popBackStackImmediate();
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
    public void launchChains() {
        Intent intent = new Intent(this, ChainActivity.class);
        startActivity(intent);
    }

    @Override
    public void validateCredentials(String url) {
        AuthenticateMemberTask task = new AuthenticateMemberTask();
        task.execute(new String[]{url.toString()});

    }

    private class AuthenticateMemberTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

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
                    response = "Unable to add course, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }

            }
            return response;
        }

        /**
         * It checks to see if there was a problem with the URL(Network) which is when an
         * exception is caught. It tries to call the parse Method and checks to see if it was successful.
         * If not, it displays the exception.
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


    ////////////////////

    private class AddMemberTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

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
                    response = "Unable to add course, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }

            }
            return response;
        }

        /**
         * It checks to see if there was a problem with the URL(Network) which is when an
         * exception is caught. It tries to call the parse Method and checks to see if it was successful.
         * If not, it displays the exception.
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
