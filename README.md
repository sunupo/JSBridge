
# 说明 
- demo 实现了 js 调用 native，并且获取 native 的返回值
- 若native 调用 js 并想要获取 js 的返回值：可以 native 端提供一个发事件的方法 sendEvent() 给js，native 监听事件，native 调用 js，js 完成后调用sendEvent()方法发送事件。

# 参考
> [Android JSBridge的原理与实现](https://blog.csdn.net/sbsujjbcy/article/details/50752595?spm=1001.2014.3001.5506
> [native <-互相调用-> js](https://www.cnblogs.com/sunupo/p/16647567.html)
> [【js与native通信】1 通信协议制定](https://www.cnblogs.com/sunupo/p/16647606.html)