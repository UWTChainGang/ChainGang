package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import chain.Chain;
import link.Link;

/**
 * Activity to process a member tasks
 */
public class ChainActivity extends AppCompatActivity
        implements ChainListFragment.OnChainListFragmentInteractionListener,
        LinkListFragment.OnLinkListFragmentInteractionListener {

    public static final String EXTRA_LINK = "extra_link";

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
                Snackbar.make(view, "this will send email to warden", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * Implementation of OnChainListFragmentInteractionListener interface
     *
     * @param chain for reference of its links.
     */
    @Override
    public void onChainListFragmentInteraction(Chain chain) {
        LinkListFragment linkListFragment = new LinkListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ChainListFragment.CHAIN_SELECTED, chain);
        linkListFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.chain_container, linkListFragment)
                .addToBackStack(null)
                .commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "this will send email to warden", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * Implementation of OnLinkListFragmentInteractionListener interface
     *
     * @param link for reference of its links.
     */
    @Override
    public void onLinkSelected(Link link) {
        Intent intent = new Intent(this, LinkActivity.class);
        intent.putExtra(EXTRA_LINK, link);
        startActivity(intent);

    }
}
