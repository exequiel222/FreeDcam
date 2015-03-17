package com.troop.freedcam.ui.handler;

import com.troop.freedcam.ui.AppSettingsManager;
import com.troop.freedcam.ui.MainActivity_v2;
import com.troop.freedcam.ui.menu.fragments.ShutterItemFragmentNubia;
import com.troop.freedcam.ui.menu.fragments.ShutterItemsFragments;

/**
 * Created by Ingo on 14.03.2015.
 */
public class ThemeHandler
{
    AppSettingsManager appSettingsManager;
    MainActivity_v2 activity_v2;

    public ThemeHandler(MainActivity_v2 activity_v2, AppSettingsManager appSettingsManager)
    {
        this.appSettingsManager = appSettingsManager;
        this.activity_v2 = activity_v2;

    }

    public void GetThemeFragment()
    {
        String theme = appSettingsManager.getString(AppSettingsManager.SETTING_Theme);
        if (theme.equals("")) {
            theme = "Classic";
            appSettingsManager.setString(AppSettingsManager.SETTING_Theme, theme);
        }
        if (activity_v2.shutterItemsFragment != null)
        {
            android.support.v4.app.FragmentTransaction transaction = activity_v2.getSupportFragmentManager().beginTransaction();
            transaction.remove(activity_v2.shutterItemsFragment);
            transaction.commit();
            activity_v2.shutterItemsFragment.onDestroyView();

            activity_v2.shutterItemsFragment = null;
        }

        if (theme.equals("Classic"))
            activity_v2.shutterItemsFragment = new ShutterItemsFragments();
        if (theme.equals("Nubia"))
            activity_v2.shutterItemsFragment = new ShutterItemFragmentNubia();
    }

    public void SetTheme(String theme)
    {
        GetThemeFragment();
        activity_v2.inflateShutterItemFragment();
        //activity_v2.shutterItemsFragment.ParametersLoaded();
    }

    public static int[] getShutterFragmentDimensions()
    {
        
    }
}
