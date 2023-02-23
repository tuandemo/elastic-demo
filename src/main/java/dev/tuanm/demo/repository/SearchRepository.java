package dev.tuanm.demo.repository;

import dev.tuanm.demo.dao.entity.Item;
import dev.tuanm.demo.dao.projection.AggregationItem;
import org.joda.time.LocalDateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SearchRepository extends JpaRepository<Item, Long> {
    @Query(value = " SELECT " +
            "   i.id AS itemId," +
            "   b.name AS bicycleName," +
            "   b.year AS bicycleYear, " +
            "   br.name AS brandName, " +
            "   m.name AS modelName, " +
            "   i.description AS description, " +
            "   i.price AS price, " +
            "   i.listedAt AS listedAt " +
            " FROM Item i " +
            " LEFT JOIN Bicycle b ON i.bicycleId = b.id " +
            " LEFT JOIN Model m ON b.modelId = m.id " +
            " LEFT JOIN Brand br ON m.brandId = br.id " +
            " WHERE " +
            "    (:#{#query} IS NULL OR " +
            "      b.name LIKE %:#{#query}% OR " +
            "      m.name LIKE %:#{#query}% OR " +
            "      br.name LIKE %:#{#query}% OR " +
            "      i.description LIKE %:#{#query}% " +
            "    ) AND " +
            "    (:#{#startPrice} IS NULL OR i.price >= :#{#startPrice}) AND " +
            "    (:#{#endPrice} IS NULL OR i.price <= :#{#endPrice}) AND " +
            "    (:#{#startDate} IS NULL OR i.listedAt >= :#{#startDate}) AND " +
            "    (:#{#endDate} IS NULL OR i.listedAt <= :#{#endDate}) ")
    List<AggregationItem> search(
            Pageable page,
            String query,
            Double startPrice, Double endPrice,
            LocalDateTime startDate, LocalDateTime endDate
    );

    @Query(value = " SELECT " +
            "   i.id AS itemId," +
            "   b.name AS bicycleName," +
            "   b.year AS bicycleYear, " +
            "   br.name AS brandName, " +
            "   m.name AS modelName, " +
            "   i.description AS description, " +
            "   i.price AS price, " +
            "   i.listedAt AS listedAt " +
            " FROM Item i " +
            " LEFT JOIN Bicycle b ON i.bicycleId = b.id " +
            " LEFT JOIN Model m ON b.modelId = m.id " +
            " LEFT JOIN Brand br ON m.brandId = br.id " +
            " WHERE " +
            "   i.deleted = false AND " +
            "   i.id = :#{#itemId} " +
            " ORDER BY i.listedAt DESC ")
    List<AggregationItem> findByItemId(Pageable page, Long itemId);

    default List<AggregationItem> all() {
        return this.search(null, null, null, null, null, null);
    }

    default Optional<AggregationItem> findByItemId(Long itemId) {
        return this.findByItemId(PageRequest.of(0, 1), itemId)
                .stream()
                .findFirst();
    }
}
