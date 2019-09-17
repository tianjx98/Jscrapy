package me.tianjx98.Jscrapy.duplicatefilter.impl;

import me.tianjx98.Jscrapy.duplicatefilter.DuplicateFilter;
import me.tianjx98.Jscrapy.http.Request;

public class TrieDuplicateFilter implements DuplicateFilter {
    private Trie trie = new Trie();

    @Override
    public boolean isDuplicate(Request request) {
        String url = request.getUrl().toString();
        if (trie.contains(url)) return true;
        trie.add(url);
        return false;
    }

    //public boolean isDuplicate(String url) {
    //    if (trie.contains(url)) return true;
    //    trie.add(url);
    //    return false;
    //}
}