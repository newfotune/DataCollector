package com.data.pooja.datacollector;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.data.pooja.connectors.GPSTracker;
import com.data.pooja.connectors.RequestCreator;
import com.data.pooja.connectors.RequestHandler;
import com.data.pooja.connectors.SQLiteHandler;
import com.data.pooja.objects.Entity;
import com.data.pooja.objects.GasStation;
import com.data.pooja.objects.Resturant;
import com.data.pooja.objects.RoadConstruction;
import com.data.pooja.objects.StopSign;
import com.data.pooja.objects.TrafficCamera;
import com.data.pooja.objects.TrafficLight;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Nwoke Fortune Chiemeziem.
 * Activity for the uset to enter the required.
 */
public class EnterDataActivity extends ActionBarActivity {

    private GPSTracker tracker;
    private String longitude_text;
    private String latitude_text;
    private String objectType;
    private ProgressDialog progressDialog;
    private String uid = "-1";
    private String brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_data);
        progressDialog = new ProgressDialog(this);

        TextView type = (TextView) findViewById(R.id.type);
        objectType = getIntent().getStringExtra("type");
        type.setText(objectType);

        uid = new SQLiteHandler(getApplicationContext()).getUserDetails().get("user_id");
        setUpLocation();
        EditText brandEditText = ((EditText) findViewById(R.id.brand));
        if (!isBrandValid()) { //if entity doesn't have brand type
            ((ViewGroup) brandEditText.getParent()).removeView(brandEditText);
        }

        EditText lat = (EditText)findViewById(R.id.latitude_editText);
        EditText lon = (EditText) findViewById(R.id.longitude_editText);
        lat.setText(latitude_text);
        lon.setText(longitude_text);
    }

    /**
     * Checks of the entity type required the brand EditText.
     * @return true if Restaurant or gas station, false other wise.
     */
    private boolean isBrandValid() {
        return objectType.equals(MainActivity.GAS_STATION) || objectType.equals(MainActivity.RESTAURANT);
    }

    /**
     * Gets and loads the current latitude and longitude.
     */
    private void setUpLocation() {
        tracker = new GPSTracker(this);
        if (tracker.canGetLocation()) {
            longitude_text = tracker.getLongitude()+"";
            latitude_text = tracker.getLatitude()+"";
        } else {
            longitude_text = latitude_text = "Unavailable";
            tracker.showSettingsAlert();
        }
    }

    /**
     * Uploades the data to the database online.
     */
    private void uploadData() {
        String description = ((EditText) findViewById(R.id.description)).getText().toString();
        if (isBrandValid())
            brand = ((EditText) findViewById(R.id.brand)).getText().toString();

        if (isBrandValid() && brand.isEmpty() && (objectType.equalsIgnoreCase(MainActivity.GAS_STATION)
                || objectType.equalsIgnoreCase(MainActivity.RESTAURANT))) {
            Toast.makeText(this, "You must enter a brand for " + objectType, Toast.LENGTH_LONG).show();
        } else {
            //pass in arrayList of images instead.
            Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
            List<String> images = new ArrayList<>();
            for (int i = 0; i < 3;i++) {
                if (getIntent().getStringExtra("image_"+i) == null)
                    break;
                else
                    images.add(getIntent().getStringExtra("image_"+i));
            }

            switch (objectType) {
                case MainActivity.GAS_STATION:
                    uploadEntity(new GasStation(currentDate, Float.valueOf(latitude_text),
                            Float.valueOf(longitude_text), description, images, brand));
                    break;
                case MainActivity.RESTAURANT:
                    uploadEntity(new Resturant(currentDate, Float.valueOf(latitude_text),
                            Float.valueOf(longitude_text), description, images, brand));
                    break;
                case MainActivity.STOP_SIGN:
                    uploadEntity(new StopSign(currentDate, Float.valueOf(latitude_text),
                            Float.valueOf(longitude_text), description, images));
                    break;
                case MainActivity.TRAFFIC_CAMERA:
                    uploadEntity(new TrafficCamera(currentDate, Float.valueOf(latitude_text),
                            Float.valueOf(longitude_text), description, images));
                    break;
                case MainActivity.TRAFFIC_LIGHT:
                    uploadEntity(new TrafficLight(currentDate, Float.valueOf(latitude_text),
                            Float.valueOf(longitude_text), description, images));
                    break;
                case MainActivity.ROAD_CONSTRUCTION:
                    uploadEntity(new RoadConstruction(currentDate, Float.valueOf(latitude_text),
                            Float.valueOf(longitude_text), description, images));
                    break;
            }
        }
    }

    /**
     * Uploads the entity to the MySQL database.
     * @param entity the eneity to be uploaded.
     */
    private void uploadEntity(final Entity entity) {
        progressDialog.setMessage("Adding "+ objectType + "...");
        progressDialog.show();

        StringRequest stringRequest = RequestCreator.createRequest(uid, getApplicationContext(), progressDialog, entity);
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void upload(View view) {
        uploadData();
    }
}
