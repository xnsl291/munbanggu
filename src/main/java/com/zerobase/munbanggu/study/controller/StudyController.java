package com.zerobase.munbanggu.study.controller;

import static com.zerobase.munbanggu.common.type.ErrorCode.STUDY_NOT_EXIST;
import static com.zerobase.munbanggu.common.type.ErrorCode.USER_NOT_EXIST;

import com.zerobase.munbanggu.auth.TokenProvider;
import com.zerobase.munbanggu.study.dto.StudyDto;
import com.zerobase.munbanggu.study.exception.StudyException;
import com.zerobase.munbanggu.study.model.entity.Study;
import com.zerobase.munbanggu.study.model.entity.StudyMember;
import com.zerobase.munbanggu.study.service.StudyService;
import com.zerobase.munbanggu.user.model.entity.User;
import com.zerobase.munbanggu.user.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/study")
@RequiredArgsConstructor
public class StudyController {
    private static final String AUTH_HEADER = "Authorization";
    private final StudyService studyService;
    private final UserService userService;
    private final TokenProvider tokenProvider;
  
    @PostMapping()
    public ResponseEntity<?> openStudy(@RequestHeader(name = AUTH_HEADER) String token,@RequestBody StudyDto studyDto) {

        Map<String, Object> response = new HashMap<>();
        response.put("errorCode", HttpStatus.NOT_FOUND);
        response.put("message", "스터디를 개설할 수 없습니다.");

        Optional<User> user = userService.getUser(tokenProvider.getId(token));
        if (user.isPresent()) {
            try{
                studyService.openStudy(studyDto);
                return new ResponseEntity<>(HttpStatus.CREATED);

            }catch (StudyException ex){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }catch (Exception ex){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
            }

            /*Study openedStudy = studyService.openStudy(studyDto, user.get());
            return new ResponseEntity<>(openedStudy, HttpStatus.CREATED);*/

        }else {
            // 토큰이 유효하지 않은 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
        }
    }

    @PutMapping("{study_id}")
    public ResponseEntity<?> updateStudy(@PathVariable Long studyId,
            @RequestHeader(name = AUTH_HEADER) String token,
            @RequestBody StudyDto updatedStudyDto) {
        Map<String, Object> response = new HashMap<>();
        response.put("errorCode", HttpStatus.NOT_FOUND);
        response.put("message", STUDY_NOT_EXIST);

        if (token == null) {
            // 'Authorization' 헤더가 누락된 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 존재하지 않음");
        }
        Optional<User> user = userService.getUser(tokenProvider.getId(token));
        if (user.isPresent()) {
            try{
                studyService.updateStudy(studyId, updatedStudyDto);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            catch (StudyException ex){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }catch (Exception ex){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
            }
        }else {
            // 토큰이 유효하지 않은 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
        }

    }

    @DeleteMapping("{study_id}")
    public ResponseEntity<?> deleteStudy(@PathVariable Long studyId,
            @RequestHeader(name = AUTH_HEADER) String token
    ) {
        Map<String, Object> response = new HashMap<>();
        response.put("errorCode", HttpStatus.NOT_FOUND);
        response.put("message", STUDY_NOT_EXIST);
        if (token == null) {
            // 'Authorization' 헤더가 누락된 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 존재하지 않음");
        }
        Optional<User> user = userService.getUser(tokenProvider.getId(token));
        if (user.isPresent()) {
            try{
                studyService.deleteStudy(studyId);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            catch (StudyException ex){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }catch (Exception ex){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
            }
        }else {
            // 토큰이 유효하지 않은 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

    }
    @GetMapping
    public ResponseEntity<List<Study>> searchStudiesByKeyword(@RequestParam String keyword) {

        studyService.searchStudiesByKeyword(keyword);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Study> getStudyDetails(@PathVariable Long id) {
        Study study = studyService.getStudyDetails(id);
        if (study != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            // 스터디를 찾을 수 없는 경우
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{studyId}/members")
    public ResponseEntity<String> addMemberToStudy(@PathVariable Long studyId,
            @RequestParam Long userId,
            @RequestHeader(name = "Authorization") String token) {

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 존재하지 않음");
        }

        Optional<User> user = userService.getUser(tokenProvider.getId(token));
        if (user.isPresent()) {
            studyService.addMemberToStudy(studyId, userId);
            return  new ResponseEntity<>(HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
        }
    }

}
