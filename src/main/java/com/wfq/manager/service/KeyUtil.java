package com.wfq.manager.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author allen
 * @ClassName KeyUtil
 * @Description
 * @Date 2019/9/30 10:46
 **/
public class KeyUtil {
    static Pattern pattern = Pattern.compile("\\{(.+?)\\}");

    public static String getKeyFromTemplate(String template, String... args) {
        Matcher matcher = pattern.matcher(template);
        int i = 0;
        while (matcher.find() && i < args.length) {
            template = template.replace(matcher.group(), args[i]);
            i++;
        }
        return template;
    }


//    public static void main(String[] args) {
//        String template = "A:{0}:{1}";
//        String a = getKeyFromTemplate(template, "a", "b");
//        System.out.println(a);
//    }
}