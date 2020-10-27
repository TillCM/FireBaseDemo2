package com.tillster.fakestagramvideo.Permissions;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

public class PicPermissions
{


    public static boolean hasPermissions(Context context, String ... permissions)
    {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M && context != null && permissions != null);
        {
            for (String permission : permissions)
            {
                if(ActivityCompat.checkSelfPermission(context,permission)!= PackageManager.PERMISSION_GRANTED)
                {
                    return false;
                }
            }
        }

        return true;
    }

}
