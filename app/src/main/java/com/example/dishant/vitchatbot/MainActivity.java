package com.example.dishant.vitchatbot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.Result;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonElement;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements AIListener {

    private Button listenButton, chat;
    private TextView resultTextView;
    private AIService aiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listenButton = (Button) findViewById(R.id.listen);
        chat = (Button) findViewById(R.id.chat);
        resultTextView = (TextView) findViewById(R.id.result);

        final AIConfiguration aiConfiguration = new AIConfiguration("e00460bcc1474b8b9e59c4c195943cc7", AIConfiguration.SupportedLanguages.English, AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(this, aiConfiguration);
        aiService.setListener(this);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(i);
            }
        });

        listenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aiService.startListening();
            }
        });

    }


    @Override
    public void onResult(ai.api.model.AIResponse result) {

        Result res = result.getResult();

        // Get parameters

        String parameterString = "";
        if(res.getParameters() != null && !res.getParameters().isEmpty()){
            for (final Map.Entry<String, JsonElement> entry : res.getParameters().entrySet()) {
                parameterString += "(" + entry.getKey() + ", " + entry.getValue() + ") ";
            }
        }

        resultTextView.setText(res.getFulfillment().getSpeech());

    }

    @Override
    public void onError(ai.api.model.AIError error) {
        resultTextView.setText(error.toString());
    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
}
