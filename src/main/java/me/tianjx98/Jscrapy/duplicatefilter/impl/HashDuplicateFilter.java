package me.tianjx98.Jscrapy.duplicatefilter.impl;

import me.tianjx98.Jscrapy.duplicatefilter.DuplicateFilter;
import me.tianjx98.Jscrapy.http.Request;

import java.util.HashSet;

/**
 * 使用HashSet去重
 *
 * @ClassName HashDuplicateFilter
 * @Description TODO
 * @Author tian
 * @Date 2019/7/21 20:58
 * @Version 1.0
 */
public class HashDuplicateFilter implements DuplicateFilter {
    private final HashSet<String> urls = new HashSet<String>();

    @Override
    public boolean isDuplicate(Request request) {
        String url = request.getUrl().toString();
        if (request.isDoFilter() && urls.contains(url)) return true;
        urls.add(url);
        return false;
    }
}
