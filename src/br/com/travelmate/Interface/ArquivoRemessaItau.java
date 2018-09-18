/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.Interface;

import java.io.IOException;

import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Unidadenegocio;

/**
 *
 * @author Wolverine
 */
public interface ArquivoRemessaItau {
    
    String gerarHeader(Contasreceber conta, int numeroSequencial, Unidadenegocio unidade, String agencia, String contaBanco, String digitoConta)throws IOException;
    String gerarDetalhe(Contasreceber conta, int numeroSequencial, Unidadenegocio unidade, String agencia, String contaBanco, String digitoConta)throws IOException, Exception;
    //String gerarMulta(Contasreceber conta, int numeroSequencial, Unidadenegocio unidade)throws IOException, Exception;
    String gerarTrailer(int numeroSequencial)throws IOException;
}
