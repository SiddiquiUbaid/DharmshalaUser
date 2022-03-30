 package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ImageHeaderParserUtils;
import com.example.Domain.Temples;
import com.example.Fregments.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class StoryDisplay extends AppCompatActivity {
ImageView img_Story;
ImageView timage;
TextView tname,txtBack;
Temples temples = null ;
StoriesProgressView storiesProgressView;
View reverse,skip;

private int counter = 0;

private final long[] duration = new long[]{500L,1000L,1500L,4000L,1000};
long  pressTime = 0L;
long limit = 500L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_display);

        img_Story = findViewById(R.id.img_Story);
        timage = findViewById(R.id.timage);
        tname = findViewById(R.id.tname);
        reverse = findViewById(R.id.reverse);
        skip = findViewById(R.id.skip);
        txtBack = findViewById(R.id.txtBack);

         View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){

                    case MotionEvent.ACTION_DOWN:
                        pressTime = System.currentTimeMillis();
                        storiesProgressView.pause();
                        return false;

                    case MotionEvent.ACTION_UP:
                        long now = System.currentTimeMillis();
                        storiesProgressView.resume();
                        return limit < now - pressTime;
                }
                return false;
            }
        };


        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoryDisplay.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        storiesProgressView = findViewById(R.id.storyProgress);
        storiesProgressView.setStoriesCount(5);
        storiesProgressView.setStoryDuration(5000L);
        storiesProgressView.setStoriesListener(new StoriesProgressView.StoriesListener() {
            @Override
            public void onNext() {

                switch (counter){

                    case 0:
                        Glide.with(getApplicationContext()).load(temples.getImgUrl2()).into(img_Story);
                        Glide.with(getApplicationContext()).load(temples.getImgUrl2()).into(timage);
                        counter++;
                        break;

                    case 1:
                        Glide.with(getApplicationContext()).load(temples.getImgUrl3()).into(img_Story);
                        Glide.with(getApplicationContext()).load(temples.getImgUrl3()).into(timage);
                        counter++;
                        break;

                    case 2:
                        Glide.with(getApplicationContext()).load(temples.getImgUrl4()).into(img_Story);
                        Glide.with(getApplicationContext()).load(temples.getImgUrl4()).into(timage);
                        counter++;
                        break;

                    case 3:
                        Glide.with(getApplicationContext()).load(temples.getImgUrl5()).into(img_Story);
                        Glide.with(getApplicationContext()).load(temples.getImgUrl5()).into(timage);
                        counter++;
                        break;
                }
            }

            @Override
            public void onPrev() {

                switch (counter-1){

                    case 0:
                        Glide.with(getApplicationContext()).load(temples.getImgUrl1()).into(img_Story);
                        Glide.with(getApplicationContext()).load(temples.getImgUrl1()).into(timage);
                        counter--;
                        break;
                    case 1:
                        Glide.with(getApplicationContext()).load(temples.getImgUrl2()).into(img_Story);
                        Glide.with(getApplicationContext()).load(temples.getImgUrl2()).into(timage);
                        counter--;
                        break;

                    case 2:
                        Glide.with(getApplicationContext()).load(temples.getImgUrl3()).into(img_Story);
                        Glide.with(getApplicationContext()).load(temples.getImgUrl3()).into(timage);
                        counter--;
                        break;

                    case 3:
                        Glide.with(getApplicationContext()).load(temples.getImgUrl4()).into(img_Story);
                        Glide.with(getApplicationContext()).load(temples.getImgUrl4()).into(timage);
                        counter--;
                        break;

                    case 4:
                        Glide.with(getApplicationContext()).load(temples.getImgUrl5()).into(img_Story);
                        Glide.with(getApplicationContext()).load(temples.getImgUrl5()).into(timage);
                        counter--;
                        break;

                }
            }

            @Override
            public void onComplete() {
                Intent intent = new Intent(StoryDisplay.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        storiesProgressView.startStories();

        final Object object = getIntent().getSerializableExtra("details");
        if( object instanceof Temples){
            temples = (Temples) object;
        }

        if(temples != null) {
            Glide.with(getApplicationContext()).load(temples.getImgUrl1()).into(timage);
            Glide.with(getApplicationContext()).load(temples.getImgUrl1()).into(img_Story);
            tname.setText(temples.getName());
        }

        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.reverse();
            }
        });
        reverse.setOnTouchListener(View::onTouchEvent);


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.skip();
            }
        });
        skip.setOnTouchListener(View::onTouchEvent);
    }

    @Override
    protected void onDestroy() {
        storiesProgressView.destroy();
        super.onDestroy();
    }
}