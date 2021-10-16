package com.project.hss.api.lib;

import org.bouncycastle.jcajce.provider.digest.SHA256;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;

public final class Encrypt {

    private Encrypt() {
    }

    public static String getSha2bit256(String utf8) {
        byte[] result = getSha2bit256(utf8.getBytes(StandardCharsets.UTF_8));
        return Hex.toHexString(result);
    }

    public static byte[] getSha2bit256(byte[] bytes) {
        SHA256.Digest digest = new SHA256.Digest();
        return digest.digest(bytes);
    }
}
