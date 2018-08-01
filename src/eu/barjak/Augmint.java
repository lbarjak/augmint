package eu.barjak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
        ArrayList<StringBuilder> rates = new ArrayList<>();
        
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        final String oldDate = "20150809";
        c.setTime(myDateFormat.parse(oldDate));
        Integer prevSeq = 1087;
        c.add(Calendar.DAY_OF_MONTH, prevSeq - 3 + 1);
        String newDate = myDateFormat.format(c.getTime());
        
        String u = "https://coinmarketcap.com/currencies/ethereum/historical-data/?start="
                + newDate
                + "&end=20181231";
        URL url = new URL(u);
        BufferedReader in;
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        String iL;//inputLine

        while ((iL = in.readLine()) != null) {
            Pattern pattern1 = Pattern.compile("td class=\"text-left");
            Matcher matcher1 = pattern1.matcher(iL);
            if (matcher1.find()) {
                row.
                        append("    {\n").
                        append("        \"seq\":").append(",\n").
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

                rates.add(new StringBuilder(row));
                row.setLength(0);
            }
        }
        in.close();
        for (int i = rates.size() - 1; i >= 0; i--) {
            System.out.print(rates.get(i).
                    replace(rates.get(i).indexOf("seq") + 5,
                            rates.get(i).indexOf("seq") + 5,
                            " " + ++prevSeq));
        }
    }
}
/*  {
        "seq": 3,
        "date": "9 Aug 15",
        "open": 0.706136,
        "high": 0.87981,
        "low": 0.629191,
        "close": 0.701897
    },
    ....
    {
        "seq": 1087,
        "date": "28 Jul 18",
        "open": 469.68,
        "high": 471.59,
        "low": 462.99,
        "close": 466.90
    }
 */
