package com.example._cotizador.services.builder;

import com.example._cotizador.dto.DetallePlanDto;

import com.example._cotizador.entity.DetallePlan;
import org.springframework.stereotype.Component;


@Component
public class DetallePlanBuilder {
    public DetallePlan build(DetallePlanDto detallePlanDto){
        DetallePlan e = new DetallePlan();
        e.setCodigo(detallePlanDto.getCodigo());
        e.setFile(detallePlanDto.getFile());
        return e;
    }
}
