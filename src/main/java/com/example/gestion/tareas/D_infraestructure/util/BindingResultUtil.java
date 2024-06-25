package com.example.gestion.tareas.D_infraestructure.util;

import lombok.experimental.UtilityClass;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class BindingResultUtil {
    public static Map<String, Object> catchBadRequest(BindingResult bindingResult, String messageError, String successMessage, @NotNull Object entity) {
        Map<String, Object> mapData = new HashMap<>();
        if (bindingResult.hasErrors()){
            mapData.put("mapResponseBadRequest", ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError));
            mapData.put("mapValueTrue", true);
        } else {
            mapData.put("mapResponseSuccess", ResponseEntity.status(HttpStatus.OK).body(successMessage + "\n" + JacksonUtil.serializeObjet(entity)));
            mapData.put("mapValueFalse", false);
        }
        return mapData;
    }
}
