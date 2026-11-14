/*
 * Copyright (C) 2024-2025 sunilpaulmathew <sunil.kde@gmail.com>
 *
 * This file is part of Package Manager, a simple, yet powerful application
 * to manage other application installed on an android device.
 *
 */

package com.smartpack.packagemanager.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartpack.packagemanager.MainActivity;
import com.smartpack.packagemanager.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import in.sunilpaulmathew.sCommon.CommonUtils.sCommonUtils;
import in.sunilpaulmathew.sCommon.CommonUtils.sSerializableItems;
import in.sunilpaulmathew.sCommon.Dialog.sSingleChoiceDialog;
import navView.NavView;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on February 10, 2020
 */
public class AppSettings {

    public static int getAPKNameOptionsPosition(Context context) {
        return sCommonUtils.getInt("exportedAppName", 2, context);
    }

    public static int getExitMenuPosition(Context context) {
        if (sCommonUtils.getBoolean("exit_confirmation", true, context)) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int getInstallerOptionsPosition(Context context) {
        if (sCommonUtils.getBoolean("neverShow", false, context)) {
            return 1;
        } else {
            return 0;
        }
    }

    private static int getLanguagePosition(Context context) {
        switch (getLanguage(context)) {
            case "ar":
                return 1;
            case "en":
                return 2;
            default:
                return 0;
        }
    }

    public static List<sSerializableItems> getCredits() {
        List<sSerializableItems> mData = new ArrayList<>();
        mData.add(new sSerializableItems(null, "Willi Ye", "Kernel Adiutor", "https://github.com/Grarak/KernelAdiutor"));
        mData.add(new sSerializableItems(null, "topjohnwu", "libsu", "https://github.com/topjohnwu/libsu"));
        mData.add(new sSerializableItems(null, "RikkaApps", "Shizuku", "https://github.com/RikkaApps/Shizuku"));
        mData.add(new sSerializableItems(null, "Srikanth Reddy Lingala", "zip4j", "https://github.com/srikanth-lingala"));
        mData.add(new sSerializableItems(null, "Aefyr", "SAI", "https://github.com/Aefyr/SAI"));
        mData.add(new sSerializableItems(null, "Nitin Kalra", "Split Apk Install", "https://github.com/nkalra0123/"));
        mData.add(new sSerializableItems(null, "APK Explorer & Editor", "aXML", "https://github.com/apk-editor/aXML"));
        mData.add(new sSerializableItems(null, "Lennoard Silva", "Code Contributions & Portuguese (Brazilian) Translations", "https://github.com/Lennoard"));
        mData.add(new sSerializableItems(null, "Agnieszka C","Code Contributions, Testing & Polish Translations", "https://github.com/Aga-C"));
        mData.add(new sSerializableItems(null, "Toxinpiper", "App Icon", "https://t.me/toxinpiper"));
        mData.add(new sSerializableItems(null, "Valdnet", "Testing", "https://github.com/Valdnet"));
        mData.add(new sSerializableItems(null, "FiestaLake", "Korean Translations", "https://github.com/FiestaLake"));
        mData.add(new sSerializableItems(null, "Mikesew1320", "Amharic Translations", "https://github.com/Mikesew1320"));
        mData.add(new sSerializableItems(null, "tsiflimagas", "Greek Translations", "https://github.com/tsiflimagas"));
        mData.add(new sSerializableItems(null, "741™", "Greek Translations", null));
        mData.add(new sSerializableItems(null, "Nikita", "Russian & Ukrainian Translations", "https://t.me/MONSTER_PC"));
        mData.add(new sSerializableItems(null, "tommynok", "Ukrainian Translations", null));
        mData.add(new sSerializableItems(null, "B3Nd2R & Reno", "French Translations", null));
        mData.add(new sSerializableItems(null, "Jan & Ray", "German Translations", null));
        mData.add(new sSerializableItems(null, "omerakgoz34 (BSÇE)", "Turkish Translations", "https://github.com/omerakgoz34"));
        mData.add(new sSerializableItems(null, "Emrehelvaci83", "Turkish Translations", null));
        mData.add(new sSerializableItems(null, "Woytazzer", "Czech Translations", null));
        mData.add(new sSerializableItems(null, "Javi", "Spanish Translations", null));
        mData.add(new sSerializableItems(null, "Hoa Gia Đại Thiếu", "Vietnamese Translations", null));
        mData.add(new sSerializableItems(null, "jason5545", "Chinese (Simplified & Traditional) Translations", "https://github.com/jason5545"));
        mData.add(new sSerializableItems(null, "Cláudia Sebastião", "Portuguese (Portugal) Translations", null));
        mData.add(new sSerializableItems(null, "Carnum", "Portuguese (Portugal) Translations", null));
        mData.add(new sSerializableItems(null, "Erős Pista", "Hungarian Translations", null));
        mData.add(new sSerializableItems(null, "AbsurdUsername", "Italian Translations", "https://github.com/AbsurdUsername"));
        mData.add(new sSerializableItems(null, "rotid™", "Arabic Translations", null));
        mData.add(new sSerializableItems(null, "Pen™", "Slovakian Translations", null));
        mData.add(new sSerializableItems(null, "yair aaron", "Hebrew Translations", null));
        return mData;
    }

    public static String generateExportedAPKName(String appName, String packageName, String versionName, String ext, Context context) {
        int position = getAPKNameOptionsPosition(context);

        switch (position) {
            case 4:
                return appName + "_" + packageName + "_" + versionName + ext;
            case 3:
                return packageName + "_" + versionName + ext;
            case 2:
                return appName + "_" + versionName + ext;
            case 1:
                return packageName + ext;
            default:
                return appName + ext;
        }
    }

    private static NavView getNavView(Activity activity) {
        return activity.findViewById(R.id.nav_view);
    }

    public static String getExportedAPKName(Context context) {
        String[] options = getAPKNameOptionsMenu(context);
        int pos = getAPKNameOptionsPosition(context);

        if (pos < 0 || pos >= options.length) {
            pos = 2;
        }
        return options[pos];
    }

    public static String getExitingStatus(Context context) {
        if (sCommonUtils.getBoolean("exit_confirmation", true, context)) {
            return context.getString(R.string.exit_confirmation);
        } else {
            return context.getString(R.string.exit_simple);
        }
    }

    public static String getInstallerStatus(Context context) {
        if (sCommonUtils.getBoolean("neverShow", false, context)) {
            return context.getString(R.string.installer_file_picker);
        } else {
            return context.getString(R.string.installer_instructions);
        }
    }

    public static String[] getAPKNameOptionsMenu(Context context) {
        String version = context.getString(R.string.version, "").trim().replace(":", "");
        return new String[] {
                context.getString(R.string.name),
                context.getString(R.string.package_id),
                context.getString(R.string.name) + "_" + version,
                context.getString(R.string.package_id) + "_" + version,
                context.getString(R.string.name) + "_" + context.getString(R.string.package_id) + "_" + version
        };
    }

    public static String[] getExitOptionsMenu(Context context) {
        return new String[] {
                context.getString(R.string.exit_simple),
                context.getString(R.string.exit_confirmation)
        };
    }

    public static String[] getInstallerOptionsMenu(Context context) {
        return new String[] {
                context.getString(R.string.installer_instructions),
                context.getString(R.string.installer_file_picker)
        };
    }

    private static String getCountry(Context context) {
        return sCommonUtils.getString("country", java.util.Locale.getDefault().getLanguage(), context);
    }

    private static String getLanguage(Context context) {
        return sCommonUtils.getString("appLanguage", java.util.Locale.getDefault().getLanguage(), context);
    }

    public static String getLanguageDescription(Context context) {
        switch (getLanguage(context)) {
            case "ar":
                return context.getString(R.string.language_ar);
            case "en":
                return context.getString(R.string.language_en, "US");
            default:
                return context.getString(R.string.app_theme_auto);
        }
    }

    private static String[] getLanguageMenu(Context context) {
        return new String[] {
                context.getString(R.string.app_theme_auto),
                context.getString(R.string.language_ar),
                context.getString(R.string.language_en, "US"),
        };
    }

    public static void applyMargin(RecyclerView recyclerView, Activity activity) {
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) == state.getItemCount() - 1) {
                    outRect.bottom = getNavView(activity).getHeight();
                } else {
                    outRect.bottom = 0;
                }
            }
        });
    }

    public static void launchInstaller(Activity activity) {
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).launchInstaller();
        }
    }

    public static void navigateToFragment(Activity activity, int position) {
        getNavView(activity).setSelectedPosition(position);
    }

    public static void setLanguage(Activity activity) {
        new sSingleChoiceDialog(R.drawable.ic_language, activity.getString(R.string.language),
                getLanguageMenu(activity), getLanguagePosition(activity), activity) {

            @Override
            public void onItemSelected(int position) {
                switch (position) {
                    case 0:
                        if (Objects.equals(getLanguage(activity), Locale.getDefault().getLanguage()) && Objects.equals(getCountry(activity), Locale.getDefault().getCountry())) {
                            return;
                        }
                        sCommonUtils.saveString("appLanguage", java.util.Locale.getDefault().getLanguage(), activity);
                        sCommonUtils.saveString("country", java.util.Locale.getDefault().getCountry(), activity);
                        break;
                    case 1:
                        if (Objects.equals(getLanguage(activity), "ar")) {
                            return;
                        }
                        sCommonUtils.saveString("appLanguage", "ar", activity);
                        sCommonUtils.saveString("country", null, activity);
                        break;
                    case 2:
                        if (Objects.equals(getLanguage(activity), "en")) {
                            return;
                        }
                        sCommonUtils.saveString("appLanguage", "en", activity);
                        sCommonUtils.saveString("country", "US", activity);
                        break;
                }
                Utils.restartApp(activity);
            }
        }.show();
    }

    public static void setSlideInAnimation(final View viewToAnimate, int position) {
        // Only animate items appearing for the first time
        if (position > -1) {
            viewToAnimate.setTranslationY(50f);
            viewToAnimate.setAlpha(0f);

            viewToAnimate.animate()
                    .translationY(0f)
                    .alpha(1f)
                    .setDuration(150)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
        } else {
            // Reset properties to ensure recycled views are displayed correctly
            viewToAnimate.setTranslationY(0f);
            viewToAnimate.setAlpha(1f);
        }
    }

}