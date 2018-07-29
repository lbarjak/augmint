package eu.barjak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Augmint {

    static StringBuilder row = new StringBuilder();
    static ArrayList<String> rates = new ArrayList<>();

    public static void main(String[] args) throws MalformedURLException, IOException {

        URL ethHistData = new URL("https://coinmarketcap.com/currencies/ethereum/historical-data/?start=20180301&end=20180729");
        BufferedReader in = new BufferedReader(new InputStreamReader(ethHistData.openStream()));
        String inputLine;
        int counter = 1087;
        
        while ((inputLine = in.readLine()) != null) {
            Pattern pattern1 = Pattern.compile("td class=\"text-left");
            Matcher matcher1 = pattern1.matcher(inputLine);
            if (matcher1.find()) {
                row.append("    {\n"
                        + "        \"seq\": ").append(counter--).append(",\n"
                        + "        \"date\": \"").append(inputLine.substring(26, 28)).append(" ").append(inputLine.substring(22, 25)).append(" ").append(inputLine.substring(32, 34)).append("\",\n"
                        + "        \"open\": ");
                inputLine = in.readLine();
                //System.out.println(inputLine.indexOf(">"));
                row.append(inputLine.substring(inputLine.indexOf(">") + 1, inputLine.indexOf(">") + 7)).append(",\n        \"high\": ");
                inputLine = in.readLine();
                row.append(inputLine.substring(inputLine.indexOf(">") + 1, inputLine.indexOf(">") + 7)).append(",\n        \"low\": ");
                inputLine = in.readLine();
                row.append(inputLine.substring(inputLine.indexOf(">") + 1, inputLine.indexOf(">") + 7)).append(",\n        \"close\": ");
                inputLine = in.readLine();
                row.append(inputLine.substring(inputLine.indexOf(">") + 1, inputLine.indexOf(">") + 7)).append("\n    },\n");
                rates.add(row.toString());
                //System.out.print(row);
                row.setLength(0);
            }
        }
        for (int i = rates.size() - 1; i > 0; i--) {
            System.out.print(rates.get(i));
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
