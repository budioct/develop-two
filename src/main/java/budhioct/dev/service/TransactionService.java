package budhioct.dev.service;

public interface TransactionService {
    void createTransaction(Long subAgentId, Long folksyId, Long amountGas, Double pricePerUnit);
}
