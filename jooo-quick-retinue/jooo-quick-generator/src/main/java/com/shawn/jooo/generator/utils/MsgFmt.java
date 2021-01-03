package com.shawn.jooo.generator.utils;

import java.text.MessageFormat;

/**
 *
 * @author shawn
 */
public class MsgFmt {

    private MsgFmt() {
    }
    public static String getString(String text, String... parms) {
            return MessageFormat.format(text, parms);

    }
}
