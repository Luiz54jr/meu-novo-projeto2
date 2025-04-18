import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class main {
    private static final String API_KEY = "bf38e1cef793d8fee8ed3a19";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("=== Conversor de Moedas ===");
            System.out.println("1. USD -> BRL");
            System.out.println("2. BRL -> USD");
            System.out.println("3. EUR -> BRL");
            System.out.println("4. BRL -> EUR");
            System.out.println("5. GBP -> BRL");
            System.out.println("6. BRL -> GBP");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();

            if (opcao >= 1 && opcao <= 6) {
                System.out.print("Digite o valor: ");
                double valor = sc.nextDouble();

                String from = "", to = "";
                switch (opcao) {
                    case 1: from = "USD"; to = "BRL"; break;
                    case 2: from = "BRL"; to = "USD"; break;
                    case 3: from = "EUR"; to = "BRL"; break;
                    case 4: from = "BRL"; to = "EUR"; break;
                    case 5: from = "GBP"; to = "BRL"; break;
                    case 6: from = "BRL"; to = "GBP"; break;
                }

                double resultado = converterMoeda(from, to, valor);
                System.out.printf("Resultado: %.2f %s = %.2f %s%n", valor, from, resultado, to);
            }
        } while (opcao != 0);

        System.out.println("Encerrando...");
        sc.close();
    }

    private static double converterMoeda(String from, String to, double valor) {
        try {
            String urlStr = API_URL + API_KEY + "/latest/" + from;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            Scanner scanner = new Scanner(url.openStream());
            StringBuilder inline = new StringBuilder();
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();

            JsonObject json = JsonParser.parseString(inline.toString()).getAsJsonObject();
            JsonObject rates = json.getAsJsonObject("conversion_rates");
            double taxa = rates.get(to).getAsDouble();
            return valor * taxa;
        } catch (Exception e) {
            System.out.println("Erro ao converter: " + e.getMessage());
            return 0.0;
        }
    }
}