package com.ontotext.graphdb.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import java.io.File;

public class CustomAnalyzer2 extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer tokenizer = new StandardTokenizer();
        String dictionaryFileName = "en_US/en_US.dic";
        ClassLoader classLoader1 = ClassLoader.getSystemClassLoader();
        File dictionaryFile = new File(classLoader1.getResource(dictionaryFileName).getFile());
        return new TokenStreamComponents(tokenizer, new LowerCaseFilter(tokenizer));

    }
}
