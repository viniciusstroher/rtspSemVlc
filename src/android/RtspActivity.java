package org.apache.cordova.rtsp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;

import android.net.Uri;
import android.view.Window;
import android.widget.MediaController;
import android.widget.VideoView;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;

import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import java.util.List;
import java.util.ArrayList;
import org.apache.cordova.rtsp.FakeR;
import java.util.Arrays;

import android.widget.Button;
import android.view.KeyEvent;



import java.lang.IllegalArgumentException;
import java.lang.SecurityException;
import java.lang.IllegalStateException;
import java.io.IOException;
import android.media.AudioManager;
public class RtspActivity extends Activity implements MediaPlayer.OnPreparedListener,SurfaceHolder.Callback{
    private final static int VideoSizeChanged = -1;
    private String link_rtsp;
    private FakeR  fakeR;
    private String        mFilePath;
    private SurfaceView   mSurface;
    private SurfaceHolder holder;
    private MediaPlayer   mediaPlayer = null;
    private int mVideoWidth;
    private int mVideoHeight;
    private String [] optionString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVideoWidth = 400;
        mVideoHeight= 400;
        
        fakeR = new FakeR(this);
        setContentView(fakeR.getId("layout", "rtsp_activity"));
        
        //PEGA PARAMETRO
        link_rtsp = getIntent().getStringExtra("LINK_RTSP");
        try{
            optionString = getIntent().getStringExtra("OPTIONS_VLC").split(" ");
        }catch(Exception e){
            Log.i("RTSP"," PARAMETRO OPTIONS_VLC ERROR: "+e.getMessage());
             RtspActivity.this.finish();

        }
        
        final Button button = (Button)findViewById(fakeR.getId("id", "button_id"));
        button.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
             // Code here executes on main thread after user presses button
            RtspActivity.this.finish();

          }
        });

        mSurface = (SurfaceView) findViewById(fakeR.getId("id", "surface"));        
        holder   = mSurface.getHolder();
        holder.addCallback(this);
        holder.setFixedSize(mVideoWidth, mVideoHeight);
    }

    @Override
    protected void onResume() {
        super.onResume();
        
    }

    @Override
    protected void onPause() {
        super.onPause();
        
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null){
            mediaPlayer.release();
        } 
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPrepared(MediaPlayer player) {
        player.start();
    }


    @Override
    public void surfaceCreated(SurfaceHolder arg0) {

      try {
           mediaPlayer = new MediaPlayer();
           mediaPlayer.setDisplay(holder);
           mediaPlayer.setDataSource(link_rtsp);
           mediaPlayer.prepare();
           mediaPlayer.setOnPreparedListener(this);
           mediaPlayer.setAudioStreamType(AudioManager.AUDIOFOCUS_NONE);

      } catch (IllegalArgumentException e) {
        Log.i("RTSP","IllegalArgumentException: "+e.getMessage());
      } catch (SecurityException e) {
        Log.i("RTSP","SecurityException: "+e.getMessage());
      } catch (IllegalStateException e) {
        Log.i("RTSP","IllegalStateException: "+e.getMessage());
      } catch (IOException e) {
        Log.i("RTSP","IOException: "+e.getMessage());
      }
     }
}