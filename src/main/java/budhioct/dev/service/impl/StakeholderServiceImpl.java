package budhioct.dev.service.impl;

import budhioct.dev.dto.StakeholderDTO;
import budhioct.dev.entity.Stakeholder;
import budhioct.dev.repository.StakeholderRepository;
import budhioct.dev.service.StakeholderService;
import budhioct.dev.utilities.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StakeholderServiceImpl implements StakeholderService {

    private final StakeholderRepository stakeholderRepository;
    private final ValidationService validation;

    @Transactional(readOnly = true)
    public List<StakeholderDTO.StakeholderResponse> listStakeholder() {
        List<StakeholderDTO.StakeholderResponse> list = stakeholderRepository.findAll()
                .stream()
                .map(StakeholderDTO::toStakeholderResponse)
                .toList();

        if (list.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "stakeholder not found");
        }

        return list;
    }

    @Transactional(readOnly = true)
    public StakeholderDTO.StakeholderDetailResponse detailStakeholder(Long id) {
        validation.validate(id);
        Stakeholder stakeholder = stakeholderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "stakeholder not found"));
        return StakeholderDTO.toStakeholderDetailResponse(stakeholder);
    }

}
