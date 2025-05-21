import java.io.*;

public class FiltroLinux {

    public static void main(String[] args) {
        String arquivoEntrada = "../dados/games_formated_release_data.csv";
        String arquivoSaida = "../dados/games_linux.csv";

        try {
            // Verificar se o arquivo de entrada existe
            File arquivo = new File(arquivoEntrada);
            if (!arquivo.exists()) {
                System.err.println("ERRO: O arquivo " + arquivoEntrada + " não foi encontrado!");
                System.err.println("Verifique se o arquivo games_formatted_release_data.csv existe na pasta dados.");
                return;
            }

            filtrar(arquivoEntrada, arquivoSaida);
            System.out.println("Filtragem de jogos Linux concluída com sucesso!");
        } catch (IOException e) {
            System.err.println("Falha ao tentar filtrar jogos para Linux: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void filtrar(String entrada, String saida) throws IOException {
        BufferedReader leitor = new BufferedReader(new FileReader(entrada));
        BufferedWriter escritor = new BufferedWriter(new FileWriter(saida));

        String cabecalho = leitor.readLine();
        escritor.write(cabecalho);
        escritor.newLine();

        // Procuramos o índice da coluna "Linux"
        String[] colunas = cabecalho.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        int indiceLinux = -1;
        for (int i = 0; i < colunas.length; i++) {
            if (colunas[i].replace("\"", "").trim().equalsIgnoreCase("Linux")) {
                indiceLinux = i;
                break;
            }
        }

        if (indiceLinux == -1) {
            System.err.println("Não foi possível encontrar a coluna Linux.");
            leitor.close();
            escritor.close();
            return;
        }

        // Verificamos quais jogos suportam Linux
        String linha;
        int jogosLinux = 0;
        while ((linha = leitor.readLine()) != null) {
            String[] campos = linha.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            if (campos.length > indiceLinux) {
                String valorLinux = campos[indiceLinux].replace("\"", "").trim().toLowerCase();
                if (valorLinux.equals("true")) {
                    escritor.write(linha);
                    escritor.newLine();
                    jogosLinux++;
                }
            }
        }

        leitor.close();
        escritor.close();
        System.out.println("Total de jogos com suporte a Linux encontrados: " + jogosLinux);
    }
}
