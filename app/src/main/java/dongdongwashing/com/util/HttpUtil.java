package dongdongwashing.com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by star on 2017/2/28.
 */

public class HttpUtil {

    private HttpUtil() {

    }

    /**
     * get方法请求数据
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callBack 回调接口
     */
    public static void get(final String url, final Map<String, String> params, final HttpResponseCallBack callBack) {
        if (callBack != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 拼装请求参数列表
                        final StringBuilder sb = new StringBuilder(64);
                        if (params != null) {
                            sb.append("?");
                            for (Map.Entry<String, String> entry : params.entrySet()) {
                                sb.append(entry.getKey());
                                sb.append("=");
                                sb.append(entry.getValue());
                                sb.append("&");
                            }
                            sb.deleteCharAt(sb.length() - 1);
                        }

                        // 构建新连接
                        URL httpUrl = null;
                        try {
                            httpUrl = new URL(url + sb.toString());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                        conn.setConnectTimeout(10000);
                        conn.setReadTimeout(10000);
                        conn.setUseCaches(true);
                        conn.setRequestMethod("GET");
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                        // 接受返回数据
                        sb.setLength(0);
                        String strTemp;
                        while ((strTemp = br.readLine()) != null) {
                            sb.append(strTemp).append('\n');
                        }
                        conn.disconnect();

                        AsyncRun.run(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(sb.toString());
                            }
                        });
                    } catch (final IOException e) {
                        AsyncRun.run(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onFailure("失败信息：", e);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * post方法请求数据
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callBack 回调地址
     */
    public static void post(final String url, final Map<String, String> params, final HttpResponseCallBack callBack) {
        if (callBack != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final StringBuilder sb = new StringBuilder(64);

                        URL httpUrl = new URL(url);
                        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                        conn.setConnectTimeout(3000);
                        conn.setReadTimeout(3000);
                        conn.setUseCaches(true);
                        conn.setRequestMethod("POST");
                        conn.setDoOutput(true);

                        //拼装请求参数列表
                        if (params != null) {
                            for (Map.Entry<String, String> entry : params.entrySet()) {
                                sb.append(entry.getKey());
                                sb.append("=");
                                sb.append(entry.getValue());
                                sb.append("&");
                            }
                            sb.deleteCharAt(sb.length() - 1);
                            conn.getOutputStream().write(sb.toString().getBytes());
                        }

                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                        //接受返回数据
                        sb.setLength(0);
                        String strTemp;
                        while ((strTemp = br.readLine()) != null) {
                            sb.append(strTemp).append('\n');
                        }

                        conn.disconnect();

                        AsyncRun.run(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(sb.toString());
                            }
                        });

                    } catch (final IOException e) {
                        AsyncRun.run(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onFailure("发生未知错误！", e);
                            }
                        });
                    }
                }
            }).start();
        }
    }

    /**
     * 获取回调
     */
    public interface HttpResponseCallBack {
        void onSuccess(String result);

        void onFailure(String result, Exception e);
    }
}