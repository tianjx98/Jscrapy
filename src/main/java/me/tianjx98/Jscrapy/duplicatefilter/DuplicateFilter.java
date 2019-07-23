package me.tianjx98.Jscrapy.duplicatefilter;

import me.tianjx98.Jscrapy.http.Request;

public interface DuplicateFilter {
    /**
     * 检验请求之前是否访问过
     * 如果访问过，返回 true
     * 否则返回false，并将当前请求的url记录下来
     *
     * @param request 请求类
     * @return 请求重复返回true，否则返回false
     */
    boolean isDuplicate(Request request);
}
