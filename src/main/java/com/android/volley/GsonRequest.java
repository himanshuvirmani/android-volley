package com.android.volley;

import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

public abstract class GsonRequest<S, T> extends Request<T> {

  private final Type mType;
  private final Response.Listener<T> mListener;
  private final Map<String, String> mHeaders;
  private Gson mGson;
  private S mPayload;
  private String mURL;

  public GsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener,
      ErrorListener errorListener, Map<String, String> headers, S payload) {
    super(method, url, errorListener);
    VolleyLog.v("Invoking GsonRequest for " + url);
    VolleyLog.v(clazz.getSimpleName()
            + " : method="
            + method
            + " : url= "
            + url
            + " : headers = "
            + headers
            + " : mParams = "
            + payload);

    this.mType = clazz;
    this.mListener = listener;
    this.mURL = url;
    this.mHeaders = headers;
    this.mPayload = payload;
  }

  public GsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener,
      ErrorListener errorListener, Map<String, String> headers) {
    this(method, url, clazz, listener, errorListener, headers, null);
  }

  public GsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener,
      ErrorListener errorListener) {
    this(method, url, clazz, listener, errorListener, null, null);
  }

  public GsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener,
      ErrorListener errorListener, S payload) {
    this(method, url, clazz, listener, errorListener, null, payload);
  }

  /**
   * Set this when your gson strategy is different from camel case. e.g. lower case with underscore
   */
  protected Gson getGsonInstance() {
    if (mGson == null) mGson = new GsonBuilder().create();
    return mGson;
  }

  @Override public String getBodyContentType() {
    return "application/json";
  }

  @Override public Map<String, String> getHeaders() throws AuthFailureError {
    return mHeaders != null ? mHeaders : super.getHeaders();
  }

  /*private void setTimeout(int timeout) {

    RetryPolicy policy = new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    setRetryPolicy(policy);
  }*/

  @Override public byte[] getBody() throws AuthFailureError {
    try {
      return getGsonInstance().toJson(mPayload).getBytes(getParamsEncoding());
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      VolleyLog.e(e.toString());
    }
    return null;
  }

  @Override protected void deliverResponse(T response) {
    mListener.onResponse(response);
  }

  @Override
  protected com.android.volley.Response<T> parseNetworkResponse(NetworkResponse response) {
    try {
      String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
      VolleyLog.v("Receiving response for " + mURL);
      VolleyLog.v("parseNetworkResponse : "
          + mType
          + " :url="
          + mURL
          + " :statusCode="
          + response.statusCode
          + " :JSON= "
          + json);

      return com.android.volley.Response.success((T) getGsonInstance().fromJson(json, mType),
          HttpHeaderParser.parseCacheHeaders(response));
    } catch (UnsupportedEncodingException e) {
      return com.android.volley.Response.error(new ParseError(e));
    } catch (JsonSyntaxException e) {
      return com.android.volley.Response.error(new ParseError(e));
    }
  }

/*  public static class GsonRequestBuider<S,T> {

    private final Method method;
    private final String mURL;
    private final Type mType;
    private final Response.Listener<T> mListener;
    private final ErrorListener errorListener;
    private Map<String, String> mHeaders;
    private S mPayload;
    private Gson mGson;

    public GsonRequestBuider(Method method, String mURL, Type mType, Response.Listener<T> mListener,
        ErrorListener errorListener) {
      this.method = method;
      this.mURL = mURL;
      this.mType = mType;
      this.mListener = mListener;
      this.errorListener = errorListener;
    }

    public GsonRequestBuider setHeaders(Map<String,String> headers) {
      mHeaders = headers;
      return this;
    }

    *//**
   * Set your payload by converting it to gson here.
   * @param payload
   * @return
   *//*
    public GsonRequestBuider setPayload(S payload) {
      mPayload = payload;
      return this;
    }

    *//**
   * Set this when your gson strategy is different from camel case. e.g. lower case with underscore
   * @param gson
   * @return
   *//*
    public GsonRequestBuider setGson(Gson gson) {
      mGson = gson;
      return this;
    }

    public GsonRequest createRequest() {
      return new GsonRequest<S,T>(method,mURL,mType,mListener,errorListener,mHeaders,mPayload,mGson);
    }
  }*/
}