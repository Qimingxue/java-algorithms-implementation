package com.jwetherell.algorithms.data_structures;

import java.util.Set;
import java.util.TreeSet;


/**
 * A suffix trie is a data structure that presents the suffixes of a given
 * string in a way that allows for a particularly fast implementation of many
 * important string operations. This implementation is based upon a Trie which
 * is NOT a compact trie.
 * 
 * http://en.wikipedia.org/wiki/Suffix_trie
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class SuffixTrie<C extends CharSequence> extends Trie<C> {

    /**
     * Create a suffix trie from sequence
     * 
     * @param sequence to create a suffix trie from.
     */
    @SuppressWarnings("unchecked")
    public SuffixTrie(C sequence) {
        root = new Node(null, null, false);
        int length = sequence.length();
        for (int i = 0; i < length; i++) {
            CharSequence seq = sequence.subSequence(i, length);
            super.add((C) seq);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean add(C sequence) {
        int length = sequence.length();
        for (int i = 0; i < length; i++) {
            CharSequence seq = sequence.subSequence(i, length);
            super.add((C) seq);
        }
        return true;
    }

    /**
     * Does the sequence exists in the trie.
     * 
     * @param sequence to locate in the trie.
     * @return True if sequence exists in trie.
     */
    public boolean doesSubStringExist(C sequence) {
        char[] chars = sequence.toString().toCharArray();
        int length = chars.length;
        Node current = root;
        for (int i = 0; i < length; i++) {
            int idx = current.childIndex(chars[i]);
            if (idx < 0) return false;
            current = current.getChild(idx);
        }
        return true;
    }

    /**
     * Get all suffixes in the trie.
     * 
     * @return set of suffixes in trie.
     */
    public Set<String> getSuffixes() {
        return this.getSuffixes(root);
    }

    /**
     * Get all suffixes at node.
     * 
     * @param node to get all suffixes at.
     * @return set of suffixes in trie at node.
     */
    private Set<String> getSuffixes(Node node) {
        StringBuilder builder = new StringBuilder();
        if (node.character!=null) builder.append(node.character);
        Set<String> set = new TreeSet<String>();
        if (node.getChildrenSize() == 0) {
            set.add(builder.toString());
        } else {
            for (int i=0; i<node.getChildrenSize(); i++) {
                Node c = node.getChild(i);
                set.addAll(getSuffixes(c,builder.toString()));
            }
        }
        return set;
    }

    /**
     * Get all suffixes at node and prepend the prefix.
     * 
     * @param node to get all suffixes from.
     * @param prefix to prepend to suffixes.
     * @return set of suffixes in trie at node.
     */
    private Set<String> getSuffixes(Node node, String prefix) {
        StringBuilder builder = new StringBuilder(prefix);
        if (node.character!=null) builder.append(node.character);
        Set<String> set = new TreeSet<String>();
        if (node.getChildrenSize() == 0) {
            set.add(builder.toString());
        } else {
            for (int i=0; i<node.getChildrenSize(); i++) {
                Node c = node.getChild(i);
                set.addAll(getSuffixes(c,builder.toString()));
            }
        }
        return set;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TriePrinter.getString(this);
    }
}
