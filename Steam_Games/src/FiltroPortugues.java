import java.io.*;

public class FiltroPortugues {

    public static void main(String[] args) {
        String csvEntrada = "../dados/games_formated_release_data.csv";
        String csvSaida = "../dados/portuguese_supported_games.csv";

        try {
            // Verificar se o arquivo de entrada existe
            File arquivo = new File(csvEntrada);
            if (!arquivo.exists()) {
                System.err.println("ERRO: O arquivo " + csvEntrada + " não foi encontrado!");
                System.err.println("Verifique se o arquivo games_formatted_release_data.csv existe na pasta dados.");
                return;
            }

            filtrar(csvEntrada, csvSaida);
            System.out.println("Filtragem de jogos em português concluída com sucesso!");
        } catch (IOException e) {
            System.err.println("Falha ao filtrar jogos com suporte a português: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void filtrar(String entrada, String saida) throws IOException {
        BufferedReader leitor = new BufferedReader(new FileReader(entrada));
        BufferedWriter escritor = new BufferedWriter(new FileWriter(saida));

        String cabecalho = leitor.readLine();
        escritor.write(cabecalho);
        escritor.newLine();

        // Encontramos o índice da coluna "Supported languages"
        String[] colunas = cabecalho.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        int indiceIdiomas = -1;
        for (int i = 0; i < colunas.length; i++) {
            if (colunas[i].replace("\"", "").trim().equalsIgnoreCase("Supported languages")) {
                indiceIdiomas = i;
                break;
            }
        }

        if (indiceIdiomas == -1) {
            System.err.println("Coluna 'Supported languages' não encontrada.");
            leitor.close();
            escritor.close();
            return;
        }

        // Verificamos se o jogo dá suporte à língua portuguesa
        String linha;
        int jogosPortugues = 0;
        while ((linha = leitor.readLine()) != null) {
            String[] campos = linha.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            if (campos.length > indiceIdiomas) {
                String idiomas = campos[indiceIdiomas].toLowerCase();
                if (idiomas.contains("portuguese")) {
                    escritor.write(linha);
                    escritor.newLine();
                    jogosPortugues++;
                }
            }
        }

        leitor.close();
        escritor.close();
        System.out.println("Total de jogos com suporte a português encontrados: " + jogosPortugues);
    }
}
