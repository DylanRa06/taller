package com.krakedev.taller;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TallerApiTests {
    @LocalServerPort
    private int port;
    @Autowired
    private JdbcTemplate jdbc;
    private final HttpClient http = HttpClient.newHttpClient();

    private HttpResponse<String> enviar(String metodo, String ruta, String json) throws Exception {
        HttpRequest.Builder request = HttpRequest.newBuilder(
                URI.create("http://localhost:" + port + "/api/" + ruta));
        request.header("Content-Type", "application/json");
        request.method(metodo, json == null ? HttpRequest.BodyPublishers.noBody()
                : HttpRequest.BodyPublishers.ofString(json));
        return http.send(request.build(), HttpResponse.BodyHandlers.ofString());
    }

    private int id(String json) {
        var matcher = Pattern.compile("\"id\"\\s*:\\s*(\\d+)").matcher(json);
        assertTrue(matcher.find(), json);
        return Integer.parseInt(matcher.group(1));
    }

    @Test
    void cicloCompletoConRelacionesYValidaciones() throws Exception {
        assertEquals(201, enviar("POST", "vehiculos", """
                {"placa":"TEST123","marca":"Toyota","modelo":"Corolla","anio":"2020"}
                """).statusCode());
        assertEquals(200, enviar("GET", "vehiculos/TEST123", null).statusCode());
        assertTrue(enviar("GET", "vehiculos", null).body().contains("TEST123"));
        var mecanico = enviar("POST", "mecanicos", """
                {"nombre":"Carlos","especialidad":"Motor"}
                """);
        assertEquals(201, mecanico.statusCode());
        int mecanicoId = id(mecanico.body());
        var segundo = enviar("POST", "mecanicos", """
                {"nombre":"Juan","especialidad":"Electricista"}
                """);
        assertEquals(201, segundo.statusCode());
        int segundoId = id(segundo.body());
        String datos = """
                {"vehiculo":{"placa":"TEST123"},"mecanicos":[{"id":%d},{"id":%d}],
                "costo":350.75,"descripcion":"Cambio de motor",
                "fechaIngreso":"2026-10-28","fechaEntrega":"2026-10-30T15:30:00"}
                """.formatted(mecanicoId, segundoId);
        var reparacion = enviar("POST", "reparaciones", datos);
        assertEquals(201, reparacion.statusCode(), reparacion.body());
        int reparacionId = jdbc.queryForObject("select id from reparaciones", Integer.class);
        assertTrue(reparacion.body().contains("Carlos"));
        assertTrue(reparacion.body().contains("Juan"));
        assertEquals(2, jdbc.queryForObject("select count(*) from reparacion_mecanicos", Integer.class));
        assertEquals(1, jdbc.queryForObject("select count(*) from reparaciones", Integer.class));
        assertEquals(200, enviar("GET", "reparaciones/" + reparacionId, null).statusCode());
        assertTrue(enviar("GET", "reparaciones", null).body().contains("TEST123"));
        assertEquals(400, enviar("POST", "reparaciones", datos.replace("TEST123", "NOEXISTE")).statusCode());
        assertEquals(400, enviar("POST", "reparaciones", datos.replace("\"id\":" + mecanicoId,
                "\"id\":999999")).statusCode());
        assertEquals(1, jdbc.queryForObject("select count(*) from reparaciones", Integer.class));
        assertEquals(200, enviar("PUT", "vehiculos/TEST123", """
                {"placa":"OTRA","marca":"Toyota","modelo":"Yaris","anio":"2022"}
                """).statusCode());
        assertEquals(404, enviar("GET", "vehiculos/OTRA", null).statusCode());
        assertTrue(enviar("GET", "vehiculos/TEST123", null).body().contains("Yaris"));
        assertEquals(200, enviar("PUT", "mecanicos/" + mecanicoId, """
                {"nombre":"Carlos actualizado","especialidad":"Suspension"}
                """).statusCode());
        assertTrue(enviar("GET", "mecanicos/" + mecanicoId, null).body().contains("Suspension"));
        assertEquals(200, enviar("GET", "mecanicos", null).statusCode());
        assertEquals(200, enviar("PUT", "reparaciones/" + reparacionId,
                datos.replace("350.75", "400.5")).statusCode());
        assertTrue(enviar("GET", "reparaciones/" + reparacionId, null).body().contains("400.5"));
        assertEquals(404, enviar("GET", "mecanicos/999999", null).statusCode());
        assertEquals(404, enviar("PUT", "reparaciones/999999", datos).statusCode());
        assertEquals(404, enviar("DELETE", "vehiculos/NOEXISTE", null).statusCode());
        assertEquals(204, enviar("DELETE", "reparaciones/" + reparacionId, null).statusCode());
        assertEquals(0, jdbc.queryForObject("select count(*) from reparacion_mecanicos", Integer.class));
        assertEquals(404, enviar("GET", "reparaciones/" + reparacionId, null).statusCode());
        assertEquals(204, enviar("DELETE", "mecanicos/" + mecanicoId, null).statusCode());
        assertEquals(204, enviar("DELETE", "mecanicos/" + segundoId, null).statusCode());
        assertEquals(204, enviar("DELETE", "vehiculos/TEST123", null).statusCode());
        assertEquals(404, enviar("GET", "vehiculos/TEST123", null).statusCode());
    }
}
