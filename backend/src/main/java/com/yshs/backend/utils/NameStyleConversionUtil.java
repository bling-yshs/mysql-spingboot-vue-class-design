package com.yshs.backend.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NameStyleConversionUtil {
    //将下划线命名转换成驼峰下命名
    public String convertToCamelCase(String input) {
        StringBuilder result = new StringBuilder();
        boolean nextUpperCase = false;
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (currentChar == '_') {
                nextUpperCase = true;
            } else if (nextUpperCase) {
                result.append(Character.toUpperCase(currentChar));
                nextUpperCase = false;
            } else {
                result.append(Character.toLowerCase(currentChar));
            }
        }
        return result.toString();
    }

    public List<String> convertToCamelCase(List<String> inputs) {
        List<String> results = new ArrayList<>();
        for (String input : inputs) {
            String result = convertToCamelCase(input);
            results.add(result);
        }
        return results;
    }

    // 将驼峰命名转换成下划线命名
    public String convertToUnderscoreCase(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                if (i > 0) {
                    result.append('_');
                }
                result.append(Character.toLowerCase(currentChar));
            } else {
                result.append(currentChar);
            }
        }
        return result.toString();
    }

    public List<String> convertToUnderscoreCase(List<String> inputs) {
        List<String> results = new ArrayList<>();
        for (String input : inputs) {
            String result = convertToUnderscoreCase(input);
            results.add(result);
        }
        return results;
    }
}
