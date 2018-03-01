package com.ontotext.graphdb.lucene;/*
 * Lemmatizing library for Lucene
 * Copyright (C) 2010 Lars Buitinck
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.tagger.maxent.TaggerConfig;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;


public class EnglishLemmaAnalyzer extends Analyzer {

    private MaxentTagger posTagger;

    public EnglishLemmaAnalyzer(String posModelFile) throws Exception {
        this(makeTagger(posModelFile));
    }

    public EnglishLemmaAnalyzer(MaxentTagger tagger) {
        super();
        posTagger = tagger;
    }

    public static MaxentTagger makeTagger(String modelFile) throws Exception {
        TaggerConfig config = new TaggerConfig("-model", modelFile);
        // The final argument suppresses a "loading" message on stderr.
        return new MaxentTagger(modelFile, config, false);
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {

        Tokenizer source = new EnglishLemmaTokenizer(posTagger);
        return new TokenStreamComponents(source);
    }


}