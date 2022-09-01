# WebView

<details>
  <summary>WebView 提供了这些方法</summary>
  <p> - 提供了这些方法</p>
  <pre><code>
```
addJavascriptInterface
canGoBack
canGoBackOrForward
canGoForward
capturePicture
clearCache
clearFormData
clearHistory
clearSslPreferences
clearView
destroy
disablePlatformNotifications
documentHasImages
enablePlatformNotifications
findAddress
getContentHeight
getFavicon
getHttpAuthUsernamePassword
getProgress
getScale
getTitle
getUrl
getZoomControls
goBack
goBackOrForward
goForward
invokeZoomPicker
loadData
loadDataWithBaseURL
loadUrl
overlayHorizontalScrollbar
overlayVerticalScrollbar
pageDown
pageUp
pauseTimers
reload
requestFocusNodeHref
requestImageRef
resumeTimers
savePassword
setDownloadListener
setHorizontalScrollbarOverlay
setHttpAuthUsernamePassword
setInitialScale
setVerticalScrollbarOverlay
setWebChromeClient
setWebViewClient
stopLoading
zoomIn
zoomOut
```
</code></pre>
</details>
# 1 SetWebViewClient和 SetWebChromeClient的区别

仅仅是渲染一个HTML网页，只需要用setWebViewClient就可以了，但是如果要处理比较复杂的事务，就考虑用setWebChromeClient辅助WebView处理JavaScript的对话框，网站图标，网站title，加载进度等.

https://blog.csdn.net/u014473112/article/details/52176412

## WebChromeClient
```

WebView articleContentWebView = new WebView(this);
articleContentWebView.setWebViewClient(new WebViewClient(){
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
});
```

## WebViewClient
```
WebView articleContentWebView = new WebView(this);
articleContentWebView.setWebChromeClient(new WebChromeClient(){
  @Override
  public void onProgressChanged(WebView view, int newProgress) {
    super.onProgressChanged(view, newProgress);
  }
});
```
# 2 native 调用 js

## 网页JS供java调用的代码

```
<script type="text/javascript">
  function funWithoutParam(){
		document.getElementById("appId").innerHTML = "native 调用 js"
  }
  function funWithParam(param){
		document.getElementById("appId").innerHTML = "native 调用 js 的参数： " + param
  }
</script>
```
## java 端的代码

```
WebView webView = new WebView(this);
articleContentWebView.getSettings().setJavaScriptEnabled(true);

Button = btn = findViewById(R.id.btn);
btn.setOnCLickListener((View v)->{
	webView.loadUrl("javascript:funWithoutParam");
  String param = "native param"
	webView.loadUrl("javascript:funWithoutParam(" + param + ")");
});

//articleContentWebView.loadDataWithBaseURL(null,"ture HTML DOCS","text/html","UTF-8",null);
//articleContentWebView.loadData("ture HTML DOCS","text/html","UTF-8");
```
Webview 对象通过 loadUrl 接口来加载以 javascript 协议头的方式,就可以调用JS中定义的接口了。

# 3 js 调用native

## java 端代码
```
WebView webView = new WebView(this);
// 启用js
wenView.getSettings().setJavaScriptEnabled(true); 
// 加载本地网页 ture HTML DOCS
articleContentWebView.loadData("ture HTML DOCS","text/html","UTF-8");
// 注入对象
mMCWebInterface = new MCWebInterface(activity, webview);  
webview.addJavascriptInterface(mMCWebInterface, "MCWebInterface");
```
- mMCWebInterface: 供给 js 调用的对象

- "MCWebInterface": js 调用 native 对象时使用的名字

## 网页 js 端代码
```
<div>
	<button onClick="window.MCWebInterface.invokeStart('this is param')">
  	点击调用 native 代码
  </button>
</div>
```
# 4 注意

需要注意的是在Android4.2之前addJavascriptInterface接口存在注入漏洞，即JS可以通过反射获取到native端的其他接口，进行其他非法操作，所以4.2之后升级增加了JS只能访问带有 @JavascriptInterface注解的Java函数的限制，在本地定义的提供给JS调用的接口都需要增加@android.webkit.JavascriptInterface声明。

## 直接让小于4.2版本的系统不起作用
```
@SuppressLint("JavascriptInterface")
@Override
public void addJavascriptInterface(Object object, String name) {
  if (Build.VERSION.SDK_INT >= 17) {
    super.addJavascriptInterface(object, name);
  }
}
```
## 供给JS调用的接口增加@JavascriptInterface声明。

在本地定义的提供给JS调用的接口都需要增加@android.webkit.JavascriptInterface声明。
