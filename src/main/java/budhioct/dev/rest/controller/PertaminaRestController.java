package budhioct.dev.rest.controller;

import budhioct.dev.dto.PertaminaDTO;
import budhioct.dev.rest.config.RestResponse;
import budhioct.dev.service.PertaminaService;
import budhioct.dev.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/pertamina")
public class PertaminaRestController {

    @Autowired
    private PertaminaService pertaminaService;

    @GetMapping(
            path = "/fetch",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Map<String, List<PertaminaDTO.PertaminaResponse>>> listPertamina() {
        List<PertaminaDTO.PertaminaResponse> pertamina = pertaminaService.listPertamina();
        Map<String, List<PertaminaDTO.PertaminaResponse>> response = new HashMap<>();
        response.put("pertamina", pertamina);
        return ResponseEntity.ok(response);
    }

    @GetMapping(
            path = "{id}/detail",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.object<PertaminaDTO.PertaminaDetailResponse> detailPertamina(@PathVariable(name = "id") Long id) {
        PertaminaDTO.PertaminaDetailResponse pertamina = pertaminaService.detailPertamina(id);
        return RestResponse.object.<PertaminaDTO.PertaminaDetailResponse>builder()
                .data(pertamina)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();
    }


}
