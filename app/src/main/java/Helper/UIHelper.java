package Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;








import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.thebasicapp.EasyResumeBuilder.R;

/**
 * Created by Absar Uddin on 8/19/2015.
 */
public class UIHelper {

    public static void hideKeyboard(View v,Context cntxt)
    {

        if (v != null) {
            InputMethodManager imm = (InputMethodManager)cntxt.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

   

    public static void showToast(String strTxt,Context cntxt){
        Toast.makeText(cntxt,strTxt,Toast.LENGTH_SHORT).show();

    }

    /*public static String getLangSelection(Context context)
    {
//        Context context = global.getAppContext();
        if(context.getString(R.string.lang).equalsIgnoreCase("english"))
        {
            return "en";
        }
        else
            return "ar";
    }*/

    public static void networkThreadException()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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

    public static String getDate(long mDate ) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd,MMM,yyyy hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        dateFormat.setTimeZone(tz);
        return dateFormat.format(new Date(mDate*1000));
    }

    public static String getDateInNumbers(long mDate ) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        dateFormat.setTimeZone(tz);
        return dateFormat.format(new Date(mDate*1000));
    }

    public static String getDateInFormat(long mDate ) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        dateFormat.setTimeZone(tz);
        return dateFormat.format(new Date(mDate*1000));
    }

    public static Calendar getCalendarSpecifiDate(String mDate ) {

        if(mDate.isEmpty())
            return null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static String getDateFromTimeStamp(long mDate ) {


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        dateFormat.setTimeZone(tz);
        return dateFormat.format(new Date(mDate*1000));


    }

    public static String replace(Activity activity, Bitmap bmp,String paths) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        //bmp = BitmapFactory.decodeFile(paths,options);
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = paths;
        File file = new File(path);
        try {
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            ostream.write(bytes.toByteArray());
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
    
    public static void saveData(int key, String value, Context context) {
        SharedPreferences sp = context.getApplicationContext()
                .getSharedPreferences("appData", 0);
        SharedPreferences.Editor editor;
        editor = sp.edit();
        editor.putInt(value, key);
        editor.commit();
    }
    

    public static int getSaveData(String key, Context context) {
        SharedPreferences sp = context.getApplicationContext()
                .getSharedPreferences("appData", 0);
        int data = sp.getInt(key, 0);
        return data;
    }
    
    public static void saveData(String key, boolean value, Context context) {
        SharedPreferences sp = context.getApplicationContext()
                .getSharedPreferences("appData", 0);
        SharedPreferences.Editor editor;
        editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    
    public static boolean getbooleanData(String key,Context context){
    	SharedPreferences sp = context.getApplicationContext()
                .getSharedPreferences("appData", 0);
        boolean value = sp.getBoolean(key, false);
        return value;
    }
    
    public static String getDateFormat(Context context){
    	String dateFormat="";
    	int position=getSaveData(Constants.DATE_FORMAT, context);
    	switch(position){
    	case 0:
    		dateFormat=Constants.DATETYPE1;
    		break;
    	case 1:
    		dateFormat=Constants.DATETYPE2;
    		break;
    	case 2:
    		dateFormat=Constants.DATETYPE3;
    		break;
    	case 3:
    		dateFormat=Constants.DATETYPE4;
    		break;
    	}
    	return dateFormat;
    }
    
    public static boolean isDateAfter(String startDate, String endDate,Context context) {
        try {
//            String myFormatString = "yyyy-M-dd"; // for example
        	String myFormatString=getDateFormat(context);
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);

            if (date1.after(startingDate))
                return true;
            else
                return false;
        } catch (Exception e) {

            return false;
        }
    }
    
    public static Long persistDate(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }

    static Date date;
    public static String getConvertedDate(String toDisplaydate,Context context){
        String mDate="";
        try {
            date = parseDate(toDisplaydate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat format = new SimpleDateFormat(getDateFormat(context));
        mDate=format.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return mDate;
    }
    
    public static Date loadDate(Cursor cursor, int index) {
        if (cursor.isNull(index)) {
            return null;
        }
        return new Date(cursor.getLong(index));
    }
    
    public static void showAlert(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("");
        builder.setMessage(message)
                .setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).show();
    }


    public static String getsavedDate(String date,Context context){
    		String myFormatString=getDateFormat(context);
    		SimpleDateFormat df = new SimpleDateFormat(myFormatString);
    		df.setTimeZone(TimeZone.getTimeZone("UTC"));
    		Date date1 = null;
			try {
				date1 = df.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	    
    	    String outputText = df.format(date1);
    	    return outputText;
    }
        private static List<ThreadLocal<SimpleDateFormat>> threadLocals = new  ArrayList<ThreadLocal<SimpleDateFormat>>();
        static List<String> dateformat=new ArrayList<String>();
        
        public static List<ThreadLocal<SimpleDateFormat>> FlexibleDateParser(){
            threadLocals.clear();
            dateformat.add(Constants.DATETYPE1);
            dateformat.add(Constants.DATETYPE2);
            dateformat.add(Constants.DATETYPE3);
            dateformat.add(Constants.DATETYPE4);
            for (final String format : dateformat) {
                ThreadLocal<SimpleDateFormat> dateFormatTL = new ThreadLocal<SimpleDateFormat>() {
                    protected SimpleDateFormat initialValue() {
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
//                        sdf.setTimeZone(tz); 
                        sdf.setLenient(false);
                        return sdf;
                    }
                };
                threadLocals.add(dateFormatTL);
            } 
            return threadLocals;
        }
    
    public static Date parseDate(String dateStr) throws ParseException {
        for (ThreadLocal<SimpleDateFormat> tl : FlexibleDateParser()) {
            SimpleDateFormat sdf = tl.get();
            try {
                return sdf.parse(dateStr);
            } catch (ParseException e) {
                // Ignore and try next date parser
            }
        }
        // All parsers failed
        return null;
    } 
    
    public static boolean isInternetAvailable(Context context) {
    	ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	 return cm.getActiveNetworkInfo() != null;

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
			b.compress(CompressFormat.PNG, 100 /* ignored for PNG */, bos);
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

    public static String addTypetoString(Context context,String date){
        String dateFormat="";
        int position=getSaveData(Constants.DATE_FORMAT, context);
        switch(position){
            case 0:
                dateFormat="Type1/";
                break;
            case 1:
                dateFormat="Type2/";
                break;
            case 2:
                dateFormat="Type3/";
                break;
            case 3:
                dateFormat="Type4/";
                break;
        }
        return dateFormat+date;
    }
}
