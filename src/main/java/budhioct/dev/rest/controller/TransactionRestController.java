package budhioct.dev.rest.controller;

import budhioct.dev.rest.config.RestResponse;
import budhioct.dev.service.TransactionService;
import budhioct.dev.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionRestController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(
            path = "/proses",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.object<String> createTransaction(
            @RequestParam(name = "sourceSubAgentId") Long sourceSubAgentId,
            @RequestParam(name = "targetFolksyId") Long targetFolksyId,
            @RequestParam(name = "amountGas") Long amountGas,
            @RequestParam(name = "pricePerUnit") Double pricePerUnit
    ) {
        transactionService.createTransaction(sourceSubAgentId, targetFolksyId, amountGas, pricePerUnit);
        return RestResponse.object.<String>builder()
                .data("")
                .status_code(Constants.OK)
                .message(Constants.TRANSACTION_MESSAGE)
                .build();
    }


}
