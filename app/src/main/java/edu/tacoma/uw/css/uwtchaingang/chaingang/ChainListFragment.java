package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import chain.Chain;
import data.ChainDB;

import static android.support.constraint.Constraints.TAG;

/**
 * The ListFragment for Chains {@link Chain}
 *
 * @author Michael Quandt
 * @author James E Johnston
 * @author Denis Yakovlev
 * @version 20 May 2017
 */
public class ChainListFragment extends Fragment {

    /**
     * Constant for a selected chain key.
     */
    public final static String CHAIN_SELECTED = "chain_selected";

    /**
     * Constant for column counter
     */
    private static final String ARG_COLUMN_COUNT = "column-count";

    /**
     * Link to the chain table in the database
     */
    private static final String CHAINURL = "http://chaingangwebservice.us-west-2.elasticbeanstalk.com/chains?";

    /**
     * Column counter initializer
     */
    private int mColumnCount = 1;

    /**
     * Listener for chain list interactions
     */
    private OnChainListFragmentInteractionListener mListener;

    /**
     * List of Chains
     */
    private List<Chain> mChainList;

    /**
     * RecyclerView variable
     */
    private RecyclerView mRecyclerView;

    /**
     * Chain DB variable
     */
    private ChainDB mChainDB;

    /**
     * Required empty constructor
     */
    public ChainListFragment() {

    }

    /**
     * Method to create an instance of the ChainListFragment
     *
     * @param columnCount  column counter
     * @return instance of ChainListFragment
     */
    public static ChainListFragment newInstance(int columnCount) {
        ChainListFragment fragment = new ChainListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Calls super onCreate method
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ChainListFragment", "onCreate Called from class: ChainListFragment.java");
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    /**
     * Create a view for the chain list
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chain_list, container, false);

        Bundle bundle = getArguments();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }
        ConnectivityManager connMgr
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            String email = bundle.getString("USER");
            String theChainUrl = buildChainURL(view,email);
            ChainAsyncTask chainAsyncTask = new ChainAsyncTask();
            chainAsyncTask.execute(new String[]{theChainUrl});

        } else {
            Toast.makeText(view.getContext(),
                    "Displaying locally stored data",
                    Toast.LENGTH_LONG).show();
            if (mChainDB == null) {
                mChainDB = new ChainDB(getActivity());
            }
            if (mChainList == null) {
                mChainList = mChainDB.getChains();
            }
            mRecyclerView.setAdapter(new MyChainRecyclerViewAdapter(mChainList, mListener));
        }
        return view;
    }


    /**
     * Checks if context is OnChainListFragmentInteractionListener, if so activate listener
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChainListFragmentInteractionListener) {
            mListener = (OnChainListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnChainListFragmentInteractionListener");
        }
    }

    /**
     * Calls for the super onDetach method
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
    private String buildChainURL(View v, String email) {

        StringBuilder sb = new StringBuilder(CHAINURL);

        try {
            // use shared prefs first then email param as a backup
            SharedPreferences sharedPref = getActivity().getSharedPreferences("user_id",Context.MODE_PRIVATE);
            String memberEmail = sharedPref.getString("memberEmail", email);
            sb.append("member=");
            sb.append(URLEncoder.encode(memberEmail, "UTF-8"));

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }
    /**
     * This interface to allow an interaction in this fragment to be communicated
     * to the Chain Activity and other fragments contained in the Activity.
     */
    public interface OnChainListFragmentInteractionListener {

        void onChainListFragmentInteraction(Chain chain);
    }

    /**
     * Class to get a list of chains, synchronized with the member database
     */
    private class ChainAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            Log.i("", "Doinbackground");
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
                    response = "No Network";
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            Log.i("Async Task Tag", "doInBackground: " + response);
            return response;
        }

        /**
         * It checks the list of chains
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            Log.i("", "onPostExecute");

            if (result.startsWith("No Network")) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }
            try {
                mChainList = Chain.parseChainJSON(result);
            }
            catch (JSONException e) {
                Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                        .show();
                return;
            }

            if (!mChainList.isEmpty()) {

                if (mChainDB == null) {
                    mChainDB = new ChainDB(getActivity());
                }

                // Delete old data so that we can refresh the local
                // database with the network data.
                mChainDB.deleteChains();

                // Also, add to the local database
                for (int i = 0; i < mChainList.size(); i++) {

                    Chain chain = mChainList.get(i);
                    mChainDB.insertChain(chain.getmChainID(),
                            chain.getmChainTitle(),
                            chain.getmChainDesc(),
                            chain.getmMemberID());

                }

                mRecyclerView.setAdapter(new MyChainRecyclerViewAdapter(mChainList, mListener));
            }
        }

    }

}
