package budhioct.dev.dto;

import budhioct.dev.entity.Transaction;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class TransactionDTO {

    @Setter
    @Getter
    @Builder
    public static class TransactionResponse {
        private Long id;
        private Long amountGas;
        private Double totalPrice;
        private String transactionStatus; // dari LogStock.status
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime transactionDate;
    }

    public static TransactionResponse toTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .amountGas(transaction.getAmountGas())
                .totalPrice(transaction.getTotalPrice())
                .transactionStatus(transaction.getSubAgent().getStock().getLogStocks().getLast().getStatus())
                .transactionDate(transaction.getDate())
                .build();
    }


}
