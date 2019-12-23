package handson;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class VeloProduct {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private LocalizedString name;
    private LocalizedString description;
    private Set<Category> categories;
    private Set<VeloVariant> veloVariants = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public LocalizedString getName() {
        return name;
    }

    public void setName(LocalizedString name) {
        this.name = name;
    }

    public LocalizedString getDescription() {
        return description;
    }

    public void setDescription(LocalizedString description) {
        this.description = description;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<VeloVariant> getVeloVariants() {
        return veloVariants;
    }

    public void setVeloVariants(Set<VeloVariant> veloVariants) {
        this.veloVariants = veloVariants;
    }
}