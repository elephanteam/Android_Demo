package com.elephantgroup.one.entity;

import android.content.Context;
import android.text.TextUtils;

import com.elephantgroup.one.util.SharedPrefsUtil;
import com.elephantgroup.one.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Users' personal information
 */
public class UserInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String token;

    /**
     * The user password
     */
    private String password;

    /**
     * Whether in the blacklist (0 not, 1)
     */
    private String inblack;

    private String mobile;//Phone number (only return when check your information, if is empty, is not binding mobile phone number)

    private String email;//Email (only in return, check your data if the empty the unbounded email)

    /**The user id*/
    private String localId;

    /**SMT ID*/
    private String mid;

    /**The user name*/
    protected String username;

    /**The distance between the user*/
    private String distance;

    /**The user signature*/
    private String sightml;

    /**address*/
    private String address;
    
    /**age*/
    private String age;

    /**1 is the male  2 for women*/
    private String gender;

    /**Head portrait*/
    private String pic;

    /***/
    private String note;

    /**Contact person with multi-select*/
    private boolean isChecked=false;


   

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getUsername() {
        return username;
    }

    public void setSightml(String sightml) {
        this.sightml = sightml;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    public String getShowName() {
        if(!TextUtils.isEmpty(getNote())){
            return getNote();
        }
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDistance() {
        //Conversion accuracy
        distance = TextUtils.isEmpty(distance) ? "0" : distance;
        BigDecimal bg = new BigDecimal(distance);
        double j = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(j);
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getSightml() {
        if(sightml == null){
            sightml = "";
        }
        return sightml;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getInblack() {
        return inblack;
    }

    public void setInblack(String inblack) {
        this.inblack = inblack;
    }

   
    public UserInfoVo readMyUserInfo(Context mContext) {

        String userinfodata = SharedPrefsUtil.readString(mContext, SharedPrefsUtil.FILE_USER, SharedPrefsUtil.KEY_LOGIN_USERINFO);

        if (TextUtils.isEmpty(userinfodata)) {
            return null;
        }

        try {
            JSONObject infoData = new JSONObject(userinfodata);

            setToken(infoData.optString("token"));
            setMid(infoData.optString("mid"));
            setPassword(infoData.optString("password"));
            setLocalId(infoData.optString("localid"));
            setUsername(infoData.optString("username"));
            setSightml(infoData.optString("sightml"));
            setAddress(infoData.optString("birthcity"));

            setAge(infoData.optString("age"));
            setGender(infoData.optString("gender"));
            setPic(infoData.optString("pic"));
            setMobile(infoData.optString("mobile"));
            setEmail(infoData.optString("email"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return this;
    }

    public UserInfoVo parse(JSONObject obj) {
        if (obj == null) {
            return null;
        }
        setToken(obj.optString("token"));
        setMid(obj.optString("mid"));
        setPassword(obj.optString("password"));
        setUsername(obj.optString("username"));
        setLocalId(obj.optString("localid"));

        setNote(obj.optString("note"));
        setSightml(obj.optString("sightml"));
        setAddress(obj.optString("birthcity"));
        setAge(obj.optString("age"));
        setGender(obj.optString("gender"));
        setPic(obj.optString("pic"));
        setDistance(obj.optString("distance"));
        setInblack(obj.optString("inbalck"));
        setMobile(obj.optString("mobile"));
        setEmail(obj.optString("email"));
        return this;
    }

    /**
     * modify head
     *
     * @ param thumb thumbnails
     * @ param PIC a larger version
     * @ param mContext context
     */
    public void updateJsonAvatar( String pic, Context mContext) {
        String userinfodata = SharedPrefsUtil.readString(mContext, SharedPrefsUtil.FILE_USER, SharedPrefsUtil.KEY_LOGIN_USERINFO);
        try {
            JSONObject obj = new JSONObject(userinfodata);
            obj.put("pic", pic);
            setPic(pic);
            SharedPrefsUtil.write(mContext, SharedPrefsUtil.FILE_USER, SharedPrefsUtil.KEY_LOGIN_USERINFO, obj.toString());
            readMyUserInfo(mContext);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /**
     * modify head
     *
     * @ param note a larger version
     * @ param mContext context
     */
    public void updateJsonNote(String note, Context mContext) {
        String userinfodata = SharedPrefsUtil.readString(mContext, SharedPrefsUtil.FILE_USER, SharedPrefsUtil.KEY_LOGIN_USERINFO);
        try {
            JSONObject obj = new JSONObject(userinfodata);
            obj.put("note", note);
            setNote(note);
            SharedPrefsUtil.write(mContext, SharedPrefsUtil.FILE_USER, SharedPrefsUtil.KEY_LOGIN_USERINFO, obj.toString());
            readMyUserInfo(mContext);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modify the user information json string
     */
    public void updateJsonUserInfo(String username, String sightml, String gender, String birthcity, Context mContext) {
        String userinfodata = SharedPrefsUtil.readString(mContext, SharedPrefsUtil.FILE_USER, SharedPrefsUtil.KEY_LOGIN_USERINFO);
        try {
            JSONObject obj = new JSONObject(userinfodata);
            obj.put("username", username);
            obj.put("sightml", sightml);
            obj.put("gender", gender);
            obj.put("birthcity", birthcity);
            SharedPrefsUtil.write(mContext, SharedPrefsUtil.FILE_USER, SharedPrefsUtil.KEY_LOGIN_USERINFO, obj.toString());
            readMyUserInfo(mContext);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modify the binding mobile phone number information
     */
    public void updateJsonBindMobile(String mobile, Context mContext) {
        String userinfodata = SharedPrefsUtil.readString(mContext, SharedPrefsUtil.FILE_USER, SharedPrefsUtil.KEY_LOGIN_USERINFO);
        try {
            JSONObject obj = new JSONObject(userinfodata);
            obj.put("mobile", mobile);
            SharedPrefsUtil.write(mContext, SharedPrefsUtil.FILE_USER, SharedPrefsUtil.KEY_LOGIN_USERINFO, obj.toString());
            readMyUserInfo(mContext);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modify the binding E-mail information
     */
    public void updateJsonBindEmail(String email, Context mContext) {
        String userinfodata = SharedPrefsUtil.readString(mContext, SharedPrefsUtil.FILE_USER, SharedPrefsUtil.KEY_LOGIN_USERINFO);
        try {
            JSONObject obj = new JSONObject(userinfodata);
            obj.put("email", email);
            SharedPrefsUtil.write(mContext, SharedPrefsUtil.FILE_USER, SharedPrefsUtil.KEY_LOGIN_USERINFO, obj.toString());
            readMyUserInfo(mContext);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
