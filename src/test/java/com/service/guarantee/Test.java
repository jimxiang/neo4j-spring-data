package com.service.guarantee;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Test {
    public static void main(String args[]) {
        List<String> list = new ArrayList<>();
        list.add("a");
        System.out.println(list);
        System.out.println(CollectionUtils.isEmpty(list));
        System.out.println(CollectionUtils.isEmpty(new ArrayList<>()));
        System.out.println(CollectionUtils.isEmpty((Collection<?>) null));

        System.out.println(StringUtils.isEmpty(""));
        System.out.println(StringUtils.isEmpty(null));
    }
}
