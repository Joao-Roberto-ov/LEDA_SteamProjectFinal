public class SortingAlgorithms {
    // Selection Sort
    public static void selectionSort(Game[] games, String attribute) {
        for (int i = 0; i < games.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < games.length; j++) {
                if (compareGames(games[j], games[minIdx], attribute) < 0) {
                    minIdx = j;
                }
            }
            Game temp = games[i];
            games[i] = games[minIdx];
            games[minIdx] = temp;
        }
    }

    // Insertion Sort
    public static void insertionSort(Game[] games, String attribute) {
        for (int i = 1; i < games.length; i++) {
            Game key = games[i];
            int j = i - 1;
            while (j >= 0 && compareGames(games[j], key, attribute) > 0) {
                games[j + 1] = games[j];
                j--;
            }
            games[j + 1] = key;
        }
    }

    // Merge Sort
    public static void mergeSort(Game[] games, String attribute) {
        if (games.length < 2) return;
        
        int mid = games.length / 2;
        Game[] left = new Game[mid];
        Game[] right = new Game[games.length - mid];
        
        System.arraycopy(games, 0, left, 0, mid);
        System.arraycopy(games, mid, right, 0, games.length - mid);
        
        mergeSort(left, attribute);
        mergeSort(right, attribute);
        merge(games, left, right, attribute);
    }

    private static void merge(Game[] games, Game[] left, Game[] right, String attribute) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (compareGames(left[i], right[j], attribute) <= 0) {
                games[k++] = left[i++];
            } else {
                games[k++] = right[j++];
            }
        }
        while (i < left.length) games[k++] = left[i++];
        while (j < right.length) games[k++] = right[j++];
    }

    // Quick Sort otimizado
    public static void quickSort(Game[] games, String attribute) {
        quickSort(games, 0, games.length - 1, attribute);
    }

    private static void quickSort(Game[] games, int low, int high, String attribute) {
        while (low < high) {
            int pi = partition(games, low, high, attribute);
            
            // Sempre processa recursivamente a partição menor
            if (pi - low < high - pi) {
                quickSort(games, low, pi - 1, attribute);
                low = pi + 1;  // Processa a partição maior iterativamente
            } else {
                quickSort(games, pi + 1, high, attribute);
                high = pi - 1;  // Processa a partição menor iterativamente
            }
        }
    }

    private static int partition(Game[] games, int low, int high, String attribute) {
        // Escolhe o pivô como mediana de três elementos
        int mid = low + (high - low) / 2;
        Game pivot = medianOfThree(games, low, mid, high, attribute);
        
        // Coloca o pivô no final
        swap(games, mid, high);
        
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compareGames(games[j], pivot, attribute) <= 0) {
                i++;
                swap(games, i, j);
            }
        }
        
        // Coloca o pivô na posição correta
        swap(games, i + 1, high);
        return i + 1;
    }

    private static Game medianOfThree(Game[] games, int low, int mid, int high, String attribute) {
        // Ordena os três elementos
        if (compareGames(games[low], games[mid], attribute) > 0) {
            swap(games, low, mid);
        }
        if (compareGames(games[low], games[high], attribute) > 0) {
            swap(games, low, high);
        }
        if (compareGames(games[mid], games[high], attribute) > 0) {
            swap(games, mid, high);
        }
        return games[mid];
    }

    private static void swap(Game[] games, int i, int j) {
        Game temp = games[i];
        games[i] = games[j];
        games[j] = temp;
    }

    // Quick Sort com Mediana de 3 (também otimizado)
    public static void quickSortMedian3(Game[] games, String attribute) {
        quickSortMedian3(games, 0, games.length - 1, attribute);
    }

    private static void quickSortMedian3(Game[] games, int low, int high, String attribute) {
        while (low < high) {
            int pi = partitionMedian3(games, low, high, attribute);
            
            // Sempre processa recursivamente a partição menor
            if (pi - low < high - pi) {
                quickSortMedian3(games, low, pi - 1, attribute);
                low = pi + 1;  // Processa a partição maior iterativamente
            } else {
                quickSortMedian3(games, pi + 1, high, attribute);
                high = pi - 1;  // Processa a partição menor iterativamente
            }
        }
    }

    private static int partitionMedian3(Game[] games, int low, int high, String attribute) {
        // Escolhe o pivô como mediana de três elementos
        int mid = low + (high - low) / 2;
        Game pivot = medianOfThree(games, low, mid, high, attribute);
        
        // Coloca o pivô no final
        swap(games, mid, high);
        
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compareGames(games[j], pivot, attribute) <= 0) {
                i++;
                swap(games, i, j);
            }
        }
        
        // Coloca o pivô na posição correta
        swap(games, i + 1, high);
        return i + 1;
    }

    // Counting Sort (para atributos numéricos inteiros)
    public static void countingSort(Game[] games, String attribute) {
        if (!attribute.equals("reviews") && !attribute.equals("userScore") && !attribute.equals("achievements")) {
            throw new IllegalArgumentException("Counting Sort só pode ser usado com atributos numéricos inteiros");
        }

        // Encontrar o valor máximo e mínimo
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        
        for (Game game : games) {
            int value = getValue(game, attribute);
            max = Math.max(max, value);
            min = Math.min(min, value);
        }

        // Ajustar o range para começar do zero
        int range = max - min + 1;
        
        // Se o range for muito grande, usar outro algoritmo
        if (range > games.length * 2) {
            System.out.println("AVISO: Range muito grande para Counting Sort, usando Quick Sort...");
            quickSort(games, attribute);
            return;
        }

        // Criar array de contagem com tamanho otimizado
        int[] count = new int[range];
        Game[] output = new Game[games.length];

        // Contar ocorrências
        for (Game game : games) {
            count[getValue(game, attribute) - min]++;
        }

        // Calcular posições finais
        for (int i = 1; i < range; i++) {
            count[i] += count[i - 1];
        }

        // Construir array ordenado
        for (int i = games.length - 1; i >= 0; i--) {
            int value = getValue(games[i], attribute) - min;
            output[count[value] - 1] = games[i];
            count[value]--;
        }

        // Copiar de volta para o array original
        System.arraycopy(output, 0, games, 0, games.length);
    }

    private static int getValue(Game game, String attribute) {
        switch (attribute) {
            case "reviews":
                return game.getReviews();
            case "userScore":
                return game.getUserScore();
            case "achievements":
                return game.getAchievements();
            default:
                return 0;
        }
    }

    // Heap Sort
    public static void heapSort(Game[] games, String attribute) {
        int n = games.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(games, n, i, attribute);
        }

        for (int i = n - 1; i > 0; i--) {
            Game temp = games[0];
            games[0] = games[i];
            games[i] = temp;

            heapify(games, i, 0, attribute);
        }
    }

    private static void heapify(Game[] games, int n, int i, String attribute) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && compareGames(games[left], games[largest], attribute) > 0) {
            largest = left;
        }

        if (right < n && compareGames(games[right], games[largest], attribute) > 0) {
            largest = right;
        }

        if (largest != i) {
            Game temp = games[i];
            games[i] = games[largest];
            games[largest] = temp;

            heapify(games, n, largest, attribute);
        }
    }

    // Método auxiliar para comparar jogos baseado no atributo
    public static int compareGames(Game a, Game b, String attribute) {
        switch (attribute) {
            case "name":
                return a.getName().compareTo(b.getName());
            case "price":
                return Double.compare(a.getPrice(), b.getPrice());
            case "reviews":
                return Integer.compare(a.getReviews(), b.getReviews());
            case "releaseDate":
                return a.getReleaseDate().compareTo(b.getReleaseDate());
            case "developer":
                return a.getDeveloper().compareTo(b.getDeveloper());
            case "publisher":
                return a.getPublisher().compareTo(b.getPublisher());
            case "metacriticScore":
                return Double.compare(a.getMetacriticScore(), b.getMetacriticScore());
            case "userScore":
                return Integer.compare(a.getUserScore(), b.getUserScore());
            case "achievements":
                return Integer.compare(b.getAchievements(), a.getAchievements()); // Ordem decrescente
            case "approvalRate":
                return Double.compare(a.getApprovalRate(), b.getApprovalRate());
            case "averagePlaytime":
                return Double.compare(a.getAveragePlaytime(), b.getAveragePlaytime());
            case "medianPlaytime":
                return Double.compare(a.getMedianPlaytime(), b.getMedianPlaytime());
            default:
                throw new IllegalArgumentException("Atributo desconhecido: " + attribute);
        }
    }
} 