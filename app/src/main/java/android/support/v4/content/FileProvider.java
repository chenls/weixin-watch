/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ContentProvider
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.pm.ProviderInfo
 *  android.content.res.XmlResourceParser
 *  android.database.Cursor
 *  android.database.MatrixCursor
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Environment
 *  android.os.ParcelFileDescriptor
 *  android.text.TextUtils
 *  android.webkit.MimeTypeMap
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.v4.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

public class FileProvider
extends ContentProvider {
    private static final String ATTR_NAME = "name";
    private static final String ATTR_PATH = "path";
    private static final String[] COLUMNS = new String[]{"_display_name", "_size"};
    private static final File DEVICE_ROOT = new File("/");
    private static final String META_DATA_FILE_PROVIDER_PATHS = "android.support.FILE_PROVIDER_PATHS";
    private static final String TAG_CACHE_PATH = "cache-path";
    private static final String TAG_EXTERNAL = "external-path";
    private static final String TAG_EXTERNAL_CACHE = "external-cache-path";
    private static final String TAG_EXTERNAL_FILES = "external-files-path";
    private static final String TAG_FILES_PATH = "files-path";
    private static final String TAG_ROOT_PATH = "root-path";
    private static HashMap<String, PathStrategy> sCache = new HashMap();
    private PathStrategy mStrategy;

    private static File buildPath(File file, String ... stringArray) {
        for (String string2 : stringArray) {
            if (string2 == null) continue;
            file = new File(file, string2);
        }
        return file;
    }

    private static Object[] copyOf(Object[] objectArray, int n2) {
        Object[] objectArray2 = new Object[n2];
        System.arraycopy(objectArray, 0, objectArray2, 0, n2);
        return objectArray2;
    }

    private static String[] copyOf(String[] stringArray, int n2) {
        String[] stringArray2 = new String[n2];
        System.arraycopy(stringArray, 0, stringArray2, 0, n2);
        return stringArray2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static PathStrategy getPathStrategy(Context context, String string2) {
        HashMap<String, PathStrategy> hashMap = sCache;
        synchronized (hashMap) {
            PathStrategy pathStrategy;
            PathStrategy pathStrategy2 = pathStrategy = sCache.get(string2);
            if (pathStrategy == null) {
                try {
                    pathStrategy2 = FileProvider.parsePathStrategy(context, string2);
                    sCache.put(string2, pathStrategy2);
                }
                catch (IOException iOException) {
                    throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", iOException);
                }
                catch (XmlPullParserException xmlPullParserException) {
                    throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", xmlPullParserException);
                }
            }
            return pathStrategy2;
        }
    }

    public static Uri getUriForFile(Context context, String string2, File file) {
        return FileProvider.getPathStrategy(context, string2).getUriForFile(file);
    }

    private static int modeToMode(String string2) {
        if ("r".equals(string2)) {
            return 0x10000000;
        }
        if ("w".equals(string2) || "wt".equals(string2)) {
            return 0x2C000000;
        }
        if ("wa".equals(string2)) {
            return 0x2A000000;
        }
        if ("rw".equals(string2)) {
            return 0x38000000;
        }
        if ("rwt".equals(string2)) {
            return 0x3C000000;
        }
        throw new IllegalArgumentException("Invalid mode: " + string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static PathStrategy parsePathStrategy(Context context, String object) throws IOException, XmlPullParserException {
        SimplePathStrategy simplePathStrategy = new SimplePathStrategy((String)object);
        XmlResourceParser xmlResourceParser = context.getPackageManager().resolveContentProvider((String)object, 128).loadXmlMetaData(context.getPackageManager(), META_DATA_FILE_PROVIDER_PATHS);
        if (xmlResourceParser == null) {
            throw new IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data");
        }
        int n2;
        while ((n2 = xmlResourceParser.next()) != 1) {
            if (n2 != 2) continue;
            File[] fileArray = xmlResourceParser.getName();
            String string2 = xmlResourceParser.getAttributeValue(null, ATTR_NAME);
            String string3 = xmlResourceParser.getAttributeValue(null, ATTR_PATH);
            Object var3_5 = null;
            if (TAG_ROOT_PATH.equals(fileArray)) {
                object = DEVICE_ROOT;
            } else if (TAG_FILES_PATH.equals(fileArray)) {
                object = context.getFilesDir();
            } else if (TAG_CACHE_PATH.equals(fileArray)) {
                object = context.getCacheDir();
            } else if (TAG_EXTERNAL.equals(fileArray)) {
                object = Environment.getExternalStorageDirectory();
            } else if (TAG_EXTERNAL_FILES.equals(fileArray)) {
                fileArray = ContextCompat.getExternalFilesDirs(context, null);
                object = var3_5;
                if (fileArray.length > 0) {
                    object = fileArray[0];
                }
            } else {
                object = var3_5;
                if (TAG_EXTERNAL_CACHE.equals(fileArray)) {
                    fileArray = ContextCompat.getExternalCacheDirs(context);
                    object = var3_5;
                    if (fileArray.length > 0) {
                        object = fileArray[0];
                    }
                }
            }
            if (object == null) continue;
            simplePathStrategy.addRoot(string2, FileProvider.buildPath((File)object, string3));
        }
        return simplePathStrategy;
    }

    public void attachInfo(Context context, ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo);
        if (providerInfo.exported) {
            throw new SecurityException("Provider must not be exported");
        }
        if (!providerInfo.grantUriPermissions) {
            throw new SecurityException("Provider must grant uri permissions");
        }
        this.mStrategy = FileProvider.getPathStrategy(context, providerInfo.authority);
    }

    public int delete(Uri uri, String string2, String[] stringArray) {
        if (this.mStrategy.getFileForUri(uri).delete()) {
            return 1;
        }
        return 0;
    }

    public String getType(Uri object) {
        int n2 = ((File)(object = this.mStrategy.getFileForUri((Uri)object))).getName().lastIndexOf(46);
        if (n2 >= 0) {
            object = ((File)object).getName().substring(n2 + 1);
            object = MimeTypeMap.getSingleton().getMimeTypeFromExtension((String)object);
            if (object != null) {
                return object;
            }
        }
        return "application/octet-stream";
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("No external inserts");
    }

    public boolean onCreate() {
        return true;
    }

    public ParcelFileDescriptor openFile(Uri uri, String string2) throws FileNotFoundException {
        return ParcelFileDescriptor.open((File)this.mStrategy.getFileForUri(uri), (int)FileProvider.modeToMode(string2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public Cursor query(Uri matrixCursor, String[] matrixCursor2, String object, String[] stringArray, String object2) {
        object = this.mStrategy.getFileForUri((Uri)matrixCursor);
        matrixCursor = matrixCursor2;
        if (matrixCursor2 == null) {
            matrixCursor = COLUMNS;
        }
        stringArray = new String[((Object[])matrixCursor).length];
        matrixCursor2 = new Object[((Object[])matrixCursor).length];
        int n2 = ((Object[])matrixCursor).length;
        int n3 = 0;
        int n4 = 0;
        while (true) {
            int n5;
            if (n3 >= n2) {
                matrixCursor = FileProvider.copyOf(stringArray, n4);
                matrixCursor2 = FileProvider.copyOf((Object[])matrixCursor2, n4);
                matrixCursor = new MatrixCursor((String[])matrixCursor, 1);
                matrixCursor.addRow((Object[])matrixCursor2);
                return matrixCursor;
            }
            object2 = matrixCursor[n3];
            if ("_display_name".equals(object2)) {
                stringArray[n4] = "_display_name";
                n5 = n4 + 1;
                matrixCursor2[n4] = ((File)object).getName();
                n4 = n5;
            } else if ("_size".equals(object2)) {
                stringArray[n4] = "_size";
                n5 = n4 + 1;
                matrixCursor2[n4] = Long.valueOf(((File)object).length());
                n4 = n5;
            }
            ++n3;
        }
    }

    public int update(Uri uri, ContentValues contentValues, String string2, String[] stringArray) {
        throw new UnsupportedOperationException("No external updates");
    }

    static interface PathStrategy {
        public File getFileForUri(Uri var1);

        public Uri getUriForFile(File var1);
    }

    static class SimplePathStrategy
    implements PathStrategy {
        private final String mAuthority;
        private final HashMap<String, File> mRoots = new HashMap();

        public SimplePathStrategy(String string2) {
            this.mAuthority = string2;
        }

        public void addRoot(String string2, File file) {
            if (TextUtils.isEmpty((CharSequence)string2)) {
                throw new IllegalArgumentException("Name must not be empty");
            }
            try {
                File file2 = file.getCanonicalFile();
                this.mRoots.put(string2, file2);
            }
            catch (IOException iOException) {
                throw new IllegalArgumentException("Failed to resolve canonical path for " + file, iOException);
            }
        }

        @Override
        public File getFileForUri(Uri object) {
            Object object2 = object.getEncodedPath();
            int n2 = ((String)object2).indexOf(47, 1);
            Object object3 = Uri.decode((String)((String)object2).substring(1, n2));
            object2 = Uri.decode((String)((String)object2).substring(n2 + 1));
            if ((object3 = this.mRoots.get(object3)) == null) {
                throw new IllegalArgumentException("Unable to find configured root for " + object);
            }
            object = new File((File)object3, (String)object2);
            try {
                object2 = ((File)object).getCanonicalFile();
                if (!((File)object2).getPath().startsWith(((File)object3).getPath())) {
                    throw new SecurityException("Resolved path jumped beyond configured root");
                }
            }
            catch (IOException iOException) {
                throw new IllegalArgumentException("Failed to resolve canonical path for " + object);
            }
            return object2;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Uri getUriForFile(File object) {
            void var2_8;
            Iterator<Map.Entry<String, File>> iterator;
            String string2;
            try {
                string2 = ((File)object).getCanonicalPath();
                object = null;
                iterator = this.mRoots.entrySet().iterator();
            }
            catch (IOException iOException) {
                throw new IllegalArgumentException("Failed to resolve canonical path for " + object);
            }
            while (iterator.hasNext()) {
                Map.Entry<String, File> entry = iterator.next();
                String string3 = entry.getValue().getPath();
                if (!string2.startsWith(string3) || object != null && string3.length() <= object.getValue().getPath().length()) continue;
                object = entry;
            }
            if (object == null) {
                throw new IllegalArgumentException("Failed to find configured root that contains " + string2);
            }
            String string4 = ((File)object.getValue()).getPath();
            if (string4.endsWith("/")) {
                String string5 = string2.substring(string4.length());
            } else {
                String string6 = string2.substring(string4.length() + 1);
            }
            object = Uri.encode((String)object.getKey()) + '/' + Uri.encode((String)var2_8, (String)"/");
            return new Uri.Builder().scheme("content").authority(this.mAuthority).encodedPath((String)object).build();
        }
    }
}

