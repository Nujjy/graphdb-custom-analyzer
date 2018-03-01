package com.ontotext.graphdb.lucene;

import com.google.common.collect.Iterables;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.process.Morphology;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;


import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class EnglishLemmaTokenizer extends Tokenizer {

    private Iterator<TaggedWord> tagged;
    private MaxentTagger tagger;
    private PositionIncrementAttribute posIncr;
    private TaggedWord currentWord;
    private CharTermAttribute termAtt;
    private boolean lemmaNext;
    private boolean tokenized;

    public EnglishLemmaTokenizer(MaxentTagger tagger) {
        super();
        init(tagger);
    }


    private void init(MaxentTagger tagger) {

        lemmaNext = false;
        tokenized = false;
        this.tagger = tagger;
        posIncr = addAttribute(PositionIncrementAttribute.class);
        termAtt = addAttribute(CharTermAttribute.class);
}

    @Override
    public final boolean incrementToken() throws IOException {
        if(!tokenized) {
            List<List<HasWord>> tokenized = MaxentTagger.tokenizeText(this.input);
            tagged = Iterables.concat(tagger.process(tokenized)).iterator();
            this.tokenized = true;
        }

        if (lemmaNext) {
            // Emit a lemma
            posIncr.setPositionIncrement(1);
            String tag  = currentWord.tag();
            String form = currentWord.word();
            termAtt.setEmpty();
            termAtt.append(Morphology.stemStatic(form, tag).word());
        } else {
            // Emit inflected form, if not filtered out.

            // 0 because the lemma will come in the same position
            int increment = 0;
            for (;;) {
                if (!tagged.hasNext())
                    return false;
                currentWord = tagged.next();
                if (!unwantedPOS(currentWord.tag()))
                    break;
                increment++;
            }

            posIncr.setPositionIncrement(increment);
            termAtt.setEmpty();
            termAtt.append(currentWord.word());
        }

        lemmaNext = !lemmaNext;
        return true;
    }

    private static final Pattern unwantedPosRE = Pattern.compile(
            "^(CC|DT|[LR]RB|MD|POS|PRP|UH|WDT|WP|WP\\$|WRB|\\$|\\#|\\.|\\,|:)$"
    );

     protected boolean unwantedPOS(String tag) {
        return unwantedPosRE.matcher(tag).matches();
    }
}