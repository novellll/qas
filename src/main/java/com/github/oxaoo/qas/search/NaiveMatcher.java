package com.github.oxaoo.qas.search;

import com.github.oxaoo.mp4ru.syntax.tokenize.SimpleTokenizer;
import com.github.oxaoo.mp4ru.syntax.tokenize.Tokenizer;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 25.03.2017
 */
public class NaiveMatcher {

    public static List<ExtractInfo> matching(String snippetsFragment, String text) {
        List<ExtractInfo> extractInfoList = new ArrayList<>();
        List<String> snippets = snippetSplitter(snippetsFragment);
        List<String> sentences = textSplitter(text);
        Tokenizer tokenizer = new SimpleTokenizer();
        for (String snippet : snippets) {
            SentenceScoreHandler scoreHandler = new SentenceScoreHandler(sentences.size());
            List<String> snippetTokens = tokenizer.tokenization(snippet);
            for (int i = 0; i < sentences.size(); i++) {
                for (String snippetToken : snippetTokens) {
                    if (sentences.get(i).contains(snippetToken)) scoreHandler.inc(i);
                }
            }
            List<SentenceScore> scores = scoreHandler.top(3);
            List<String> relevantSentences = scores.stream()
                    .map(s -> sentences.get(s.getIdSentence()))
                    .collect(Collectors.toList());
            extractInfoList.add(new ExtractInfo(snippet, relevantSentences));
        }
        return extractInfoList;
    }

    private static List<String> snippetSplitter(String snippetsFragment) {
        snippetsFragment = snippetsFragment.replaceAll("[^\\S ]+", "");
        String separateToken = "\\.\\.\\.";
        return Arrays.stream(snippetsFragment.split(separateToken))
                .map(String::trim)
                .map(s -> s.replace(String.valueOf((char) 160), "")) // skip the '&nbsp;'
                .filter(s -> !(s.isEmpty()))
                .collect(Collectors.toList());
    }

    private static List<String> textSplitter(String text) {
        List<String> sentences = new ArrayList<>();
        BreakIterator iterator = BreakIterator.getSentenceInstance();
        iterator.setText(text);
        int start = iterator.first();
        for (int end = iterator.next();
             end != BreakIterator.DONE;
             start = end, end = iterator.next()) {
            sentences.add(text.substring(start, end));
        }
        return sentences;
    }
}
