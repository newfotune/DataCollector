package com.data.pooja.connectors;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.data.pooja.datacollector.MainActivity;
import com.data.pooja.objects.Entity;
import com.data.pooja.objects.GasStation;
import com.data.pooja.objects.Resturant;
import com.data.pooja.objects.RoadConstruction;
import com.data.pooja.objects.StopSign;
import com.data.pooja.objects.TrafficCamera;
import com.data.pooja.objects.TrafficLight;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fortune on 2/13/2017.
 * @version 1.0
 * Creates a POST or GET request.
 */
public class RequestCreator {

    public static StringRequest createRequest(final String uid, final Context context, final ProgressDialog pd, final Entity entity) {

        if (entity instanceof GasStation)
            return createGasStationPost(uid, context, pd, (GasStation) entity);
        if (entity instanceof Resturant)
            return createRestaurantPost(uid, context, pd, (Resturant) entity);
        if (entity instanceof StopSign)
            return createStopSignPost(uid, context, pd, (StopSign) entity);
        if (entity instanceof TrafficCamera)
            return createTrafficCameraPost(uid, context, pd, (TrafficCamera) entity);
        if (entity instanceof TrafficLight)
            return createTrafficLightPost(uid, context, pd, (TrafficLight) entity);
        if (entity instanceof RoadConstruction)
            return createRoadConstructionPost(uid, context, pd, (RoadConstruction) entity);
        else
            return null;
    }

    private static StringRequest createRoadConstructionPost(final String uid, final Context context,
                                                        final ProgressDialog pd, final RoadConstruction construction) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_UPLOAD, getResponseListener(context, pd) , getErrorListener(context, pd)) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("type", MainActivity.ROAD_CONSTRUCTION);
                params.put("date", construction.getDateCreated().toString());
                params.put("geolat", construction.getLatitude()+"");

                params.put("geolong", construction.getLongitude()+"");
                params.put("user", uid);

                params.put("note", construction.getNote());
                params.put("entity_type", 6+"");

                List<String> images = construction.getImages();
                for (int i =0; i < images.size(); i++)
                    params.put("image_"+i, images.get(i));
                return params;
            }
        };
        return stringRequest;
    }

    private static StringRequest createTrafficLightPost(final String uid, final Context context,
                                                        final ProgressDialog pd, final TrafficLight light) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_UPLOAD, getResponseListener(context, pd) , getErrorListener(context, pd)) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("type", MainActivity.TRAFFIC_LIGHT);
                params.put("date", light.getDateCreated().toString());
                params.put("geolat", light.getLatitude()+"");

                params.put("geolong", light.getLongitude()+"");
                params.put("user", uid);

                params.put("note", light.getNote());
                params.put("entity_type", 4+"");

                List<String> images = light.getImages();
                for (int i =0; i < images.size(); i++)
                    params.put("image_"+i, images.get(i));
                return params;
            }
        };
        return stringRequest;
    }

    private static StringRequest createTrafficCameraPost(final String uid, final Context context,
                                                    final ProgressDialog pd, final TrafficCamera camera) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_UPLOAD, getResponseListener(context, pd) , getErrorListener(context, pd)) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("type", MainActivity.TRAFFIC_CAMERA);
                params.put("date", camera.getDateCreated().toString());
                params.put("geolat", camera.getLatitude()+"");

                params.put("geolong", camera.getLongitude()+"");

                List<String> images = camera.getImages();
                for (int i =0; i < images.size(); i++)
                    params.put("image_"+i, images.get(i));

                params.put("user", uid);

                params.put("note", camera.getNote());
                params.put("entity_type", 5+"");
                return params;
            }
        };
        return stringRequest;
    }

    private static StringRequest createStopSignPost(final String uid, final Context context,
                                                    final ProgressDialog pd, final StopSign stopsign) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_UPLOAD, getResponseListener(context, pd) , getErrorListener(context, pd)) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("type", MainActivity.STOP_SIGN);
                params.put("date", stopsign.getDateCreated().toString());
                params.put("geolat", stopsign.getLatitude()+"");

                params.put("geolong", stopsign.getLongitude()+"");

                List<String> images = stopsign.getImages();
                for (int i =0; i < images.size(); i++)
                    params.put("image_"+i, images.get(i));
                params.put("user", uid);

                params.put("note", stopsign.getNote());
                params.put("entity_type", 3+"");
                return params;
            }
        };
        return stringRequest;
    }

    private static StringRequest createRestaurantPost(final String uid, final Context context,
                                                        final ProgressDialog pd, final Resturant res) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_UPLOAD, getResponseListener(context, pd) , getErrorListener(context, pd)) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("type", MainActivity.RESTAURANT);
                params.put("date", res.getDateCreated().toString());
                params.put("geolat", res.getLatitude()+"");

                params.put("geolong", res.getLongitude()+"");
                List<String> images = res.getImages();
                for (int i =0; i < images.size(); i++)
                    params.put("image_"+i, images.get(i));
                params.put("user", uid);

                params.put("note", res.getNote());
                params.put("entity_type", 2+"");
                params.put("brand", res.getBrand());
                return params;
            }
        };
        return stringRequest;
    }

    private static StringRequest createGasStationPost(final String uid, final Context context,
                                                      final ProgressDialog pd, final GasStation station) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_UPLOAD, getResponseListener(context, pd) , getErrorListener(context, pd)) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("type", MainActivity.GAS_STATION);
                params.put("date", station.getDateCreated().toString());
                params.put("geolat", station.getLatitude()+"");

                params.put("geolong", station.getLongitude()+"");
                List<String> images = station.getImages();
                for (int i =0; i < images.size(); i++)
                    params.put("image_"+i, images.get(i));
                params.put("user", uid);

                params.put("note", station.getNote());
                params.put("entity_type", 1+"");
                params.put("brand", station.getBrand());
                return params;
            }
        };
        return stringRequest;
    }

    private static Response.Listener<String> getResponseListener(final Context context, final ProgressDialog pd) {
        Response.Listener<String> rs = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSON error", "Did not recieve JSON back");
                }
            }
        };
        return rs;
    }

    private static Response.ErrorListener getErrorListener(final Context context, final ProgressDialog pd) {
        Response.ErrorListener re = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.hide();
                        Log.e("Error", "Error was reported");
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                };
        return re;
    }
}
