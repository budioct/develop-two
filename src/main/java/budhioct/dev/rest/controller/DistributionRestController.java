package budhioct.dev.rest.controller;

import budhioct.dev.rest.config.RestResponse;
import budhioct.dev.service.DistributionService;
import budhioct.dev.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/distribution")
public class DistributionRestController {

    @Autowired
    private DistributionService distributionService;

    @PostMapping("/transfer/stock")
    public RestResponse.object<String> distributeStock(
            @RequestParam(name = "sourceStockId") Long sourceStockId,
            @RequestParam(name = "targetStockId") Long targetStockId,
            @RequestParam(name = "amount") Long amount
    ) {
        distributionService.distribute(sourceStockId, targetStockId, amount);
        return RestResponse.object.<String>builder()
                .data("")
                .status_code(Constants.OK)
                .message(Constants.DELETE_MESSAGE)
                .build();
    }

}
