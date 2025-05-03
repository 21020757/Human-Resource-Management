package org.example.hrm.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class Base64Service {
    public String encode(MultipartFile file) throws IOException {
        return Base64.getEncoder().encodeToString(file.getBytes());
    }

    public Resource decode(String base64) throws IOException {
        byte[] decoded = Base64.getDecoder().decode(base64);
        return new ByteArrayResource(decoded);
    }

    private byte[] decodeToBytes(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
