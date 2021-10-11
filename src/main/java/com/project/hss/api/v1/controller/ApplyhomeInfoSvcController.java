package com.project.hss.api.v1.controller;

import com.project.hss.api.lib.Helper;
import com.project.hss.api.v1.dto.request.api.*;
import com.project.hss.api.v1.dto.response.api.LttotPblancListRes;
import com.project.hss.api.v1.service.ApplyhomeInfoSvcService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "ApplyhomeInfoSvc", description = "전국 청약 분양정보 조회 서비스 api 데이터 요청")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/hhs")
@RestController
public class ApplyhomeInfoSvcController {

    private final ApplyhomeInfoSvcService applyhomeInfoSvcService;
    private final com.project.hss.api.v1.dto.Response response;

    @Operation(summary = "APT 분양 정보 조회 (완)", description = "APT 분양 정보 조회", tags = "ApplyhomeInfoSvc", security = {@SecurityRequirement(name = "Bearer")})
    @PostMapping("/getLttotPblancList")
    public ResponseEntity<?> getLttotPblancList(@Validated LttotPblancListReq lttotPblancListReq, Errors errors) throws IOException {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return applyhomeInfoSvcService.getLttotPblancList(lttotPblancListReq);
    }

    @Operation(summary = "오피스텔/도시형/(공공지원)민간임대 분양정보 조회", description = "오피스텔/도시형/(공공지원)민간임대 분양정보 조회", tags = "ApplyhomeInfoSvc", security = {@SecurityRequirement(name = "Bearer")})
    @PostMapping("/getNotAPTLttotPblancList")
    public ResponseEntity<?> getNotAPTLttotPblancList(@Validated NotAPTLttotPblancListReq notAPTLttotPblancListReq, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return applyhomeInfoSvcService.getNotAPTLttotPblancList(notAPTLttotPblancListReq);
    }

    @Operation(summary = "APT무순위/취소후재공급 분양정보 조회", description = "APT무순위/취소후재공급 분양정보 조회", tags = "ApplyhomeInfoSvc", security = {@SecurityRequirement(name = "Bearer")})
    @PostMapping("/getRemndrLttotPblancList")
    public ResponseEntity<?> getRemndrLttotPblancList(@Validated RemndrLttotPblancListReq remndrLttotPblancListReq, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return applyhomeInfoSvcService.getRemndrLttotPblancList(remndrLttotPblancListReq);
    }

    @Operation(summary = "APT 분양정보 상세 조회", description = "APT 분양정보 상세 조회", tags = "ApplyhomeInfoSvc", security = {@SecurityRequirement(name = "Bearer")})
    @PostMapping("/getAPTLttotPblancDetail")
    public ResponseEntity<?> getAPTLttotPblancDetail(@Validated APTLttotPblancDetailReq aptLttotPblancDetailReq, Errors errors) throws IOException {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return applyhomeInfoSvcService.getAPTLttotPblancDetail(aptLttotPblancDetailReq);
    }

    @Operation(summary = "오피스텔/도시형/(공공지원)민간임대 분양정보 상세 조회", description = "오피스텔/도시형/(공공지원)민간임대 분양정보 상세 조회", tags = "ApplyhomeInfoSvc", security = {@SecurityRequirement(name = "Bearer")})
    @PostMapping("/getUrbtyOfctlLttotPblancDetail")
    public ResponseEntity<?> getUrbtyOfctlLttotPblancDetail(@Validated UrbtyOfctlLttotPblancDetailReq urbtyOfctlLttotPblancDetailReq, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return applyhomeInfoSvcService.getUrbtyOfctlLttotPblancDetail(urbtyOfctlLttotPblancDetailReq);
    }

    @Operation(summary = "APT무순위/취소후재공급 분양정보 상세 조회", description = "APT무순위/취소후재공급 분양정보 상세 조회", tags = "ApplyhomeInfoSvc", security = {@SecurityRequirement(name = "Bearer")})
    @PostMapping("/getRemndrLttotPblancDetail")
    public ResponseEntity<?> getRemndrLttotPblancDetail(@Validated RemndrLttotPblancDetailReq remndrLttotPblancDetailReq, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return applyhomeInfoSvcService.getRemndrLttotPblancDetail(remndrLttotPblancDetailReq);
    }

    @Operation(summary = "APT 분양정보 주택형별 상세 조회", description = "APT 분양정보 주택형별 상세 조회", tags = "ApplyhomeInfoSvc", security = {@SecurityRequirement(name = "Bearer")})
    @PostMapping("/getAPTLttotPblancMdl")
    public ResponseEntity<?> getAPTLttotPblancMdl(@Validated APTLttotPblancMdlReq aptLttotPblancMdlReq, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return applyhomeInfoSvcService.getAPTLttotPblancMdl(aptLttotPblancMdlReq);
    }

    @Operation(summary = "오피스텔/도시형/(공공지원)민간임대 분양정보 주택형별 상세 조회", description = "오피스텔/도시형/(공공지원)민간임대 분양정보 주택형별 상세 조회", tags = "ApplyhomeInfoSvc", security = {@SecurityRequirement(name = "Bearer")})
    @PostMapping("/getUrbtyOfctlLttotPblancMdl")
    public ResponseEntity<?> getUrbtyOfctlLttotPblancMdl(@Validated UrbtyOfctlLttotPblancMdlReq urbtyOfctlLttotPblancMdlReq, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return applyhomeInfoSvcService.getUrbtyOfctlLttotPblancMdl(urbtyOfctlLttotPblancMdlReq);
    }

    @Operation(summary = "APT무순위/취소후재공급 분양정보 주택형별 상세 조회", description = "APT무순위/취소후재공급 분양정보 주택형별 상세 조회", tags = "ApplyhomeInfoSvc", security = {@SecurityRequirement(name = "Bearer")})
    @PostMapping("/getRemndrLttotPblancMdl")
    public ResponseEntity<?> getRemndrLttotPblancMdl(@Validated RemndrLttotPblancMdlReq remndrLttotPblancMdlReq, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return applyhomeInfoSvcService.getRemndrLttotPblancMdl(remndrLttotPblancMdlReq);
    }
}
