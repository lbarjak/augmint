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
        String u = "https://coinmarketcap.com/currencies/ethereum/historical-data/?start=20180301&end=20180731";
        URL url = new URL(u);
        BufferedReader in;
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        String iL;//inputLine
        
        int i1 = u.indexOf("start=") + 6;
        int i2 = u.indexOf("end=") + 4;
        String start = u.substring(i1, i1 + 8);
        String end = u.substring(i2, i2 + 8);
        SimpleDateFormat myDate = new SimpleDateFormat("yyyyMMdd");
        long diff = myDate.parse(end).getTime() - myDate.parse(start).getTime();
        int days = (int) (diff / 1000 / 60 / 60 / 24);

        int prev = 937;
        int counter = prev + 1 + days;

        while ((iL = in.readLine()) != null) {
            Pattern pattern1 = Pattern.compile("td class=\"text-left");
            Matcher matcher1 = pattern1.matcher(iL);
            if (matcher1.find()) {
                row.
                        append("    {\n").
                        append("        \"seq\": ").append(counter--).append(",\n").
                        append("        \"date\": \"").
                        append(iL.substring(26, 28)).append(" ").
                        append(iL.substring(22, 25)).append(" ").
                        append(iL.substring(32, 34)).append("\",\n");  
                iL = in.readLine();
                row.
                        append("        \"open\": ").
                        append(iL.substring(iL.indexOf(">") + 1, iL.indexOf(">") + 7)).append(",\n");  
                iL = in.readLine();
                row.
                        append("        \"high\": ").
                        append(iL.substring(iL.indexOf(">") + 1, iL.indexOf(">") + 7)).append(",\n");
                iL = in.readLine();
                row.
                        append("        \"low\": ").
                        append(iL.substring(iL.indexOf(">") + 1, iL.indexOf(">") + 7)).append(",\n");
                iL = in.readLine();
                row.
                        append("        \"close\": ").
                        append(iL.substring(iL.indexOf(">") + 1, iL.indexOf(">") + 7)).append(",\n").
                        append("    },\n");

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
