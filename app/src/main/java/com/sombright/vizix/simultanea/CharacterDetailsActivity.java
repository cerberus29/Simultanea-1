package com.sombright.vizix.simultanea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class CharacterDetailsActivity extends AppCompatActivity {
    private int mCharacterNum;
    private static final String TAG_STATS = "stats";
    private static final String TAG_LORE = "lore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);
        // Setup the tabs
        TabHost tabHost = findViewById(R.id.tabHostStatsLore);
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec(TAG_STATS);
        spec.setContent(R.id.tabStats);
        spec.setIndicator("Stats");
        tabHost.addTab(spec);
        spec = tabHost.newTabSpec(TAG_LORE);
        spec.setContent(R.id.tabLore);
        spec.setIndicator("Lore");
        tabHost.addTab(spec);

        // Retrieve the character selection sent by the main activity as part of the "intent"
        Intent intent = getIntent();
        mCharacterNum = intent.getIntExtra("index", 0);
        Character character = CharacterPool.charactersList[mCharacterNum];

        ImageView imageViewCharacter = findViewById(R.id.imageViewCharacter);
        imageViewCharacter.setImageResource(character.getImageResource());
        TextView textViewCharacterName = findViewById(R.id.textViewCharacterName);
        textViewCharacterName.setText(character.getStringResourceName());
        TextView textViewStats = findViewById(R.id.textViewStats);
        String msg_stats = String.format(getString(R.string.msg_stats),
                character.getHeal(), character.getRecovery(),
                character.getAttack(), character.getDefense());
        textViewStats.setText(msg_stats);
        TextView textViewLore = findViewById(R.id.textViewLore);
        textViewLore.setText(character.getStringResourceLore());
        textViewLore.setMovementMethod(new ScrollingMovementMethod());
        Button pickButton = findViewById(R.id.buttonPick);
        pickButton.setEnabled(character.isPlayable());
        if (!character.isPlayable()){
            pickButton.setText("Locked");
        }
    }

    public void onClickPick(View v) {
        // Return the character selection back to the main activity
        PreferencesProxy prefs = new PreferencesProxy(this);
        String name = getString(CharacterPool.charactersList[mCharacterNum].getStringResourceName());
        prefs.setCharacter(name);
        finish();
    }
}