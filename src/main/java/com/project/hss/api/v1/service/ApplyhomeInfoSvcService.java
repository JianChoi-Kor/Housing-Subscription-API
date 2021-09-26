package com.project.hss.api.v1.service;

import com.project.hss.api.v1.dto.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApplyhomeInfoSvcService {

    private Response response;

    public ResponseEntity<?> getLttotPblancList() {
        return response.success();
    }

    public ResponseEntity<?> getNotAPTLttotPblancList() {
        return response.success();
    }

    public ResponseEntity<?> getRemndrLttotPblancList() {
        return response.success();
    }

    public ResponseEntity<?> getAPTLttotPblancDetail() {
        return response.success();
    }

    public ResponseEntity<?> getUrbtyOfctlLttotPblancDetail() {
        return response.success();
    }

    public ResponseEntity<?> getRemndrLttotPblancDetail() {
        return response.success();
    }

    public ResponseEntity<?> getAPTLttotPblancMdl() {
        return response.success();
    }

    public ResponseEntity<?> getUrbtyOfctlLttotPblancMdl() {
        return response.success();
    }

    public ResponseEntity<?> getRemndrLttotPblancMdl() {
        return response.success();
    }
}
