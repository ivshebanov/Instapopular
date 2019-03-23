package com.instapopulars.instapopular;

import static com.instapopulars.instapopular.Constant.DriverConstant.Driver.Chrome.CHROME_DRIVER_MAC;
import static com.instapopulars.instapopular.Constant.DriverConstant.Driver.Chrome.CHROME_DRIVER_WINDOWS;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.UNSUBSCRIBE_BTN_MAC;
import static com.instapopulars.instapopular.Constant.UnsubscribeConstant.Xpath.UNSUBSCRIBE_BTN_WINDOWS;
import static com.instapopulars.instapopular.Constant.Utils.MAC;
import static com.instapopulars.instapopular.Constant.Utils.OS_NAME;
import static com.instapopulars.instapopular.Constant.Utils.WINDOWS;

public class Utils {

    private static final String ocName = cutOff(System.getProperty(OS_NAME));

    public static String getChromeDriver() {
        if (MAC.equalsIgnoreCase(ocName)) {
            return CHROME_DRIVER_MAC;
        }
        if (WINDOWS.equalsIgnoreCase(ocName)) {
            return CHROME_DRIVER_WINDOWS;
        }
        return "";
    }

    public static String getUnsubscribeBtn() {
        if (MAC.equalsIgnoreCase(ocName)) {
            return UNSUBSCRIBE_BTN_MAC;
        }
        if (WINDOWS.equalsIgnoreCase(ocName)) {
            return UNSUBSCRIBE_BTN_WINDOWS;
        }
        return "";
    }

    private static String cutOff(String ocName) {
        return ocName.substring(0, ocName.indexOf(" "));
    }
}
