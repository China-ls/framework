package com.infinite.water.core.http;

import java.io.InputStream;

/**
 * Created by hx on 16-7-20.
 */
public interface IHttpAction {

    public void onResponse(InputStream is) throws Throwable;

    public void onFailure(Throwable e);

    public void onComplete();

}
