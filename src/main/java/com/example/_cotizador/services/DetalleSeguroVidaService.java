package com.example._cotizador.services;

import com.example._cotizador.dto.DetalleSeguroVidaDto;
import com.example._cotizador.entity.DetalleSeguroVida;
import com.example._cotizador.repository.DetalleSeguroVidaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface DetalleSeguroVidaService {

    /**
     * Aquí solo está el método para guardar los contratos PDF de seguros de vida
     */
    DetalleSeguroVida saveDetalleSeguroVida(DetalleSeguroVidaDto dto);

    Optional<DetalleSeguroVida> getDetalleSeguroVidaById(Long id);

    Optional<DetalleSeguroVida> getByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<DetalleSeguroVida> getAllDetalleSegurosVida();

    void deleteDetalleSeguroVida(Long id);

    @Transactional
    List<DetalleSeguroVida> saveDetalleSegurosVidaMasivo(List<DetalleSeguroVidaDto> dtos);
}

@Service
class DetalleSeguroVidaServiceImpl implements DetalleSeguroVidaService {

    private final DetalleSeguroVidaRepository detalleSeguroVidaRepository;

    public DetalleSeguroVidaServiceImpl(DetalleSeguroVidaRepository detalleSeguroVidaRepository) {
        this.detalleSeguroVidaRepository = detalleSeguroVidaRepository;
    }

    @Override
    public DetalleSeguroVida saveDetalleSeguroVida(DetalleSeguroVidaDto dto) {
        try {
            System.out.println("Guardando DetalleSeguroVida con segCodigo: " + dto.getSegCodigo());
            
            DetalleSeguroVida nuevo = new DetalleSeguroVida();
            
            actualizarDatosEntidad(dto, nuevo);
            
            System.out.println("Entidad creada, guardando en base de datos...");
            DetalleSeguroVida guardado = detalleSeguroVidaRepository.save(nuevo);
            System.out.println("Guardado exitoso con ID: " + guardado.getId());
            
            return guardado;
        } catch (Exception e) {
            System.err.println("Error en saveDetalleSeguroVida: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Optional<DetalleSeguroVida> getDetalleSeguroVidaById(Long id) {
        return detalleSeguroVidaRepository.findById(id);
    }

    @Override
    public Optional<DetalleSeguroVida> getByCodigo(String codigo) {
        return detalleSeguroVidaRepository.findBySegCodigo(codigo);
    }

    @Override
    public boolean existsByCodigo(String codigo) {
        return detalleSeguroVidaRepository.existsBySegCodigo(codigo);
    }

    @Override
    public List<DetalleSeguroVida> getAllDetalleSegurosVida() {
        return detalleSeguroVidaRepository.findAll();
    }

    @Override
    public void deleteDetalleSeguroVida(Long id) {
        if (!detalleSeguroVidaRepository.existsById(id)) {
            throw new RuntimeException("Detalle de seguro de vida no encontrado con ID: " + id);
        }
        detalleSeguroVidaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<DetalleSeguroVida> saveDetalleSegurosVidaMasivo(List<DetalleSeguroVidaDto> dtos) {
        try {
            System.out.println("Iniciando saveDetalleSegurosVidaMasivo con " + dtos.size() + " DTOs");
            
            return dtos.stream()
                    .map(dto -> {
                        System.out.println("Procesando DTO con segCodigo: " + dto.getSegCodigo());
                        return saveDetalleSeguroVida(dto);
                    })
                    .toList();
        } catch (Exception e) {
            System.err.println("Error en saveDetalleSegurosVidaMasivo: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // Método auxiliar para actualizar datos de la entidad
    private void actualizarDatosEntidad(DetalleSeguroVidaDto dto, DetalleSeguroVida entidad) {
        entidad.setSegCodigo(dto.getSegCodigo());
        entidad.setFileName(dto.getFileName());
        entidad.setContentType(dto.getContentType());
        entidad.setPdfData(dto.getPdfData());
    }
}
