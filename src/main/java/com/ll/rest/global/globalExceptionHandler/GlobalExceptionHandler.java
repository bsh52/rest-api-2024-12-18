package com.ll.rest.global.globalExceptionHandler;

import com.ll.rest.global.app.Appconfig;
import com.ll.rest.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<RsData<Void>> handler(NoSuchElementException e) {

        if (Appconfig.isNotProd()) { //운영모드일때는 실행 안함
            e.printStackTrace(); //콘솔에 에러 로그 출력
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new RsData<>(
                                "404-1",
                                "해당 데이터가 존재하지 않습니다."
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RsData<Void>> handler(MethodArgumentNotValidException e) {

        if (Appconfig.isNotProd()) {
            e.printStackTrace(); //콘솔에 에러 로그 출력
        }

        String message = e.getBindingResult()
                .getAllErrors()
                .stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> (FieldError) error)
                .map(error -> error.getField() + "-" + error.getCode() + "-" + error.getDefaultMessage())
                .sorted(Comparator.comparing(String::toString))
                .collect(Collectors.joining("\n"));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new RsData<>(
                                "400-1",
                                message
                        )
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RsData<Void>> handler(RuntimeException e) {

        if (Appconfig.isNotProd()) { //운영모드일때는 실행 안함
            e.printStackTrace(); //콘솔에 에러 로그 출력
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new RsData<>(
                                "400-1",
                                e.getMessage()
                        )
                );
    }
}
