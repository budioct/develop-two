package budhioct.dev.service.impl;

import budhioct.dev.dto.PutanginaDTO;
import budhioct.dev.entity.Putangina;
import budhioct.dev.repository.PutanginaRepository;
import budhioct.dev.service.PutanginaService;
import budhioct.dev.utilities.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PutanginaServiceImpl implements PutanginaService {

    private final PutanginaRepository putanginaRepository;
    private final ValidationService validation;

    @Transactional(readOnly = true)
    public List<PutanginaDTO.PutanginaResponse> listPutangina() {
        List<PutanginaDTO.PutanginaResponse> list = putanginaRepository.findAll()
                .stream()
                .map(PutanginaDTO::toPutanginaResponse)
                .toList();

        if (list.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "putangina not found");
        }

        return list;
    }

    @Transactional(readOnly = true)
    public PutanginaDTO.PutanginaDetailResponse detailPutangina(Long id) {
        validation.validate(id);
        Putangina putangina = putanginaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "putangina not found"));
        return PutanginaDTO.toPutanginaDetailResponse(putangina);
    }

}
