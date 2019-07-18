package com.afuhan.sdklibrary.modelbase;

public class SendAuth {

    public static class Resp extends BaseResp {

    }

    public static class Req {
        public String scope;
        public String state;
    }

}
