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

public class Rates {

    public static void main(String[] args) {
        try {
            new Rates().rates();
        } catch (IOException | ParseException ex) {
            Logger.getLogger(Rates.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rates() throws IOException, ParseException {

        StringBuilder row = new StringBuilder();
        ArrayList<StringBuilder> rates = new ArrayList<>();

        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        final String firstDate = "20150809";
        c.setTime(yyyyMMdd.parse(firstDate));
        //Integer prevSeq = 1087;
        Integer prevSeq = 1105;
        c.add(Calendar.DAY_OF_MONTH, prevSeq - 3 + 1);
        String newDate = yyyyMMdd.format(c.getTime());

        String u = "https://coinmarketcap.com/currencies/ethereum/historical-data/?start="
                + newDate
                + "&end=20181231";
        BufferedReader in = new BufferedReader(new InputStreamReader(new URL(u).openStream()));
        String inputLine;
        final String[] status = {"open", "high", "low", "close"};

        while ((inputLine = in.readLine()) != null) {
            Pattern pattern1 = Pattern.compile("td class=\"text-left");
            Matcher matcher1 = pattern1.matcher(inputLine);
            if (matcher1.find()) {
                row.append("    {\n");
                row.append("        \"seq\":,\n");
                row.append("        \"date\": \"").
                        append(inputLine.substring(26, 28)).append(" ").
                        append(inputLine.substring(22, 25)).append(" ").
                        append(inputLine.substring(32, 34)).append("\",\n");
                for (String str : status) {
                    inputLine = in.readLine();
                    row.append("        \"").append(str).append("\": ").
                            append(inputLine.substring(inputLine.indexOf(">") + 1, inputLine.indexOf(">") + 7)).
                            append(",\n");
                }
                row.append("    }");

                rates.add(new StringBuilder(row));
                row.setLength(0);
            }
        }
        in.close();
        for (int i = rates.size() - 1; i >= 0; i--) {
            System.out.print(rates.get(i).
                    replace(rates.get(i).indexOf(",\n"),
                            rates.get(i).indexOf(",\n"),
                            " " + ++prevSeq));
            if (i > 0) {
                System.out.print(",");
            }
            System.out.println();
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
        "seq": 1105,
        "date": "15 Aug 18",
        "open": 280.39,
        "high": 303.59,
        "low": 280.12,
        "close": 282.36
    }
 */
