package com.elephantgroup.one.net.download;

public interface ProgressListener {
    /**
     * progress表示当前已经下载的文件大小
     * total表示文件大小
     * speed表示下载速度
     * done表示是否下载完成
     * Created by 1013369768 on 2017/10/20.
     */
    void onProgress(long progress, long total, long speed, boolean done);

    void onStartDownload();

    void onFinishDownload();

    void onFail(String errorInfo);
}
