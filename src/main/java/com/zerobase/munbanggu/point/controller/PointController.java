
package com.zerobase.munbanggu.point.controller;


//import static org.hibernate.id.enhanced.StandardOptimizerDescriptor.log;

import com.zerobase.munbanggu.point.dto.KakaoResponse;
import com.zerobase.munbanggu.point.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/pint/user")
@RequiredArgsConstructor
public class PointController {
    private final KakaoPayService kakaoPayService;

    @PostMapping("/{user_id}")
    public ResponseEntity<KakaoResponse> readyToKakaoPay(
            @PathVariable("user_id") Long userId,
            @RequestBody KakaoPayRequest payRequest) {

        KakaoResponse response = kakaoPayService.kakaoPayReady(payRequest);
        return ResponseEntity.ok(response);
    }
}


