package me.tianjx98.jscrapy.duplicatefilter.impl;

import java.util.HashSet;

import me.tianjx98.jscrapy.duplicatefilter.DuplicateFilter;
import me.tianjx98.jscrapy.http.Request;

/**
 * 使用HashSet去重
 *
 * @ClassName HashDuplicateFilter
 * @Author tian
 * @Date 2019/7/21 20:58
 * @Version 1.0
 */
public class HashDuplicateFilter implements DuplicateFilter {
    private final HashSet<String> urls = new HashSet<String>();

    @Override
    public boolean isDuplicate(Request request) {
        String url = request.getUrl();
        if (urls.contains(url))
            return true;
        urls.add(url);
        return false;
    }
}
