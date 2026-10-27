package pe.edu.utp.tmspaucars.services.guia.validations;

import pe.edu.utp.tmspaucars.dtos.GuiaRequestDTO;

public interface ValidacionGuia {
    void validar(GuiaRequestDTO dto);
}