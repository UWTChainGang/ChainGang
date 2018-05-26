package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import chain.Chain;
import edu.tacoma.uw.css.uwtchaingang.chaingang.LinkListFragment.OnLinkListFragmentInteractionListener;
import link.Link;

import java.util.List;


public class MyLinkRecyclerViewAdapter extends RecyclerView.Adapter<MyLinkRecyclerViewAdapter.ViewHolder> {

    private final Chain mChain;
    private final OnLinkListFragmentInteractionListener mListener;

    public MyLinkRecyclerViewAdapter(Chain theChain, OnLinkListFragmentInteractionListener listener) {
        mChain = theChain;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_link, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mLink = mChain.getMchainsInLink().get(position);
        holder.mIdView.setText(mChain.getMchainsInLink().get(position).getmLinkText());
        holder.mContentView.setText(Boolean.toString(mChain.getMchainsInLink().get(position).ismIsCompleted()));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onLinkSelected(holder.mLink, mChain);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChain.getMchainsInLink().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Link mLink;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
