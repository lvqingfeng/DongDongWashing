package dongdongwashing.com.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by 沈 on 2017/5/11.
 */

public class DataConversionByShen {

    public DataConversionByShen() {

    }

    public final static String getTimeByHour(String str) {
        if (!str.equals("")) {
            if (TextUtils.equals(str, "7点")) {
                str = "07";
            } else if (TextUtils.equals(str, "8点")) {
                str = "08";
            } else if (TextUtils.equals(str, "9点")) {
                str = "09";
            } else if (TextUtils.equals(str, "10点")) {
                str = "10";
            } else if (TextUtils.equals(str, "11点")) {
                str = "11";
            } else if (TextUtils.equals(str, "12点")) {
                str = "12";
            } else if (TextUtils.equals(str, "13点")) {
                str = "13";
            } else if (TextUtils.equals(str, "14点")) {
                str = "14";
            } else if (TextUtils.equals(str, "15点")) {
                str = "15";
            } else if (TextUtils.equals(str, "16点")) {
                str = "16";
            } else if (TextUtils.equals(str, "17点")) {
                str = "17";
            } else if (TextUtils.equals(str, "18点")) {
                str = "18";
            } else if (TextUtils.equals(str, "19点")) {
                str = "19";
            } else if (TextUtils.equals(str, "20点")) {
                str = "20";
            }
            return new String(str);
        }
        return new String(str);
    }

    public final static String getTimeByMinute(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (TextUtils.equals(str, "00分")) {
                str = "00";
            } else if (TextUtils.equals(str, "10分")) {
                str = "10";
            } else if (TextUtils.equals(str, "20分")) {
                str = "20";
            } else if (TextUtils.equals(str, "30分")) {
                str = "30";
            } else if (TextUtils.equals(str, "40分")) {
                str = "40";
            } else if (TextUtils.equals(str, "50分")) {
                str = "50";
            }
            return new String(str);
        }
        return new String(str);
    }

    public final static String getOrderStateMsg(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (TextUtils.equals(str, "1")) {
                str = "待支付";
            } else if (TextUtils.equals(str, "2")) {
                str = "待接单";
            } else if (TextUtils.equals(str, "3")) {
                str = "已接单";
            } else if (TextUtils.equals(str, "4")) {
                str = "进行中";
            } else if (TextUtils.equals(str, "5")) {
                str = "已完成";
            } else if (TextUtils.equals(str, "6")) {
                str = "已取消";
            }
            return str;
        }
        return str;
    }

    public final static String getOrderStateByRecord(String str) {
        if (!TextUtils.isEmpty(str)) {
            if ("1".equals(str)) {
                str = "订单已生成，待支付";
            } else if (TextUtils.equals(str, "2")) {
                str = "订单已支付，待接单";
            } else if (TextUtils.equals(str, "3")) {
                str = "订单已被接受，已安排人员前去洗车";
            } else if (TextUtils.equals(str, "4")) {
                str = "正在洗车";
            } else if (TextUtils.equals(str, "5")) {
                str = "车辆清洗完成";
            } else if (TextUtils.equals(str, "6")) {
                str = "订单取消";
            }
            return str;
        }
        return str;
    }

    /**
     * 正则判断车牌
     *
     * @param carNumber
     * @return
     */
    public final static boolean isCarNumberNO(String carNumber) {
        if (TextUtils.isEmpty(carNumber)) return false;
        else return carNumber.matches(GlobalConsts.REGEX_CAR_NUMBER);
    }

    /**
     * 获取本app的versionName
     */
    public final static String getVersionName(Context context, String versionName) {
        versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 判断用户手机是否安装微信客户端
     */
    public final static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager(); // 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);  // 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }
}
