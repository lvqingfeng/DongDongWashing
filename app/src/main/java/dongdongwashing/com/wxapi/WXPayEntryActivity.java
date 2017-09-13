package dongdongwashing.com.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import dongdongwashing.com.util.GlobalConsts;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    private Intent weChatIntent = new Intent();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(WXPayEntryActivity.this, GlobalConsts.APP_ID);
        api.registerApp(GlobalConsts.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.d("test", "微信支付返回状态值-------------------:" + baseResp.errCode);
        Log.d("test", "微信支付返回的结果-------------------:" + baseResp.toString());
        this.finish();
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) { // 支付成功
                weChatIntent.setAction("WE_CHAT_PAY_SUCCESS");
                WXPayEntryActivity.this.sendBroadcast(weChatIntent);
            } else { // 支付失败或者取消支付
                weChatIntent.setAction("WE_CHAT_PAY_FAILURE");
                WXPayEntryActivity.this.sendBroadcast(weChatIntent);
                setResult(RESULT_OK);
            }
        }
    }
}