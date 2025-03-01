package budhioct.dev.rest.controller;

import budhioct.dev.dto.PutanginaDTO;
import budhioct.dev.rest.config.RestResponse;
import budhioct.dev.service.PutanginaService;
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
@RequestMapping(path = "/api/v1/putangina")
public class PutanginaRestController {

    @Autowired
    private PutanginaService putanginaService;

    @GetMapping(
            path = "/fetch",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Map<String, List<PutanginaDTO.PutanginaResponse>>> listPutangina() {
        List<PutanginaDTO.PutanginaResponse> putangina = putanginaService.listPutangina();
        Map<String, List<PutanginaDTO.PutanginaResponse>> response = new HashMap<>();
        response.put("putanginas", putangina);
        return ResponseEntity.ok(response);
    }

    @GetMapping(
            path = "{id}/detail",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.object<PutanginaDTO.PutanginaDetailResponse> detailPutangina(@PathVariable(name = "id") Long id) {
        PutanginaDTO.PutanginaDetailResponse putangina = putanginaService.detailPutangina(id);
        return RestResponse.object.<PutanginaDTO.PutanginaDetailResponse>builder()
                .data(putangina)
                .status_code(Constants.OK)
                .message(Constants.ITEM_EXIST_MESSAGE)
                .build();
    }


}
