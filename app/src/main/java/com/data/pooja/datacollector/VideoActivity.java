package com.data.pooja.datacollector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoActivity extends ActionBarActivity {

    private Button mRecordView, mPlayView, bUpload;
    private VideoView mVideoView;
    private int ACTIVITY_START_CAMERA_APP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mRecordView = (Button)findViewById(R.id.recordButton);
        mPlayView = (Button)findViewById(R.id.playButton);
        mVideoView = (VideoView)findViewById(R.id.videoView);
        bUpload = (Button) findViewById(R.id.bUpload);

        mRecordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent callVideoAppIntent = new Intent();
                callVideoAppIntent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);

                startActivityForResult(callVideoAppIntent,ACTIVITY_START_CAMERA_APP );

            }
        });

        mPlayView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){

                mVideoView.start();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK){
            Uri videoUri = data.getData();
            mVideoView.setVideoURI(videoUri);
        }
    }

    public void bUpload(View view)
    {
        Toast.makeText(VideoActivity.this, "Video has been uploaded", Toast.LENGTH_SHORT).show();
    }

}


