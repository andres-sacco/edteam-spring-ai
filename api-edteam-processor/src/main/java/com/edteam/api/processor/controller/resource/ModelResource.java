package com.edteam.api.processor.controller.resource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;


@Tag(name = "Model", description = "Operations related with the models of the API")
public interface ModelResource {


    @Operation(description = "Return the available models", responses = {
            @ApiResponse(responseCode = "200", description = "Return the models of the API", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = List.class)))})
    ResponseEntity<List<String>> getModels();
}
