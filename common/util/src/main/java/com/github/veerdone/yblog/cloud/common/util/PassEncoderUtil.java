package com.github.veerdone.yblog.cloud.common.util;

public class PassEncoderUtil {
    public static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public static String encode(CharSequence charSequence) {
        return passwordEncoder.encode(charSequence);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
