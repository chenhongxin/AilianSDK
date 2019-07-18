package com.afuhan.sdklibrary.openapi;

import android.content.Context;

public class ALAPIFactory {

    public static IALAPI createWXAPI(Context context, String appid) {
        return new IALAPI(context, appid);
    }

}
