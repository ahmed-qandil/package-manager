/*
 * Copyright (C) 2021-2022 sunilpaulmathew <sunil.kde@gmail.com>
 *
 * This file is part of Package Manager, a simple, yet powerful application
 * to manage other application installed on an android device.
 *
 */

package com.smartpack.packagemanager.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartpack.packagemanager.BuildConfig;
import com.smartpack.packagemanager.R;
import com.smartpack.packagemanager.adapters.SettingsAdapter;
import com.smartpack.packagemanager.utils.AppSettings;
import com.smartpack.packagemanager.utils.Billing;
import com.smartpack.packagemanager.utils.RecyclerViewSwipeListener;
import com.smartpack.packagemanager.utils.Serializables.SettingsItems;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import in.sunilpaulmathew.sCommon.CommonUtils.sCommonUtils;
import in.sunilpaulmathew.sCommon.Dialog.sSingleChoiceDialog;
import in.sunilpaulmathew.sCommon.ThemeUtils.sThemeUtils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on February 10, 2020
 */
public class SettingsActivity extends BaseActivity {

    private RecyclerViewSwipeListener mRecyclerViewSwipeListener;
    private SettingsAdapter mRecycleViewAdapter;

    @SuppressLint("StringFormatInvalid")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithInsets(R.layout.activity_settings, R.id.layout_root);

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecycleViewAdapter = new SettingsAdapter(getData(), (settingsItems, position) -> {
            Context context = getApplicationContext();
            if (settingsItems.isWebLink()) {
                sCommonUtils.launchUrl(settingsItems.getUrl(), this);
            } else if (settingsItems.getId() == 1) {
                Intent settings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                settings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
                settings.setData(uri);
                startActivity(settings);
            } else if (settingsItems.getId() == 2) {
                AppSettings.setLanguage(this);
            } else if (settingsItems.getId() == 3) {
                new sSingleChoiceDialog(R.drawable.ic_theme, getString(R.string.app_theme),
                        new String[] {
                                getString(R.string.app_theme_auto),
                                getString(R.string.app_theme_dark),
                                getString(R.string.app_theme_light)
                        }, sCommonUtils.getInt("appTheme", 0, this), this) {

                    @Override
                    public void onItemSelected(int selectedPosition) {
                        if (selectedPosition == sCommonUtils.getInt("appTheme", 0, context)) {
                            return;
                        }
                        sCommonUtils.saveInt("appTheme", selectedPosition, context);
                        switch (selectedPosition) {
                            case 2:
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                                break;
                            case 1:
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                                break;
                            default:
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                                break;
                        }
                        settingsItems.setDescription(sThemeUtils.getAppTheme(context));
                        mRecycleViewAdapter.notifyItemChanged(position);
                    }
                }.show();
            } else if (settingsItems.getId() == 4) {
                new sSingleChoiceDialog(R.drawable.ic_pencil, getString(R.string.exported_apps_name),
                        AppSettings.getAPKNameOptionsMenu(this), AppSettings.getAPKNameOptionsPosition(this), this) {

                    @Override
                    public void onItemSelected(int itemPosition) {
                        if (itemPosition == AppSettings.getAPKNameOptionsPosition(context)) {
                            return;
                        }
                        sCommonUtils.saveInt("exportedAppName", itemPosition, context);
                        settingsItems.setDescription(AppSettings.getExportedAPKName(context));
                        mRecycleViewAdapter.notifyItemChanged(position);
                    }
                }.show();
            } else if (settingsItems.getId() == 5) {
                new sSingleChoiceDialog(R.drawable.ic_install, getString(R.string.installer_clicking),
                        AppSettings.getInstallerOptionsMenu(this), AppSettings.getInstallerOptionsPosition(this), this) {

                    @Override
                    public void onItemSelected(int itemPosition) {
                        switch (itemPosition) {
                            case 0:
                                if (sCommonUtils.getBoolean("neverShow", false, context)) {
                                    sCommonUtils.saveBoolean("neverShow", false, context);
                                }
                                break;
                            case 1:
                                if (!sCommonUtils.getBoolean("neverShow", false, context)) {
                                    sCommonUtils.saveBoolean("neverShow", true, context);
                                }
                                break;
                        }
                        settingsItems.setDescription(AppSettings.getInstallerStatus(context));
                        mRecycleViewAdapter.notifyItemChanged(position);
                    }
                }.show();
            } else if (settingsItems.getId() == 6) {
                new sSingleChoiceDialog(R.drawable.ic_exit, getString(R.string.exiting_app),
                        AppSettings.getExitOptionsMenu(this), AppSettings.getExitMenuPosition(this), this) {

                    @Override
                    public void onItemSelected(int itemPosition) {
                        switch (itemPosition) {
                            case 0:
                                if (sCommonUtils.getBoolean("exit_confirmation", true, context)) {
                                    sCommonUtils.saveBoolean("exit_confirmation", false, context);
                                }
                                break;
                            case 1:
                                if (!sCommonUtils.getBoolean("exit_confirmation", true, context)) {
                                    sCommonUtils.saveBoolean("exit_confirmation", true, context);
                                }
                                break;
                        }
                        settingsItems.setDescription(AppSettings.getExitingStatus(context));
                        mRecycleViewAdapter.notifyItemChanged(position);
                    }
                }.show();
            }
        }));

        mRecyclerViewSwipeListener = new RecyclerViewSwipeListener(mRecyclerView) {
            @Override
            public void onSwipeLeft() {
                finish();
            }

            @Override
            public void onSwipeRight() {
            }
        };
    }

    @SuppressLint("StringFormatInvalid")
    private List<SettingsItems> getData() {
        List <SettingsItems> mData = new CopyOnWriteArrayList<>();
        mData.add(new SettingsItems(getString(R.string.app_name) + " " + Billing.getAppVersion(), "", sCommonUtils.getDrawable(R.mipmap.ic_launcher_round, this), 1));

        // User interface
        mData.add(new SettingsItems(getString(R.string.user_interface)));
        mData.add(new SettingsItems(getString(R.string.language), AppSettings.getLanguageDescription(this), sCommonUtils.getDrawable(R.drawable.ic_language, this), 2));
        mData.add(new SettingsItems(getString(R.string.app_theme), sThemeUtils.getAppTheme(this), sCommonUtils.getDrawable(R.drawable.ic_theme, this), 3));

        // General
        mData.add(new SettingsItems(getString(R.string.general)));
        mData.add(new SettingsItems(getString(R.string.exported_apps_name), AppSettings.getExportedAPKName(this), sCommonUtils.getDrawable(R.drawable.ic_pencil, this), 4));
        mData.add(new SettingsItems(getString(R.string.installer_clicking), AppSettings.getInstallerStatus(this), sCommonUtils.getDrawable(R.drawable.ic_install, this), 5));
        mData.add(new SettingsItems(getString(R.string.exiting_app), AppSettings.getExitingStatus(this), sCommonUtils.getDrawable(R.drawable.ic_exit, this), 6));

        return mData;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mRecyclerViewSwipeListener != null) {
            mRecyclerViewSwipeListener.detach();
            mRecyclerViewSwipeListener = null;
        }
    }

}