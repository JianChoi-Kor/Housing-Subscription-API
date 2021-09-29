package com.project.hss.api.v1.service;

import com.project.hss.api.v1.dto.Response;
import com.project.hss.api.v1.dto.request.api.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApplyhomeInfoSvcService {

    private Response response;

    public ResponseEntity<?> getLttotPblancList(LttotPblancList lttotPblancList) {
        return response.success();
    }

    public ResponseEntity<?> getNotAPTLttotPblancList(NotAPTLttotPblancList notAPTLttotPblancList) {
        return response.success();
    }

    public ResponseEntity<?> getRemndrLttotPblancList(RemndrLttotPblancList remndrLttotPblancList) {
        return response.success();
    }

    public ResponseEntity<?> getAPTLttotPblancDetail(APTLttotPblancDetail aptLttotPblancDetail) {
        return response.success();
    }

    public ResponseEntity<?> getUrbtyOfctlLttotPblancDetail(UrbtyOfctlLttotPblancDetail urbtyOfctlLttotPblancDetail) {
        return response.success();
    }

    public ResponseEntity<?> getRemndrLttotPblancDetail(RemndrLttotPblancDetail remndrLttotPblancDetail) {
        return response.success();
    }

    public ResponseEntity<?> getAPTLttotPblancMdl(APTLttotPblancMdl aptLttotPblancMdl) {
        return response.success();
    }

    public ResponseEntity<?> getUrbtyOfctlLttotPblancMdl(UrbtyOfctlLttotPblancMdl urbtyOfctlLttotPblancMdl) {
        return response.success();
    }

    public ResponseEntity<?> getRemndrLttotPblancMdl(RemndrLttotPblancMdl remndrLttotPblancMdl) {
        return response.success();
    }
}
