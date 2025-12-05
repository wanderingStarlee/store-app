package com.example.store_app;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {
    private List<Product> items = new ArrayList<>();

    public void addProduct(Product product) {
        items.add(product);
    }

    // Исправлено: удаляет только первый найденный товар с таким ID
    public void removeProductById(Long id) {
        Optional<Product> productToRemove = items.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        productToRemove.ifPresent(product -> items.remove(product));
    }

    public List<Product> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }

    public Double getTotalPrice() {
        return items.stream().mapToDouble(Product::getPrice).sum();
    }
}