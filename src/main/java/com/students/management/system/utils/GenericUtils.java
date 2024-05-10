package com.students.management.system.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

@Component
@Slf4j
public class GenericUtils {




    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public static boolean isUrlValid(String url) {
        try {
            url = url.replaceAll("\\s", "%20");
            URL obj = new URL(url);
            obj.toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    public static String appendSpaceInPostalCode(String postalCode) {
        String postalCodeWithSpace = postalCode;
        if (postalCode.length() > 3) {
            postalCodeWithSpace = postalCode.substring(0, 3) + " " + postalCode.substring(3);
        }
        return postalCodeWithSpace;
    }

    static List<String> acceptedFileTypes = Arrays.asList("video/mp4","video/x-m4v","video/x-ms-wmv","video/3gpp","video/x-flv","video/x-msvideo","video/quicktime","video/webm","application/x-matroska", "image/png", "image/jpeg", "image/jpg","image/gif","application/pdf");
    public static boolean validateFileExtension(MultipartFile file) {
        Tika tika = new Tika();
        try {
            file.getContentType();
            return acceptedFileTypes.contains(tika.detect(file.getBytes()));
        } catch (IOException e) {
            log.error("Exception in file validation", e);
        }
        return false;
    }

    private static final Random random = new Random();
    public static int generateOtp(){
        return 100000 + random.nextInt(900000);
    }

}
