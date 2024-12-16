package com.shiaofuk.sqlserver.rentation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class IDCardValidator implements ConstraintValidator<IDCardValidator.IDCard, String> {

    @Constraint(validatedBy = IDCardValidator.class)  // 绑定到验证器
    @Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface IDCard {
        String message() default "身份证号码不合法";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }


    @Override
    public void initialize(IDCard constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        // 校验15位和18位身份证
        if (value.length() == 18) {
            return validate18IDCard(value);
        } else if (value.length() == 15) {
            return validate15IDCard(value);
        }

        return false;
    }

    // 校验18位身份证
    private boolean validate18IDCard(String value) {
        // 校验长度
        if (value.length() != 18) {
            return false;
        }

        // 校验前17位是否是数字
        String regex = "[0-9]{17}";
        if (!value.substring(0, 17).matches(regex)) {
            return false;
        }

        // 校验校验位
        char checkCode = calculateCheckCode(value.substring(0, 17));
        return checkCode == value.charAt(17);
    }

    // 校验15位身份证
    private boolean validate15IDCard(String value) {
        if (value.length() != 15) {
            return false;
        }

        // 校验15位身份证前14位是否是数字
        if (!value.matches("[0-9]{15}")) {
            return false;
        }

        // 转换为18位
        String eighteenIDCard = convert15To18(value);
        return validate18IDCard(eighteenIDCard);
    }

    // 计算18位身份证的校验码
    private char calculateCheckCode(String idCard17) {
        int[] weights = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
        char[] checkCodes = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };

        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += (idCard17.charAt(i) - '0') * weights[i];
        }

        int mod = sum % 11;
        return checkCodes[mod];
    }

    // 将15位身份证转换为18位
    private String convert15To18(String value) {
        String newIDCard = value.substring(0, 6) + "19" + value.substring(6);
        char checkCode = calculateCheckCode(newIDCard);
        return newIDCard + checkCode;
    }
}
