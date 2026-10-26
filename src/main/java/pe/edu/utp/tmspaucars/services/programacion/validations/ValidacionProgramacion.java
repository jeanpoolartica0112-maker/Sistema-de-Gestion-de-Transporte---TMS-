package pe.edu.utp.tmspaucars.services.programacion.validations;
import pe.edu.utp.tmspaucars.dtos.ProgramacionRequestDTO;

public interface ValidacionProgramacion {
    void validar(ProgramacionRequestDTO dto);
}