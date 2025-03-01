package budhioct.dev.service;

import budhioct.dev.dto.PutanginaDTO;

import java.util.List;

public interface PutanginaService {

    List<PutanginaDTO.PutanginaResponse> listPutangina();
    PutanginaDTO.PutanginaDetailResponse detailPutangina(Long id);

}
