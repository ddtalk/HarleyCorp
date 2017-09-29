package com.alibaba.dingtalk.openapi.springbootdemo.repository;

import com.alibaba.dingtalk.openapi.springbootdemo.repository.model.CorpJsTicket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorpJsTicketRedisRepository extends CrudRepository<CorpJsTicket, String> {
}
