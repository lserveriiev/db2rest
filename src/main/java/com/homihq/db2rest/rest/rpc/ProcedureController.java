package com.homihq.db2rest.rest.rpc;

import com.homihq.db2rest.jdbc.service.JdbcProcedureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("procedure")
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "db2rest.datasource", name = "type", havingValue = "jdbc")
public class ProcedureController {

    private final JdbcProcedureService jdbcProcedureService;

    @PostMapping("/{procName}")
    public ResponseEntity<Map<String, Object>> execute(@PathVariable String procName,
                                                       @RequestBody Map<String,Object> inParams) {
        log.debug("Execute stored procedure {} with IN params {}", procName, inParams.entrySet());
        return ResponseEntity.ok(jdbcProcedureService.execute(procName, inParams));
    }
}
