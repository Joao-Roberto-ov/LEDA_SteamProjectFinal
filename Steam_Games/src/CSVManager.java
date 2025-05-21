import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CSVManager {
    private static final String CSV_SEPARATOR = ",";
    private static final DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
    private static final int MAX_ERRORS_TO_SHOW = 5; // Limite de erros para mostrar
    private static int errorCount = 0;

    public static Game[] readGamesFromCSV(String filePath) throws IOException {
        List<Game> games = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        errorCount = 0; // Resetar contador de erros
        
        // Pular o cabeçalho
        String header = reader.readLine();
        
        String line;
        int linhaAtual = 1;
        while ((line = reader.readLine()) != null) {
            linhaAtual++;
            String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            
            try {
                if (values.length < 19) { // Precisamos de pelo menos 19 colunas
                    if (errorCount < MAX_ERRORS_TO_SHOW) {
                        System.err.println("AVISO: Linha " + linhaAtual + " ignorada - número insuficiente de colunas");
                    }
                    errorCount++;
                    continue;
                }

                // Processar cada campo individualmente
                String appId = values[0].replace("\"", "").trim();
                String name = values[1].replace("\"", "").trim();
                if (name.isEmpty() || name.equals("0")) {
                    continue; // Pular linhas sem nome válido
                }

                String releaseDate = values[2].replace("\"", "").trim();
                double price = parseDouble(values[6]);
                String reviews = values[12].replace("\"", "").trim();
                String developer = values[17].replace("\"", "").trim();
                String publisher = values[18].replace("\"", "").trim();
                
                // Extrair scores do Metacritic
                double metacriticScore = 0;
                int userScore = 0;
                int achievements = parseInt(values[19]);
                if (values.length > 19 && values[19] != null && !values[19].isEmpty()) {
                    String metacriticUrl = values[19].replace("\"", "").trim();
                    if (metacriticUrl.contains("metacritic.com")) {
                        // Tentar extrair o score do URL
                        String[] urlParts = metacriticUrl.split("/");
                        if (urlParts.length > 0) {
                            String lastPart = urlParts[urlParts.length - 1];
                            if (lastPart.contains("?")) {
                                lastPart = lastPart.split("\\?")[0];
                            }
                            try {
                                metacriticScore = Double.parseDouble(lastPart);
                            } catch (NumberFormatException e) {
                                metacriticScore = 0;
                            }
                        }
                    }
                }

                // Extrair reviews positivas e negativas
                int positiveReviews = 0;
                int negativeReviews = 0;
                if (reviews.contains("positive")) {
                    String[] reviewParts = reviews.split("positive");
                    if (reviewParts.length > 0) {
                        positiveReviews = parseInt(reviewParts[0].trim());
                    }
                }
                if (reviews.contains("negative")) {
                    String[] reviewParts = reviews.split("negative");
                    if (reviewParts.length > 0) {
                        negativeReviews = parseInt(reviewParts[0].trim());
                    }
                }

                // Validações básicas
                if (price < 0) price = 0;
                if (metacriticScore < 0 || metacriticScore > 100) metacriticScore = 0;
                if (userScore < 0 || userScore > 100) userScore = 0;
                if (positiveReviews < 0) positiveReviews = 0;
                if (negativeReviews < 0) negativeReviews = 0;

                // Se developer ou publisher forem booleanos, definir como vazio
                if (developer.equalsIgnoreCase("true") || developer.equalsIgnoreCase("false")) {
                    developer = "";
                }
                if (publisher.equalsIgnoreCase("true") || publisher.equalsIgnoreCase("false")) {
                    publisher = "";
                }

                // Criar o jogo apenas com os campos relevantes
                Game game = new Game(name, price, positiveReviews + negativeReviews, releaseDate, developer, publisher,
                                   metacriticScore, userScore, positiveReviews, negativeReviews,
                                   0.0, 0.0, achievements);
                games.add(game);
            } catch (Exception e) {
                if (errorCount < MAX_ERRORS_TO_SHOW) {
                    System.err.println("ERRO: Problema ao processar linha " + linhaAtual);
                    System.err.println("Linha: " + line);
                    System.err.println("Erro: " + e.getMessage());
                }
                errorCount++;
            }
        }
        
        reader.close();
        System.out.println("Total de jogos carregados com sucesso: " + games.size());
        if (errorCount > 0) {
            System.out.println("Total de erros encontrados: " + errorCount);
            if (errorCount > MAX_ERRORS_TO_SHOW) {
                System.out.println("(Apenas os primeiros " + MAX_ERRORS_TO_SHOW + " erros foram mostrados)");
            }
        }
        return games.toArray(new Game[0]);
    }

    public static void writeGamesToCSV(Game[] games, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        
        // Escrever cabeçalho apenas com os campos necessários
        writer.write("name,price,reviews,releaseDate,developer,publisher,metacriticScore,userScore,positiveReviews,negativeReviews,achievements");
        writer.newLine();
        
        // Escrever dados apenas com os campos necessários
        for (Game game : games) {
            StringBuilder linha = new StringBuilder();
            linha.append("\"").append(escapeCSV(game.getName())).append("\",");
            linha.append(df.format(game.getPrice())).append(",");
            linha.append(game.getReviews()).append(",");
            linha.append("\"").append(escapeCSV(game.getReleaseDate())).append("\",");
            linha.append("\"").append(escapeCSV(game.getDeveloper() != null ? game.getDeveloper() : "")).append("\",");
            linha.append("\"").append(escapeCSV(game.getPublisher() != null ? game.getPublisher() : "")).append("\",");
            linha.append(df.format(game.getMetacriticScore())).append(",");
            linha.append(game.getUserScore()).append(",");
            linha.append(game.getPositiveReviews()).append(",");
            linha.append(game.getNegativeReviews()).append(",");
            linha.append(game.getAchievements());
            
            writer.write(linha.toString());
            writer.newLine();
        }
        
        writer.close();
    }

    private static double parseDouble(String value) {
        if (value == null || value.isEmpty() || value.equalsIgnoreCase("null")) {
            return 0.0;
        }

        // Remove caracteres não numéricos exceto ponto decimal e sinal de negativo
        value = value.replaceAll("[^0-9.\\-]", "");
        
        // Se após a limpeza a string estiver vazia, retorna 0
        if (value.isEmpty()) {
            return 0.0;
        }

        // Verifica se há mais de um ponto decimal
        int dotCount = 0;
        for (char c : value.toCharArray()) {
            if (c == '.') dotCount++;
        }
        if (dotCount > 1) {
            // Se houver mais de um ponto, mantém apenas o primeiro
            int firstDot = value.indexOf('.');
            value = value.substring(0, firstDot + 1) + value.substring(firstDot + 1).replace(".", "");
        }

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            // Se ainda assim falhar, tenta remover o sinal de negativo se estiver no final
            if (value.endsWith("-")) {
                value = "-" + value.substring(0, value.length() - 1);
                try {
                    return Double.parseDouble(value);
                } catch (NumberFormatException e2) {
                    return 0.0;
                }
            }
            return 0.0;
        }
    }

    private static int parseInt(String value) {
        if (value == null || value.isEmpty() || value.equalsIgnoreCase("null")) {
            return 0;
        }

        // Remove caracteres não numéricos exceto sinal de negativo
        value = value.replaceAll("[^0-9\\-]", "");
        
        // Se após a limpeza a string estiver vazia, retorna 0
        if (value.isEmpty()) {
            return 0;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            // Se falhar, tenta remover o sinal de negativo se estiver no final
            if (value.endsWith("-")) {
                value = "-" + value.substring(0, value.length() - 1);
                try {
                    return Integer.parseInt(value);
                } catch (NumberFormatException e2) {
                    return 0;
                }
            }
            return 0;
        }
    }

    private static String escapeCSV(String value) {
        if (value == null) return "";
        return value.replace("\"", "\"\"");
    }
} 