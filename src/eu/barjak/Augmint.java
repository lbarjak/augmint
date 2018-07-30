package eu.barjak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Augmint {

    public static void main(String[] args) {
        try {
            new Augmint().augmint();
        } catch (IOException | ParseException ex) {
            Logger.getLogger(Augmint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void augmint() throws IOException, ParseException {
        
        StringBuilder row = new StringBuilder();
        ArrayList<String> rates = new ArrayList<>();
        
        URL ethHistData = new URL("https://coinmarketcap.com/currencies/ethereum/historical-data/?start=20180301&end=20180729");
        BufferedReader in;
        in = new BufferedReader(new InputStreamReader(ethHistData.openStream()));
        String inputLine;
        int index = ethHistData.toString().indexOf("start=");
        
        String start = ethHistData.toString().substring(index + 6, index + 14);
        String end = ethHistData.toString().substring(index + 6 + 13, index + 14 + 13);
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
        long diff = DATE_FORMAT.parse(end).getTime() - DATE_FORMAT.parse(start).getTime();
        int days = (int) (diff / 1000 / 60 / 60 / 24);

        int prev = 937;
        int counter = prev + 1 + days;

        while ((inputLine = in.readLine()) != null) {
            Pattern pattern1 = Pattern.compile("td class=\"text-left");
            Matcher matcher1 = pattern1.matcher(inputLine);
            if (matcher1.find()) {
                row.
                        append("    {\n        \"seq\": ").
                        append(counter--).
                        append(",\n        \"date\": \"").
                        append(inputLine.substring(26, 28)).
                        append(" ").
                        append(inputLine.substring(22, 25)).
                        append(" ").
                        append(inputLine.substring(32, 34)).
                        append("\",\n        \"open\": ");
                inputLine = in.readLine();
                row.
                        append(inputLine.substring(inputLine.indexOf(">") + 1, inputLine.indexOf(">") + 7)).
                        append(",\n        \"high\": ");
                inputLine = in.readLine();
                row.
                        append(inputLine.substring(inputLine.indexOf(">") + 1, inputLine.indexOf(">") + 7)).
                        append(",\n        \"low\": ");
                inputLine = in.readLine();
                row.
                        append(inputLine.substring(inputLine.indexOf(">") + 1, inputLine.indexOf(">") + 7)).
                        append(",\n        \"close\": ");
                inputLine = in.readLine();
                row.
                        append(inputLine.substring(inputLine.indexOf(">") + 1, inputLine.indexOf(">") + 7)).
                        append("\n    },\n");

                rates.add(row.toString());
                row.setLength(0);
            }
        }
        in.close();
        for (int i = rates.size() - 1; i >= 0; i--) {
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
