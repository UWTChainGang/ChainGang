package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * Fragment is the app landing for new and restarts.
 *
 * @author Michael Quandt
 * @author James E Johnston
 * @author Denis Yakovlev
 * @version 23 May 2017
 */
public class LoginFragment extends Fragment {

    /**
     * Listener for the login interaction process
     */
    private OnLoginFragmentInteractionListener mListener;

    /**
     * The Terms of Service textview
     */
    private TextView mTextView;
    /**
     * Required empty public constructor
     */
    public LoginFragment() {
    }

    /**
     * Factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Call for the super onCreate method
     *
     * @param savedInstanceState current instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
    }

    /**
     * Create the main view of app with login / create a member options
     *
     * @param inflater
     * @param container
     * @param savedInstanceState current state
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button newMemberButton = (Button) view.findViewById(R.id.create_account_btn);
        newMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.launchAddNewMember();
            }
        });
        Button loginCredentials = (Button) view.findViewById(R.id.login_btn);
        loginCredentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity)getActivity()).launchLoginCredentials();
            }
        });
        mTextView = (TextView) view.findViewById(R.id.t_and_c);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse("http://chaingangwebservice.us-west-2.elasticbeanstalk.com/tos");
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        });
        getActivity().setTitle("Welcome");
        return view;
    }

    /**
     * Checks if context is OnLoginFragmentInteractionListener, if so activate listener
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentInteractionListener) {
            mListener = (OnLoginFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginFragmentInteractionListener");
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
     * This interface to allow an interaction in this fragment to be communicated
     * to the Login Activity and other fragments contained in the Activity.
     */
    public interface OnLoginFragmentInteractionListener {
        void launchAddNewMember();
    }
}
