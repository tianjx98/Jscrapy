package me.tianjx98.Jscrapy.duplicatefilter.impl;

import java.util.HashMap;
import java.util.Map;

class Trie {
    private Map<Character, Trie> nexts;
    private boolean isTail;

    public Trie() {
        this.nexts = new HashMap<>();
    }

    public void add(String str) {
        Trie trie = this;
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            Trie next = trie.nexts.get(c);
            if (next == null) {//下个字符不存在,就添加
                Trie newTrie = new Trie();
                trie.nexts.put(c, newTrie);
                trie = newTrie;
            } else {
                trie = next;
            }
            if (i == length - 1) trie.isTail = true;
        }
    }

    public boolean contains(String str) {
        Trie trie = this;
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            Trie next = trie.nexts.get(c);
            if (next == null) {
                return false;
            } else {
                trie = next;
                if (i == length - 1 && !trie.isTail) return false;
            }
        }
        return true;
    }
}