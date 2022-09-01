package com.example.jsbridge.webclient;

import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.jsbridge.JSBridge;

import static android.content.ContentValues.TAG;

public class JSBridgeWebChromeClient extends WebChromeClient {
    @Override
    public boolean onJsPrompt(WebView view,
                              String url, /* file:///android_asset/index.html */
                              String message/* JSBridge://bridge:389256501/showToast?{"msg":"Hello JSBridge"}*/,
                              String defaultValue,
                              JsPromptResult result) {
        Log.d(TAG, "onJsPrompt: "+ url+"--\t"+message+"--\t"+defaultValue+"--\t"+result);
        result.confirm(JSBridge.callJava(view, message));
//        result.confirm("JSBridge.callJava(view, message)");
        return true;
    }
}
