package com.muhammet.repository;

import com.muhammet.repository.entity.Yetki;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableElasticsearchRepositories
public interface YetkiRepository extends ElasticsearchRepository<Yetki,String> {
    List<Yetki> findAllByUserId(String userId);
}
