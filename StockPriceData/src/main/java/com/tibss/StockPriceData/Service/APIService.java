package com.tibss.StockPriceData.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.tibss.StockPriceData.Models.Stock;
import com.tibss.StockPriceData.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class APIService {

    private final String API_KEY = "5b9899c2a3mshbe8faaddc389f75p1e8601jsn8d93c65bc56e";
    private final String BASE_URL_YahooFinance = "https://yahoo-finance166.p.rapidapi.com/api/";
    private final String BASE_URL_SeekingAlpha = "https://seeking-alpha.p.rapidapi.com/";
    private final String HOST_YahooFinance = "yahoo-finance166.p.rapidapi.com";
    private final String HOST_SeekingAlpha = "seeking-alpha.p.rapidapi.com";
    private final RestTemplate restTemplate;


    @Autowired
    private final StockRepository stockRepository;

    public APIService(RestTemplate restTemplate, StockRepository stockRepository) {
        this.restTemplate = restTemplate;
        this.stockRepository = stockRepository;
    }

    public Stock loadStockStatistics(String ticker) {
        String url = BASE_URL_SeekingAlpha + "symbols/get-profile?symbols=" + ticker;

        // Set the headers for the request
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", API_KEY);
        headers.set("x-rapidapi-host", HOST_SeekingAlpha);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Make the API request
            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
            JsonNode body = response.getBody();

            if(body != null) {
                Stock tempStock = new Stock();

                for (JsonNode stock : body.path("data")) {

                    String symbol = stock.path("id").toString().replace("\"", "");
                    JsonNode attributes = stock.path("attributes");
                    String longDescr = attributes.path("longDesc").toString().replace("\"", "");
                    String sectorName = attributes.path("sectorname").toString().replace("\"", "");
                    String webPage = attributes.path("webpage").toString().replace("\"", "");
                    long marketCap = attributes.path("marketCap").asLong();
                    double dividendYield = attributes.path("divYield") != null ?
                            attributes.path("divYield").asDouble() : 0;
                    double lastPrice = attributes.path("lastDaily").path("last").asDouble();

                    double[] financials = getStockDebtAndCashValues(symbol);

                    double totalDebt = financials[0];
                    double totalCash = financials[1];

                    Stock stockEntity = stockRepository.findBySymbol(symbol).orElse(new Stock());
                    // Check if the stock needs to be updated
                    if (!stockEntity.getSymbol().isEmpty()) {
                        if(stockEntity.getUpdateDate() == null ) stockEntity.setUpdateDate(LocalDateTime.now());

                        LocalDateTime lastUpdated = stockEntity.getUpdateDate();
                        LocalDateTime now = LocalDateTime.now();

                        // Update if the stock hasn't been updated in the last 24 hours
                        long hoursSinceUpdate = ChronoUnit.HOURS.between(lastUpdated, now);
                        if (hoursSinceUpdate < 24) {
                            stockRepository.save(stockEntity);
                            continue;
                        }
                    }

                    stockEntity.setSymbol(symbol);
                    stockEntity.setSectorName(sectorName);
                    stockEntity.setDescription(longDescr);
                    stockEntity.setWebsiteURL(webPage);
                    stockEntity.setPrice(lastPrice);
                    stockEntity.setDividendYield(dividendYield);
                    stockEntity.setMarketCap((double) marketCap);
                    stockEntity.setTotalDebt(totalDebt);
                    stockEntity.setTotalCashEquivalents(totalCash);

                    stockRepository.save(stockEntity);
                    tempStock = stockEntity;
                    System.out.println("New stock saved: " + stockEntity.getSymbol());
                }

                return tempStock;
            }
        } catch (Exception e) {
            System.err.println("Error fetching data: " + e.getMessage());
        }

        // Return null or some default value if not found
        return null;
    }


    //todo colocar tambem os URLS das imagens maybe
    public ArrayList<String> getStockNews(String ticker) {
        ArrayList<String> news = new ArrayList<String>();

        String url = BASE_URL_YahooFinance + "news/v2/list-by-symbol?size=20&number=1&id=" + ticker;
        // Set the headers for the request
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", API_KEY);
        headers.set("x-rapidapi-host", HOST_SeekingAlpha);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Make the API request
            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
            JsonNode body = response.getBody();

            if(body != null) {

                for(JsonNode articles : body.path("data")) {
                    news.add(articles.path("attributes").path("title").toString());
                }

                return news;
            }
        } catch (Exception e) {
            System.err.println("Error fetching data: " + e.getMessage());
        }

        // Return null or some default value if not found
        return null;
    }

    public double[] getStockDebtAndCashValues(String ticker) {
        String url = BASE_URL_SeekingAlpha + "symbols/get-financials?symbol=" + ticker +
                "&target_currency=USD&period_type=annual&statement_type=balance-sheet";

        // Set the headers for the request
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", API_KEY);
        headers.set("x-rapidapi-host", HOST_SeekingAlpha);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Make the API request3
            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
            JsonNode body = response.getBody();

            if(body != null) {
                // Extract totalDebt and totalCash from the JSON structure
                JsonNode totalDebtNode = body.get(9).path("rows").get(6).path("cells").get(10).path("raw_value");
                JsonNode totalCashNode = body.get(0).path("rows").get(2).path("cells").get(9).path("raw_value");

                double totalDebt = totalDebtNode.isMissingNode() ? 0 : totalDebtNode.asDouble();
                double totalCash = totalCashNode.isMissingNode() ? 0 : totalCashNode.asDouble();

                return new double[]{totalDebt, totalCash};
            }
        } catch (Exception e) {
            System.err.println("Error fetching data: " + e.getMessage());
        }

        // Return null or some default value if not found
        return null;
    }
}
