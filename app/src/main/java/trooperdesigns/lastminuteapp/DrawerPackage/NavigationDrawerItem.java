package trooperdesigns.lastminuteapp.DrawerPackage;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by james on 15-06-13.
 */
@AllArgsConstructor @Data
public class NavigationDrawerItem {

    private Drawable icon;
    private String menuItemText;
    private Fragment fragment;

}