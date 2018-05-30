package Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Absar Uddin on 1/12/2016.
 */
public class MediaStoreUtils {

    Cursor cursor;
    int column_index;
    // Declare our Views, so we can access them later
    String imagePath;

    private MediaStoreUtils() {
    }

//    public static Intent getPickImageIntent(final Context context) {
//        final Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        return Intent.createChooser(intent, "Select picture");
//    }

    public static Intent getPickImageIntent(final Context context)  {
        Intent pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        pictureActionIntent.setType("image/*");
        pictureActionIntent.putExtra("return-data", false);
        return  Intent.createChooser(pictureActionIntent, "Select picture");
    }

    //UPDATED!
    public static String getPath(Uri uri ,Context context) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(uri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String getPath(Uri uri, Activity c) {
        String selectedImagePath;
        // 1:MEDIA GALLERY --- query from MediaStore.Images.Media.DATA
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = c.managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            selectedImagePath = cursor.getString(column_index);
        } else {
            selectedImagePath = null;
        }

        if (selectedImagePath == null) {
            // 2:OI FILE Manager --- call method: uri.getPath()
            selectedImagePath = uri.getPath();
        }
        return selectedImagePath;
    }

    public static Uri uriImage;
    public static Intent setCameraIntent(Activity c) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file =   getOutputPhotoFile(c);
        uriImage = Uri.fromFile(file);
        i.putExtra(MediaStore.EXTRA_OUTPUT,  uriImage);
        return Intent.createChooser(i, "Capture Camera");
    }

    private static File getOutputPhotoFile(Activity c) {
        File directory = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                c.getPackageName());
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                //Log.e("TAG", "Failed to create storage directory.");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.UK)
                .format(new Date());
        return new File(directory.getPath() + File.separator + "IMG_"
                + timeStamp + ".png");
    }

    /**
     * This utility method used for rotate picked image from Gallery or Camera.
     * @param imageFile
     * @return
     */
    public synchronized static String rotateImageFile(File imageFile) {
        Bitmap b = null;
        File image = null;
        int IMAGE_MAX_SIZE = 512;
        int h, w;
        System.gc();
        Runtime.getRuntime().freeMemory();
        // Decode image size
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream fis = new FileInputStream(imageFile);
            try {
                BitmapFactory.decodeStream(fis, null, o);
            } catch (OutOfMemoryError e) {
                BitmapFactory.decodeStream(fis, null, o);
            }
            fis.close();
            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(
                        2,
                        (int) Math.round(Math.log(IMAGE_MAX_SIZE
                                / (double) Math.max(o.outHeight, o.outWidth))
                                / Math.log(0.3)));
            }
            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(imageFile);
            try {
                b = BitmapFactory.decodeStream(fis, null, o2);
                h = b.getHeight();
                w = b.getWidth();
            } catch (OutOfMemoryError e) {
                b = BitmapFactory.decodeStream(fis, null, o2);
                h = b.getHeight();
                w = b.getWidth();
            }
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            int rotate = 0;
            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }
            Matrix mtx = new Matrix();
            mtx.postRotate(rotate);
            try {
                b = Bitmap.createBitmap(b, 0, 0, w, h, mtx, true);
            } catch (OutOfMemoryError e) {
                b = Bitmap.createBitmap(b, 0, 0, w, h, mtx, true);
            }
            // b = b.copy(Bitmap.Config.ARGB_8888, true);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.PNG, 100 /* ignored for PNG */, bos);
            byte[] bitmapdata = bos.toByteArray();
            String imageName = imageFile.getName();
            image = new File(imageFile.getAbsolutePath());
            image.createNewFile();
            FileOutputStream fos = new FileOutputStream(image);
            fos.write(bitmapdata);
            bos.flush();
            bos.close();
            fos.close();
            b.recycle();
            System.gc();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image.getAbsolutePath();
    }
}
