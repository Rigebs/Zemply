package com.cruz_sur.api.service;

import com.cruz_sur.api.dto.OpinionDTO;
import com.cruz_sur.api.dto.OpinionSummaryDTO;

import java.util.List;

public interface IOpinionService {
    OpinionDTO saveOpinion(OpinionDTO opinionDTO);
    List<OpinionDTO> getOpinionsByCompania(Long companiaId);
    List<OpinionDTO> getOpinionsByAuthenticatedUser();
    OpinionDTO changeStatus(Long id, Integer status);
    OpinionDTO updateOpinion(Long id, OpinionDTO opinionDTO);

    OpinionSummaryDTO summaryOpinion(Long companiaId);
}
