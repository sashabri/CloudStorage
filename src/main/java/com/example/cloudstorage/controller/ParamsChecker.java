package com.example.cloudstorage.controller;

import com.example.cloudstorage.controller.entities.LoadFileBody;
import com.example.cloudstorage.controller.entities.LoginBody;
import com.example.cloudstorage.exception.*;
import com.example.cloudstorage.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamsChecker {
    private static final Logger log = LoggerFactory.getLogger(ParamsChecker.class);

    public static void checkShouldBeNotEmptyStr(String param) throws InputParamIsNullException {
        if (param == null || param.isEmpty()) {
            InputParamIsNullException inputParamIsNullException = new InputParamIsNullException("Invalid data");
            log.error("Input param - " + param + " is null", inputParamIsNullException);
            throw inputParamIsNullException;
        }
    }

    public static void checkShouldBeNotEmptyNumber(Integer numberParam) throws InputParamIsNullException {
        if (numberParam == 0) {
            InputParamIsNullException inputParamIsNullException = new InputParamIsNullException("Invalid data");
            log.error("Input number + " + numberParam + " == 0", inputParamIsNullException);
            throw inputParamIsNullException;
        }
    }

    public static void checkShouldBeNotEmptyLoginBody(LoginBody body) throws InvalidLoginBodyException {
        if (body == null) {
            InvalidLoginBodyException invalidLoginBodyException = new InvalidLoginBodyException("You json is empty");
            log.error("Input LoginBody is null", invalidLoginBodyException);
            throw invalidLoginBodyException;
        }
    }
    public static void checkShouldBeNotEmptyLoadFileBody(LoadFileBody body) throws InvalidLoadFileBodyException {
        if (body == null) {
            InvalidLoadFileBodyException invalidLoadFileBodyException = new InvalidLoadFileBodyException("You json is empty");
            log.error("Input LoadFileBody is null", invalidLoadFileBodyException);
            throw invalidLoadFileBodyException;
        }
    }

    public static void checkShouldBeNotEmptyUser(UserInfo user) throws UserIsNullException {
        if (user == null) {
            UserIsNullException userIsNullException = new UserIsNullException("User not found");
            log.error("Input UserInfo is null", userIsNullException);
            throw userIsNullException;
        }
    }
}


