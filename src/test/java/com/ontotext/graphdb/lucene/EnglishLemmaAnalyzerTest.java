package com.ontotext.graphdb.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.*;

public class EnglishLemmaAnalyzerTest {

    @Test
    public void testPrintMessage() throws Exception {
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