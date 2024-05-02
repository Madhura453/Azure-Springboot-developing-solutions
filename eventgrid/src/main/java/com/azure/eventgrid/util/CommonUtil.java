package com.azure.eventgrid.util;

import com.azure.eventgrid.annotation.Fixed;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

@Slf4j
public class CommonUtil {

    public static void processFixedFile(Field[] fields, String line, Object newInstance) throws IllegalAccessException {
        for (Field field : fields) {
            field.setAccessible(true);
            Fixed fixed = field.getAnnotation(Fixed.class);
            int from = fixed.startIndex();
            int to = fixed.endIndex();
            String value = null;
            if (from <= line.length() && to <= line.length()) {
                value = line.substring(from, to).trim();
            }
            field.set(newInstance, StringUtils.trim(value));
        }
    }
}
