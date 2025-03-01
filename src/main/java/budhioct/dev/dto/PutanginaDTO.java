package budhioct.dev.dto;

import budhioct.dev.entity.OfficialAgent;
import budhioct.dev.entity.Putangina;
import budhioct.dev.entity.Stock;
import budhioct.dev.utilities.Ownership;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class PutanginaDTO {

    @Getter
    @Setter
    @Builder
    public static class PutanginaResponse {
        private Long id;
        private String putanginaGroupAffiliate;
        private String address;
        private String contact;
        private Long stock_amount_gas;
        private List<String> officialAgentName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime updatedAt;
    }

    @Getter
    @Setter
    @Builder
    public static class PutanginaDetailResponse {
        private Long id;
        private String putanginaGroupAffiliate;
        private String address;
        private String contact;
        private StockResponse stock;
        private List<OfficialAgentResponse> officialAgents;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime updatedAt;
    }

    @Getter
    @Setter
    @Builder
    public static class OfficialAgentResponse {
        private Long id;
        private String agentName;
        private String address;
        private Long stock_owner_id;
        private Long stock_amount_gas;
    }

    @Getter
    @Setter
    @Builder
    public static class StockResponse {
        private Long id;
        private Ownership ownership;
        private Long stock_amount;
    }

    public static PutanginaResponse toPutanginaResponse(Putangina putangina) {
        return PutanginaResponse.builder()
                .id(putangina.getId())
                .putanginaGroupAffiliate(putangina.getPutanginaGroupAffiliate())
                .address(putangina.getAddress())
                .contact(putangina.getContact())
                .createdAt(putangina.getCreatedAt())
                .updatedAt(putangina.getUpdatedAt())
                .officialAgentName(putangina.getOfficialAgents().stream().map(OfficialAgent::getAgentName).toList())
                .stock_amount_gas(putangina.getStock().getStock_amount())
                .build();
    }

    public static PutanginaDetailResponse toPutanginaDetailResponse(Putangina putangina) {
        return PutanginaDetailResponse.builder()
                .id(putangina.getId())
                .putanginaGroupAffiliate(putangina.getPutanginaGroupAffiliate())
                .address(putangina.getAddress())
                .contact(putangina.getContact())
                .officialAgents(putangina.getOfficialAgents().stream().map(PutanginaDTO::toOfficialAgentResponse).toList())
                .stock(toStockResponse(putangina.getStock()))
                .createdAt(putangina.getCreatedAt())
                .updatedAt(putangina.getUpdatedAt())
                .build();
    }

    public static OfficialAgentResponse toOfficialAgentResponse(OfficialAgent officialAgent){
        return OfficialAgentResponse.builder()
                .id(officialAgent.getId())
                .agentName(officialAgent.getAgentName())
                .stock_owner_id(officialAgent.getStock().getId())
                .stock_amount_gas(officialAgent.getStock().getStock_amount())
                .address(officialAgent.getAddress())
                .build();
    }

    public static StockResponse toStockResponse(Stock stock) {
        return StockResponse.builder()
                .id(stock.getId())
                .ownership(stock.getOwnership())
                .stock_amount(stock.getStock_amount())
                .build();
    }

}
