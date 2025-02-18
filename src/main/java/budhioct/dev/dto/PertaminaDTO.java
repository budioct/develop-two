package budhioct.dev.dto;

import budhioct.dev.entity.OfficialAgent;
import budhioct.dev.entity.Pertamina;
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

}
