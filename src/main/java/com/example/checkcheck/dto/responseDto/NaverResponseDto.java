package com.example.checkcheck.dto.responseDto;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class NaverResponseDto {

    private Long naverId;
    private String nickname;
    private String email;


}
