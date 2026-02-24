package com.example._cotizador.services;

import com.example._cotizador.dto.SeguroVidaDto;
import com.example._cotizador.entity.SeguroVida;
import java.util.List;
import java.util.Map;

public interface SeguroVidaService {

    Map<String, Object> cargaMasiva(List<SeguroVidaDto> segurosDto);

    List<SeguroVida> getAllSeguros();

    Map<String, Object> getSegurosPaginados(int page, int size);

    SeguroVida saveSeguro(SeguroVidaDto seguroDto);

    void deleteSeguro(Long id);

    SeguroVida cambiarVisibilidad(Long id);

    SeguroVida updateSeguro(Long id, SeguroVidaDto seguroDto);
}
