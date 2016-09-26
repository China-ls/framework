package com.infinite.eoa.auth.client;

import com.infinite.eoa.auth.client.entity.AuthRequest;
import com.infinite.eoa.auth.client.entity.AuthResponse;
import com.infinite.eoa.auth.client.entity.AuthToken;

/**
 * Created by hx on 16-7-4.
 */
public interface IAuthClient {

    public AuthToken getToken(AuthRequest request);

    public AuthResponse checkPermistion(AuthRequest request);

}
