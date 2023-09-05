package com.woorifisa.kboxwoori.domain.event.controller;

import com.woorifisa.kboxwoori.domain.event.dto.EventResponseDto;
import com.woorifisa.kboxwoori.domain.user.exception.NotAuthenticatedAccountException;
import com.woorifisa.kboxwoori.domain.event.exception.WooriLinkRequiredException;
import com.woorifisa.kboxwoori.domain.event.service.EventService;
import com.woorifisa.kboxwoori.global.config.security.PrincipalDetails;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseDto<EventResponseDto> getCurrentEvent() {
        return ResponseDto.success(eventService.findCurrentEvent());
    }

    @PostMapping("/{eventId}")
    public ResponseDto joinEvent(@PathVariable Long eventId,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails) {
        //TODO: eventId가 다를 때 처리
        if (principalDetails == null) {
            throw NotAuthenticatedAccountException.EXCEPTION;
        }

        if (!principalDetails.isWooriLinked()) {
            throw WooriLinkRequiredException.EXCEPTION;
        }

        eventService.joinEvent(principalDetails.getUsername());
        return ResponseDto.success();
    }

    @GetMapping("/{eventId}")
    public ResponseDto<EventResponseDto> getEvent(@PathVariable Long eventId) {
        return ResponseDto.success(eventService.getEvent(eventId));
    }

    @PostMapping("/{eventId}/addr")
    public ResponseDto saveAddress(@PathVariable Long eventId,
                                   @AuthenticationPrincipal PrincipalDetails principalDetails,
                                   @RequestBody String addr) {
        if (principalDetails == null) {
            throw NotAuthenticatedAccountException.EXCEPTION;
        }

        eventService.saveAddress(eventId, principalDetails.getUsername(), addr);
        return ResponseDto.success();
    }
}
