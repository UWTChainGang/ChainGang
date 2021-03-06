package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.content.Context;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chain.Chain;
import data.ChainDB;
import link.Link;

import static android.support.constraint.Constraints.TAG;

/**
 * A fragment containing Link Items
 *
 * @author Michael Quandt
 * @author James E Johnston
 * @author Denis Yakovlev
 * @version 20 May 2017
 */
public class LinkListFragment extends Fragment {


    /**
     * Constant used a Key for value of a selected chain.
     */
    public final static String CHAIN_SELECTED = "chain_selected";
    public final static String LINK_SELECTED = "link_selected";

    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    /**
     * The listener for Link interaction.
     */
    private OnLinkListFragmentInteractionListener mListener;
    private RecyclerView mRecylcerView;

    /**
     * The current chain belonging to this Member
     */
    private Chain mChain;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LinkListFragment() {
    }


    public static LinkListFragment newInstance(int columnCount) {
        LinkListFragment fragment = new LinkListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mChain = (Chain) getArguments().get(CHAIN_SELECTED);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_link_list, container, false);
        getActivity().setTitle("CHAIN:");
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecylcerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecylcerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecylcerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            mRecylcerView.setAdapter(new MyLinkRecyclerViewAdapter(mChain, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLinkListFragmentInteractionListener) {
            mListener = (OnLinkListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLinkListFragmentInteractionListener");
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
     *
     */
    public interface OnLinkListFragmentInteractionListener {
        void onLinkSelected(Link theLink, Chain theChain);
    }






}
