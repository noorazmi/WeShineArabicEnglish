package com.moderneng.providers;

import android.net.Uri;

import com.android.vending.expansion.zipfile.APEZProvider;

import java.io.File;

/**
 * <p/>
 * Created by: Noor  Alam on 15/09/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class CustomAPEZProvider extends APEZProvider {
    private static final String AUTHORITY = "com.moderneng.arbeng.provider";

    @Override
    public String getAuthority() {
        return AUTHORITY;
    }


    public static Uri buildUri(String path) {
        StringBuilder contentPath = new StringBuilder("content://");

        contentPath.append(AUTHORITY);
        contentPath.append(File.separator);
        contentPath.append(path);

        return Uri.parse(contentPath.toString());
    }
}

