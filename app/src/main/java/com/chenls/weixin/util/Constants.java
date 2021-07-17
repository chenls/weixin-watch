package com.chenls.weixin.util;

public class Constants {
    public static final String AUDIO_DIRECTORY = "/weixinQingliao/";
    public static final int UNSYNC_LIMIT = 2;
    public static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/51.0.2704.79 Chrome/51.0.2704.79 Safari/537.36";

    public static final class Action {
        public static final String CLOSE_APP = "com.chenls.weixin.CLOSEAPP";
        public static final String NEW_MSG = "com.chenls.weixin.MSG";
        public static final String RESCHEDULE = "com.chenls.weixin.RESCHEDULE";
    }

    public static class LoginCode {
        public static final String INIT = "408";
        public static final String LOGIN = "200";
        public static final String SCANNED = "201";
    }

    public static final class MsgType {
        public static final int INIT = 51;
        public static final int TEXT = 1;
        public static final int VOICE = 34;
    }

    public static final class MyRetry {
        public static final float BACKOFF_MULT = 2.0f;
        public static final int RETRIES = 1;
        public static final int TIMEOUT_MS = 2500;
    }

    public static final class Period {
        public static final long HOME_STANDARD = 60000;
        public static final long LOGIN = 7000;
    }

    public static final class SyncCheckCode {
        public static final String ERROR = "1101";
        public static final String FAIL = "1100";
        public static final String SUCCESS = "0";
    }
}
