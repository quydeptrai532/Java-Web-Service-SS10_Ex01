package com.example.ex01.config;

import com.example.ex01.model.entity.CartItem;
import com.example.ex01.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final CartItemRepository repository;

    @Override
    public void run(String... args) throws Exception {
        // Chỉ thêm dữ liệu nếu trong DB chưa có gì (tránh bị trùng lặp khi chạy lại app)
        if (repository.count() == 0) {
            log.info("Found 0 items in DB. Bắt đầu seed dữ liệu mẫu...");

            CartItem item1 = CartItem.builder()
                    .userId("U01")
                    .productId("P123")
                    .quantity(2)
                    .build();

            CartItem item2 = CartItem.builder()
                    .userId("U01")
                    .productId("P007")
                    .quantity(1)
                    .build();

            CartItem item3 = CartItem.builder()
                    .userId("U02")
                    .productId("P123")
                    .quantity(5)
                    .build();

            // Lưu toàn bộ vào Database
            repository.saveAll(List.of(item1, item2, item3));
            log.info("Seed dữ liệu mẫu thành công!");
        } else {
            log.info("Dữ liệu đã tồn tại trong DB. Bỏ qua bước seed.");
        }
    }
}