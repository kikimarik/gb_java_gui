package sample.models;

import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Response {
    public static String generate(String input, ArrayList<TextField> params) {
        Matcher matcher = Pattern.compile(Template.paramPattern).matcher(input);
        int matchCounter = 0;
        StringBuilder output = new StringBuilder();
        while (matcher.find()) {
            input =
                    input.replaceFirst(
                            "\\{#" + matcher.group("param") + "#}",
                            params.get(matchCounter++).getText()

            );
        }
        output.append(input);
        return output.toString();
    }
}
