package library.model;

public enum CategoryType {
    FICTION("Kurgu"),
    ROMANCE("Roman"),
    SCIENCE("Bilim"),
    HISTORY("Tarih"),
    FANTASY("Fantastik");

    private final String description;

    CategoryType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
