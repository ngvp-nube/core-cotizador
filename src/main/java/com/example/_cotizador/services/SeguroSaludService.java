package com.example._cotizador.services;

import com.example._cotizador.dto.SeguroSaludDto;
import com.example._cotizador.entity.SeguroSalud;
import java.util.List;
import java.util.Map;

public interface SeguroSaludService {

    Map<String, Object> cargaMasiva(List<SeguroSaludDto> segurosDto);

    List<SeguroSalud> getAllSeguros();

    Map<String, Object> getSegurosPaginados(int page, int size);

    SeguroSalud saveSeguro(SeguroSaludDto seguroDto);

    void deleteSeguro(Long id);

    SeguroSalud cambiarVisibilidad(Long id);

    SeguroSalud updateSeguro(Long id, SeguroSaludDto seguroDto);
}
