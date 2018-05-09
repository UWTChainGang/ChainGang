package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.content.Context;
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
import java.util.List;

import chain.Chain;

/**
 */
public class ChainListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String chainURL = "http://chaingangwebservice.us-west-2.elasticbeanstalk.com/chains";
    private int mColumnCount = 1;
    private OnChainListFragmentInteractionListener mListener;
    private List<Chain> mChainList;
    private RecyclerView mRecyclerView;

    public ChainListFragment() {

    }

    public static ChainListFragment newInstance(int columnCount) {
        ChainListFragment fragment = new ChainListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ChainListFragment", "onCreate Called from class: ChainListFragment.java");
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chain_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            ChainAsyncTask courseAsyncTask = new ChainAsyncTask();
            courseAsyncTask.execute(new String[]{chainURL});
            Log.i("AdapterTag", "RecyclerView Launched");
        }
        return view;
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnChainListFragmentInteractionListener {

        void onChainListFragmentInteraction(Chain chain);
    }

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
                    response = "Unable to download the list of Chains, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            Log.i("Async Task Tag", "doInBackground: " + response);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("", "onPostExecute");

            if (result.startsWith("Unable to")) {
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
            // Everything is good, show the list of courses.
            if (!mChainList.isEmpty()) {
                mRecyclerView.setAdapter(new MyChainRecyclerViewAdapter(mChainList, mListener));
            }
        }

    }

}
