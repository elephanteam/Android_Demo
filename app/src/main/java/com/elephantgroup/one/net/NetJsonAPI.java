package com.elephantgroup.one.net;

import android.text.TextUtils;

import com.elephantgroup.one.MyApplication;
import com.elephantgroup.one.language.LanguageType;
import com.elephantgroup.one.language.MultiLanguageUtil;
import com.elephantgroup.one.util.Rsa;
import com.elephantgroup.one.util.SharedPrefsUtil;
import com.elephantgroup.one.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.UUID;


public class NetJsonAPI {

    public static JSONObject getJsonRequest(String service,String method,boolean needToken,Map<String,String> params){

        if (needToken && (MyApplication.myInfo == null || TextUtils.isEmpty(MyApplication.myInfo.getToken()))){
            return null;
        }

        String tempToken = null ;
        if(params != null){
            tempToken = params.get("token");
            if(!TextUtils.isEmpty(tempToken)){
                params.remove("token");
            }
        }

        boolean isEncrpyt = false ;
        if(params != null && params.containsKey("encrypt")){
            String encrypt = params.get("encrypt");
            isEncrpyt = TextUtils.equals("1", encrypt);
            params.remove("encrypt");
        }

        JSONObject jsonParams = new JSONObject();
        JSONObject jsonBody = new JSONObject();
        try {
            if(params != null){
                for (Map.Entry<String, String> s : params.entrySet()) {
                    jsonParams.put(s.getKey(), s.getValue());
                }
            }
            String location = SharedPrefsUtil.readString(MyApplication.mContext, SharedPrefsUtil.FILE_USER, SharedPrefsUtil.KEY_LOCATION);

            if(!TextUtils.isEmpty(location) && location.contains(",")){
                String[] split = location.split(",");
                jsonParams.put("lat", split[0]);
                jsonParams.put("lng", split[1]);
            }else{
                jsonParams.put("lat", 0);
                jsonParams.put("lng", 0);
            }
            jsonParams.put("system", "android" + android.os.Build.VERSION.RELEASE);
            jsonParams.put("model", android.os.Build.MODEL);
            try {
                jsonParams.put("imei", Utils.getIMEI(MyApplication.mContext));
                jsonParams.put("resolution", Utils.getScreenPixels(MyApplication.mContext));
                jsonParams.put("version", Utils.getVersionName(MyApplication.mContext));
            } catch (Exception e) {
            }

            int language = MultiLanguageUtil.getInstance().getLanguageType();
            if(LanguageType.LANGUAGE_CHINESE_SIMPLIFIED == language){
                jsonBody.put("lang","0");
            }else{
                jsonBody.put("lang","1");
            }

            jsonBody.putOpt("service", service);
            jsonBody.putOpt("method", method);
            jsonBody.putOpt("encrypt", isEncrpyt ? "1":"0");
            jsonBody.putOpt("sn", UUID.randomUUID().toString());
            if(MyApplication.myInfo != null ){
                String token = MyApplication.myInfo.getToken();
                if(!TextUtils.isEmpty(token)){
                    jsonBody.putOpt("token", Rsa.encryptByPublic(token) );
                }
            }else if(!TextUtils.isEmpty(tempToken)){
                jsonBody.putOpt("token", Rsa.encryptByPublic(tempToken) );
            }
            jsonBody.putOpt("tokenencrypt", "1");
            if(isEncrpyt){
                String paramsString = jsonParams.toString();
                jsonBody.putOpt("params", Rsa.encryptByPublic(paramsString));
            }else{
                jsonBody.putOpt("params", jsonParams);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonBody;
    }

}
