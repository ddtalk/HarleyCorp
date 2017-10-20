package com.alibaba.dingtalk.openapi.springbootdemo.repository;

import com.alibaba.dingtalk.openapi.springbootdemo.repository.model.CorpAccessToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorpAccessTokenRedisRepository extends CrudRepository<CorpAccessToken, String> {
}
