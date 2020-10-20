package com.example.sqllearning.repository;

import com.example.sqllearning.vo.data.Geo;
import org.springframework.data.repository.CrudRepository;

public interface GeoRepository extends CrudRepository<Geo, String> {}
