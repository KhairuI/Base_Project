package com.example.baseproject.utils.helper;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static android.os.Build.VERSION_CODES.N;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;

import java.util.Collections;
import java.util.Locale;

public class LocaleManager {
    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_BANGLA = "bn";
    private static final String LANGUAGE_KEY = "language_key";

    private final SharedPreferences prefs;

    public LocaleManager(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Utility.isAtLeastVersion(N) ? config.getLocales().get(0) : config.locale;
    }

    public Context setLocale(Context c) {
        downloadLanguageResources(c);
        return updateResources(c, getLanguage());
    }

    private void downloadLanguageResources(Context context) {
        // Creates a request to download and install additional language resources.
        SplitInstallRequest request =
                null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            request = SplitInstallRequest.newBuilder()
                    // Uses the addLanguage() method to include French language resources in the request.
                    // Note that country codes are ignored. That is, if your app
                    // includes resources for “fr-FR” and “fr-CA”, resources for both
                    // country codes are downloaded when requesting resources for "fr".
                    .addLanguage(Locale.forLanguageTag(getLanguage()))
                    .build();
        }

// Submits the request to install the additional language resources.

        SplitInstallManager splitInstallManager =
                SplitInstallManagerFactory.create(context);

        try {
            if (request != null)
                splitInstallManager.startInstall(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //new section

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            splitInstallManager.deferredLanguageInstall(
                    Collections.singletonList(Locale.forLanguageTag(getLanguage())));
        }


    }

    public Context setNewLocale(Context c, String language) {
        persistLanguage(language);
        return updateResources(c, language);
    }

    public String getLanguage() {
        return prefs.getString(LANGUAGE_KEY, LANGUAGE_ENGLISH);
    }

    @SuppressLint("ApplySharedPref")
    private void persistLanguage(String language) {
        // use commit() instead of apply(), because sometimes we kill the application process immediately
        // which will prevent apply() to finish
        prefs.edit().putString(LANGUAGE_KEY, language).commit();
    }

    private Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Utility.isAtLeastVersion(JELLY_BEAN_MR1)) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }
}
