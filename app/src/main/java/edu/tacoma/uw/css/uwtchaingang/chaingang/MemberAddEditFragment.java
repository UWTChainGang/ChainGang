package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.support.constraint.Constraints.TAG;


/**
 * Fragment to handle add / edit member
 */
public class MemberAddEditFragment extends Fragment {

    /**
     * Ad and edit fragment constant
     */
    private final static String MEMBER_ADD_EDIT_FRAG = "MemberAddFragment: ";

    /**
     * Database url
     */
    private final static String MEMEBER_ADD_URL
            = "http://chaingangwebservice.us-west-2.elasticbeanstalk.com/users/add/?";

    /**
     * First name of a member
     */
    private EditText mFName;

    /**
     * Second name of a member
     */
    private EditText mLName;

    /**
     * Member email
     */
    private EditText mEmail;

    /**
     * Member password
     */
    private EditText mPassword;

    /**
     * Member password
     */
    private EditText mPasswordConfirm;

    /**
     * Listener for add /edit interaction
     */
    private OnAddEditInteractionListener mListener;

    /**
     * Required empty public constructor
     */
    public MemberAddEditFragment() {
    }

    /**
     * Create a new instance of the MemberAddEditFragment using the provided parameters.
     *
     * @return A new instance of fragment MemberAddEditFragment.
     */
    public static MemberAddEditFragment newInstance(String param1, String param2) {
        MemberAddEditFragment fragment = new MemberAddEditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Calls for the super onCreate method
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
     * Creating a view for adding a new member
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_member_add_edit, container, false);
        mFName = (EditText) view.findViewById(R.id.firstname);
        mLName = (EditText) view.findViewById(R.id.lastname);
        mEmail = (EditText) view.findViewById(R.id.email);
        mPassword = (EditText) view.findViewById(R.id.password);
        mPasswordConfirm = (EditText) view.findViewById(R.id.confirmpassword);
        Button addButton = (Button) view.findViewById(R.id.createAccountButton);
        addButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Build new member url with the user data
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                if (isValidMemberInput(v)) {
                    mListener.addEditMember(buildMemberURL(v));
                }
            }
        });

        return view;
    }

    /**
     * Checks if context is OnAddEditInteractionListener, if so activate listener
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddEditInteractionListener) {
            mListener = (OnAddEditInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddEditInteractionListener");
        }
    }

    // Calls for the super onDetach method
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Building a member url with entered data
     *
     * @param v view
     * @return url
     */
    private String buildMemberURL(View v) {

        StringBuilder sb = new StringBuilder(MEMEBER_ADD_URL);
        try {
            String email = mEmail.getText().toString();
            sb.append("user=");
            sb.append(URLEncoder.encode(email, "UTF-8"));


            String password = mPassword.getText().toString();
            sb.append("&password=");
            sb.append(URLEncoder.encode(password, "UTF-8"));

            String fname = mFName.getText().toString();
            sb.append("&fname=");
            sb.append(URLEncoder.encode(fname, "UTF-8"));
            //Log.i(TAG, sb.toString());

            String lname = mLName.getText().toString();
            sb.append("&lname=");
            sb.append(URLEncoder.encode(lname, "UTF-8"));
            Log.i(TAG, sb.toString());
        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

    /**
     * Validation for member input to the EditText fields.
     *
     * @param view access to the current view
     * @return whether the member input is valid.
     */
    private boolean isValidMemberInput(View view) {
        String email = mEmail.getText().toString();
        Pattern pattern;
        // Regex for a valid email address
        String emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.find()) {
            Toast.makeText(view.getContext(), "Invalid Email" , Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        String password = mPassword.getText().toString();
        String passwordConfirm = mPasswordConfirm.getText().toString();
        String passwordRegEx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        pattern = Pattern.compile(passwordRegEx);
        matcher = pattern.matcher(password);
        if (!password.equals(passwordConfirm)) {
            Toast.makeText(view.getContext(), "Passwords do not match " , Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        if(!matcher.find()) {
            Toast.makeText(view.getContext(), "Password example: @#23WEwe " , Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        String fname = mFName.getText().toString();
        String lname = mLName.getText().toString();
        if (fname.length() < 2 || lname.length() < 2) {
            Toast.makeText(view.getContext(), "Please enter a name of 2 or more characters " , Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        return true;
    }

    /**
     * Interface to handle interaction events
     */
    public interface OnAddEditInteractionListener {
        void addEditMember(String url);
    }
}
