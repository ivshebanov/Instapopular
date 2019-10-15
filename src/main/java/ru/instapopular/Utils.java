package ru.instapopular;

import static ru.instapopular.Constant.AnalysisConstant.LOGIN_USER_MAC;
import static ru.instapopular.Constant.AnalysisConstant.LOGIN_USER_WINDOWS;
import static ru.instapopular.Constant.DriverConstant.Driver.Chrome.CHROME_DRIVER_MAC;
import static ru.instapopular.Constant.DriverConstant.Driver.Chrome.CHROME_DRIVER_WINDOWS;
import static ru.instapopular.Constant.Utils.*;

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

    public static String getLoginUserBtn() {
        if (MAC.equalsIgnoreCase(ocName)) {
            return LOGIN_USER_MAC;
        }
        if (WINDOWS.equalsIgnoreCase(ocName)) {
            return LOGIN_USER_WINDOWS;
        }
        return "";
    }

    private static String cutOff(String ocName) {
        return ocName.substring(0, ocName.indexOf(" "));
    }
}
