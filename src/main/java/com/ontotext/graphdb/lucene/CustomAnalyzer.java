package com.ontotext.graphdb.lucene;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.en.KStemFilter;
import org.apache.lucene.analysis.hunspell.Dictionary;
import org.apache.lucene.analysis.hunspell.HunspellStemFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.synonym.SynonymGraphFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.analysis.synonym.WordnetSynonymParser;

import org.apache.lucene.store.RAMDirectory;

import java.io.*;
import java.text.ParseException;

public class CustomAnalyzer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        try {

            //MaxentTagger posTagger = EnglishLemmaAnalyzer.makeTagger("edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");
            //final Tokenizer source = new EnglishLemmaTokenizer(reader, posTagger);

            Tokenizer tokenizer = new StandardTokenizer();
            TokenStream tokenStream = tokenizer;

            tokenStream = new StandardFilter(tokenStream);
            tokenStream = new LowerCaseFilter(tokenStream);
            tokenStream = new ASCIIFoldingFilter(tokenStream);
            tokenStream = new KStemFilter(tokenStream);

            String dictionaryFileName = "en_US/en_US.dic";
            ClassLoader classLoader1 = ClassLoader.getSystemClassLoader();
            File dictionaryFile = new File(classLoader1.getResource(dictionaryFileName).getFile());

            String affixFileName = "en_US/en_US.aff";
            ClassLoader classLoader2 = ClassLoader.getSystemClassLoader();
            File affixFile = new File(classLoader2.getResource(affixFileName).getFile());

            InputStream dictionaryStream = new FileInputStream(dictionaryFile);
            InputStream affixStream = new FileInputStream(affixFile);


            Dictionary dictionary = new Dictionary(new RAMDirectory(),"tmpDic",affixStream, dictionaryStream);

            SynonymMap mySynonymMap = buildSynonym();

            tokenStream = new SynonymGraphFilter(tokenStream,mySynonymMap,true);
            tokenStream = new HunspellStemFilter(tokenStream, dictionary);
            tokenStream = new StopFilter(tokenStream, StopAnalyzer.ENGLISH_STOP_WORDS_SET);
            return new TokenStreamComponents(tokenizer, tokenStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private SynonymMap buildSynonym() throws IOException, ParseException {
        String wordNetFileName = "wn/wn_s.pl";
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource(wordNetFileName).getFile());

        InputStream stream = new FileInputStream(file);
        Reader rulesReader = new InputStreamReader(stream);

        WordnetSynonymParser parser = new WordnetSynonymParser(true, true, new StandardAnalyzer(CharArraySet.EMPTY_SET));
        parser.parse(rulesReader);
        return parser.build();
    }
}
