public class ResultadoOrdenacao {
    private final String algoritmo;
    private final String atributo;
    private final String caso;
    private final long tempoExecucao;
    private final long memoriaUtilizada;

    public ResultadoOrdenacao(String algoritmo, String atributo, String caso, long tempoExecucao, long memoriaUtilizada) {
        this.algoritmo = algoritmo;
        this.atributo = atributo;
        this.caso = caso;
        this.tempoExecucao = tempoExecucao;
        this.memoriaUtilizada = memoriaUtilizada;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public String getAtributo() {
        return atributo;
    }

    public String getCaso() {
        return caso;
    }

    @Override
    public String toString() {
        return String.format("%-15s | %-12s | %-9s | %10d | %12d",
            algoritmo,
            atributo,
            caso,
            tempoExecucao,
            memoriaUtilizada / 1024); // Converter bytes para KB
    }
} 