package com.android.searchlauncher;

import static com.android.searchlauncher.OverlayCallbackImpl.KEY_ENABLE_MINUS_ONE;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.preference.Preference;

import com.android.launcher3.settings.SettingsActivity.LauncherSettingsFragment;

// Add Launcher3 Style Switch add by qty {{&&
import android.os.Bundle;
import android.provider.Settings;
import com.android.launcher3.R;
// &&}}

public class SearchSettingsFragment extends LauncherSettingsFragment {

    protected static final String GSA_PACKAGE = "com.google.android.googlequicksearchbox";

    // Add Launcher3 Style Switch add by qty {{&&
    private static final String LAUNCHER_STYLE_PREFERENCE_KEY = "pref_launcher_style";
    private Preference mLauncherStylePref;
    // &&}}

    private Preference mShowGoogleAppPref;

    @Override
    protected boolean initPreference(Preference preference) {
        switch (preference.getKey()) {
            case KEY_ENABLE_MINUS_ONE:
                mShowGoogleAppPref = preference;
                updateIsGoogleAppEnabled();
                return true;
                
            // Add Launcher3 Style Switch add by qty {{&&
            case LAUNCHER_STYLE_PREFERENCE_KEY:
                mLauncherStylePref = preference;
                if (android.os.SystemProperties.getBoolean("ro.wb.launcher_style_switch",false)) {
                    updateLauncherStyleEnabled();
                } else {
                    getPreferenceScreen().removePreference(mLauncherStylePref);
                }
                return true;
            // &&}}

            case "pref_smartspace":
                return false;
        }

        return super.initPreference(preference);
    }

    private void updateIsGoogleAppEnabled() {
        if (mShowGoogleAppPref != null) {
            mShowGoogleAppPref.setEnabled(isGSAEnabled(getContext()));
        }
    }
    
    // Add Launcher3 Style Switch add by qty {{&&
    private void updateLauncherStyleEnabled() {
        if (android.os.SystemProperties.getBoolean("ro.wb.launcher_style_switch",false)) {
            if (mLauncherStylePref != null && getActivity() != null) {
                int style = Settings.System.getInt(getActivity().getContentResolver(), "launcher_style", 0);
                mLauncherStylePref.setSummary(style == 0 ? 
                            getString(R.string.launcher_style_drawer) : getString(R.string.launcher_style_nomal));
            }
        }
    }
    // &&}}

    @Override
    public void onResume() {
        super.onResume();
        updateIsGoogleAppEnabled();
        // Add Launcher3 Style Switch add by qty {{&&
        updateLauncherStyleEnabled();
        // &&}}
    }

    public static boolean isGSAEnabled(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo(GSA_PACKAGE, 0).enabled;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
