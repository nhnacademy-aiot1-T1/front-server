package com.nhnacademy.front.server.controller;

import com.nhnacademy.front.server.dto.register.RegisterCheckDto;
import com.nhnacademy.front.server.dto.register.RegisterRequestDto;
import com.nhnacademy.front.server.exception.RegisterFailException;
import com.nhnacademy.front.server.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
@Slf4j
public class RegisterController {
    private static final String REGISTER_PAGE = "register";
    private static final String REASON_ATTRIBUTE_NAME = "Message";

    private final AuthService authService;

    /**
     * 회원가입 페이지로 이동합니다.
     */
    @GetMapping
    public String showRegisterForm() {
        return REGISTER_PAGE;
    }

    @PostMapping
    public String doRegister(@ModelAttribute @Valid RegisterCheckDto registerCheckDto, Model model, RedirectAttributes redirectAttributes) {
        if (!registerCheckDto.getPassword().equals(registerCheckDto.getPasswordRetype())) { // todo, annotation 만들어지면 if 문 없애고 validation 순서 확인, 결과 확인
            model.addAttribute(REASON_ATTRIBUTE_NAME, "pw와 pw 확인이 일치하지 않습니다!");

            return REGISTER_PAGE;
        }

        RegisterRequestDto registerRequestDto = new RegisterRequestDto(registerCheckDto.getId(), registerCheckDto.getPassword(), registerCheckDto.getName(), registerCheckDto.getEmail());

        try {
            authService.registerUser(registerRequestDto);

        } catch (RegisterFailException e) {
            log.error(e.getMessage());

            model.addAttribute(REASON_ATTRIBUTE_NAME, e.getMessage());
            return REGISTER_PAGE;
        }

        redirectAttributes.addFlashAttribute(REASON_ATTRIBUTE_NAME, "회원가입이 완료되었습니다!");

        return "redirect:login"; // todo, login
    }
}
