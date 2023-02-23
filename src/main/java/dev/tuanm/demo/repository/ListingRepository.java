package dev.tuanm.demo.repository;

import dev.tuanm.demo.dao.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Item, Long> {
}
