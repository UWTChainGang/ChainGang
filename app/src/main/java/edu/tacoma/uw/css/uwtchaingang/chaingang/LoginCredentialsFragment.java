package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnLoginCredentialsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginCredentialsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginCredentialsFragment extends Fragment {

    private static final String MEMBER_AUTHENTICATE_URL = "http://chaingangwebservice.us-west-2.elasticbeanstalk.com/users/login/?";

    private EditText mMemberEmail;
    private EditText mMemberPassword;


    private OnLoginCredentialsFragmentInteractionListener mListener;

    public LoginCredentialsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginCredentialsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginCredentialsFragment newInstance(String param1, String param2) {
        LoginCredentialsFragment fragment = new LoginCredentialsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_credentials, container, false);
        mMemberEmail = (EditText) view.findViewById(R.id.email_credentials);
        mMemberPassword = (EditText) view.findViewById(R.id.password_credentials);
        Button loginCredentials = (Button) view.findViewById(R.id.login_btn_credentials);
        loginCredentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do logic for authentication
                mListener.validateCredentials(buildMemberURL(v));

            }
        });

        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.launchLoginCredentials();
        }
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnLoginCredentialsFragmentInteractionListener {

        void launchLoginCredentials();
        void validateCredentials(String url);
        void launchChains();
    }


}
