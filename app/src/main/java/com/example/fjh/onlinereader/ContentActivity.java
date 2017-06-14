package com.example.fjh.onlinereader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Window;
import android.widget.TextView;

import com.example.fjh.onlinereader.Bean.Catalog;
import com.example.fjh.onlinereader.Manager.ActivityManager;


public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Transition slide_right = TransitionInflater.from(this).inflateTransition(android.R.transition.slide_right);
        getWindow().setEnterTransition(slide_right);


        setContentView(R.layout.activity_content);
        ActivityManager.addActivity(this);
        Catalog catalog = (Catalog) getIntent().getSerializableExtra("bookCatalog");
        TextView title = (TextView)findViewById(R.id.content_title);
        title.setText(catalog.getTitle()+" "+catalog.getName());
        TextView content=(TextView)findViewById(R.id.content_text);
        content.setText(catalog.getContent());
    }
}
