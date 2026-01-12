package currencyapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverter {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter base currency (e.g. USD): ");
        String from = sc.nextLine().toUpperCase();

        System.out.print("Enter target currency (e.g. INR): ");
        String to = sc.nextLine().toUpperCase();

        try {
            String urlStr = "https://open.er-api.com/v6/latest/" + from;

            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in =
                    new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            String json = response.toString();

            // Find target currency rate
            String search = "\"" + to + "\":";
            int index = json.indexOf(search);

            if (index == -1) {
                throw new RuntimeException("Currency not found!");
            }

            index += search.length();
            StringBuilder rateStr = new StringBuilder();

            while (index < json.length()) {
                char c = json.charAt(index);
                if ((c >= '0' && c <= '9') || c == '.') {
                    rateStr.append(c);
                } else {
                    break;
                }
                index++;
            }

            double rate = Double.parseDouble(rateStr.toString());
            double convertedAmount = amount * rate;

            System.out.println("Converted Amount: " + convertedAmount + " " + to);

        } catch (Exception e) {
            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }
}

