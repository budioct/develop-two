package budhioct.dev.service.impl;

import budhioct.dev.dto.SubAgentDTO;
import budhioct.dev.dto.SubAgentTransactionProjection;
import budhioct.dev.entity.SubAgent;
import budhioct.dev.repository.SubAgentRepository;
import budhioct.dev.service.SubAgentService;
import budhioct.dev.utilities.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubAgentServiceImpl implements SubAgentService {

    private final SubAgentRepository subAgentRepository;
    private final ValidationService validation;

    @Transactional(readOnly = true)
    public List<SubAgentDTO.SubAgentResponse> listSubAgent() {
        List<SubAgentDTO.SubAgentResponse> list = subAgentRepository.findAll()
                .stream()
                .map(SubAgentDTO::toSubAgentResponse)
                .toList();

        if (list.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "sub agent not found");
        }

        return list;
    }

    @Transactional(readOnly = true)
    public SubAgentDTO.SubAgentDetailResponse detailSubAgentDetail(Long id) {
        validation.validate(id);
        SubAgent subAgent = subAgentRepository.findSubAgentWithTransactions(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "sub agent not found"));
        return SubAgentDTO.toSubAgentDetailResponse(subAgent);
    }

//    @Transactional(readOnly = true)
//    public SubAgentDTO.SubAgentDetailResponse getSubAgentWithTransactions(Long id) {
//        List<SubAgentTransactionProjection> subAgent = subAgentRepository.findSubAgentWithTransactionsRaw(id);
//        if (subAgent.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "sub agent not found");
//        }
//        SubAgentTransactionProjection firstrow = subAgent.getFirst();
//        SubAgentDTO.SubAgentDetailResponse subAgentResponse = SubAgentDTO.SubAgentDetailResponse.builder()
//                .id(firstrow.getSubAgentId())
//                .subAgentName(firstrow.getSubAgentName())
//                .address(firstrow.getAddress())
//                .stock(SubAgentDTO.StockResponse.builder()
//                        .id(firstrow.getStockid())
//                        .ownership(firstrow.getStockOwnership())
//                        .stock_amount(firstrow.getStockStock_amount())
//                        .build())
//                .officialAgent(SubAgentDTO.OfficialAgentResponse.builder()
//                        .id(firstrow.getOfficialAgentId())
//                        .agentName(firstrow.getOfficialAgentAgentName())
//                        .address(firstrow.getOfficialAgentAddress())
//                        .stock_owner_id(firstrow.getOfficialAgentStock_owner_id())
//                        .stock_amount_gas(firstrow.getOfficialAgentStock_amount_gas())
//                        .subholdingGroupAffiliate(firstrow.getOfficialAgentSubholdingGroupAffiliate())
//                        .build())
//                .transaction(new ArrayList<>())
//                .build();
//
//        SubAgentDTO.TransactionResponse data = null;
//        for (SubAgentTransactionProjection row : subAgent) {
//            if (row.getTransactionId() != null) {
//                data = SubAgentDTO.TransactionResponse.builder()
//                        .id(row.getTransactionId())
//                        .amountGas(row.getAmountGas())
//                        .totalPrice(row.getTotalPrice())
//                        .transactionDate(row.getTransactionDate())
//                        .build();
//            }
//            subAgentResponse.getTransaction().add(data);
//        }
//
//        return firstrow;
//    }

    @Transactional(readOnly = true)
    public SubAgentDTO.SubAgentDetailResponse getSubAgentWithTransactions(Long id) {
        List<SubAgentTransactionProjection> subAgentProjections = subAgentRepository.findSubAgentWithTransactionsRaw(id);

        if (subAgentProjections.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "sub agent not found");
        }

        // Ambil data pertama sebagai referensi untuk SubAgent, Stock, dan OfficialAgent
        SubAgentTransactionProjection firstRow = subAgentProjections.getFirst();

        SubAgentDTO.SubAgentDetailResponse subAgentResponse = SubAgentDTO.SubAgentDetailResponse.builder()
                .id(firstRow.getSubAgentId())
                .subAgentName(firstRow.getSubAgentName())
                .address(firstRow.getAddress())
                .stock(SubAgentDTO.StockResponse.builder()
                        .id(firstRow.getStockid())
                        .ownership(firstRow.getStockOwnership())
                        .stock_amount(firstRow.getStockStock_amount())
                        .build())
                .officialAgent(SubAgentDTO.OfficialAgentResponse.builder()
                        .id(firstRow.getOfficialAgentId())
                        .agentName(firstRow.getOfficialAgentAgentName())
                        .address(firstRow.getOfficialAgentAddress())
                        .stock_owner_id(firstRow.getOfficialAgentStock_owner_id())
                        .stock_amount_gas(firstRow.getOfficialAgentStock_amount_gas())
                        .subholdingGroupAffiliate(firstRow.getOfficialAgentSubholdingGroupAffiliate())
                        .build())
                .transaction(new ArrayList<>()) // List transaksi akan ditambahkan di bawah
                .createdAt(firstRow.getCreatedAt())
                .updatedAt(firstRow.getUpdatedAt())
                .build();

        // Loop melalui semua transaksi yang ada dan tambahkan ke dalam list
        List<SubAgentDTO.TransactionResponse> transactions = subAgentProjections.stream()
                .filter(row -> row.getTransactionId() != null) // Hindari transaksi null
                .map(row -> SubAgentDTO.TransactionResponse.builder()
                        .id(row.getTransactionId())
                        .amountGas(row.getAmountGas())
                        .totalPrice(row.getTotalPrice())
                        .transactionDate(row.getTransactionDate())
                        .build())
                .toList();

        subAgentResponse.setTransaction(transactions);

        return subAgentResponse;
    }


}
