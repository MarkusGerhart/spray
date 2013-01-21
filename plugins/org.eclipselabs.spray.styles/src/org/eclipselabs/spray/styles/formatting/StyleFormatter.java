/*
 * generated by Xtext
 */
package org.eclipselabs.spray.styles.formatting;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter;
import org.eclipse.xtext.formatting.impl.FormattingConfig;
import org.eclipse.xtext.util.Pair;
import org.eclipselabs.spray.styles.services.StyleGrammarAccess;

import com.google.inject.Inject;

/**
 * This class contains custom formatting description.
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#formatting
 * on how and when to use it
 * Also see {@link org.eclipse.xtext.xtext.XtextFormattingTokenSerializer} as an
 * example
 */
public class StyleFormatter extends AbstractDeclarativeFormatter {

    @Inject
    private StyleGrammarAccess grammar;

    @Override
    protected void configureFormatting(FormattingConfig c) {
        c.setLinewrap(0, 1, 2).before(grammar.getSL_COMMENTRule());
        c.setLinewrap(0, 1, 2).before(grammar.getML_COMMENTRule());
        c.setLinewrap(0, 1, 1).after(grammar.getML_COMMENTRule());

        c.setAutoLinewrap(120);

        handleBlocks(c);

        List<Keyword> bracketsToIgnore = new ArrayList<Keyword>();
        bracketsToIgnore.add(grammar.getHighlightingValuesAccess().getLeftParenthesisKeyword_1());

        for (Pair<Keyword, Keyword> kw : grammar.findKeywordPairs("(", ")")) {
            if (!bracketsToIgnore.contains(kw.getFirst())) {
                c.setSpace(" ").before(kw.getFirst());
                c.setNoSpace().after(kw.getFirst());
                c.setNoSpace().before(kw.getSecond());
            }
        }

        for (Keyword kw : grammar.findKeywords("=")) {
            c.setSpace(" ").around(kw);
        }

        // no space before comma, one space after
        for (Keyword kw : grammar.findKeywords(",")) {
            c.setNoSpace().before(kw);
            c.setSpace(" ").after(kw);
        }

        handleNoSpaceBeforeINT(c);

        handleLineWrapBeforeKeywords(c);

        c.setLinewrap().around(grammar.getGradientAccess().getDescriptionAssignment_4_2());
        c.setLinewrap().around(grammar.getGradientColorAreaRule());

        c.setLinewrap().after(grammar.getHighlightingValuesAccess().getLeftParenthesisKeyword_1());
        c.setLinewrap().around(grammar.getHighlightingValuesAccess().getRightParenthesisKeyword_6());

        c.setLinewrap().around(grammar.getHighlightingValuesAccess().getSelectedAssignment_2_2());
        c.setLinewrap().around(grammar.getHighlightingValuesAccess().getMultiselectedAssignment_3_2());
        c.setLinewrap().around(grammar.getHighlightingValuesAccess().getAllowedAssignment_4_2());
        c.setLinewrap().around(grammar.getHighlightingValuesAccess().getUnallowedAssignment_5_2());

        c.setIndentation(grammar.getHighlightingValuesAccess().getLeftParenthesisKeyword_1(), grammar.getHighlightingValuesAccess().getRightParenthesisKeyword_6());
    }

    protected void handleLineWrapBeforeKeywords(FormattingConfig c) {
        // line wraps
        c.setLinewrap(2).between(grammar.getStyleRule(), grammar.getStyleRule());
        c.setLinewrap(2).between(grammar.getGradientRule(), grammar.getStyleRule());
        c.setLinewrap(2).between(grammar.getStyleRule(), grammar.getGradientRule());
        c.setLinewrap().before(grammar.getStyleAccess().getDescriptionKeyword_5_0());
        c.setLinewrap().before(grammar.getStyleLayoutAccess().getTransparencyKeyword_1_0_0());
        c.setLinewrap().before(grammar.getStyleLayoutAccess().getBackgroundColorKeyword_1_1_0());
        c.setLinewrap().before(grammar.getStyleLayoutAccess().getGradientOrientationKeyword_1_2_0());
        c.setLinewrap().before(grammar.getStyleLayoutAccess().getHighlightingKeyword_1_3_0());
        c.setLinewrap().before(grammar.getStyleLayoutAccess().getLineColorKeyword_1_4_0());
        c.setLinewrap().before(grammar.getStyleLayoutAccess().getLineWidthKeyword_1_5_0());
        c.setLinewrap().before(grammar.getStyleLayoutAccess().getLineStyleKeyword_1_6_0());
        c.setLinewrap().before(grammar.getStyleLayoutAccess().getFontNameKeyword_1_7_0());
        c.setLinewrap().before(grammar.getStyleLayoutAccess().getFontColorKeyword_1_8_0());
        c.setLinewrap().before(grammar.getStyleLayoutAccess().getFontSizeKeyword_1_9_0());
        c.setLinewrap().before(grammar.getStyleLayoutAccess().getFontItalicKeyword_1_10_0());
        c.setLinewrap().before(grammar.getStyleLayoutAccess().getFontBoldKeyword_1_11_0());
    }

    protected void handleNoSpaceBeforeINT(FormattingConfig c) {
        // no space before integers
        c.setNoSpace().before(grammar.getRGBColorAccess().getRedINTTerminalRuleCall_2_0());
        c.setNoSpace().before(grammar.getRGBColorAccess().getGreenINTTerminalRuleCall_4_0());
        c.setNoSpace().before(grammar.getRGBColorAccess().getBlueINTTerminalRuleCall_6_0());
    }

    protected void handleBlocks(FormattingConfig c) {
        for (Pair<Keyword, Keyword> kw : grammar.findKeywordPairs("{", "}")) {
            c.setLinewrap().after(kw.getFirst());
            c.setLinewrap().around(kw.getSecond());
            c.setIndentation(kw.getFirst(), kw.getSecond());
        }
    }

}
