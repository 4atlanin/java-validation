package com.example.validation;

import com.example.validation.validators.domain.TestCustomAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationController
{
    @Autowired
    public ValidationService validationService;

    @PostMapping("/post")
    ResponseEntity createrSomething(@RequestBody TestCustomAnnotation dto ) {


        validationService.validateCustomAnnotation( dto );

        return ResponseEntity.ok( dto );
    }

}
