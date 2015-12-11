package com.snyxius.apps.dealwithit.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.fragments.SearchDeals;

import org.json.JSONObject;

/**
 * Created by amanjham on 11/12/15 AD.
 */

public class SearchDealsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText search;
    private String search1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        initialize();
    }

    private void initialize(){
       findViewById(R.id.close).setOnClickListener(this);
        search = (EditText)findViewById(R.id.search);
        search.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container,new SearchDeals().newInstance(search.getText().toString()))
                            .commit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.close:
                onBackPressed();
                break;
        }
    }

}
