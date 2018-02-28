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
    public void testPrintMessage() throws IOException {
        String text = "reorganize";
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
    /*
    @Test
    public void testPrintMessage2() throws IOException {
        String text = "make";

        Analyzer analyzer = new StopwordAnalyzerBase(Version.LUCENE_36, CharArraySet.EMPTY_SET) {
            @Override
            protected TokenStreamComponents createComponents(String s, Reader reader) {
                final Tokenizer source = new StandardTokenizer(matchVersion, reader);
                TokenStream tokenStream = source;
                tokenStream = new StandardFilter(matchVersion, tokenStream);
                tokenStream = new LowerCaseFilter(matchVersion, tokenStream);
                tokenStream = new ASCIIFoldingFilter(tokenStream);
                tokenStream = new PorterStemFilter(tokenStream);
                return new TokenStreamComponents(source, tokenStream);
            }
        };

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
    public void testPrintMessage3() throws IOException {
        String text = "made";

        Analyzer analyzer = new StopwordAnalyzerBase(Version.LUCENE_36, StandardAnalyzer.STOP_WORDS_SET) {
            @Override
            protected TokenStreamComponents createComponents(String s, Reader reader) {
                Tokenizer source = new StandardTokenizer(matchVersion, reader);

                TokenStream tokenStream = source;
                TokenStream filter = new StandardFilter(matchVersion, source);
                //filter = new LowerCaseFilter(matchVersion, filter);


                try {
                    File dictionaryFile = new File("en_US/en_US.dic");
                    File affixFile = new File("en_US/en_US.aff");

                    InputStream dictionaryStream = new FileInputStream(dictionaryFile);
                    InputStream affixStream = new FileInputStream(affixFile);

                    HunspellDictionary dictionary = new HunspellDictionary(affixStream, dictionaryStream, matchVersion);


                    filter = new HunspellStemFilter(filter, dictionary);
                    return new TokenStreamComponents(source, filter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

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
    public void testPrintMessage4() throws IOException {
        String text = "make";

        Analyzer analyzer = new StopwordAnalyzerBase(Version.LUCENE_36, CharArraySet.EMPTY_SET) {
            @Override
            protected TokenStreamComponents createComponents(String s, Reader reader) {
                final Tokenizer source = new StandardTokenizer(matchVersion, reader);
                TokenStream tokenStream = source;
                //tokenStream = new StandardFilter(matchVersion, tokenStream);
                //tokenStream = new LowerCaseFilter(matchVersion, tokenStream);
                //tokenStream = new ASCIIFoldingFilter(tokenStream);
                //tokenStream = new KStemFilter(tokenStream);

                try {
                    SynonymMap mySynonymMap = buildSynonym();

                    tokenStream = new SynonymFilter(tokenStream, mySynonymMap, true);
                    return new TokenStreamComponents(source, tokenStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            private SynonymMap buildSynonym() throws IOException, ParseException {
                File file = new File("wn/wn_s.pl");
                InputStream stream = new FileInputStream(file);
                Reader rulesReader = new InputStreamReader(stream);
                SynonymMap.Builder parser = null;

                parser = new WordnetSynonymParser(true, true, new StandardAnalyzer(matchVersion, CharArraySet.EMPTY_SET));
                ((WordnetSynonymParser) parser).add(rulesReader);
                SynonymMap synonymMap = parser.build();
                return synonymMap;
            }
        };

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
    public void testPrintMessage5() throws Exception {
        String text = "made";
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
    */


}