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

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnAddEditInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MemberAddEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemberAddEditFragment extends Fragment {
    private final static String MEMBER_ADD_EDIT_FRAG = "MemberAddFragment: ";
    private final static String MEMEBER_ADD_URL
            = "http://chaingangwebservice.us-west-2.elasticbeanstalk.com/users/add/?";//user=someguy420@uw.edu&password=kitty&fname=Mike&lname=Hunt"
    private EditText mFName;
    private EditText mLName;
    private EditText mEmail;
    private EditText mPassword;


    private OnAddEditInteractionListener mListener;

    public MemberAddEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MemberAddEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MemberAddEditFragment newInstance(String param1, String param2) {
        MemberAddEditFragment fragment = new MemberAddEditFragment();
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
        View view = inflater.inflate(R.layout.fragment_member_add_edit, container, false);
        mFName = (EditText) view.findViewById(R.id.firstname);
        mLName = (EditText) view.findViewById(R.id.lastname);
        mEmail = (EditText) view.findViewById(R.id.email);
        mPassword = (EditText) view.findViewById(R.id.password);
        Button addButton = (Button) view.findViewById(R.id.createAccountButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.addEditMember(buildMemberURL(v));
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.addEditMember(uri);
        }
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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
            Log.i(TAG, sb.toString());

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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnAddEditInteractionListener {

        void addEditMember(String url);
    }
}
