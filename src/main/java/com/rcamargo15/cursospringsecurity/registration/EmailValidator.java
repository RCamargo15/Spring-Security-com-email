package com.rcamargo15.cursospringsecurity.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        if(s.length() > 10 && s.contains("@gmail.com")){
            return true;
        }
        return false;
    }
}
