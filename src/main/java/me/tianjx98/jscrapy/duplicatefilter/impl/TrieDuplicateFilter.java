package me.tianjx98.jscrapy.duplicatefilter.impl;

import me.tianjx98.jscrapy.duplicatefilter.DuplicateFilter;
import me.tianjx98.jscrapy.http.Request;

public class TrieDuplicateFilter implements DuplicateFilter {
    private Trie trie = new Trie();

    @Override
    public boolean isDuplicate(Request request) {
        String url = request.getUrl();
        if (trie.contains(url))
            return true;
        trie.add(url);
        return false;
    }

    // public boolean isDuplicate(String url) {
    // if (trie.contains(url)) return true;
    // trie.add(url);
    // return false;
    // }
}
