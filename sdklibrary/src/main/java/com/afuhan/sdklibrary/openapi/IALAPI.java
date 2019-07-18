package com.afuhan.sdklibrary.openapi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.afuhan.sdklibrary.modelbase.BaseResp;
import com.afuhan.sdklibrary.modelbase.SendAuth;

public class IALAPI {

    final String SCOPE = "scope";
    final String STATE = "state";
    Context context;
    String appid;
    Intent intent;
    private IALAPIEventHandler ialapiEventHandler;

    public IALAPI(Context context, String appid) {
        this.context = context;
        this.appid = appid;
    }

    public void registerApp(String appid) {
        this.appid = appid;
    }

    public void handleIntent(Intent intent, IALAPIEventHandler ialapiEventHandler) {
        this.intent = intent;
        this.ialapiEventHandler = ialapiEventHandler;
        String scope = intent.getStringExtra(SCOPE);
        String state = intent.getStringExtra(STATE);
        String code = intent.getStringExtra("code");
        if (!TextUtils.isEmpty(scope) && !TextUtils.isEmpty(state)) {
            try {
                Intent toALIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("achain://api.vip0.com/open?" + SCOPE + "=" + scope + "&" + STATE + "=" + state + "&package=" + context.getPackageName()));
                context.startActivity(toALIntent);
                ((AppCompatActivity) context).finish();
            } catch (Exception e) {
                e.printStackTrace();
                SendAuth.Resp resp = new SendAuth.Resp();
                resp.errCode = BaseResp.ErrCode.ERR_AUTH_DENIED;
                ialapiEventHandler.onResp(resp);
            }
        } else if (!TextUtils.isEmpty(scope)) {
            if ("scope_login".equals(scope)) {
                if (TextUtils.isEmpty(code)) {
                    SendAuth.Resp resp = new SendAuth.Resp();
                    resp.errCode = BaseResp.ErrCode.ERR_USER_CANCEL;
                    ialapiEventHandler.onResp(resp);
                } else {
                    SendAuth.Resp resp = new SendAuth.Resp();
                    resp.errCode = BaseResp.ErrCode.ERR_OK;
                    resp.code = code;
                    ialapiEventHandler.onResp(resp);
                }
            }
        }
    }

    public void sendReq(SendAuth.Req req) {
        if (context != null) {
            Intent intent = new Intent();
            intent.setClassName(context.getPackageName(), context.getPackageName() + ".alapi.ALEntryActivity");
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                intent.putExtra(SCOPE, req.scope);
                intent.putExtra(STATE, req.state);
                context.startActivity(intent);
            } else {
                if (ialapiEventHandler != null) {
                    SendAuth.Resp resp = new SendAuth.Resp();
                    resp.errCode = BaseResp.ErrCode.ERR_AUTH_DENIED;
                    ialapiEventHandler.onResp(resp);
                }
            }
        }
    }
}
