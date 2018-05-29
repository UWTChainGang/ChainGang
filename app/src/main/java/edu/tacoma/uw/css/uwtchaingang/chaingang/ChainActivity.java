package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import chain.Chain;
import link.Link;


/**
 * The main activity for context of ListFragments both Link {@link Link} and
 * Chain {@link Chain}.
 *
 * @author Michael Quandt
 * @author James E Johnston
 * @author Denis Yakovlev
 * @version 20 May 2017
 */
public class ChainActivity extends AppCompatActivity
        implements ChainListFragment.OnChainListFragmentInteractionListener,
        LinkListFragment.OnLinkListFragmentInteractionListener {

    /**
     * For Log debugging.
     */
    public static final String CHAIN_ACTIVITY = "CHAIN_ACTIVITY";
    /**
     * Put Extra constant
     */
    public static final String EXTRA_LINK = "extra_link";
    /**
     * Put Extra constant
     */
    public final static String EXTRA_CHAIN = "extra_chain";

    /**
     * the Member who the content belongs to.
     */
    private String mMember;

    /**
     * Initialize the ChainList Fragment
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chain);
        setTitle(R.string.chain_activity_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        mMember = intent.getStringExtra("USER");
        // Populate the Links
        if (intent.getSerializableExtra(ChainListFragment.CHAIN_SELECTED) != null) {
            Chain chain = (Chain) intent.getSerializableExtra(ChainListFragment.CHAIN_SELECTED);
            LinkListFragment linkListFragment = new LinkListFragment();
            Bundle args = new Bundle();
            args.putSerializable(ChainListFragment.CHAIN_SELECTED, chain);
            linkListFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.chain_container, linkListFragment)
                    .commit();

        } else {            //populate the chains
            ChainListFragment chainListFragment = new ChainListFragment();
            Bundle args = new Bundle();
            args.putString("USER", intent.getStringExtra("USER"));
            chainListFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.chain_container, chainListFragment)
                    .commit();
        }
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
    }

    /**
     * Implementation of OnLinkListFragmentInteractionListener interface
     *
     * @param theLink for reference of its links.
     */
    @Override
    public void onLinkSelected(Link theLink, Chain theChain) {
        Intent intent = new Intent(this, LinkActivity.class);
        intent.putExtra(EXTRA_LINK, theLink);
        intent.putExtra(EXTRA_CHAIN, theChain);
        startActivity(intent);
        getSupportFragmentManager().popBackStackImmediate();
    }


    /**
     * Method creates options menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course, menu);

        return true;
    }

    /**
     * Method handles logout menu option
     *
     * @param item selected option
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS)
                    , Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN)
                    , false)
                    .commit();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.action_to_chains) {
            SharedPreferences sharedPref = getSharedPreferences("user_id",Context.MODE_PRIVATE);
            String memberEmail = sharedPref.getString("memberEmail", mMember);
            Intent intent = new Intent(this, ChainActivity.class);
            intent.putExtra("USER", memberEmail);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}
