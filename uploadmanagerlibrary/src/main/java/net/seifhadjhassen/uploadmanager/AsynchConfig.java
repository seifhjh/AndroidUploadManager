package net.seifhadjhassen.uploadmanager;

import com.loopj.android.http.AsyncHttpClient;

final class AsynchConfig {

    static AsyncHttpClient mClient = new AsyncHttpClient(true,80,443);

}
