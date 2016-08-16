package com.infinite.framework.auth.client;

import com.infinite.framework.auth.client.entity.AuthRequest;
import com.infinite.framework.auth.client.entity.AuthResponse;
import com.infinite.framework.auth.client.entity.AuthToken;

/**
 * Created by hx on 16-7-4.
 */
public interface IAuthClient {

    public AuthToken getToken(AuthRequest request);

    public AuthResponse checkPermistion(AuthRequest request);

}
