package budhioct.dev.service;

import budhioct.dev.dto.SubAgentDTO;

import java.util.List;

public interface SubAgentService {

    List<SubAgentDTO.SubAgentResponse> listSubAgent();
    SubAgentDTO.SubAgentDetailResponse detailSubAgentDetail(Long id);
}
