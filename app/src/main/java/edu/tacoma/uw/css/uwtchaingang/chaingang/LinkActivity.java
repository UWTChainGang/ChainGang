package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import link.Link;

public class LinkActivity extends AppCompatActivity {

    private Link mLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        Intent intent = getIntent();
        mLink = (Link) intent.getSerializableExtra(LinkListFragment.LINK_SELECTED);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
