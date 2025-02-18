package budhioct.dev.service.impl;

import budhioct.dev.dto.PertaminaDTO;
import budhioct.dev.repository.PertaminaRepository;
import budhioct.dev.service.PertaminaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PertaminaServiceImpl implements PertaminaService {

    private final PertaminaRepository pertaminaRepository;

    @Transactional(readOnly = true)
    public List<PertaminaDTO.PertaminaResponse> listPertamina() {
        List<PertaminaDTO.PertaminaResponse> list = pertaminaRepository.findAll()
                .stream()
                .map(PertaminaDTO::toPertaminaResponse)
                .toList();

        if (list.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pertamina not found");
        }

        return list;
    }

}
