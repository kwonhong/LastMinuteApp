package trooperdesigns.lastminuteapp.UtilPackage;

import android.content.Context;

/**
 * Created by james on 15-08-09.
 */
public class Utils {

    public static String getString(Context context, int keyID) {
        return context.getResources().getString(keyID);
    }
}
