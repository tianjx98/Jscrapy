package me.tianjx98.Jscrapy.duplicatefilter.impl;

import me.tianjx98.Jscrapy.duplicatefilter.DuplicateFilter;
import me.tianjx98.Jscrapy.http.Request;

import java.util.BitSet;

/**
 * @ClassName BloomFilter
 * @Author tianjx98
 * @Date 2019-09-16 18:25
 */
public class BloomDuplicateFilter implements DuplicateFilter {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_SIZE = 2 << 28;

    /*
     * 不同哈希函数的种子，一般取质数
     * seeds数组共有8个值，则代表采用8种不同的哈希函数
     */
    private static final int[] SEEDS = new int[]{3, 5, 7, 11, 13, 31, 37, 61};

    /*
     * 初始化一个给定大小的位集
     * BitSet实际是由“二进制位”构成的一个Vector。
     * 假如希望高效率地保存大量“开－关”信息，就应使用BitSet.
     */
    private BitSet bitSets = new BitSet(DEFAULT_SIZE);

    private void add(String str) {
        for (int seed : SEEDS) {
            bitSets.set(hash(str, seed), true);
        }
    }

    private boolean contains(String str) {
        if (str == null) return false;
        for (int seed : SEEDS) {
            if (!bitSets.get(hash(str, seed))) {
                return false;
            }
        }
        return true;
    }

    private int hash(String str, int seed) {
        int result = 0;
        int length = str.length();
        for (int i = 0; i < length; i++) {
            result = result * seed + str.charAt(i);
        }
        return (DEFAULT_SIZE - 1) & result;
    }


    @Override
    public boolean isDuplicate(Request request) {
        String url = request.getUrl().toString();
        if (contains(url)) return true;
        add(url);
        return false;
    }

    //public boolean isDuplicate(String url) {
    //    if (contains(url)) return true;
    //    add(url);
    //    return false;
    //}
}
