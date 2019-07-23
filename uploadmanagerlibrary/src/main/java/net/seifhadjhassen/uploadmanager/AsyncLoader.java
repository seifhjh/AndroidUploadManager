package net.seifhadjhassen.uploadmanager;

import android.content.Context;

import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;

public class AsyncLoader {
    private String url;
    private LoaderCallBackHandler mCallback;
    private Context mContext;
    private RequestParams params;
    private RequestHandle handle;

    public interface LoaderCallBackHandler {
        public void onStartUploading();
        public void uploadComplete(String response);
        public void failedWithError(Throwable error);
        public void progressUpdate(long bytesWritten, long bytesTotal);
        public void onCancle();
        public void onFinish();
    }


    AsyncLoader(Context mContext, String url, RequestParams params, LoaderCallBackHandler callback) {
        this.mContext = mContext;
        this.url = url;
        this.params = params;
        this.mCallback = callback;

    }

    void startTransfer() {
        AsynchConfig.mClient.setTimeout(50000);

        handle = AsynchConfig.mClient.post(mContext, url, params,handlerInterface);
    }
    private ResponseHandlerInterface handlerInterface = new ResponseHandlerInterface() {

        @Override
        public void sendStartMessage() {
            if(mCallback != null) {
                mCallback.onStartUploading();
            }

        }

        @Override
        public void sendResponseMessage(HttpResponse response) throws IOException {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();

// TODO convert in stream to JSONObject and do whatever you need to

                StringWriter writer = new StringWriter();
                IOUtils.copy(instream, writer);
                String theString = writer.toString();

                if(mCallback != null) {
                    mCallback.uploadComplete(theString);
                }
            }

        }

        @Override
        public void sendSuccessMessage(int arg0, Header[] arg1, byte[] arg2) {

        }

        @Override
        public void sendFailureMessage(int arg0, Header[] arg1,
                                       byte[] arg2, Throwable error) {
            if(mCallback != null) {
                mCallback.failedWithError(error);
            }
        }

        @Override
        public void sendFinishMessage() {
            if(mCallback != null) {
                mCallback.onFinish();
            }
        }

        @Override
        public void sendProgressMessage(long bytesWritten, long bytesTotal) {
            if(mCallback != null) {
                mCallback.progressUpdate(bytesWritten, bytesTotal);
            }

        }

        @Override
        public void setUseSynchronousMode(boolean arg0) {
        }

        @Override
        public void setRequestURI(URI arg0) {
        }

        @Override
        public void setRequestHeaders(Header[] arg0) {
        }

        @Override
        public URI getRequestURI() {
            return null;
        }
        @Override
        public Header[] getRequestHeaders() {
            return null;
        }

        @Override
        public void sendCancelMessage() {

            if(mCallback != null) {
                mCallback.onCancle();
                mCallback.onFinish();
            }
        }

        @Override
        public void sendRetryMessage(int retryNo) {
// TODO Auto-generated method stub

        }

        @Override
        public boolean getUseSynchronousMode() {
// TODO Auto-generated method stub
            return false;
        }

        @Override
        public void setUsePoolThread(boolean usePoolThread) {
// TODO Auto-generated method stub

        }

        @Override
        public boolean getUsePoolThread() {
// TODO Auto-generated method stub
            return false;
        }

        @Override
        public void onPreProcessResponse(ResponseHandlerInterface instance,
                                         HttpResponse response) {
// TODO Auto-generated method stub

        }

        @Override
        public void onPostProcessResponse(ResponseHandlerInterface instance,
                                          HttpResponse response) {
// TODO Auto-generated method stub

        }

        @Override
        public void setTag(Object TAG) {
// TODO Auto-generated method stub

        }

        @Override
        public Object getTag() {
// TODO Auto-generated method stub
            return null;
        }
    };

    /**
     * Cancel upload by calling this method
     */
    public void cancel() throws Exception {
        AsynchConfig.mClient.cancelAllRequests(true);
        AsynchConfig.mClient.setEnableRedirects(true);
        handle.cancel(true);

    }
}
