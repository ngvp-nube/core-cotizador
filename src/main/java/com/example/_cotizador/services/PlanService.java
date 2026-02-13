package com.example._cotizador.services;

import com.example._cotizador.dto.PlanComercialDto;
import com.example._cotizador.entity.PlanComercial;
import java.util.List;
import java.util.Map;

public interface PlanService {

    Map<String, Object> cargaMasiva(List<PlanComercialDto> planesDto);

    List<PlanComercial> getAllPlanes();

    PlanComercial savePlan(PlanComercialDto planDto);
}