package com.dimmunity.Tree.controller;

import com.dimmunity.Tree.dto.MemberDTO;
import com.dimmunity.Tree.dto.MemberRequestDTO;
import com.dimmunity.Tree.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private final MemberService memberService;

    // 회원가입 페이지 출력 요청
    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save")    // name값을 requestparam에 담아온다
    public ResponseEntity<String> save(@RequestBody MemberDTO memberDTO, Errors errors, Model model) {
        memberService.save(memberDTO);
        return new ResponseEntity<>("Member registered successfully", HttpStatus.CREATED);
    }
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam("email") String email) {
        boolean isEmailTaken = memberService.isEmailTaken(email);
        return ResponseEntity.ok(isEmailTaken);
    }
    //로그인
    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }
    @PostMapping("/login") // session : 로그인 유지
    public ResponseEntity<Object> login(@RequestBody MemberRequestDTO memberRequestDTO, HttpSession session) {
        //System.out.println("Received MemberDTO: " + memberDTO);
        MemberDTO loginResult = memberService.login(memberRequestDTO);
        if (loginResult != null) {
            // login 성공-login한 이메일정보를 session에 담아줌, 구절작성페이지로감
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            // login 실패, 실패창 구현필요 /member/loginfail
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    //회원리스트-나중에 없앨예정
    @GetMapping("/list")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        // html로 가져갈 데이터->model 사용
        model.addAttribute("memberList", memberDTOList);
        return "list";
    }
    //회원조회
    @GetMapping("/member/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        MemberDTO memberDTO=memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail";
    }



    //회원정보수정
    // 수정완료시 로그인페이지로 가도록
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateUser(@RequestBody MemberDTO memberDTO) {
        boolean isValid = memberService.validateUser(memberDTO);
        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody MemberDTO memberDTO) {
        memberService.changePassword(memberDTO);
        return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
    }


    //로그아웃
    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

}
