package com.github.oxaoo.qas.syntax.parse;

import org.maltparser.MaltParserService;
import org.maltparser.core.exception.MaltChainedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author oxaoo
 * @since 25/09/16
 */
public class SyntaxAnalyzer {
    private static final Logger LOG = LoggerFactory.getLogger(SyntaxAnalyzer.class);

    private final MaltParserService maltParserService;

    public SyntaxAnalyzer() throws MaltChainedException {
        maltParserService = new MaltParserService(SyntaxPropertyKeys.OPTION_CONTAINER);
    }

    public boolean analyze() {
        final String command = SyntaxPropertyKeys.CONFIG_WORKINGDIR_PATH
                + SyntaxPropertyKeys.CONFIG_NAME_MODEL
                + SyntaxPropertyKeys.INPUT_INFILE_PATH
                + SyntaxPropertyKeys.OUTPUT_OUTFILE_PATH
                + SyntaxPropertyKeys.CONFIG_FLOWCHART_PARSE;

        try {
            maltParserService.runExperiment(command.trim());
        } catch (final MaltChainedException e) {
            LOG.error("Failed to syntax analyze: [{}]", e);
            return false;
        }
        return true;
    }
}
