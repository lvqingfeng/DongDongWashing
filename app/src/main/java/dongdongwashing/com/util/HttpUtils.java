package dongdongwashing.com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 封装联网操作
 */
public class HttpUtils {

    // private InputStream inputStream;

    /**
     * 发送get请求 获取服务端返回的输入流
     *
     * @param path
     */
    public static InputStream get(String path) throws IOException {
        URL homeSinger = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) homeSinger.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(10000);
        conn.setRequestMethod("GET");
        conn.setUseCaches(true);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        InputStream is = conn.getInputStream();
        return is;
    }

    /**
     * 把输入流 按照utf-8编码解析为字符串
     *
     * @param is
     * @return 解析成功的字符串
     */
    public static String isToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

}



