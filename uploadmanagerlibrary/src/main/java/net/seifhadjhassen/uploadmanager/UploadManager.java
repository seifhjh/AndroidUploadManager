package net.seifhadjhassen.uploadmanager;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;


public  class UploadManager {


    public interface OnUploadComplitListener{
        void onComplite(String response);
        void onFailed(Throwable error);
    }

    public interface OnProgressListener{
        void onProgress(long percent);
        void onComplite(String response);
        void onFailed(Throwable error);
    }
    public interface OnProgressMultiListener{
        void onProgress(long percent);
        void onComplite(List<String> listResponse);
        void onFailed(Throwable error);
    }
    public interface OnUploadEventListener{
        void onStartUploading();
        void uploadComplete(String response);
        void failedWithError(Throwable error);
        void progressUpdate(long bytesWritten, long bytesTotal);
        void onCancle();
        void onFinish();
    }

    public static String urlServer;



    public static void uploadFileFromFile(Context context,File file){
        AsyncLoader.LoaderCallBackHandler callHandler =new AsyncLoader.LoaderCallBackHandler() {
            @Override
            public void onStartUploading() {
                Log.e("Upload","onStartUploading :");

            }

            @Override
            public void uploadComplete(String response) {
                Log.e("Upload","uploadComplete :"+response);

            }

            @Override
            public void failedWithError(Throwable error) {
                Log.e("Upload","failedWithError :"+error.getMessage());

            }

            @Override
            public void progressUpdate(long bytesWritten, long bytesTotal) {
                long prog=bytesWritten/(bytesTotal/100);
                //Log.e("Upload","progressUpdate :"+prog+" %");

            }

            @Override
            public void onCancle() {

            }

            @Override
            public void onFinish() {
                Log.e("Upload","onFinish :");

            }
        };


        RequestParams params = new RequestParams();
        try {
            params.put("file", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(urlServer!=null) {
            AsyncLoader asyncUploader = new AsyncLoader(context,
                    urlServer,
                    params, callHandler);
            asyncUploader.startTransfer();
        }else {
            Log.e("UploadManager","urlServer is null , please add upload url server , UploadManager.urlServer=<YOUR-URL-SERVER>;");
        }

    }

    public static void uploadFileFromFile(Context context, File file, final OnUploadComplitListener uploadComplitListener){
        AsyncLoader.LoaderCallBackHandler callHandler =new AsyncLoader.LoaderCallBackHandler() {
            @Override
            public void onStartUploading() {

            }

            @Override
            public void uploadComplete(String response) {


                Log.e("uploadManager","uploadComplete "+response);

                uploadComplitListener.onComplite(response);


            }

            @Override
            public void failedWithError(Throwable error) {
                uploadComplitListener.onFailed(error);

            }

            @Override
            public void progressUpdate(long bytesWritten, long bytesTotal) {

            }

            @Override
            public void onCancle() {

            }

            @Override
            public void onFinish() {

            }
        };


        RequestParams params = new RequestParams();
        try {
            params.put("file", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        if(urlServer!=null) {
            AsyncLoader asyncUploader = new AsyncLoader(context,
                    urlServer,
                    params, callHandler);
            asyncUploader.startTransfer();
        }else {
            Log.e("UploadManager","urlServer is null , please add upload url server , UploadManager.urlServer=<YOUR-URL-SERVER>;");
        }

    }

    public static void uploadFileFromFile(Context context, File file, final OnProgressListener progressListener){
        AsyncLoader.LoaderCallBackHandler callHandler =new AsyncLoader.LoaderCallBackHandler() {
            @Override
            public void onStartUploading() {

            }

            @Override
            public void uploadComplete(String response) {

                Log.e("uploadManager","uploadComplete "+response);

                progressListener.onComplite(response);





            }

            @Override
            public void failedWithError(Throwable error) {
                progressListener.onFailed(error);

            }

            @Override
            public void progressUpdate(long bytesWritten, long bytesTotal) {
                Log.e("uploadmanager",bytesWritten+"");
                progressListener.onProgress(bytesWritten/(bytesTotal/100));

            }

            @Override
            public void onCancle() {

            }

            @Override
            public void onFinish() {

            }
        };


        RequestParams params = new RequestParams();
        try {
            params.put("file", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        if(urlServer!=null) {
            AsyncLoader asyncUploader = new AsyncLoader(context,
                    urlServer,
                    params, callHandler);
            asyncUploader.startTransfer();
        }else {
            Log.e("UploadManager","urlServer is null , please add upload url server , UploadManager.urlServer=<YOUR-URL-SERVER>;");
        }

    }


    public static void uploadFileFromFile(Context context, File file, final OnUploadEventListener uploadEventListener){
        AsyncLoader.LoaderCallBackHandler callHandler =new AsyncLoader.LoaderCallBackHandler() {
            @Override
            public void onStartUploading() {
                uploadEventListener.onStartUploading();

            }

            @Override
            public void uploadComplete(String response) {
                String encode = null;

                Log.e("uploadManager","uploadComplete "+response);
                try {
                    encode = URLEncoder.encode(response, "ISO-8859-1");

                    response = URLDecoder.decode(encode, "UTF-8");
                    JSONObject jsonArray = new JSONObject(response);
                    Log.e("jsonlength setUserInfo", response.length() + " " );

                    JSONObject master = jsonArray.getJSONObject("master");
                    uploadEventListener.uploadComplete(master.getString("url"));




                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void failedWithError(Throwable error) {
                uploadEventListener.failedWithError(error);

            }

            @Override
            public void progressUpdate(long bytesWritten, long bytesTotal) {
                uploadEventListener.progressUpdate(bytesWritten,bytesTotal);

            }

            @Override
            public void onCancle() {
                uploadEventListener.onCancle();

            }

            @Override
            public void onFinish() {
                uploadEventListener.onFinish();

            }
        };


        RequestParams params = new RequestParams();
        try {
            params.put("file", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        if(urlServer!=null) {
            AsyncLoader asyncUploader = new AsyncLoader(context,
                    urlServer,
                    params, callHandler);
            asyncUploader.startTransfer();
        }else {
            Log.e("UploadManager","urlServer is null , please add upload url server , UploadManager.urlServer=<YOUR-URL-SERVER>;");
        }

    }


    private static void uploadMultiFiles(final Context context, final List<File> listFile, final List<String> listResponse, final int index, final int lastIndex, final int numFiles, final OnProgressMultiListener progressListener) {

        if(index<=lastIndex)
        {
            uploadFileFromFile(context, listFile.get(index), new OnProgressListener() {
                int indexx=index;
                @Override
                public void onProgress(long percent) {
                    double percOfOnFile=(double)(100/numFiles);
                    double percOfCurrentFile=percOfOnFile*(indexx+1);
                    Log.e("Uploadmanager","percOfOnFile ="+percOfOnFile);

                    Log.e("Uploadmanager",percent+"/"+"(100/"+percOfCurrentFile+")");

                    double perc=100/percOfCurrentFile;
                    Log.e("Uploadmanager",percent+"/"+perc+" %");
                    Log.e("Uploadmanager",percent/perc+" %");

                    progressListener.onProgress((long) (percent/perc));

                }

                @Override
                public void onComplite(String response) {
                    listResponse.add(response);
                    indexx++;
                    if(indexx<=lastIndex)
                        uploadMultiFiles(context,listFile,listResponse,indexx,lastIndex,numFiles,progressListener);
                    else progressListener.onComplite(listResponse);


                }

                @Override
                public void onFailed(Throwable error) {
                    progressListener.onFailed(error);

                }
            });
        }
    }



}
