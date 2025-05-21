import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConversorData {

    public static void main(String[] args) {
        String csvOriginal = "../dados/games.csv";
        String csvTemp = "../dados/games_temp.csv";
        String csvFinal = "../dados/games_formated_release_data.csv";

        try {
            // Verificar se o arquivo de entrada existe
            File arquivoEntrada = new File(csvOriginal);
            if (!arquivoEntrada.exists()) {
                System.err.println("ERRO: O arquivo " + csvOriginal + " não foi encontrado!");
                return;
            }

            // Verificar se o diretório de saída existe
            File diretorioSaida = new File("../dados");
            if (!diretorioSaida.exists()) {
                diretorioSaida.mkdirs();
            }

            System.out.println("Iniciando conversão de datas...");
            alterarCabecalho(csvOriginal, csvTemp);
            System.out.println("Cabecalho alterado com sucesso.");

            corrigirDatas(csvTemp, csvFinal);
            System.out.println("Datas corrigidas com sucesso.");

            // Apagar o arquivo temporário
            File temp = new File(csvTemp);
            if (temp.delete()) {
                System.out.println("Arquivo temporário removido com sucesso.");
            } else {
                System.err.println("AVISO: Não foi possível remover o arquivo temporário.");
            }

            System.out.println("Conversão concluída com sucesso!");

        } catch (IOException e) {
            System.err.println("ERRO ao processar os arquivos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void alterarCabecalho(String entrada, String saida) throws IOException {
        try (BufferedReader leitor = new BufferedReader(new FileReader(entrada));
             BufferedWriter escritor = new BufferedWriter(new FileWriter(saida))) {

            String cabecalho = leitor.readLine();
            if (cabecalho == null) {
                throw new IOException("O arquivo de entrada está vazio!");
            }

            // Troca o nome da coluna
            cabecalho = cabecalho.replace("DiscountDLC count", "Discount,DLC count");
            escritor.write(cabecalho);
            escritor.newLine();

            // Copia os dados sem alterar nada
            String linha;
            while ((linha = leitor.readLine()) != null) {
                escritor.write(linha);
                escritor.newLine();
            }
        }
    }

    private static void corrigirDatas(String entrada, String saida) throws IOException {
        try (BufferedReader leitor = new BufferedReader(new FileReader(entrada));
             BufferedWriter escritor = new BufferedWriter(new FileWriter(saida))) {

            String cabecalho = leitor.readLine();
            if (cabecalho == null) {
                throw new IOException("O arquivo temporário está vazio!");
            }

            escritor.write(cabecalho);
            escritor.newLine();

            // Procura o índice da coluna "Release date"
            String[] colunas = cabecalho.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            int indiceData = -1;
            for (int i = 0; i < colunas.length; i++) {
                if (colunas[i].replace("\"", "").trim().equalsIgnoreCase("Release date")) {
                    indiceData = i;
                    break;
                }
            }

            if (indiceData == -1) {
                throw new IOException("Coluna 'Release date' não encontrada no arquivo!");
            }

            String linha;
            int linhasProcessadas = 0;
            while ((linha = leitor.readLine()) != null) {
                String[] campos = linha.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (campos.length <= indiceData) {
                    System.err.println("AVISO: Linha ignorada por não ter todos os campos necessários: " + linha);
                    continue;
                }

                String dataOriginal = campos[indiceData].replace("\"", "").trim();
                String dataCorrigida = "\"" + formatarData(dataOriginal) + "\"";
                campos[indiceData] = dataCorrigida;

                for (int i = 0; i < campos.length; i++) {
                    escritor.write(campos[i]);
                    if (i < campos.length - 1) escritor.write(",");
                }
                escritor.newLine();
                linhasProcessadas++;
            }

            System.out.println("Total de linhas processadas: " + linhasProcessadas);
        }
    }

    private static String formatarData(String data) {
        if (data == null || data.isEmpty() || data.equalsIgnoreCase("null")) {
            return "";
        }

        SimpleDateFormat[] formatosEntrada = new SimpleDateFormat[] {
            new SimpleDateFormat("MM/yyyy"),
            new SimpleDateFormat("MM/dd/yyyy"),
            new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
        };
        SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy");

        for (SimpleDateFormat formato : formatosEntrada) {
            try {
                Date d = formato.parse(data);
                return formatoSaida.format(d);
            } catch (ParseException e) {
                // Continua tentando outros formatos
            }
        }

        // Se não conseguir converter, retorna a data original
        return data;
    }
}
