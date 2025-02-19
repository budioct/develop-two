package budhioct.dev.dto;

import budhioct.dev.entity.OfficialAgent;
import budhioct.dev.entity.Pertamina;
import budhioct.dev.entity.Stock;
import budhioct.dev.utilities.Ownership;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class PertaminaDTO {

    @Getter
    @Setter
    @Builder
    public static class PertaminaResponse {
        private Long id;
        private String pertaminaGroupAffiliate;
        private String address;
        private String contact;
        private List<String> officialAgentName;
        private Long stock_amount_gas;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime updatedAt;
    }

    @Getter
    @Setter
    @Builder
    public static class PertaminaDetailResponse {
        private Long id;
        private String pertaminaGroupAffiliate;
        private String address;
        private String contact;
        private List<OfficialAgentResponse> officialAgents;
        private StockResponse stock;
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
        private Long stock_amount_gas;
        private String address;
    }

    @Getter
    @Setter
    @Builder
    public static class StockResponse {
        private Long id;
        private Ownership ownership;
        private Long stock_amount;
    }

    public static PertaminaResponse toPertaminaResponse(Pertamina pertamina) {
        return PertaminaResponse.builder()
                .id(pertamina.getId())
                .pertaminaGroupAffiliate(pertamina.getPertaminaGroupAffiliate())
                .address(pertamina.getAddress())
                .contact(pertamina.getContact())
                .createdAt(pertamina.getCreatedAt())
                .updatedAt(pertamina.getUpdatedAt())
                .officialAgentName(pertamina.getOfficialAgents().stream().map(OfficialAgent::getAgentName).toList())
                .stock_amount_gas(pertamina.getStock().getStock_amount())
                .build();
    }

    public static PertaminaDetailResponse toPertaminaDetailResponse(Pertamina pertamina) {
        return PertaminaDetailResponse.builder()
                .id(pertamina.getId())
                .pertaminaGroupAffiliate(pertamina.getPertaminaGroupAffiliate())
                .address(pertamina.getAddress())
                .contact(pertamina.getContact())
                .officialAgents(pertamina.getOfficialAgents().stream().map(PertaminaDTO::toOfficialAgentResponse).toList())
                .stock(toStockResponse(pertamina.getStock()))
                .createdAt(pertamina.getCreatedAt())
                .updatedAt(pertamina.getUpdatedAt())
                .build();
    }

    public static OfficialAgentResponse toOfficialAgentResponse(OfficialAgent officialAgent){
        return OfficialAgentResponse.builder()
                .id(officialAgent.getId())
                .agentName(officialAgent.getAgentName())
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
