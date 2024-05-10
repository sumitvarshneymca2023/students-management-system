package com.students.management.system.utils;

import com.students.management.system.constants.MessageConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommonUtilsService {

    public ResponseEntity<Object> requestValidation(BindingResult bindingResult) {
        List<String> errors = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        log.error(MessageConstants.REQUEST_ERROR, errors);
        return ResponseHandler.response("", errors.toString(), false, HttpStatus.BAD_REQUEST);
    }

}
