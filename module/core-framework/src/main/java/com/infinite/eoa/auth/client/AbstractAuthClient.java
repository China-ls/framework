package com.infinite.eoa.auth.client;

import com.infinite.eoa.auth.client.entity.AuthRequest;
import com.infinite.eoa.auth.client.entity.AuthResponse;
import com.infinite.eoa.auth.client.entity.AuthToken;

/**
 * Created by hx on 16-7-4.
 */
public class AbstractAuthClient implements IAuthClient {
    public AuthToken getToken(AuthRequest request) {
        return null;
    }

    public AuthResponse checkPermistion(AuthRequest request) {
        return null;
    }
}
