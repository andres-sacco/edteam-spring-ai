package com.edteam.api.processor.controller.resource;

import com.edteam.api.processor.dto.ProcessorHistoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "History", description = "Operations to obtain the history requirements")
public interface ProcessorHistoryResource {

    @Operation(
            description = "Return the history from one user",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Return the models of the API",
                        content =
                                @Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = List.class)))
            })
    ResponseEntity<List<ProcessorHistoryDTO>> getHistory(@PathVariable String userId);
}
