package com.juanked.CuandoCorrer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.CuandoCorrer.BuildConfig;
import com.example.CuandoCorrer.R;
import com.juanked.CuandoCorrer.adapters.Translator;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView title = findViewById(R.id.txt_title);
        title.setText(R.string.about_title);
        TextView aboutText = findViewById(R.id.txt_about);
        aboutText.setText(R.string.app_description);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            aboutText.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }
        TextView authorTitle = findViewById(R.id.txt_about_author);
        authorTitle.setText(R.string.about_author);
        TextView nameAuthor = findViewById(R.id.txt_nameAuthor);
        nameAuthor.setText(R.string.name_author);

        ImageView image = findViewById(R.id.img_github);
        Picasso.get().load("https://avatars.githubusercontent.com/u/48260809?v=4").
                resize(300,300).into(image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/juanked"));
                startActivity(intent);
            }
        });
        TextView versionName = findViewById(R.id.txt_version);
        String result = getString(R.string.version)+BuildConfig.VERSION_NAME;
        versionName.setText(result);
    }
}