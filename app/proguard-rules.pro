# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\android\AndroidSDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#混淆时是否记录日志
-verbose
-dontshrink
#优化  不优化输入的类文件
-dontoptimize
#预校验
-dontpreverify
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*

#保护注解
-keepattributes *Annotation*,Exceptions,InnerClasses,Signature

#如果引用了v4或者v7包
-dontwarn android.support.**
-keep class android.support.** { *; }

#webView处理，项目中没有使用到webView忽略即可
-dontwarn android.webkit.WebView
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}

#这句非常重要，主要是滤掉com.meishipintu.bankoa.models.entity包下的所有.class文件不进行混淆编译
-keep class com.meishipintu.bankoa.models.entity.** {*;}

# 序列化
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#JPush
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

#Bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

# ButterKnife 混淆
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#Retrofit 混淆
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

#okhttp
-dontwarn okhttp3.**
-dontwarn okio.**

#避免混淆Exception代码
-keepattributes Exceptions

# 保留R下面的资源  
-keep class **.R$* {*;}
# 保护Native方法不被混淆
-keepclasseswithmembernames class * {native <methods>;}