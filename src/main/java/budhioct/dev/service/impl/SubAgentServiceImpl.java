package budhioct.dev.service.impl;

import budhioct.dev.dto.SubAgentDTO;
import budhioct.dev.entity.SubAgent;
import budhioct.dev.repository.SubAgentRepository;
import budhioct.dev.service.SubAgentService;
import budhioct.dev.utilities.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
        SubAgent subAgent = subAgentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "sub agent not found"));
        return SubAgentDTO.toSubAgentDetailResponse(subAgent);
    }

}
