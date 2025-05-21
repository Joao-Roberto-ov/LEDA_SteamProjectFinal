public class Game {
    private String name;
    private double price;
    private int reviews;
    private String releaseDate;
    private String developer;
    private String publisher;
    private double metacriticScore;
    private int userScore;
    private int positiveReviews;
    private int negativeReviews;
    private double averagePlaytime;
    private double medianPlaytime;
    private int achievements;

    // Construtor
    public Game(String name, double price, int reviews, String releaseDate, 
                String developer, String publisher, double metacriticScore, 
                int userScore, int positiveReviews, int negativeReviews, 
                double averagePlaytime, double medianPlaytime, int achievements) {
        this.name = name;
        this.price = price;
        this.reviews = reviews;
        this.releaseDate = releaseDate;
        this.developer = developer;
        this.publisher = publisher;
        this.metacriticScore = metacriticScore;
        this.userScore = userScore;
        this.positiveReviews = positiveReviews;
        this.negativeReviews = negativeReviews;
        this.averagePlaytime = averagePlaytime;
        this.medianPlaytime = medianPlaytime;
        this.achievements = achievements;
    }

    // Getters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getReviews() { return reviews; }
    public String getReleaseDate() { return releaseDate; }
    public String getDeveloper() { return developer; }
    public String getPublisher() { return publisher; }
    public double getMetacriticScore() { return metacriticScore; }
    public int getUserScore() { return userScore; }
    public int getPositiveReviews() { return positiveReviews; }
    public int getNegativeReviews() { return negativeReviews; }
    public double getAveragePlaytime() { return averagePlaytime; }
    public double getMedianPlaytime() { return medianPlaytime; }
    public int getAchievements() { return achievements; }

    // Método para calcular a taxa de aprovação
    public double getApprovalRate() {
        if (positiveReviews + negativeReviews == 0) return 0;
        return (double) positiveReviews / (positiveReviews + negativeReviews) * 100;
    }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setReviews(int reviews) { this.reviews = reviews; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    public void setDeveloper(String developer) { this.developer = developer; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public void setMetacriticScore(double metacriticScore) { this.metacriticScore = metacriticScore; }
    public void setUserScore(int userScore) { this.userScore = userScore; }
    public void setPositiveReviews(int positiveReviews) { this.positiveReviews = positiveReviews; }
    public void setNegativeReviews(int negativeReviews) { this.negativeReviews = negativeReviews; }
    public void setAveragePlaytime(double averagePlaytime) { this.averagePlaytime = averagePlaytime; }
    public void setMedianPlaytime(double medianPlaytime) { this.medianPlaytime = medianPlaytime; }
    public void setAchievements(int achievements) { this.achievements = achievements; }

    @Override
    public String toString() {
        return String.format("\"%s\",%.2f,%d,\"%s\",\"%s\",\"%s\",%.1f,%d,%d,%d,%.1f,%.1f,%d",
            name.replace("\"", "\"\""),
            price,
            reviews,
            releaseDate.replace("\"", "\"\""),
            developer.replace("\"", "\"\""),
            publisher.replace("\"", "\"\""),
            metacriticScore,
            userScore,
            positiveReviews,
            negativeReviews,
            averagePlaytime,
            medianPlaytime,
            achievements);
    }
} 