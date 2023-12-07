package com.muhammet.service;

import com.muhammet.repository.YetkiRepository;
import com.muhammet.repository.entity.Yetki;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class YetkiService {
    private final YetkiRepository repository;

    public void save(Yetki yetki){
        repository.save(yetki);
    }

    public Iterable<Yetki> findAll(){
        return repository.findAll();
    }

    public List<Yetki> findAllByUserId(String userId){
        return repository.findAllByUserId(userId);
    }



}
