package com.elephantgroup.one.net;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

public interface NetApiService {

    @FormUrlEncoded
    @POST(UrlConstantsApi.INDEX_URL)
    Observable<ResponseBody> login(@Field("body") String jsonRequest);


    @POST(UrlConstantsApi.INDEX_URL)
    Observable<ResponseBody> bindWallet(@HeaderMap HashMap<String, Object> header, @Body Map map);

    @Multipart
    @POST(UrlConstantsApi.INDEX_URL)
    Observable<ResponseBody> upload(@Part MultipartBody.Part jsonPart,@Part MultipartBody.Part file);

    /**
     * download file
     * 下载文件
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    /**
     * upload files
     * No @Post ("address") method, using dynamic url for easy packaging
     * 上传文件
     * 没有使用@Post（“地址”）方法，使用了动态的url，方便封装
     */
    @Multipart
    @POST
    Observable<ResponseBody> upload(@Url String url, @Part MultipartBody.Part file);
}
