package com.afuhan.sdklibrary.modelbase;

public class BaseResp {

    public int errCode;
    public String code;

    public static class ErrCode{
        public static final int ERR_OK = 1;
        public static final int ERR_USER_CANCEL = -1;
        public static final int ERR_AUTH_DENIED = -2;
    }

}
