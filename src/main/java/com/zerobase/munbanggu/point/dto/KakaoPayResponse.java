package com.zerobase.munbanggu.point.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class KakaoPayResponse {
    private String aid; // 요청 고유 번호
    private String tid; // 결제 고유 번호
    private String cid; // 가맹점 코드
    private String sid; // 정기 결제용 ID, 정기 결제 CID로 단건 결제 요청 시 발급
    private String partner_order_id; // 가맹점 주문번호
    private String partner_user_id; // 가맹점 회원 id
    private String payment_method_type; // 결제 수단, CARD 또는 MONEY 중 하나
    private Amount amount; // 결제 금액 정보
    private CardInfo card_info; // 결제 상세 정보 (카드 결제일 경우)
    private String item_name; // 상품 이름
    private String item_code; // 상품 코드
    private Integer quantity; // 상품 수량
    private LocalDateTime created_at; // 결제 준비 요청 시각
    private LocalDateTime approved_at; // 결제 승인 시각
    private String payload; // 결제 승인 요청에 대해 저장한 값, 요청 시 전달된 내용

    // Amount, CardInfo 클래스는 각각 결제 금액 정보와 카드 결제 정보를 담는 클래스로 작성되어야 합니다.
    // 해당 내용은 카카오페이 API 응답에 따라 필요한 정보를 담는 구조로 작성되어야 합니다.
}
