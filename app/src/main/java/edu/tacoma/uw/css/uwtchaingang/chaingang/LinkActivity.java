package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import chain.Chain;
import link.Link;

public class LinkActivity extends AppCompatActivity {

    public static final String LINK_ACTIVITY = "LINK_ACTIVITY";
    public static final String NOTIFY_WARDEN_URL =
            "http://chaingangwebservice.us-west-2.elasticbeanstalk.com/" +
                    "update/link?member=abcd@abc.com&chainTitle=Title%20of%20this%20Chain&link_id=1";

    private Link mLink;
    private CheckBox mTaskCheckerA;
    private TextView mExtResA;
    private EditText mLinkNotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        Intent intent = getIntent();
        mLink = (Link) intent.getSerializableExtra(ChainActivity.EXTRA_LINK);
        Log.i(LINK_ACTIVITY, "this link's isComplete" + Boolean.toString(mLink.ismIsCompleted()));
        mTaskCheckerA = (CheckBox) findViewById(R.id.taskCheckerA);
        mTaskCheckerA.setText(mLink.getmLinkInst());
        mExtResA = (TextView) findViewById(R.id.externalResA);
        mExtResA.setText("Eggcelent Facts");
        mExtResA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse("https://www.eatthis.com/egg-facts/");
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        });
        mExtResA.setMovementMethod(LinkMovementMethod.getInstance());
        mLinkNotes = (EditText) findViewById(R.id.editLinkNotes) ;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button completeButton = findViewById(R.id.completeButton);
        if (mLink.ismIsCompleted()) {
            completeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Connect with db
                    //Log.i(LINK_ACTIVITY, "this link's isComplete" + Boolean.toString(mLink.ismIsCompleted()));

                    finish();
                }
            });
        }
    }


}
