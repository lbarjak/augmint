package eu.barjak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Augmint {

    static String match1 = "";
    static StringBuilder row = new StringBuilder();

    public static void main(String[] args) throws MalformedURLException, IOException {

        URL ethHistData = new URL("https://coinmarketcap.com/currencies/ethereum/historical-data/?start=20180301&end=20180729");
        BufferedReader in = new BufferedReader(new InputStreamReader(ethHistData.openStream()));
        String inputLine;
        int counter = 937;
        while ((inputLine = in.readLine()) != null) {
            Pattern pattern1 = Pattern.compile("td class=\"text-left");

            Matcher matcher1 = pattern1.matcher(inputLine);
            if (matcher1.find()) {
                row.append("    {\n        \"seq\":" + ++counter + ",\n"
                        + "        \"date\": \"" + inputLine.substring(26, 28) + " " + inputLine.substring(22, 25) + " " + inputLine.substring(32, 34) + "\",\n"
                        + "        \"open\": ");
                //System.out.println(inputLine.substring(22));
                System.err.println(row);

            }
            //System.out.println(inputLine);
        }
    }
}
/*
    {
        "seq": 937,
        "date": "28 Feb 18",
        "open": 877.93,
        "high": 890.11,
        "low": 855.12,
        "close": 855.20
    }
 */
