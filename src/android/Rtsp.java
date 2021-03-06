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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import  android.util.Log;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
public class Rtsp extends CordovaPlugin {
    private static final String LOG_TAG = "Rtsp";

    private CallbackContext callbackContext;
    private JSONObject params;

    /**
     * Remember last device orientation to detect orientation changes.
     */
    private int orientation;

    // Helper to be compile-time compatible with both Cordova 3.x and 4.x.
    private View getView() {
        try {
            return (View)webView.getClass().getMethod("getView").invoke(webView);
        } catch (Exception e) {
            return (View)webView;
        }
    }

    @Override
    protected void pluginInitialize() {

    }

    @Override
    public void onPause(boolean multitasking) {

    }

    @Override
    public void onDestroy() {

    }


    //COMANDO EXECUTE
    //AQUI FICAO AS ACOES
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    
        if (action.equals("abrirRtsp")) {
            //pega parametros do js
            
            this.params = args.getJSONObject(0);
            Intent intent = new Intent(cordova.getActivity(), RtspActivity.class);
            try{
                String params          = this.params.getString("params");
                Log.i("RTSP"," PARAMETRO OPTIONS_VLC: "+params);
                intent.putExtra("OPTIONS_VLC", params);
            }catch(Exception e){
                Log.i("RTSP"," PARAMETRO OPTIONS_VLC ERROR: "+e.getMessage());
            }
            //LINK PARA ENVIAR PARA A ACTIVITY
            intent.putExtra("LINK_RTSP", this.params.getString("link"));

            if (this.cordova != null) {
                this.cordova.startActivityForResult((CordovaPlugin) this, intent, 0);
            }
        }

        callbackContext.success();
        return true;
    }


    //ENVIO DE MENSAGEMS A CLASSE
    @Override
    public Object onMessage(String id, Object data) {
        return null;
    }

    // Don't add @Override so that plugin still compiles on 3.x.x for a while
    public void onConfigurationChanged(Configuration newConfig) {
       
    }

}
