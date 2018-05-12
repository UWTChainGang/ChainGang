package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import chain.Chain;
import edu.tacoma.uw.css.uwtchaingang.chaingang.ChainListFragment.OnChainListFragmentInteractionListener;

import java.util.List;

/**
 *
 */
public class MyChainRecyclerViewAdapter extends RecyclerView.Adapter<MyChainRecyclerViewAdapter.ViewHolder> {

    /**
     * Chain list
     */
    private final List<Chain> mValues;

    /**
     * Listener for chain list interactions
     */
    private final OnChainListFragmentInteractionListener mListener;

    /**
     * Class constructor
     *
     * @param items chain list
     * @param listener
     */
    public MyChainRecyclerViewAdapter(List<Chain> items, OnChainListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    /**
     * View holder
     *
     * @param parent
     * @param viewType
     * @return view holder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_chain, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Bind View holder
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).getmChainID());
        holder.mIdView.setText(mValues.get(position).getmChainTitle());
        holder.mContentView.setText(mValues.get(position).getmChainDesc());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onChainListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * View holder class
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * View
         */
        public final View mView;

        /**
         * View ID
         */
        public final TextView mIdView;

        /**
         * View content
         */
        public final TextView mContentView;

        /**
         * Chain
         */
        public Chain mItem;

        /**
         * CiewHolder class constructor
         *
         * @param view
         */
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        /**
         * Overriden toSting method
         *
         * @return
         */
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
