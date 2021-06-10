package com.mercadolibre.demo_bootcamp_spring.integration.BatchService;


import com.mercadolibre.demo_bootcamp_spring.controller.BatchController;
import com.mercadolibre.demo_bootcamp_spring.services.Batch.IBatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BatchController.class)
public class IntegrationBatchController {
    @Autowired
    private MockMvc mvc;

    @MockBean
    IBatchService iBa;

/*
    @Test
    void detalle() throws Exception {
        when(cuentaService.findById(1L)).thenReturn(Datos.crearCuenta001());
        this.mvc.perform(get("/api/cuentas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.persona").value("Andres"))
                .andReturn();
        verify(cuentaService,atLeast(1)).findById(1L);
    }

    @Test
    void testTransferir() throws Exception {
        TransaccionDto dto = new TransaccionDto();
        dto.setCuentaDestinoId(2L);
        dto.setCuentaOrigenId(1l);
        dto.setMonto(new BigDecimal("100"));
        dto.setBancoId(1L);
        this.mvc.perform(post("/api/cuentas/transferir")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.mensaje").value("Transferencia realizada con exito"));

    }
*/

}
