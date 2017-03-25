package com.github.oxaoo.qas;

import com.github.oxaoo.qas.exceptions.LoadQuestionClassifierModelException;
import com.github.oxaoo.qas.qa.QuestionClassifier;
import com.github.oxaoo.qas.search.PageExtractor;
import com.github.oxaoo.qas.search.RelevantInfoExtractor;
import com.github.oxaoo.qas.search.SearchEngine;
import com.google.api.services.customsearch.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(com.github.oxaoo.mp4ru.Main.class);

    public static void main(String[] args) throws LoadQuestionClassifierModelException {
//        run();
        testSearchEngine();
//        testPageExtractor();
    }

    private static void testPageExtractor() {
//        new PageExtractor().testExtract();
    }

    private static void testSearchEngine() {
        SearchEngine engine = new SearchEngine();
        List<Result> results = engine.find("где находится эльбрус?");
        List<String> relevantFragments = RelevantInfoExtractor.extract(results);
        LOG.info("Final result of search: \n{}", relevantFragments.toString());
//        List<Result> snippets = engine.stubFind();
//        PageExtractor pageExtractor = new PageExtractor();
//        pageExtractor.extract(results);
//        engine.execute();
    }

    private static void run() throws LoadQuestionClassifierModelException {
        QuestionClassifier questionClassifier = new QuestionClassifier();
        questionClassifier.init();
    }
}
