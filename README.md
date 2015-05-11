Android Volley  
----------
This is an unofficial mirror for [android volley library](https://android.googlesource.com/platform/frameworks/volley) and is forked from mcxiaoke/android-volley, the source code will synchronize periodically with the official volley repository.


## Status

Volley is already published to Maven Central.  

* [![Maven Central](https://img.shields.io/badge/2015.05.11-com.himanshuvirmani%3Avolley--okhttp--gson--library%3A1.0.1-green.svg)](http://search.maven.org/#artifactdetails%7Ccom.himanshuvirmani%7Cvolley-okhttp-gson-library%7C1.0.1%7Cjar) 


## Usage

### for Maven

```
<dependency>
    <groupId>com.himanshuvirmani</groupId>
    <artifactId>volley-okhttp-gson-library</artifactId>
    <version>1.0.1</version>
</dependency>
```


### for Gradle

``` groovy
	repositories {
		mavenCentral()
	}
```

``` groovy
dependencies {
    compile 'com.himanshuvirmani:volley-okhttp-gson-library:1.0.1'
}
```

### code sample

Create a singleton request queue for network requests via volley.

``` java
mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
mImageLoaderQueue = Volley.newRequestQueue(context.getApplicationContext());
```

To create a request

``` java
public void getPostById(Response.Listener<Post> listener, Response.ErrorListener errorListener,
      int id) {
    mRequestQueue.add(new GetPostsById(listener, errorListener, id));
}
```

Create a request Class that extends GsonRequest<RequestPayload,ResponsePojo>

``` java
public class GetPostsById extends GsonRequest<Void, Post> {

  public GetPostsById(Response.Listener<Post> listener, Response.ErrorListener errorListener,
      int id) {
    super(Method.GET,
        ApiConfig.BASE_URL + ApiConfig.GET_POST_BY_ID.replace("<ID>", String.valueOf(id)), listener,
        errorListener);
  }
}
```

#### For complete code sample check [Android Base Template Code](https://github.com/himanshuvirmani/AndroidBaseTemplate)


## Attention  

For any Gson or OkHttp issue/suggestions please write back to me at himanshuvirmani@gmail.com or open an issue here.
Apart from that this project is just a mirror of volley, if you have found any bugs or need some features, please create an issue at [AOSP Issue Tracker](https://code.google.com/p/android/issues/list).

