package com.example.CuandoCorrer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
    }
}