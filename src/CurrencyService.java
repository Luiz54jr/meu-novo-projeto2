public class CurrencyService {
    private ExchangeRateApi api = new ExchangeRateApi();

    public double convert(String from, String to, double amount) {
        double rate = api.getExchangeRate(from, to);
        return amount * rate;
    }
}

