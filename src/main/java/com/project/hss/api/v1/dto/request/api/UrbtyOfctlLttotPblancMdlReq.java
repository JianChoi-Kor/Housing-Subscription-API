package com.project.hss.api.v1.dto.request.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UrbtyOfctlLttotPblancMdlReq {

    @Schema(description = "주택관리번호", example = "2020950007")
    @Pattern(regexp = "^[0-9]{10}$", message = "주택관리번호가 올바르지 않습니다.")
    @NotBlank(message = "주택관리번호는 필수값입니다.")
    private String houseManageNo;

    @Schema(description = "공고번호", example = "2020950007")
    @Pattern(regexp = "^[0-9]*$", message = "공고번호가 올바르지 않습니다.")
    @NotBlank(message = "공고번호는 필수입니다.")
    private String pblancNo;
}
