package com.infinite.eoa.service.codec;

/**
 * @author hx on 16-7-28.
 */
public interface IPasswordCodec {
    public String encode(String password);

    public String encode(byte[] password);

    public String decode(String password);

    public String decode(byte[] password);
}
