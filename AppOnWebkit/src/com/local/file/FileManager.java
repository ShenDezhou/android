package com.local.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.net.model.ItemInterface;
import com.net.model.Po1;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml.Encoding;

public class FileManager {
	public static long maxSize=1024;
    /**
     * Save the object
     *
     * @param context context
     * @param ser     serializable object
     * @param file    cache file
     * @throws java.io.IOException IOException
     */
    @SuppressWarnings("JavaDoc")
    public static boolean saveObject(Context context,JSONObject list,
                                     String file) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(file, Context.MODE_WORLD_READABLE);
            String str = list.toString();
            fos.write(str.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Read the object
     *
     * @param context context
     * @param file    cache file
     * @return serializable object
     * @throws java.io.IOException Exception
     */
    @SuppressWarnings("JavaDoc")
    public static JSONObject readObject(Context context, String file) {
        if (!isExistDataCache(context, file))
            return null;
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(file);
            int size = 0,offset = 0;
            byte[] buffer = new byte[(int) maxSize];
            size = fis.read(buffer);
            String str = new String(buffer);
            JSONObject json = new JSONObject(str);
            return json;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            // Delete the cache file if the deserialization failed.
            if (e instanceof InvalidClassException) {
                File data = context.getFileStreamPath(file);
                //noinspection ResultOfMethodCallIgnored
                data.delete();
            }
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Save the object
     *
     * @param context context
     * @param ser     serializable object
     * @param file    cache file
     * @throws java.io.IOException IOException
     */
    @SuppressWarnings("JavaDoc")
    public static boolean saveObjects(Context context,JSONArray list,
                                     String file) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(file, Context.MODE_WORLD_READABLE);
            String str = list.toString();
            fos.write(str.getBytes());
            maxSize = str.getBytes().length;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Read the object
     *
     * @param context context
     * @param file    cache file
     * @return serializable object
     * @throws java.io.IOException Exception
     */
    @SuppressWarnings("JavaDoc")
    public static JSONArray readObjects(Context context, String file) {
        if (!isExistDataCache(context, file))
            return null;
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(file);
            int size = 0,offset = 0;
            byte[] buffer = new byte[(int) maxSize];
            size = fis.read(buffer);
            String str = new String(buffer);
            JSONArray json = new JSONArray(str);
            return json;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            // Delete the cache file if the deserialization failed.
            if (e instanceof InvalidClassException) {
                File data = context.getFileStreamPath(file);
                //noinspection ResultOfMethodCallIgnored
                data.delete();
            }
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Judge whether the cache file is readable.
     *
     * @param context   context
     * @param cachefile cache file
     * @return true if the cache file is readable, false otherwise.
     */
    public static boolean isReadDataCache(Context context, String cachefile) {
        return readObject(context, cachefile) != null;
    }

    /**
     * Juget whether the cache file exists.
     *
     * @param context   context
     * @param cachefile cache file
     * @return true if the cache data exists, false otherwise.
     */
    public static boolean isExistDataCache(Context context, String cachefile) {
        if (context == null)
            return false;
        boolean exist = false;
        File data = context.getFileStreamPath(cachefile);
        if (data.exists())
            exist = true;
        return exist;
    }

}
