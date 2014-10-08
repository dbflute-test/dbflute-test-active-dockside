package org.docksidestage.dockside.dbflute.allcommon.cbean.coption;

import org.dbflute.cbean.coption.LikeSearchOption;
import org.dbflute.utflute.core.PlainTestCase;

/**
 * The test of likeSearchOption for Basic Example.
 * @author jflute
 * @since 0.5.9 (2007/12/20 Thursday)
 */
public class LikeSearchOptionTest extends PlainTestCase {

    // ===================================================================================
    //                                                                         Rear Option
    //                                                                         ===========
    public void test_getRearOption() throws Exception {
        // ## Arrange ##
        final LikeSearchOption option = new LikeSearchOption();
        option.escapeByPipeLine();

        // ## Act ##
        final String rearOption = option.getRearOption();

        // ## Assert ##
        assertEquals("escape '|'", rearOption.trim());
    }

    // ===================================================================================
    //                                                                          Real Value
    //                                                                          ==========
    public void test_generateRealValue() throws Exception {
        final String inputValue = "abc%def_ghi";
        {
            // ## Arrange ##
            final LikeSearchOption option = new LikeSearchOption();
            option.escapeByPipeLine();

            // ## Act ##
            final String realValue = option.generateRealValue(inputValue);

            // ## Assert ##
            log("realValue=" + realValue);
            assertEquals("abc|%def|_ghi", realValue);
        }
        {
            // ## Arrange ##
            final LikeSearchOption option = new LikeSearchOption();
            option.likePrefix().escapeBySlash();

            // ## Act ##
            final String realValue = option.generateRealValue(inputValue);

            // ## Assert ##
            log("realValue=" + realValue);
            assertEquals("abc/%def/_ghi%", realValue);
        }
        {
            // ## Arrange ##
            final LikeSearchOption option = new LikeSearchOption();
            option.likeContain().escapeByAtMark();

            // ## Act ##
            final String realValue = option.generateRealValue(inputValue);

            // ## Assert ##
            log("realValue=" + realValue);
            assertEquals("%abc@%def@_ghi%", realValue);
        }
        {
            // ## Arrange ##
            final LikeSearchOption option = new LikeSearchOption();
            option.likeSuffix().escapeByBackSlash();

            // ## Act ##
            final String realValue = option.generateRealValue(inputValue);

            // ## Assert ##
            log("realValue=" + realValue);
            assertEquals("%abc\\%def\\_ghi", realValue);
        }
        {
            // ## Arrange ##
            final LikeSearchOption option = new LikeSearchOption();
            option.escapeByPipeLine();

            // ## Act ##
            final String realValue = option.generateRealValue(inputValue + "jk|l");

            // ## Assert ##
            log("realValue=" + realValue);
            assertEquals("abc|%def|_ghijk||l", realValue);
        }
    }
}
