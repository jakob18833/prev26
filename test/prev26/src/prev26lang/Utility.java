package prev26lang;

import org.antlr.v4.runtime.*;
import prev26lang.common.report.Location;
import prev26lang.common.report.Report;
import prev26lang.phase.lexan.LexAn;

import java.util.Arrays;

public class Utility {
    // Create an ANLTR error listener
    public static ANTLRErrorListener errorListener = new BaseErrorListener() {
        @Override
        public void syntaxError(
                Recognizer<?, ?> recognizer,
                Object o,
                int line,
                int charPositionInLine,
                String msg,
                RecognitionException recognitionException
        ) {
            if (recognizer instanceof Lexer) {
                String text = (String) o;
                if (recognitionException != null) {
                    String[] p = msg.split("'");
                    text = String.join("", Arrays.copyOfRange(p, 1, p.length));
                    msg = p[0] + text;
                }

                throw new Report.Error(
                        new Location(
                                line,
                                charPositionInLine + 1,
                                line,
                                charPositionInLine + 1 + (text == null ? 0 : text.length())),
                        msg
                );
            } else if (recognizer instanceof Parser) {
                throw new Report.Error(new Location(line, charPositionInLine), //
                        "Unexpected symbol '" + ((LexAn.LocLogToken) o).getText() + "'.");
            } else {
                System.out.println("WTF");
            }

        }
    };
}
