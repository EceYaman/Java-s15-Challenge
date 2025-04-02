package com.library.model;

public enum BookStatus {
    AVAILABLE,
    BORROWED,
    RESERVED;

    public String getDescription() {
        switch (this) {
            case AVAILABLE: return "Kitap mevcut ve ödünç verilebilir.";
            case BORROWED: return "Kitap ödünç alınmış.";
            case RESERVED: return "Kitap rezerve edilmiş.";
            default: return "Bilinmeyen durum.";
        }
    }
}
