package com.data.pooja.datacollector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    public static final String GAS_STATION = "gas station";
    public static final String RESTAURANT = "restaurant";
    public static final String STOP_SIGN = "stop sign";
    public static final String TRAFFIC_LIGHT = "traffic light";
    public static final String TRAFFIC_CAMERA = "traffic camera";
    public static final String ROAD_CONSTRUCTION = "road construction";
    private String uid = "-1";

    private SparseArray<String> imageTypeMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageTypeMap = new SparseArray<>();

        GridView grid = (GridView) findViewById(R.id.gridview);
        final ImageAdapter ia = new ImageAdapter(this);

        loadUpMap(ia);

        grid.setAdapter(ia);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int image =  (Integer) ia.getItem(position);
                String type_of_entity = imageTypeMap.get(image);
                Intent i = getNewIntent(CameraActivity.class);
                i.putExtra("type-of-entity", type_of_entity);
                i.putExtra("uid", uid);
                startActivity(i);
            }
        });
    }

    private Intent getNewIntent(Class class_type) {
        return new Intent(this, class_type);
    }

    private String[] getIntentsName() {
        String[] intents = { GAS_STATION, RESTAURANT, STOP_SIGN, TRAFFIC_LIGHT, TRAFFIC_CAMERA, ROAD_CONSTRUCTION };
        return intents;
    }

    /**
     * Map image index with accompaniying intent in map.
     * @param ia the image adapter.
     */
    private void loadUpMap(final ImageAdapter ia) {
        String[] intents = getIntentsName();
        Integer[] pics = ia.getPictures();
        for (int i = 0; i< ia.getCount(); i++) {
            imageTypeMap.put(pics[i], intents[i]);
        }
    }
}
