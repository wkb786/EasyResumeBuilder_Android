package com.thebasicapp.EasyResumeBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Helper.Constants;
import Helper.MediaStoreUtils;
import Helper.UIHelper;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.soundcloud.android.crop.Crop;

public class Uploadimage extends BaseActivity implements TimerInterface {

    ImageView img_logo;
    Button save;
    private AdView adView;
    boolean checkgallery = false, checkcamera = false;
    DatabaseHandler db;
    Bitmap bitmap, yourSelectedImage;
    String imagepath = "", prof_id_string;
    int id;
    boolean checkforimage_profid = false;
    RotateAnimation anim;
    private int REQUEST_FOR_GALLERY = 4;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.image);
        context = this;
        adView = (AdView) findViewById(R.id.adView);
        db = new DatabaseHandler(this);
        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            String idstr = getIntent().getExtras().getString("ID");
            Profid = Integer.parseInt(idstr);
            prof_id_string = String.valueOf(Profid);
            Log.d("id sended is", "" + Profid);
            Prof pr = db.getProf(Profid);
            profilename = pr.getProfilename();
        }

        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
               // .addTestDevice(getString(R.string.testdevice))
                .build();
        adView.setAdListener(new ToastAdListener(Uploadimage.this, adView));
        adView.loadAd(adRequest);

        save = (Button) findViewById(R.id.save1);
        save.setEnabled(false);

        img_logo = (ImageView) findViewById(R.id.imageView1);

        List<Upload> upload = db.getAllUpload();
        for (Upload up : upload) {

            String prid = up.getProfid();
            if (prid.equals(prof_id_string)) {
                checkforimage_profid = true;
                imagepath = up.getImagepath();
                selectedImagePath = up.getImagepath();
                Log.d("path is imagepath", imagepath);
                Log.d("selectedImagePath ", selectedImagePath + "");
                id = up.getUPID();
                if (!(selectedImagePath.equalsIgnoreCase(""))) {
                    File imgFile = new File(selectedImagePath);
                    if (imgFile.exists()) {
                        Bitmap scaledBitmap = downSampleBitmap(selectedImagePath);
                        img_logo.setImageBitmap(scaledBitmap);

                        break;
                    } else {
                        showAlert(getString(R.string.image_not_found));
                    }

                }
            }
        }

    }

    protected void clearData() {
        img_logo.setImageBitmap(null);
        img_logo.setImageResource(R.drawable.upload_btn);
        selectedImagePath = "";
        db.deleteAllUpload(new Upload(id, selectedImagePath, prof_id_string));
    }

    @Override
    public void onDestroy() {

        db.close();
        adView.destroy();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        adView.pause();
        super.onPause();
    }

    int screenTime = 0;

    @Override
    public void onResume() {
        super.onResume();
        sendAnalyticsData("UploadPic", profilename);
        adView.resume();
        fromForm(getString(R.string.uploadphoto),
                getResources().getDrawable((R.drawable.upload_ic_w)), Uploadimage.this);
        img_logo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startDialog();
            }

        });

        timerThread = new TimerThread();
        timerThread.setTimerInterface(this);
    }

    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle(getResources().getString(
                R.string.Upload_Pictures_Option));
        myAlertDialog
                .setMessage(getResources().getString(R.string.how_see_pic));

        myAlertDialog.setPositiveButton(
                getResources().getString(R.string.Gallery),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        openGallery();
                    }
                });

        myAlertDialog.setNegativeButton(
                getResources().getString(R.string.Camera),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        RequestMultiplePermission();
                        if (CheckingPermissionIsEnabledOrNot()) {
                            PackageManager pm = Uploadimage.this
                                    .getPackageManager();
                            if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {


//                                startActivityForResult(MediaStoreUtils.setCameraIntent(Uploadimage.this),
//                                        Constants.REQUEST_IMAGE_CAPTURE);
                                Intent pictureIntent = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE
                                );
                                if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivityForResult(pictureIntent,
                                            Constants.REQUEST_IMAGE_CAPTURE);
                                }
                            } else {
                                showAlert(getString(R.string.No_camera));
                            }
                        }

                    }
                });
        myAlertDialog.show();
    }

    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(Uploadimage.this, new String[]
                {
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 7);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 7:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean WritePermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (CameraPermission && ReadPermission && WritePermission) {

                        Toast.makeText(Uploadimage.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        finish();
                        Toast.makeText(Uploadimage.this, "Permission Denied", Toast.LENGTH_LONG).show();

                    }
                } else {
                    finish();
                }

                break;
        }
    }

    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int ReadPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int WritePermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ReadPermissionResult == PackageManager.PERMISSION_GRANTED &&
                WritePermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    public void openGallery() {
        Intent intentG = new Intent();
        intentG = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentG, REQUEST_FOR_GALLERY);

    }

    Uri selectedImageUri;
    String selectedImagePath = "";

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_PICTURE) {

        } else if (requestCode == Constants.REQUEST_IMAGE_CAPTURE) {
            save.setEnabled(true);
            if (resultCode == RESULT_OK) {
                if (data != null && data.getExtras() != null) {
                    Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                    MediaStoreUtils.uriImage = getImageUri(context,imageBitmap);
                    handleCrop2(resultCode, imageBitmap,MediaStoreUtils.uriImage);
//                    MediaStoreUtils.uriImage = getImageUri(context,imageBitmap);
//                    handleCrop(resultCode, imageBitmap);
                }
//                    mImageView.setImageBitmap(imageBitmap);
                if (MediaStoreUtils.uriImage != null) {
//                    File nFile = new File(MediaStoreUtils.uriImage.getPath());
//                    MediaStoreUtils.rotateImageFile(nFile);
//                    beginCrop(Uri.fromFile(nFile));
                } else {
                    Toast.makeText(context, "Unable to pick this image.", Toast.LENGTH_LONG).show();
                }

            } else if (resultCode == RESULT_CANCELED) {
                showAlert(getString(R.string.cancled));
            }
        } else if (requestCode == REQUEST_FOR_GALLERY && resultCode == RESULT_OK) {

            Uri uri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(uri, projection, null, null, null);
            int column_index_data = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            final String capturedImageFilePath = cursor.getString(column_index_data);

            if (capturedImageFilePath != null) {
                File nFile = new File(capturedImageFilePath);
                UIHelper.rotateImageFile(nFile);
                beginCrop(Uri.fromFile(nFile));
//				performCrop(Uri.fromFile(nFile));
            } else {
                Toast.makeText(context, "Unable to pick this image.", Toast.LENGTH_LONG).show();
            }

        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }

    }

    /**
     * This method handle the cropped image. in this we get the final cropped
     * image URI in Intent object
     */
    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            selectedImagePath = Crop.getOutput(result).getPath();
            Bitmap bit = new BitmapFactory().decodeFile(selectedImagePath);
            img_logo.setBackgroundResource(0);
            img_logo.setImageBitmap(bit);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void handleCrop2(int resultCode, Bitmap b,Uri uri) {
        if (resultCode == RESULT_OK) {
            selectedImagePath = getRealPathFromURI(uri);
            img_logo.setBackgroundResource(0);
            img_logo.setImageBitmap(b);
        } else if (resultCode == Crop.RESULT_ERROR) {
        }
    }
    /**
     * This method is used for getting crop functionality. from here call the
     * Crop class.
     */
    private void beginCrop(Uri source) {
        Uri outputUri = Uri.fromFile(new File(getCacheDir(), createCameraImageFileName()));
        Crop.of(source, outputUri).asSquare().start(this);
    }

    /**
     * This method is used for create the image name.
     */
    private String createCameraImageFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        return timeStamp + ".jpg";
    }

    public Bitmap downSampleBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap map = BitmapFactory.decodeFile(path, options);
        int originalHeight = options.outHeight;
        int originalWidth = options.outWidth;
        // Calculate your sampleSize based on the requiredWidth and
        // originalWidth
        // For e.g you want the width to stay consistent at 500dp
        int requiredWidth = (int) (80 * getResources().getDisplayMetrics().density);
        int sampleSize = originalWidth / requiredWidth;
        // If the original image is smaller than required, don't sample
        if (sampleSize < 1) {
            sampleSize = 1;
        }

        options.inSampleSize = sampleSize;
        options.inPurgeable = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        return bitmap;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (selectedImagePath.equalsIgnoreCase("")) {
            super.onBackPressed();
        } else {
            save();
        }
    }

    private void save() {

        // TODO Auto-generated method stub

        String path = "";
        String checkpath = null;
        if (checkforimage_profid) {
            path = selectedImagePath;
            List<Upload> upload = db.getAllUpload();
            for (Upload up : upload) {
                checkpath = up.getImagepath();
            }
            if (path.equals(checkpath)) {
                db.updateUpload(new Upload(id, selectedImagePath, prof_id_string));
                Uploadimage.this.finish();

            } else {
                db.updateUpload(new Upload(id, selectedImagePath, prof_id_string));
                showAlert(getString(R.string.image_updated));
                Uploadimage.this.finish();

            }

        } else {
            String pathnew = selectedImagePath;
            if (pathnew.equals("") || pathnew.equals(null)) {
                Toast.makeText(
                        getApplicationContext(),
                        getResources().getString(R.string.select_image),
                        Toast.LENGTH_SHORT).show();
            } else {
                db.addUpload(new Upload(pathnew, prof_id_string));
                showAlert(getString(R.string.info_inserted));
                if (checkgallery) {

                    if (yourSelectedImage != null) {
                        yourSelectedImage.recycle();
                        yourSelectedImage = null;
                    }

                } else if (checkcamera) {
                    bitmap.recycle();
                    bitmap = null;
                }
                Uploadimage.this.finish();
            }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        timerThread.stopThread();
        //sendAnalyticsScreenTime(screenTime, Uploadimage.class.getSimpleName(), profilename);
    }

    @Override
    public void onStop(int count) {
        screenTime = count;
        Log.v("UploadImage", screenTime + "");
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}