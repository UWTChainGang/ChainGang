package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import member.Member;

import static android.support.constraint.Constraints.TAG;


/**
 * Fragment to handle login credentials.
 */
public class LoginCredentialsFragment extends Fragment {

    /**
     * Constant for database url
     */
    private static final String MEMBER_AUTHENTICATE_URL = "http://chaingangwebservice.us-west-2.elasticbeanstalk.com/users/login/?";

    /**
     * Member email
     */
    private EditText mMemberEmail;

    /**
     * Member password
     */
    private EditText mMemberPassword;

    /**
     * Listener for login credentials interaction fragment
     */
    private OnLoginCredentialsFragmentInteractionListener mListener;

    /**
     * Required empty public constructor
     */
    public LoginCredentialsFragment() {
    }

    /**
     * Create a new instance of LoginCreadentialsFragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginCredentialsFragment.
     */
    public static LoginCredentialsFragment newInstance(String param1, String param2) {
        LoginCredentialsFragment fragment = new LoginCredentialsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Override super onCreate method
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    /**
     * Create a view with login/password requests and a button to validate
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_credentials, container, false);

        mMemberEmail = (EditText) view.findViewById(R.id.email_credentials);
        mMemberPassword = (EditText) view.findViewById(R.id.password_credentials);

        Button loginCredentials = (Button) view.findViewById(R.id.login_btn_credentials);
        loginCredentials.setOnClickListener(new View.OnClickListener() {

            /**
             * Create an url to validate
             *
             * @param v view
             */
            @Override
            public void onClick(View v) {

                // ************************************************************************
                // Input validation added
                String email = mMemberEmail.getText().toString();
                String password = mMemberPassword.getText().toString();

                if (TextUtils.isEmpty(email) || !email.contains("@")) {
                    Toast.makeText(v.getContext(), "Enter valid email address"
                            ,Toast.LENGTH_SHORT)
                            .show();
                    mMemberEmail.requestFocus();
                } else if (TextUtils.isEmpty(password) || password.length() < 6) {
                    Toast.makeText(v.getContext(), "Enter valid password (at least 6 characters)"
                            ,Toast.LENGTH_SHORT)
                            .show();
                    mMemberPassword.requestFocus();
                } else {
                    mListener.validateCredentials(buildMemberURL(v));
                }

                // ************************************************************************


                /*
                mListener.validateCredentials(buildMemberURL(v));

                replaced by

                } else {
                    mListener.validateCredentials(buildMemberURL(v));
                }

                from above

                */
            }
        });


        // ************************************************************************
        // fragment title changed to "Login"
        getActivity().setTitle("Login");
        // ************************************************************************

        return view;
    }

    /**
     * Checks if context is OnLoginCredentialsFragmentInteractionListener, if so activate listener
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginCredentialsFragmentInteractionListener) {
            mListener = (OnLoginCredentialsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginCredentialsFragmentInteractionListener");
        }
    }

    /**
     * Call for the super onDetach method
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Building the string member url
     *
     * @param v view
     * @return string with user name and password
     */
    private String buildMemberURL(View v) {

        StringBuilder sb = new StringBuilder(MEMBER_AUTHENTICATE_URL);

        try {

            String email = mMemberEmail.getText().toString();
            sb.append("user=");
            sb.append(URLEncoder.encode(email, "UTF-8"));


            String password = mMemberPassword.getText().toString();
            sb.append("&password=");
            sb.append(URLEncoder.encode(password, "UTF-8"));

            Log.i(TAG, sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

    /**
     * Interface to handle interaction events
     */
    public interface OnLoginCredentialsFragmentInteractionListener {

        void launchLoginCredentials();
        void validateCredentials(String url);
        void launchChains();
    }
}
