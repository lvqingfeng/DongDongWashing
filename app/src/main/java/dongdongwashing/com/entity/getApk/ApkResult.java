package dongdongwashing.com.entity.getApk;

/**
 * Created by 沈 on 2017/5/17.
 */

public class ApkResult {

    private String version;
    private String sys;
    private String sysPath;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getSysPath() {
        return sysPath;
    }

    public void setSysPath(String sysPath) {
        this.sysPath = sysPath;
    }

    @Override
    public String toString() {
        return "ApkResult{" +
                "version='" + version + '\'' +
                ", sys='" + sys + '\'' +
                ", sysPath='" + sysPath + '\'' +
                '}';
    }
}
