package com.data.pooja.datacollector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Nwoke Fortune Chiemeziem
 * @Version 1.0
 */
public class CameraActivity extends ActionBarActivity {
    private int REQUEST_IMAGE_CAPTURE = 1;
    private GridLayout grid;
    private List<String> myImages;
    private Button takePhotoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        grid = (GridLayout) findViewById(R.id.grid);

        takePhotoButton = (Button) findViewById(R.id.takePhoto);
        myImages = new ArrayList<>();

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if(intent.resolveActivity(getPackageManager())!=null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }

    public void startEnterDataActivity(View view) {
        //must capture at least 1 image.
        if (myImages == null || myImages.isEmpty())
            Toast.makeText(this, "No image Captured", Toast.LENGTH_SHORT).show();
        else {
            Intent i = new Intent(this, EnterDataActivity.class);
            i.putExtra("type", getIntent().getStringExtra("type-of-entity"));
            for (int index = 0; index < myImages.size(); index++) {
                i.putExtra("image_" + index, myImages.get(index));
            }
            startActivity(i);
        }
    }

    /**
     * Converts the bitmap image to a string.
     * @param bmp the bitmap file.
     * @return the converted string.
     */
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    /**
     * Method called when the media activity returns.
     * @param requestcode the request code
     * @param resultcode the result code
     * @param data the Data retured (a bitmap image)
     */
    public void onActivityResult(int requestcode, int resultcode, Intent data) {
        if(requestcode == REQUEST_IMAGE_CAPTURE) {
            if(resultcode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                Bitmap BMP = (Bitmap)bundle.get("data");
                myImages.add(getStringImage(BMP));

                ImageView v = new ImageView(this);
                v.setImageBitmap(BMP);
                grid.addView(v);
            }
        }
        // No more than 3 images !!
        if (myImages.size() == 3)
            takePhotoButton.setEnabled(false);
    }
}
