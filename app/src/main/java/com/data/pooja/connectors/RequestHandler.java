package com.data.pooja.connectors;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Fortune on 1/24/2017.
 * @version 1.0
 * Handled the Requests created by RequestCreator.
 */
public class RequestHandler {
    public static final String TAG = RequestHandler.class.getSimpleName();
    private static RequestHandler mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCxt;

    private RequestHandler(Context mCxt) {
        mInstance = this;
        this.mCxt = mCxt;
    }

    /**
     * Returns the current Request handler Instance, or creates one if none exists.
     * @param cxt teh application context
     * @return the request handler.
     */
    public static synchronized RequestHandler getInstance(Context cxt) {
        if (mInstance == null) {
            mInstance = new RequestHandler(cxt);
        }
        return mInstance;
    }

    /* Retures the queue of all created request. */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCxt.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Cancels the request with the tag.
     * @param tag the tag whose reuqest is going to be cancelled.
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    /**
     * Add the request to the request queue.
     * @param req the request to be added to the queue.
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
    /**
     * Add the request to the request queue.
     * @param req the request to be added to the queue.
     * @param tag the tag of the reuqest.
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
}
