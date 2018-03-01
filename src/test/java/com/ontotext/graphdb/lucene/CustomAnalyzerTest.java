package com.ontotext.graphdb.lucene;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.hunspell.HunspellStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.synonym.SynonymFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.analysis.synonym.WordnetSynonymParser;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.junit.Test;

import java.io.*;
import java.text.ParseException;

import static org.junit.Assert.*;

public class CustomAnalyzerTest {


    @Test
    public void testCustomAnalyzer() throws IOException {
        String text = "going away";
        Analyzer analyzer = new CustomAnalyzer();
        TokenStream stream = analyzer.tokenStream(null, new StringReader(text));
        CharTermAttribute cattr = stream.addAttribute(CharTermAttribute.class);
        stream.reset();
        while (stream.incrementToken()) {
            System.out.println(cattr.toString());
        }
        stream.end();
        stream.close();
    }

    @Test
    public void testLemmaAnalyzer() throws Exception {
        String text = "going away";
        Analyzer analyzer = new EnglishLemmaAnalyzer("edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");
        TokenStream stream = analyzer.tokenStream(null, new StringReader(text));

        CharTermAttribute cattr = stream.addAttribute(CharTermAttribute.class);
        stream.reset();
        while (stream.incrementToken()) {
            System.out.println(cattr.toString());
        }
        stream.end();
        stream.close();
    }
}