package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import chain.Chain;

/**
 * Activity to process a member tasks
 */
public class ChainActivity extends AppCompatActivity
        implements ChainListFragment.OnChainListFragmentInteractionListener {

    /**
     * Initialize the ChainList Fragment
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ChainListFragment chainListFragment = new ChainListFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.chain_container, chainListFragment)
                .commit();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * Implementation of OnChainListFragmentInteractionListener interface
     *
     * @param chain
     */
    @Override
    public void onChainListFragmentInteraction(Chain chain) {
    }
}
