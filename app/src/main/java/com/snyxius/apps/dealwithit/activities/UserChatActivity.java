package com.snyxius.apps.dealwithit.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by snyxius on 7/12/15.
 */
public class UserChatActivity extends AppCompatActivity implements View.OnClickListener{

    boolean isShow=false;
    TextView title,desc,userId,timing,cost;
    CircleImageView proPic;
    ImageView editProfile,rightArrow,openClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        initialise();
    }

    private void initialise(){
        title=(TextView)findViewById(R.id.title);
        desc=(TextView)findViewById(R.id.desc);
        userId=(TextView)findViewById(R.id.user_id);
        timing=(TextView)findViewById(R.id.timing);
        cost=(TextView)findViewById(R.id.cost);
        proPic=(CircleImageView)findViewById(R.id.profile_pic);
        editProfile=(ImageView)findViewById(R.id.edit_profile);
        rightArrow=(ImageView)findViewById(R.id.right_arrow);
        openClose=(ImageView)findViewById(R.id.open_close);
        openClose.setOnClickListener(this);

        title.setVisibility(View.GONE);
        desc.setVisibility(View.GONE);
        userId.setVisibility(View.GONE);
        cost.setVisibility(View.GONE);
        proPic.setVisibility(View.GONE);
        editProfile.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.open_close){
            if(isShow){
                isShow=false;
                hideDatas();
            }else{
                isShow=true;
                showDatas();
            }
        }
    }


    public void hideDatas(){
        title.setVisibility(View.GONE);
        desc.setVisibility(View.GONE);
        userId.setVisibility(View.GONE);
        cost.setVisibility(View.GONE);
        proPic.setVisibility(View.GONE);
        editProfile.setVisibility(View.GONE);
        RotateAnimation anim= new RotateAnimation(0f,-180f, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        anim.setDuration(500);
        anim.setFillBefore(true);
        anim.setFillAfter(true);
        openClose.startAnimation(anim);

    }
    public void showDatas(){
        title.setVisibility(View.VISIBLE);
        desc.setVisibility(View.VISIBLE);
        userId.setVisibility(View.VISIBLE);
        cost.setVisibility(View.VISIBLE);
        proPic.setVisibility(View.VISIBLE);
        editProfile.setVisibility(View.VISIBLE);
        RotateAnimation anim= new RotateAnimation(0f,180f, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        anim.setDuration(500);
        anim.setFillAfter(true);
        anim.setFillBefore(true);
        openClose.startAnimation(anim);

    }
}
