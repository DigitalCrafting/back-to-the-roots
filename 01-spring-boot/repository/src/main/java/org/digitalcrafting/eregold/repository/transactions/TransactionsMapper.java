package org.digitalcrafting.eregold.repository.transactions;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TransactionsMapper {

    @Select("SELECT nextval('transaction_id_seq')")
    Long getNextId();

    void insert(TransactionEntity entity);

    List<TransactionEntity> getByAccountNumber(String accountNumber);
}
