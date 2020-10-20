package com.example.sqllearning.utils.common;

import java.util.UUID;

public class CommonUtil {


    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
