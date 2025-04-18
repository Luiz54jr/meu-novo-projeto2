import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ExchangeRateApi {

    public double getExchangeRate(String from, String to) {
        try {
            String urlStr = String.format(" https://v6.exchangerate-api.com/v6/bf38e1cef793d8fee8ed3a19/latest/BRL" );
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            in.close();
            con.disconnect();

            System.out.println("Resposta da API: " + response);

            JSONObject json = new JSONObject(response.toString());

            if (!json.has("conversion_rates")) {
                System.out.println(" 'rates' não encontrado na resposta JSON.");
                return 0;
            }

            return json.getJSONObject("conversion_rates").getDouble(to);



        } catch (Exception e) {
            System.out.println("Erro ao obter taxa de câmbio: " + e.getMessage());
            return 0;
        }
    }
}



