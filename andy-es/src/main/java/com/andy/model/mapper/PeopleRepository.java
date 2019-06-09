package com.andy.model.mapper;

import com.andy.model.People;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PeopleRepository extends ElasticsearchRepository<People, Long> {
    /**
     * @param min
     * @param max
     * @param lastName
     * @param pageable
     * @return
     */
    List<People> getAllByIdBetweenOrLastNameEquals(Long min, Long max, String lastName, Pageable pageable);

    /**
     *
     * @param id
     * @param lastName
     * @return
     */
    List<People> getAllByIdEqualsOrLastNameEquals(Long id, String lastName);
}
